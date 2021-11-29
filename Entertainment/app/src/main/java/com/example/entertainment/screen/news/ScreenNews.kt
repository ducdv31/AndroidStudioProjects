package com.example.entertainment.screen.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.entertainment.R
import com.example.entertainment.data.EMPTY
import com.example.entertainment.data.news.model.Article
import com.example.entertainment.screen.news.viewmodel.NewsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.Dispatchers

@ExperimentalComposeUiApi
@Composable
fun ScreenNews(
    newsViewModel: NewsViewModel
) {
    val inputSearch: MutableState<String> = remember { mutableStateOf(EMPTY) }
    val scrollState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    if (scrollState.isScrollInProgress) {
        keyboardController?.hide()
        focusManager.clearFocus()
    }

    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = { newsViewModel.getFullDataNewsSearch(inputSearch = inputSearch.value) }) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = inputSearch.value,
                onValueChange = {
                    inputSearch.value = it
                    newsViewModel.getFullDataNewsSearch(it)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                textStyle = TextStyle(
                    fontSize = 16.sp
                ),
                placeholder = {
                    Text(text = "Search")
                },
                colors = TextFieldDefaults.textFieldColors(
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    trailingIconColor = Color.Black
                ),
                leadingIcon = {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.outline_search_black_48dp),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                trailingIcon = {
                    if (inputSearch.value.isNotEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.outline_clear_black_48dp),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clickable {
                                        inputSearch.value = EMPTY
                                        newsViewModel.getFullDataNewsSearch(EMPTY)
                                    }
                            )
                        }
                    }
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
            if (newsViewModel.isLoading.value) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    CircularProgressIndicator(
                        color = Color.Red
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = scrollState
                ) {
                    itemsIndexed(items = newsViewModel.articles) { index, article ->
                        CardNews(article = article)

                        if (newsViewModel.articles.isNotEmpty()
                            && index + 1 == newsViewModel.articles.size
                        ) {
                            newsViewModel.loadMoreDataSNewsSearch()
                        }
                    }
                }

            }
            if (newsViewModel.isLoadMore.value) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(CenterHorizontally)
                ) {
                    CircularProgressIndicator(
                        color = Color.Red
                    )
                }
            }
        }
    }
}

@Composable
fun CardNews(
    article: Article
) {
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    request = ImageRequest.Builder(LocalContext.current)
                        .data(article.urlToImage)
                        .crossfade(true)
                        .dispatcher(Dispatchers.IO)
                        .build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = article.title ?: EMPTY,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Source: ${article.source?.name}",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
            )
        }
    }
}