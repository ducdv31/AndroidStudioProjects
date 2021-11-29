package com.example.entertainment.screen.weather

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.entertainment.data.EMPTY
import com.example.entertainment.data.LangWeather
import com.example.entertainment.data.Q
import com.example.entertainment.data.UnitWeather
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ScreenWeather(
    weatherViewModel: WeatherViewModel,
    scrollState: ScrollState
) {
    val elevationCard = 8.dp
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = false),
        onRefresh = {
            weatherViewModel.getWeatherData(
                q = Q.Hanoi.name,
                lang = LangWeather.en.name,
                units = UnitWeather.metric.name
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        if (weatherViewModel.isLoading.value) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Red
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(state = scrollState)
                    .fillMaxSize()
            ) {
                Card(
                    elevation = elevationCard,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = weatherViewModel.responseData.value?.name ?: EMPTY,
                            style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "lat: ${weatherViewModel.coord.value?.lat} - lon: ${weatherViewModel.coord.value?.lon}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                Card(
                    elevation = elevationCard,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        weatherViewModel.weather.value?.forEach {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${it.main} (${weatherViewModel.clouds.value?.all})",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(CenterHorizontally),
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 24.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = it.description ?: EMPTY,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(CenterHorizontally),
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }

                Card(
                    elevation = elevationCard,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${weatherViewModel.main.value?.temp} ºC",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 24.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Feel like: ${weatherViewModel.main.value?.feelsLike} ºC",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${weatherViewModel.main.value?.tempMin} - ${weatherViewModel.main.value?.tempMax}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Humidity: ${weatherViewModel.main.value?.humidity}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Pressure: ${weatherViewModel.main.value?.pressure}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Sea level: ${weatherViewModel.main.value?.seaLevel}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                Card(
                    elevation = elevationCard,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(vertical = 12.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Wind",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 24.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${weatherViewModel.wind.value?.speed} m/s",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${weatherViewModel.wind.value?.deg}º",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${weatherViewModel.wind.value?.gust}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}