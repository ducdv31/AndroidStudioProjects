package com.example.recipe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe.activity.detailrecipe.DetailRecipeActivity
import com.example.recipe.activity.main.FoodViewModel
import com.example.recipe.activity.main.MAX_PAGE_FOOD
import com.example.recipe.activity.main.compose.RecipeCard
import com.example.recipe.data.constant.RECIPE_DATA_KEY
import com.example.recipe.data.model.food.ResultsFood
import com.example.recipe.ui.theme.RecipeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val foodViewModel: FoodViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeTheme {
                // A surface container using the 'background' color from the theme
                val content = LocalContext.current
                val scrollState: LazyListState = rememberLazyListState()

                Scaffold(
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
                                        fontWeight = FontWeight.Bold
                                    )
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
                    RecipeList(
                        foodViewModel.isLoading,
                        foodViewModel.foods,
                        foodViewModel.isLoadMore,
                        onLoadMore = { foodViewModel.loadMoreFood() },
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