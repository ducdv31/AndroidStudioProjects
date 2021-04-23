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
        return day
    }

    fun getStart(): String? {
        return timeConverter?.ConvertFromMinutes(start!!.toInt())
    }

    fun getEnd(): String? {
        return timeConverter?.ConvertFromMinutes(end!!.toInt())
    }
}