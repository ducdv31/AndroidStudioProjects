package com.example.recipe.activity.main.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.recipe.R
import com.example.recipe.activity.main.MAX_PAGE_FOOD
import com.example.recipe.data.constant.EMPTY
import com.example.recipe.data.model.food.ResultsFood
import kotlinx.coroutines.Dispatchers

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
            if (isLoading.value && listFood.isNullOrEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
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
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun RecipeCard(
    resultsFood: ResultsFood,
    onClick: (ResultsFood) -> Unit
) {
    Card(
        elevation = 12.dp,
        modifier = Modifier
            .padding(all = 8.dp)
            .clickable {
                onClick.invoke(resultsFood)
            },
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            /**
             * Load with coil
             */
            Image(
                painter = rememberImagePainter(
                    request = ImageRequest.Builder(LocalContext.current)
                        .data(resultsFood.featuredImage)
                        .dispatcher(Dispatchers.IO)
                        .crossfade(true)
                        .error(R.drawable.outline_error_black_48dp)
                        .build(),
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(all = 8.dp)
                    .clip(RoundedCornerShape(10)),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 12.dp)
            ) {
                Text(
                    text = resultsFood.title ?: EMPTY,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(align = Alignment.Start),
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = resultsFood.rating.toString(),
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.End)
                        .align(alignment = Alignment.CenterVertically)
                )
            }
        }
    }
}