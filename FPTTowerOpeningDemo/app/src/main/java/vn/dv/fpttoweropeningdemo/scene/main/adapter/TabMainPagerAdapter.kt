package vn.dv.fpttoweropeningdemo.scene.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.dv.fpttoweropeningdemo.scene.flightstatus.FlightStatusScreen
import vn.dv.fpttoweropeningdemo.scene.home.HomeScreen
import vn.dv.fpttoweropeningdemo.scene.more.MoreScreen
import vn.dv.fpttoweropeningdemo.scene.mytrip.MyTripScreen
import vn.dv.fpttoweropeningdemo.scene.searchflights.SearchFlightsScreen

class TabMainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = TabMainEnum.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TabMainEnum.Home.ordinal -> HomeScreen()
            TabMainEnum.SearchFlights.ordinal -> SearchFlightsScreen()
            TabMainEnum.FlightStatus.ordinal -> FlightStatusScreen()
            TabMainEnum.MyTrips.ordinal -> MyTripScreen()
            TabMainEnum.More.ordinal -> MoreScreen()
            else -> HomeScreen()
        }
    }
}