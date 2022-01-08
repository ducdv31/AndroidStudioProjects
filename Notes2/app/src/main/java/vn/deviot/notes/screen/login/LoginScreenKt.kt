package vn.deviot.notes.screen.login

import android.text.TextUtils
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import vn.deviot.notes.R
import vn.deviot.notes.screen.login.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    onLoginSuccess: (String) -> Unit
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
                Text(text = stringResource(id = R.string.username))
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
                Text(text = stringResource(id = R.string.password))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            loginViewModel.login()
        }) {
            Text(text = stringResource(id = R.string.login))
        }
    }
}