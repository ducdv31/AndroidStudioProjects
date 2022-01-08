package vn.deviot.notes.screen.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import vn.deviot.notes.screen.login.viewmodel.LoginViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    Scaffold(
        topBar = {
            TopAppBar {
                Text(text = "Login")
            }
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = ScrollState(0)),
        ) {
            TextField(
                value = loginViewModel.username.value,
                onValueChange = {
                    loginViewModel.username.value = it
                },
                label = {
                    Text(text = "Username")
                },
                textStyle = TextStyle(
                    color = Color.Cyan
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color.Cyan
                        )
                    ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = loginViewModel.password.value,
                onValueChange = {
                    loginViewModel.password.value = it
                },
                label = {
                    Text(text = "Password")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                loginViewModel.login()
            }) {
                Text(text = "Login")
            }
        }
    }
}