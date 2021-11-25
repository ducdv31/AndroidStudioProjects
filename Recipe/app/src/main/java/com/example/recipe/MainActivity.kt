package com.example.recipe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipe.activity.detailrecipe.DetailRecipeActivity
import com.example.recipe.activity.main.FoodViewModel
import com.example.recipe.activity.main.Screen
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

                /**
                 * Bottom tab
                 */
                var tabIndex by remember { mutableStateOf(0) }

                /**
                 * Name pass args
                 */
                val nameRoute = Screen.RecipeScreen.name

                if (scrollState.isScrollInProgress) {
                    keyboardManager?.hide()
                    focusManager.clearFocus()
                }

                /**
                 * Navigation
                 */
                val navController = rememberNavController()

                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopBarMain(scope, scaffoldState)
                    },
                    drawerContent = {
                        DrawerMain()
                    },
                    bottomBar = {
                        TabRow(
                            selectedTabIndex = tabIndex,
                        ) {
                            Tab(selected = true,
                                onClick = {
                                    tabIndex = 0
                                    navController.navigate(Screen.RecipeScreen.name)
                                }) {
                                Image(
                                    painter = painterResource(id = R.drawable.outline_restaurant_menu_white_48dp),
                                    contentDescription = stringResource(id = R.string.recipe),
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(text = stringResource(id = R.string.recipe))
                            }
                            Tab(selected = false,
                                onClick = {
                                    tabIndex = 1
                                    navController.navigate(Screen.MusicScreen.name + "/Duc Dang")
                                }) {
                                Image(
                                    painter = painterResource(id = R.drawable.outline_audiotrack_white_48dp),
                                    contentDescription = stringResource(id = R.string.music),
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(text = stringResource(id = R.string.music))
                            }
                            Tab(selected = false,
                                onClick = {
                                    tabIndex = 2
                                    navController.navigate(Screen.MovieScreen.name)
                                }) {
                                Image(
                                    painter = painterResource(id = R.drawable.outline_movie_white_48dp),
                                    contentDescription = stringResource(id = R.string.movie),
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(text = stringResource(id = R.string.movie))
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RecipeScreen.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(
                            Screen.RecipeScreen.name,
                        ) {
                            RecipeScreen(inputSearch, scrollState, content)
                        }
                        composable(Screen.MusicScreen.name + "/{name}",
                            arguments = listOf(
                                navArgument("name") {
                                    type = NavType.StringType
                                }
                            )
                        ) { entry ->
                            val name = entry.arguments?.getString("name")
                            Text(text = "Music $name")
                        }
                        composable(Screen.MovieScreen.name) {
                            Text(text = "Movie")
                        }
                    }
                }

            }
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    fun RecipeScreen(
        inputSearch: MutableState<String>,
        scrollState: LazyListState,
        content: Context
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

