package vn.deviot.notes.screen.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import vn.deviot.notes.R
import vn.deviot.notes.screen.notes.component.NoteItem
import vn.deviot.notes.screen.notes.dialog.DialogAddNote
import vn.deviot.notes.screen.notes.viewmodel.NoteViewModel

@Composable
fun NoteScreen(
    token: String?,
    noteViewModel: NoteViewModel
) {
    noteViewModel.getNote()
    val showPopUpAddNote = rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    DialogAddNote(showPopUpAddNote,
        onClickOk = { noteAdd ->
            if (noteAdd.isNotEmpty()) {
                val job = CoroutineScope(Dispatchers.Default).async {
                    noteViewModel.addNote(noteAdd)
                }
                CoroutineScope(Dispatchers.Main).launch {
                    job.await()
                    delay(500)
                    noteViewModel.getNote()
                }
            }
        })

    Scaffold(floatingActionButton = {
        Surface(
            elevation = 4.dp,
            shape = CircleShape,
            modifier = Modifier.padding(8.dp)
        ) {
            IconButton(onClick = {
                showPopUpAddNote.value = true
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_note_add_black_48dp),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }) {
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            noteViewModel.listNote.forEach { noteRp ->
                noteRp?.let {
                    NoteItem(noteRp = it)
                }
            }
        }
    }
}