package com.example.entertainment.screen.weather

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entertainment.data.*
import com.example.entertainment.data.repository.Repository_Impl
import com.example.entertainment.data.weather.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repositoryImpl: Repository_Impl
) : ViewModel() {

    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val responseData: MutableState<ResponseWeather?> = mutableStateOf(null)
    val coord: MutableState<Coord?> = mutableStateOf(null)
    val weather: MutableState<List<Weather>?> = mutableStateOf(null)
    val main: MutableState<Main?> = mutableStateOf(null)
    val wind: MutableState<Wind?> = mutableStateOf(null)
    val clouds: MutableState<Clouds?> = mutableStateOf(null)
    val sys: MutableState<Sys?> = mutableStateOf(null)

    init {
        getWeatherData(
            q = Q.Hanoi.name,
            lang = LangWeather.en.name,
            units = UnitWeather.metric.name
        )
    }

    fun getWeatherData(
        q: String,
        lat: Float? = null,
        lon: Float? = null,
        lang: String? = null,
        units: String? = null
    ) {
        viewModelScope.launch {
            isLoading.value = true
            val data = repositoryImpl.getWeatherResponse(
                HOST_WEATHER,
                KEY_WEATHER,
                q,
                lat = lat,
                lon = lon,
                lang = lang,
                units = units
            )
            responseData.value = data
            coord.value = data?.coord
            weather.value = data?.weather
            main.value = data?.main
            wind.value = data?.wind
            clouds.value = data?.clouds
            sys.value = data?.sys
            isLoading.value = false
        }
    }
}