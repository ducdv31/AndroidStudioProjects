package com.example.recipe.activity.main.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TopBarMain(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    TopAppBar(modifier = Modifier.fillMaxWidth()) {
        Box {
            Text(
                text = stringResource(id = R.string.home),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .wrapContentSize(Alignment.Center),
                style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )

            Image(
                painter = painterResource(id = R.drawable.outline_menu_white_24dp),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.CenterStart)
                    .padding(vertical = 8.dp)
                    .clickable {
                        scope.launch {
                            if (scaffoldState.drawerState.isClosed) {
                                scaffoldState.drawerState.open()
                            }
                        }
                    }
            )
        }
    }
}


@Composable
fun DrawerMain() {
    Text(
        text = "Made by Dang Duc",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(16.dp),
        style = TextStyle(
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
    )
}