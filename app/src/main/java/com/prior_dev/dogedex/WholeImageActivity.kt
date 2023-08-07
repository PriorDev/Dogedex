package com.prior_dev.dogedex

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toUri
import coil.load
import com.prior_dev.dogedex.databinding.ActivityWholeImageBinding
import java.io.File

class WholeImageActivity : AppCompatActivity() {

    companion object{
        const val PHOTO_URI_KEY = "photoUriKey"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWholeImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoUri = intent.extras?.getString(PHOTO_URI_KEY)

        if(photoUri.isNullOrEmpty()){
            Toast.makeText(this, R.string.error_on_taking_photo, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        photoUri.toUri().path?.let{
            binding.wholeImage.load(File(it))
        }
    }
}