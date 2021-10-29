package com.example.composebeginer

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.composebeginer.ui.theme.ComposeBeginerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeBeginerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colors.background,
                    elevation = 8.dp
                ) {
                    Column {
                        Greeting("Dang Duc")
                        Conversation(
                            message = listOf(
                                Mess("Ok", "Hi"),
                                Mess("2", "Hello"),
                                Mess("3", "Hello"),
                                Mess("4", "Hello"),
                                Mess("5", "Hello")
                            )
                        )
                    }
                }
            }
        }
    }
}

data class Mess(val title: String, val content: String)

@Composable
fun Conversation(message: List<Mess>) {
    LazyColumn {
        items(message) { message ->
            MessageCard(message = message)
        }
    }
}

@Composable
fun MessageCard(message: Mess) {
    val isExpand by remember {
        mutableStateOf(false)
    }
    val surfaceColor: Color by animateColorAsState(
        if (isExpand) {
            MaterialTheme.colors.primary
        } else {
            MaterialTheme.colors.background
        }
    )
    Column(modifier = Modifier.clickable {
        isExpand != isExpand
    }) {
        Surface(
            color = surfaceColor,
            modifier = Modifier
                .animateContentSize()
                .padding(1.dp)
        ) {
            ClickableText(
                text = AnnotatedString(message.toString()),
                onClick = {
                    Log.e(TAG, "MessageCard: ${message.title}")
                }
            )
        }
    }


}

@Preview
@Composable
fun PreviewList() {
    Conversation(
        message = listOf(
            Mess("Ok", "Hi"),
            Mess("@", "Hello"),
            Mess("@", "Hello"),
            Mess("@", "Hello"),
            Mess("@", "Hello")
        )
    )
}

@Composable
fun Greeting(name: String) {
    var backgroundTextColorClicked by remember {
        mutableStateOf(false)
    }
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = name,
            modifier = Modifier
                .padding(start = 4.dp, end = 10.dp)
                .size(30.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Green, CircleShape)
        )
        Spacer(modifier = Modifier.size(5.dp))
        Column() {
            Text(
                text = "I am $name",
                style = MaterialTheme.typography.body1
            )
            Text(
                text = "I am Dang Duc 2",
                style = MaterialTheme.typography.body2,
                color = Color.Red,
                modifier = Modifier
                    .clickable {
                        Log.e(TAG, "Greeting: 2")
                    }
                    .drawBehind {
                        drawCircle(Color.Green)
                    }
                    .background(if (backgroundTextColorClicked) Color.Blue else Color.Gray)
                    .clickable {
                        backgroundTextColorClicked = !backgroundTextColorClicked
                    }
            )
            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 5.dp
            ) {
                Text(text = "I am Dang Duc 3")
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    Greeting(name = "Ok")
}
