package com.example.myhome.activitymain.model

data class Dht11HistoryValue(val date: String, val listData: MutableList<Dht11MinutesValue>) {
}