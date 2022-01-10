package vn.deviot.notes.screen.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import vn.deviot.notes.R

@Composable
fun DialogError(
    exception: Exception,
    onDismissDialog: () -> Unit
) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(8.dp),
            onDismissRequest = {
                openDialog.value = false
                onDismissDialog.invoke()
            },
            title = {
                Text(
                    text = stringResource(id = R.string.error_request),
                    style = MaterialTheme.textStyleValue.textTitleAlertDialog
                )
            },
            text = {
                Text(
                    text = "${exception.message}",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                )
            },
            confirmButton = {
                Surface(
                    modifier = Modifier.padding(8.dp),
                    color = Color.Red,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .clickable {
                                openDialog.value = false
                                onDismissDialog.invoke()
                            }
                    ) {
                        Text(
                            text = stringResource(id = R.string.ok),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 24.sp
                            )
                        )
                    }
                }
            }, properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = true
            )
        )
    }
}