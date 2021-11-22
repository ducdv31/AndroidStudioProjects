package com.example.recipe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.recipe.activity.detailrecipe.DetailRecipeActivity
import com.example.recipe.activity.main.FoodViewModel
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
                val scrollState: ScrollState = rememberScrollState()

                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(text = "Recipe") })
                    }
                ) {
                    RecipeList(foodViewModel,
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
    foodViewModel: FoodViewModel,
    scrollState: ScrollState = ScrollState(0),
    onClickItem: (ResultsFood) -> Unit
) {
    /*LazyColumn() {
        itemsIndexed(
            items = foodViewModel.foods.value ?: listOf()
        ) { index, item ->
            RecipeCard(resultsFood = item,
                onClick = onClickItem)
        }
    }*/

    Column(
        modifier = Modifier.verticalScroll(
            state = scrollState
        )
    ) {
        if (foodViewModel.isLoading.value) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(8.dp)
                )
            }
        }

        foodViewModel.foods.value?.let { list ->
            list.forEach {
                RecipeCard(
                    resultsFood = it,
                    onClick = onClickItem
                )
            }
        }
    }
}