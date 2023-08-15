package com.prior_dev.dogedex.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.prior_dev.dogedex.R
import com.prior_dev.dogedex.api.ApiResponseStatus

@Composable
fun ErrorDialog(
    @StringRes messageId: Int,
    onErrorDismiss: () -> Unit
) {
    AlertDialog(
        modifier = Modifier
            .semantics { testTag = "error_dialog" },
        onDismissRequest = onErrorDismiss,
        buttons = {
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                Button(
                    onClick = onErrorDismiss,
                ) {
                    Text(text = stringResource(R.string.try_again))
                }
            }
        },
        title = {
            Text(text = stringResource(R.string.oops_something_happend))
        },
        text = {
            Text(text = stringResource(id = messageId))
        }
    )
}