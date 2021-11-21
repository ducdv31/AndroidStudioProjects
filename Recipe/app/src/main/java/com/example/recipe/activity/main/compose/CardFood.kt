package com.example.recipe.activity.main.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipe.R
import com.example.recipe.data.constant.EMPTY
import com.example.recipe.data.model.food.ResultsFood
import com.example.recipe.utils.ImageUtils.loadImage

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
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            resultsFood.featuredImage?.let { url ->
                val image = loadImage(
                    url = url,
                    R.drawable.ic_launcher_foreground
                ).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(all = 8.dp)
                            .clip(RoundedCornerShape(10)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
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