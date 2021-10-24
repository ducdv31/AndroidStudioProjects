package com.example.composebeginer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
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
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Dang Duc")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "My Image",
            modifier = Modifier
                .padding(start = 4.dp, end = 10.dp)
                .size(30.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.size(5.dp))
        Column() {
            Text(text = "I am Dang Duc")
            Text(text = "I am Dang Duc 2")
            Text(text = "I am Dang Duc 3")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    Greeting(name = "Ok")
}
