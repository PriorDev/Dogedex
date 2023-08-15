package com.prior_dev.dogedex.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.VisualTransformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthField(
    modifier: Modifier,
    label: String,
    text: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    @StringRes errorMessageId: Int?,
    errorTextSemantic: String = "",
    fieldTextSemantic: String = "",
    onValueChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        if(errorMessageId != null){
            Text(
                text = stringResource(id = errorMessageId),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
                    .semantics { testTag = errorTextSemantic }
            )
        }

        OutlinedTextField(
            label = { Text(text = label) },
            value = text,
            onValueChange = onValueChanged,
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .semantics { testTag = fieldTextSemantic },
            isError = errorMessageId != null
        )
    }
}