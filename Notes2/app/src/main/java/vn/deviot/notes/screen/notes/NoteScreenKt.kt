package vn.deviot.notes.screen.notes

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import vn.deviot.notes.utils.EMPTY

@Composable
fun NoteScreen(token: String?) {
    Text(text = token ?: EMPTY)
}