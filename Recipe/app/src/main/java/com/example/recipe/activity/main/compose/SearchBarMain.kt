package com.example.recipe.activity.main.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.recipe.R
import com.example.recipe.data.constant.EMPTY

@ExperimentalComposeUiApi
@Composable
fun SearchBarFood(
    input: MutableState<String>,
    onTextChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    TextField(
        value = input.value,
        onValueChange = onTextChange,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 4.dp)
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        label = {
            Text(text = "Search")
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.outline_search_black_48dp),
                contentDescription = "Search",
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(32.dp),
            )
        },
        trailingIcon = {
            if (input.value.isNotEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.outline_clear_black_48dp),
                    contentDescription = "Clear",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clickable {
                            input.value = EMPTY
                            onTextChange(EMPTY)
                        }
                )
            }
        },
        colors = TextFieldDefaults.textFieldColors(
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            trailingIconColor = Color.Black
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
    )
}