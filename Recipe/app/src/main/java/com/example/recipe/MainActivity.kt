package com.example.recipe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.recipe.activity.detailrecipe.DetailRecipeActivity
import com.example.recipe.activity.main.FoodViewModel
import com.example.recipe.activity.main.compose.DrawerMain
import com.example.recipe.activity.main.compose.RecipeList
import com.example.recipe.activity.main.compose.SearchBarFood
import com.example.recipe.activity.main.compose.TopBarMain
import com.example.recipe.activity.main.convention.FoodEvent
import com.example.recipe.data.constant.EMPTY
import com.example.recipe.data.constant.RECIPE_DATA_KEY
import com.example.recipe.ui.theme.RecipeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val foodViewModel: FoodViewModel by viewModels()

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeTheme {

                val keyboardManager = LocalSoftwareKeyboardController.current
                val focusManager = LocalFocusManager.current
                val content = LocalContext.current
                val scrollState: LazyListState = rememberLazyListState()
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val inputSearch: MutableState<String> = remember { mutableStateOf(EMPTY) }

                if (scrollState.isScrollInProgress) {
                    keyboardManager?.hide()
                    focusManager.clearFocus()
                }

                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopBarMain(scope, scaffoldState)
                    },
                    drawerContent = {
                        DrawerMain()
                    }
                ) {
                    Column {
                        SearchBarFood(
                            inputSearch,
                            onTextChange = {
                                inputSearch.value = it
                                foodViewModel.onTriggerEvent(FoodEvent.EventSearch(it))
                            }
                        )
                        RecipeList(
                            foodViewModel.isLoading,
                            foodViewModel.foods,
                            foodViewModel.isLoadMore,
                            onLoadMore = { foodViewModel.onTriggerEvent(FoodEvent.EventLoadMore) },
                            foodViewModel.page,
                            scrollState = scrollState,
                            onClickItem = {
                                val intent = Intent(content, DetailRecipeActivity::class.java)
                                intent.putExtra(RECIPE_DATA_KEY, it)
                                content.startActivity(intent)
                            })
                    }
                }

            }
        }
    }
}

