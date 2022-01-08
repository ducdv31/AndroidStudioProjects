package vn.deviot.notes.screen.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import vn.deviot.notes.screen.notes.component.NoteItem
import vn.deviot.notes.screen.notes.viewmodel.NoteViewModel
import vn.deviot.notes.utils.EMPTY

@Composable
fun NoteScreen(
    token: String?,
    noteViewModel: NoteViewModel
) {
    noteViewModel.getNote(token?.trim() ?: EMPTY)
    Column {
        noteViewModel.listNote.forEach { noteRp ->
            noteRp?.let {
                NoteItem(noteRp = it)
            }
        }
    }
}