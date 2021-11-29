package com.example.entertainment.ui.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.entertainment.data.CATEGORY_ITEM_KEY
import com.example.entertainment.data.EMPTY
import com.example.entertainment.screen.bitcoin.ScreenBitcoin
import com.example.entertainment.screen.bitcoin.viewmodel.BitcoinViewModel
import com.example.entertainment.screen.movie.MovieScreen
import com.example.entertainment.screen.movie.viewmodel.MovieViewModel
import com.example.entertainment.screen.news.ScreenNews
import com.example.entertainment.screen.news.viewmodel.NewsViewModel
import com.example.entertainment.screen.weather.ScreenWeather
import com.example.entertainment.screen.weather.WeatherViewModel
import com.example.entertainment.ui.activity.detailMovie.DetailMovieActivity
import com.example.entertainment.ui.activity.main.theme.EntertainmentTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val movieViewModel: MovieViewModel by viewModels()
    private val bitcoinViewModel: BitcoinViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val newsViewModel: NewsViewModel by viewModels()

    @ExperimentalComposeUiApi
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
            /* weather */
            val scrollStateWeather: ScrollState = rememberScrollState()

            val tabIndex = rememberSaveable { mutableStateOf(0) }
            val listTab = MyTabList.values().toList()
            navController.addOnDestinationChangedListener { controller, destination, arguments ->
                tabIndex.value = MyTabList.valueOf(destination.route ?: EMPTY).ordinal
            }
            /* pager */
            val pagerState = rememberPagerState()
            tabIndex.value = pagerState.currentPage
            EntertainmentTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    topBar = {
                        ActionBarHome(coroutineScope, scaffoldState)
                    },
                    bottomBar = {
                        MyTabRow(
                            tabIndex = tabIndex,
                            listTab = listTab,
                            onClick = { route ->
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(MyTabList.valueOf(route).ordinal)
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    HorizontalPager(
                        count = MyTabList.values().size,
                        modifier = Modifier.padding(innerPadding),
                        state = pagerState
                    ) { page ->
                        when (page) {
                            MyTabList.TabMovie.ordinal -> {
                                MovieScreen(
                                    movieViewModel = movieViewModel,
                                    scrollState = scrollVertical,
                                    onClick = { categoryItem ->
                                        val intent =
                                            Intent(context, DetailMovieActivity::class.java)
                                        intent.putExtra(CATEGORY_ITEM_KEY, categoryItem)
                                        startActivity(intent)
                                    }
                                )
                            }

                            MyTabList.TabNews.ordinal -> {
                                ScreenNews(
                                    newsViewModel = newsViewModel
                                )
                            }

                            MyTabList.TabBitCoin.ordinal -> {
                                ScreenBitcoin(
                                    bitcoinViewModel = bitcoinViewModel,
                                    scrollState = scrollStateBitcoin
                                )
                            }
                            MyTabList.TabWeather.ordinal -> {
                                ScreenWeather(
                                    weatherViewModel = weatherViewModel,
                                    scrollState = scrollStateWeather
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
