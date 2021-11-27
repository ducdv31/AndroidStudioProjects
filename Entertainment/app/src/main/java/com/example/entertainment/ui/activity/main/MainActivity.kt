package com.example.entertainment.ui.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.entertainment.data.CATEGORY_ITEM_KEY
import com.example.entertainment.data.EMPTY
import com.example.entertainment.screen.bitcoin.ScreenBitcoin
import com.example.entertainment.screen.bitcoin.viewmodel.BitcoinViewModel
import com.example.entertainment.screen.movie.MovieScreen
import com.example.entertainment.screen.movie.viewmodel.MovieViewModel
import com.example.entertainment.ui.activity.detailMovie.DetailMovieActivity
import com.example.entertainment.ui.activity.main.theme.EntertainmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val movieViewModel: MovieViewModel by viewModels()
    private val bitcoinViewModel: BitcoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scrollVertical: ScrollState = rememberScrollState()
            val navController = rememberNavController()
            val context = LocalContext.current
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            /* bitcoin */
            val scrollStateBitcoin: ScrollState = rememberScrollState()

            val tabIndex = rememberSaveable { mutableStateOf(0) }
            val listTab = MyTabList.values().toList()
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                tabIndex.value = MyTabList.valueOf(destination.route ?: EMPTY).ordinal
            }
            EntertainmentTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        ActionBarHome(coroutineScope, scaffoldState)
                    },
                    bottomBar = {
                        MyTabRow(
                            tabIndex,
                            listTab,
                            onClick = {
                                navController.navigate(
                                    it,
                                    navOptions = NavOptions.Builder()
                                        .setLaunchSingleTop(true)
                                        .setRestoreState(true)
                                        .build()
                                )
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        startDestination = MyTabList.TabMovie.name,
                        navController = navController
                    ) {

                        composable(
                            route = MyTabList.TabMovie.name
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
                            route = MyTabList.TabBitCoin.name
                        ) {
                            ScreenBitcoin(
                                innerPadding,
                                bitcoinViewModel = bitcoinViewModel,
                                scrollStateBitcoin
                            )
                        }
                    }
                }
            }
        }
    }
}
