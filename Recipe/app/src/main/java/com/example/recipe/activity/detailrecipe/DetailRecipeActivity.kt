package com.example.recipe.activity.detailrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe.activity.detailrecipe.ui.theme.RecipeTheme
import com.example.recipe.activity.detailrecipe.viewmodel.DetailRecipeViewModel
import com.example.recipe.data.constant.EMPTY
import com.example.recipe.data.constant.RECIPE_DATA_KEY
import com.example.recipe.data.model.food.ResultsFood
import com.example.recipe.utils.ImageUtils.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailRecipeActivity : ComponentActivity() {

    private val detailRecipeViewModel: DetailRecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current as DetailRecipeActivity
            val recipe: ResultsFood? =
                context.intent?.getParcelableExtra(RECIPE_DATA_KEY)
            val isLoading: MutableState<Boolean> = mutableStateOf(false)
            RecipeTheme {
                recipe?.pk?.let {
                    LoadDetailRecipe(
                        detailRecipeViewModel.getDetailRecipe(
                            id = it,
                            isLoading = isLoading
                        ),
                        isLoading = isLoading
                    )
                }
            }
        }
    }
}

@Composable
fun LoadDetailRecipe(
    detailFood: ResultsFood,
    isLoading: MutableState<Boolean> = mutableStateOf(false)
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(
                state = ScrollState(0)
            )
    ) {
        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 100.dp)
            )
        } else {
            detailFood.featuredImage?.let { url ->
                val image = loadImage(url).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Text(
                text = detailFood.title ?: EMPTY,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 6.dp)
                    .wrapContentWidth(align = CenterHorizontally),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "Rating ${detailFood.rating}",
                modifier = Modifier.align(CenterHorizontally),
                style = TextStyle(
                    color = Color.Black
                )
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
            ) {
                Text(
                    text = "Ingredients",
                    style = TextStyle(
                        fontSize = 20.sp,
                    ),
                )

                Text(
                    text = "Update: ${detailFood.dateUpdated}",
                    style = TextStyle(
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.align(CenterVertically)
                )

            }

            detailFood.ingredients?.forEach {
                Text(
                    text = "- $it",
                    modifier = Modifier.padding(6.dp),
                    style = TextStyle(
                        color = Color.Black
                    )
                )
            }
        }
    }
}
