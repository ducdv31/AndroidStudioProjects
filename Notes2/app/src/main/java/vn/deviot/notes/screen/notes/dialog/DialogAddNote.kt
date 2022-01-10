package vn.deviot.notes.screen.notes.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import vn.deviot.notes.R
import vn.deviot.notes.screen.common.textStyleValue
import vn.deviot.notes.utils.EMPTY

@Composable
fun DialogAddNote(
    isShowDialog: MutableState<Boolean>,
    onClickOk: (String) -> Unit,
    onClickCancel: (() -> Unit) = {}
) {
    val note = remember { mutableStateOf(EMPTY) }

    if (isShowDialog.value) {
        AlertDialog(
            shape = RoundedCornerShape(8.dp),
            onDismissRequest = {
                isShowDialog.value = false
            },
            title = {
                Text(
                    text = stringResource(id = R.string.add_note),
                    style = MaterialTheme.textStyleValue.textTitleAlertDialog
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = note.value,
                        onValueChange = {
                            note.value = it
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        maxLines = 2
                    )
                }
            }, buttons = {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(onClick = {
                            isShowDialog.value = false
                            onClickOk.invoke(note.value)
                        }) {
                            Text(text = stringResource(id = R.string.ok))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        OutlinedButton(onClick = {
                            isShowDialog.value = false
                            onClickCancel.invoke()
                        }) {
                            Text(text = stringResource(id = R.string.cancel))
                        }
                    }
                }
            }, properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            )
        )
    }
}