package com.example.singlechoicejetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.singlechoicejetpack.ui.theme.SingleChoiceJetpackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SingleChoiceJetpackTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    SingleChoiceView()
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewChoice() {
    SingleChoiceView()
}

@Composable
fun SingleChoiceView() {
    var selectedIndex by remember { mutableStateOf(-1) }
    Scaffold(modifier = Modifier.background(color = Color.Cyan)) {
        val listState = rememberLazyListState()
        LazyRow(state = listState) {
            items(getListUser().size) {
                CardData(
                    user = getListUser()[it],
                    isChoose = selectedIndex == it,
                    onClick = {
                        selectedIndex = it
                    }
                )
            }
        }
    }
}

@Composable
fun CardData(user: User, isChoose: Boolean, onClick: () -> Unit) {
    Surface(color = if (isChoose) Color.Red else Color.Blue) {
        Column(modifier = Modifier.selectable(
            selected = isChoose,
            onClick = {
                onClick.invoke()
            }
        )

        ) {
            Text(
                user.name,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                user.age.toString(),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

fun getListUser(): MutableList<User> {
    val list: MutableList<User> = mutableListOf()
    (0 until 20).forEach {
        list.add(User("User $it", it + 10))
    }
    return list
}

data class User(var name: String, var age: Int)
