package com.example.myroom.activitycalendar.model

import java.io.Serializable

/* for transmit data from Adapter and send data to task fragment */
data class TaskUser(val ID: String, val Name:String) : Serializable {
}