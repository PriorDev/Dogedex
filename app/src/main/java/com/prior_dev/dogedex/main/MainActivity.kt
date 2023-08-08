package com.prior_dev.dogedex.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Rect
import android.graphics.YuvImage
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.prior_dev.dogedex.LABEL_PATH
import com.prior_dev.dogedex.MODEL_PATH
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.WholeImageActivity
import com.prior_dev.dogedex.WholeImageActivity.Companion.PHOTO_URI_KEY
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.api.ApiServiceInterceptor
import com.prior_dev.dogedex.auth.LoginActivity
import com.prior_dev.dogedex.databinding.ActivityMainBinding
import com.prior_dev.dogedex.dogdetail.DogDetailActivity
import com.prior_dev.dogedex.dogdetail.DogDetailActivity.Companion.DOG_KEY
import com.prior_dev.dogedex.dogdetail.DogDetailActivity.Companion.IS_RECOGNITION_KEY
import com.prior_dev.dogedex.doglist.DogListActivity
import com.prior_dev.dogedex.machinglearning.Classifier
import com.prior_dev.dogedex.machinglearning.DogRecognition
import com.prior_dev.dogedex.models.Dog
import com.prior_dev.dogedex.models.User
import com.prior_dev.dogedex.settings.SettingsActivity
import org.tensorflow.lite.support.common.FileUtil
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var classifier: Classifier
    private var isCameraReady = false
    private val viewModel: MainViewModel by viewModels()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                //Open Camara
            } else {
                Toast.makeText(
                    this,
                    R.string.is_necesary_granted_permission_for_use_this_app,
                    Toast.LENGTH_LONG
                )
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = User.getLoggedInUser(this)
        if(user == null){
            openLoginActivity()
            return
        }else{
            ApiServiceInterceptor.setSessionToken(user.authenticationToken)
        }

        binding.settings.setOnClickListener{
            openSettingsActivity()
        }

        binding.dogListFab.setOnClickListener {
            openDogListActivity()
        }


        viewModel.status.observe(this){ status ->
            when(status){
                is ApiResponseStatus.Loading -> binding.loadingWheel.visibility = View.VISIBLE
                is ApiResponseStatus.Success -> binding.loadingWheel.visibility = View.GONE
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.dog.observe(this){ dog ->
            if(dog != null){
                openDogDetailActivity(dog)
            }
        }

        viewModel.dogRecognition.observe(this){
            enableTakePhotoButton(it)
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestCameraPermissions()
        }else{
            setupCamera()
        }
    }

    private fun openDogDetailActivity(dog: Dog) {
        val intent = Intent(this, DogDetailActivity::class.java)
        intent.putExtra(DOG_KEY, dog)
        intent.putExtra(IS_RECOGNITION_KEY, true)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        viewModel.setupClassifier(
            FileUtil.loadMappedFile(this@MainActivity, MODEL_PATH),
            FileUtil.loadLabels(this@MainActivity, LABEL_PATH)
        )
    }

    private fun openDogListActivity() {
        startActivity(Intent(this, DogListActivity::class.java))
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun setupCamera(){
        binding.cameraPreview.post {
            imageCapture = ImageCapture.Builder()
                .setTargetRotation(binding.cameraPreview.display.rotation)
                .build()

            cameraExecutor = Executors.newSingleThreadExecutor()

            starCamera()
            isCameraReady = true
        }
    }

    private fun starCamera(){
        val cameraProviderFeature = ProcessCameraProvider.getInstance(this)

        cameraProviderFeature.addListener({
            val cameraProvider = cameraProviderFeature.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
                viewModel.recognizeImage(imageProxy)
            }

            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalysis
            )
        }, ContextCompat.getMainExecutor(this))
    }

    private fun enableTakePhotoButton(dogRecognition: DogRecognition) {
        if(dogRecognition.confidence > 70.0){
            binding.takePhotoFab.alpha = 1f
            binding.takePhotoFab.setOnClickListener {
                viewModel.getRecognizedDog(dogRecognition.id)
            }
        }else{
            binding.takePhotoFab.alpha = .2f
            binding.takePhotoFab.setOnClickListener(null)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if(::cameraExecutor.isInitialized){
            cameraExecutor.shutdown()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestCameraPermissions(){
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                setupCamera()
            }
            shouldShowRequestPermissionRationale(
                Manifest.permission.CAMERA
            ) -> {
                AlertDialog.Builder(this)
                    .setTitle(R.string.permissions)
                    .setMessage(R.string.please_grant_camera_permissions)
                    .setPositiveButton(android.R.string.ok){ _, _ ->
                        requestPermissionLauncher.launch(
                            Manifest.permission.CAMERA
                        )
                    }
                    .setNegativeButton(android.R.string.cancel){ _, _, ->}
                    .show()
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA
                )
            }
        }
    }
}

