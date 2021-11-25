package com.example.recipe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe.activity.detailrecipe.DetailRecipeActivity
import com.example.recipe.activity.main.FoodViewModel
import com.example.recipe.activity.main.MAX_PAGE_FOOD
import com.example.recipe.activity.main.compose.RecipeCard
import com.example.recipe.activity.main.compose.SearchBarFood
import com.example.recipe.activity.main.convention.FoodEvent
import com.example.recipe.data.constant.EMPTY
import com.example.recipe.data.constant.RECIPE_DATA_KEY
import com.example.recipe.data.model.food.ResultsFood
import com.example.recipe.ui.theme.RecipeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
                        TopAppBar(modifier = Modifier.fillMaxWidth()) {
                            Box {
                                Text(
                                    text = stringResource(id = R.string.home),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .wrapContentSize(Center),
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
                                        .wrapContentSize(CenterStart)
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
                    },
                    drawerContent = {
                        Text(
                            text = "Made by Dang Duc",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally)
                                .padding(16.dp),
                            style = TextStyle(
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp
                            )
                        )
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

@Composable
fun RecipeList(
    isLoading: MutableState<Boolean> = mutableStateOf(false),
    listFood: SnapshotStateList<ResultsFood?>,
    isLoadMore: MutableState<Boolean> = mutableStateOf(false),
    onLoadMore: () -> Unit,
    page: MutableState<Int> = mutableStateOf(1),
    scrollState: LazyListState,
    onClickItem: (ResultsFood) -> Unit
) {
    LazyColumn(
        state = scrollState
    ) {
        item {
            if (isLoading.value && listFood.isNullOrEmpty()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(8.dp)
                    )
                }
            }
        }

        itemsIndexed(
            items = listFood
        ) { index, item ->
            if (item != null) {
                RecipeCard(
                    resultsFood = item,
                    onClick = onClickItem
                )
            }
            if (index + 1 == page.value * MAX_PAGE_FOOD) {
                onLoadMore()
            }
        }

        item {
            if (isLoadMore.value && !isLoading.value) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .padding(8.dp)
                    )
                }
            }
        }
    }

}