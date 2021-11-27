package com.example.entertainment.screen.movie.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.entertainment.data.EMPTY
import com.example.entertainment.data.movie.model.CategoryItem
import kotlinx.coroutines.Dispatchers

@Composable
fun Film(
    title: String,
    listCategoryItem: MutableState<List<CategoryItem>?>,
    onClick: (CategoryItem) -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(top = 4.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(CenterHorizontally)
        )
        ListMovie(
            listCategoryItem = listCategoryItem,
            onClick = onClick
        )
    }
}

@Composable
private fun ListMovie(
    listCategoryItem: MutableState<List<CategoryItem>?>,
    onClick: (CategoryItem) -> Unit = {}
) {
    LazyRow() {
        itemsIndexed(items = listCategoryItem.value ?: listOf()) { index, item ->
            ItemMovieCompose(
                categoryItem = item,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun ItemMovieCompose(
    categoryItem: CategoryItem,
    onClick: (CategoryItem) -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        modifier = Modifier
            .clickable { onClick(categoryItem) }
            .width(250.dp)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = rememberImagePainter(
                    request = ImageRequest.Builder(LocalContext.current)
                        .data(categoryItem.imageUrl)
                        .dispatcher(Dispatchers.IO)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .size(220.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = categoryItem.title ?: EMPTY,
                modifier = Modifier
                    .padding(4.dp)
                    .width(IntrinsicSize.Max)
                    .wrapContentWidth(CenterHorizontally),
                style = TextStyle(
                    color = Color.Black
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}