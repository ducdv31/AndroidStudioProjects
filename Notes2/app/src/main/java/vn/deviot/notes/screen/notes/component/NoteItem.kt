package vn.deviot.notes.screen.notes.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vn.deviot.notes.screen.notes.model.NoteRp
import vn.deviot.notes.utils.EMPTY
import java.util.*


@Composable
fun NoteItem(noteRp: NoteRp) {
    Surface(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(

            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = noteRp.note ?: EMPTY, style = TextStyle(
                    color = Color.Green,
                    fontSize = 32.sp
                )
            )
            Text(text = "${Date(noteRp.date?.toLong() ?: 0)}")
        }
    }

}