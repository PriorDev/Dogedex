package com.prior_dev.dogedex.dogdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.prior_dev.dogedex.composables.ErrorDialog
import com.prior_dev.dogedex.composables.LoadingWheel
import com.prior_dev.dogedex.models.Dog

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DogDetailView(
    detailViewModel: DogDetailViewModel = hiltViewModel(),
    finishActivity: () -> Unit,
) {
    val status = detailViewModel.status.value
    val dog = detailViewModel.dog.value
    val isRecognition = detailViewModel.isRecognition.value
    val probableDogs = detailViewModel.probableDogList.collectAsState().value

    val probableDogsDialogEnable = remember {
        mutableStateOf(false)
    }

    if(status is ApiResponseStatus.Success){
        finishActivity()
    }

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(horizontal = 8.dp, vertical = 16.dp),
        contentAlignment = Alignment.TopCenter
    ){

        DogInfo(dog, isRecognition){
            detailViewModel.getProbableDogs()
            probableDogsDialogEnable.value = true
        }

        Image(
            painter = rememberImagePainter(dog.imageUrl),
            contentDescription = dog.name,
            modifier = Modifier
                .width(270.dp)
                .padding(top = 80.dp)
        )
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .semantics { testTag = "close_details_screen_fab" },
            onClick = {
                if(isRecognition){
                    detailViewModel.addDotToUser()
                }else{
                    finishActivity()
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = stringResource(id = R.string.check)
            )
        }

        if(status is ApiResponseStatus.Loading){
            LoadingWheel()
        }else if(status is ApiResponseStatus.Error){
            ErrorDialog(
                messageId = status.messageId,
                onErrorDismiss = detailViewModel::resetApiResponseStatus
            )
        }


        if(probableDogsDialogEnable.value){
            MostProbableDogsDialog(
                mostProbableDogs = probableDogs,
                onShowMostProbableDogsDialogDismiss = {
                    probableDogsDialogEnable.value = false
                },
                onItemClicked = detailViewModel::updateDog
            )
        }
    }
}

@Composable
fun DogInfo(
    dog: Dog,
    isRecognition: Boolean,
    onProbableDogsButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 180.dp)
    ){
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.dog_index_format, dog.index),
                    fontSize = 32.sp,
                    textAlign = TextAlign.End
                )

                Text(
                    text = dog.name,
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .padding(top = 32.dp)
                )

                LifeIcon()

                Text(
                    text = stringResource(id = R.string.dog_life_expectancy_format, dog.lifeExpectancy),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )

                Text(
                    text = dog.temperament,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 8.dp)
                )

                if(isRecognition){
                    Button(
                        modifier = Modifier.padding(16.dp),
                        onClick = { onProbableDogsButtonClick() }
                    ){
                        Text(
                            text = stringResource(R.string.is_the_dog_wrong),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )
                    }
                }

                Divider(
                    Modifier
                        .padding(8.dp)
                        .padding(bottom = 16.dp)
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    DogDataColumn(
                        stringResource(id = R.string.female),
                        dog.weightFemale,
                        dog.heightFemale,
                        Modifier.weight(1f)
                    )

                    VerticalDivider()

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = dog.type,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(top = 8.dp)
                        )

                        Text(
                            text = stringResource(id = R.string.group),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            color = Color.LightGray,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    VerticalDivider()

                    DogDataColumn(
                        stringResource(id = R.string.male),
                        dog.weightMale,
                        dog.heightMale,
                        Modifier.weight(1f)
                    )
                }

            }
        }
    }
}

@Composable
private fun VerticalDivider() {
    Divider(
        Modifier
            .height(42.dp)
            .width(1.dp)
    )
}

@Composable
private fun DogDataColumn(
    gender: String,
    weight: String,
    height: String,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = gender,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = weight,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.weight),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = height,
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.height),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun LifeIcon() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 80.dp, end = 80.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.secondary
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_hearth_white),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(4.dp)
            )
        }

        Surface(
            shape = RoundedCornerShape(bottomEnd = 2.dp, topEnd = 2.dp),
            modifier = Modifier
                .width(200.dp)
                .height(6.dp),
            color = MaterialTheme.colorScheme.primary
        ) { }
    }
}


