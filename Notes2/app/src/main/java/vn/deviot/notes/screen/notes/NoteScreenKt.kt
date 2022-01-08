package vn.deviot.notes.screen.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import vn.deviot.notes.screen.notes.viewmodel.NoteViewModel
import vn.deviot.notes.utils.EMPTY

@Composable
fun NoteScreen(
    token: String?,
    noteViewModel: NoteViewModel
) {
    Column {
        Text(text = token ?: EMPTY, modifier = Modifier.clickable {
            noteViewModel.getNote(token?.trim() ?: EMPTY)
        })
        noteViewModel.listNote.forEach {
            Text(text = it.toString())
        }
    }
}