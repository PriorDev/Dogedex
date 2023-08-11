package com.prior_dev.dogedex.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.prior_dev.dogedex.dogdetail.DogDetailComposeActivity
import com.prior_dev.dogedex.dogdetail.DogDetailComposeActivity.Companion.DOG_KEY
import com.prior_dev.dogedex.dogdetail.ui.theme.DogedexTheme
import com.prior_dev.dogedex.models.Dog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DogedexTheme {
                DogListView(
                    onItemClick = ::openDogDetailActivity,
                    onNavigationBackClick = ::onNavigationBackClick,
                )
            }
        }
    }

    private fun openDogDetailActivity(dog: Dog){
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DOG_KEY, dog)
        startActivity(intent)
    }

    private fun onNavigationBackClick(){
        finish()
    }
}