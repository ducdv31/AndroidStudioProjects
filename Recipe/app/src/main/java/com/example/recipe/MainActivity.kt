package com.example.recipe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
                RecipeList(foodViewModel,
                    onClickItem = {
                        val intent = Intent(content, DetailRecipeActivity::class.java)
                        intent.putExtra(RECIPE_DATA_KEY, it)
                        content.startActivity(intent)
                    })
            }
        }
    }
}

@Composable
fun RecipeList(
    foodViewModel: FoodViewModel,
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
            state = ScrollState(0)
        )
    ) {
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