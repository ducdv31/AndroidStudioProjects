package com.example.entertainment.ui.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.entertainment.data.CATEGORY_ITEM_KEY
import com.example.entertainment.data.EMPTY
import com.example.entertainment.screen.TotalMainScreen
import com.example.entertainment.screen.movie.MovieScreen
import com.example.entertainment.screen.movie.viewmodel.MovieViewModel
import com.example.entertainment.ui.activity.detailMovie.DetailMovieActivity
import com.example.entertainment.ui.activity.main.theme.EntertainmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scrollVertical: ScrollState = rememberScrollState()
            val navController = rememberNavController()
            val context = LocalContext.current
            EntertainmentTheme {
                Scaffold(
                ) { innerPadding ->
                    NavHost(
                        startDestination = TotalMainScreen.ScreenMovie.name,
                        navController = navController
                    ) {

                        composable(
                            route = TotalMainScreen.ScreenMovie.name
                        ) {
                            MovieScreen(
                                innerPadding = innerPadding,
                                movieViewModel = movieViewModel,
                                scrollState = scrollVertical,
                                onClick = { categoryItem ->
                                    val intent = Intent(context, DetailMovieActivity::class.java)
                                    intent.putExtra(CATEGORY_ITEM_KEY, categoryItem)
                                    startActivity(intent)
                                }
                            )
                        }

                        composable(
                            route = TotalMainScreen.DetailMovieScreen.name
                                    + "/{$CATEGORY_ITEM_KEY}",
                            arguments = listOf(
                                navArgument(CATEGORY_ITEM_KEY) {
                                    type = NavType.StringType
                                },
                            )
                        ) { entry ->
                            val data: String = entry.arguments?.getString("categoryItem") ?: EMPTY
                            Text(text = data)
                        }
                    }
                }
            }
        }
    }
}
