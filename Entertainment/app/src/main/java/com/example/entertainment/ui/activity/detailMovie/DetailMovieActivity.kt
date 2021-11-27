package com.example.entertainment.ui.activity.detailMovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import com.example.entertainment.data.CATEGORY_ITEM_KEY
import com.example.entertainment.data.movie.model.CategoryItem
import com.example.entertainment.screen.movie.composes.DetailMovieScreen
import com.example.entertainment.ui.activity.detailMovie.ui.theme.EntertainmentTheme

class DetailMovieActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val data = intent.getParcelableExtra<CategoryItem>(CATEGORY_ITEM_KEY)
            EntertainmentTheme {
                Scaffold() { innerPadding ->
                    DetailMovieScreen(innerPadding, data)
                }
            }
        }
    }
}
