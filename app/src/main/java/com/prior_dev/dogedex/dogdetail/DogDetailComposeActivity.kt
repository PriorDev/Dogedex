package com.prior_dev.dogedex.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.dogdetail.ui.theme.DogedexTheme
import com.prior_dev.dogedex.models.Dog

class DogDetailComposeActivity : ComponentActivity() {
    companion object{
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY =  "is_recognition"
    }

    private val viewModel: DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val isRecognition = intent?.extras?.getBoolean(IS_RECOGNITION_KEY, false) ?: false

        if(dog == null){
            Toast.makeText(this, R.string.error_dog_not_found, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setContent {
            val status = viewModel.status

            if(status.value is ApiResponseStatus.Success){
                finish()
            }

            DogedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    DogDetailView(
                        dog = dog,
                        status = status.value,
                        onButtonClick = { onFabClick(isRecognition, dog) },
                        onErrorDismiss = viewModel::resetApiResponseStatus
                    )
                }
            }
        }
    }

    private fun onFabClick(isRecognition: Boolean, dog: Dog){
        if(isRecognition){
            viewModel.addDotToUser(dog.id)
        }else{
            finish()
        }
    }
}
