package com.example.myroom.activitylistmem.model

import com.example.myroom.timeconverter.TimeConverter

class WorkFlow(day: String, start: String, end: String) {
    private var timeConverter: TimeConverter? = null
    private var day: String? = null
    private var start: String? = null
    private var end: String? = null

    init {
        timeConverter = TimeConverter()
        this.day = day
        this.start = start
        this.end = end
    }

    fun getDay(): String? {
        /* 25 : 05 : 2021 */
        /* 25 / 05 / 2021 */
        return day?.replace(":", "/")
    }

    fun getStart(): String? {
        return timeConverter?.ConvertFromMinutes(start!!.toInt())
    }

    fun getEnd(): String? {
        return timeConverter?.ConvertFromMinutes(end!!.toInt())
    }

    fun getTimeLineDay(): String {
        return timeConverter?.ConvertFromMinutes(end!!.toInt().minus(start?.toInt()!!)).toString()
    }

    fun getTimeLineMinutes(): Int {
        return end!!.toInt().minus(start?.toInt()!!)
    }
}