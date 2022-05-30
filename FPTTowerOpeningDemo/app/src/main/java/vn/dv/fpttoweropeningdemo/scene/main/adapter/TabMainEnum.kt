package vn.dv.fpttoweropeningdemo.scene.main.adapter

import androidx.annotation.IdRes
import vn.dv.fpttoweropeningdemo.R

enum class TabMainEnum(@IdRes val idRes: Int) {
    Home(R.id.home_nav),
    SearchFlights(R.id.search_flight_nav),
    FlightStatus(R.id.flight_status_nav),
    MyTrips(R.id.my_trip_nav),
    More(R.id.more_nav)
}