package com.example.entertainment.screen.movie

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.entertainment.data.PHIM_BO
import com.example.entertainment.data.PHIM_CHIEU_RAP
import com.example.entertainment.data.PHIM_HOAT_HINH
import com.example.entertainment.data.PHIM_LE
import com.example.entertainment.data.movie.model.CategoryItem
import com.example.entertainment.screen.movie.composes.Film
import com.example.entertainment.screen.movie.viewmodel.MovieViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun MovieScreen(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    movieViewModel: MovieViewModel,
    scrollState: ScrollState,
    onClick: (CategoryItem) -> Unit
) {
    SwipeRefresh(state = SwipeRefreshState(false),
        onRefresh = { movieViewModel.getListMovie() }) {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(
                    state = scrollState
                )
        ) {
            if (movieViewModel.isLoading.value) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Red
                    )
                }
            } else {
                Film(
                    title = PHIM_BO,
                    listCategoryItem = movieViewModel.phimBo,
                    onClick = onClick
                )
                Film(
                    title = PHIM_LE,
                    listCategoryItem = movieViewModel.phimLe,
                    onClick = onClick
                )
                Film(
                    title = PHIM_CHIEU_RAP,
                    listCategoryItem = movieViewModel.phimChieuRap,
                    onClick = onClick
                )
                Film(
                    title = PHIM_HOAT_HINH,
                    listCategoryItem = movieViewModel.phimHoatHinh,
                    onClick = onClick
                )
            }
        }
    }
}