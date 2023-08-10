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

class DogListActivity : ComponentActivity() {

    private val viewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val status = viewModel.status

        setContent {
            DogedexTheme {
                val dogList = viewModel.dogList
                DogListView(
                    dogList = dogList.value,
                    onItemClick = ::openDogDetailActivity,
                    onNavigationBackClick = ::onNavigationBackClick,
                    status = status.value,
                    onErrorDismiss = viewModel::resetApiResponseStatus
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