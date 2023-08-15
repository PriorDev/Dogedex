package com.prior_dev.dogedex.doglist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.api.ApiResponseStatus
import com.prior_dev.dogedex.composables.BackNavigationIcon
import com.prior_dev.dogedex.composables.ErrorDialog
import com.prior_dev.dogedex.composables.LoadingWheel
import com.prior_dev.dogedex.models.Dog

const val GRID_SPAN_COUNT = 3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogListView(
    onItemClick: (Dog) -> Unit,
    onNavigationBackClick: () -> Unit,
    viewModel: DogListViewModel = hiltViewModel(),
) {
    val status = viewModel.status.value
    val dogList = viewModel.dogList.value

    Scaffold(
        topBar = {
            LoginToolBar(onNavigationBackClick)
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_SPAN_COUNT),
            modifier = Modifier.padding(innerPadding)
        ){
            items(dogList){ dog ->
                DogGridItem(dog, onItemClick)
            }
        }
    }

    if(status is ApiResponseStatus.Loading){
        LoadingWheel()
    }else if(status is ApiResponseStatus.Error){
        ErrorDialog(messageId = status.messageId, onErrorDismiss = viewModel::resetApiResponseStatus)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginToolBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.my_dog_collection)) },
        navigationIcon = { BackNavigationIcon(onBackClick) }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun DogGridItem(dog: Dog, onDogClick: (Dog) -> Unit) {
    if (dog.inCollection) {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            onClick = { onDogClick(dog) },
            shape = RoundedCornerShape(4.dp)
        ) {
            Image(
                painter = rememberImagePainter(dog.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .background(Color.White)
                    .semantics { testTag = "dog_${dog.name}" },
            )
        }
    } else {
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .height(100.dp)
                .width(100.dp),
            color = MaterialTheme.colorScheme.secondary,
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = dog.index.toString(),
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontSize = 42.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}
