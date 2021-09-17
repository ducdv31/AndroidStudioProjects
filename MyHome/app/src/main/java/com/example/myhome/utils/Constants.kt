package com.example.myhome.utils

object Constants {
    const val BASE_URL = "https://duc-bkhn-k62.firebaseio.com/"
    const val DATE_FORMAT_GSON = "yyyy-MM-dd HH:mm:ss"
    const val DHT11_CHILD = "DHT11"
    const val CURRENT_CHILD = "Current"
    const val HISTORY_CHILD = "History"
    const val T_CHANNEL_ID: String = "Channel temperature"
    const val H_CHANNEL_ID: String = "Channel humidity"
    const val T_NOTI_ID: Int = 1
    const val H_NOTI_ID: Int = 2
    const val ESP_INFOR_CHILD: String = "ESP Information"
    const val I_TEMP_CHILD: String = "Internal_Temperature"
    const val PERMISSION: String = "permission"
    const val ID: String = "ID"
    const val USERNAME: String = "Name"
    const val EMAIL: String = "Email"
    const val URIPHOTO: String = "UriPhoto"
    const val NAME_SENSOR: String = "Name_sensor"
    const val PDF_PATH_STORAGE = "Pdf"
    const val IMAGE_PATH_STORAGE = "Images"
    const val VIDEO_PATH_STORAGE = "Videos"
    /* item type */
    const val ITEM_TYPE_DHT: Int = 1
    /* ********* */

    /* type user */
    const val MASTER_ID = 0
    const val MASTER = "Master"

    const val ROOT_ID = 1
    const val ROOT = "Root"

    const val NORMAL_ID = 2
    const val NORMAL = "Normal"

    const val NONE_ID = 3
    const val NONE = "None"
    /* ********* */

    const val BUNDLE_MANAGE_USER = "set data to bundle in set type user"
    const val EMPTY = ""


    const val BD_NAME_SENSOR = "bundle_name_sensor"
    const val BD_DATA = "bundle_data"

    const val CANT_SET_TYPE_USER = "You haven't permission to change user"
    const val CANT_DELETE_HISTORY_DATA = "You haven't permission to delete data"

    const val LOAD_DATA_ERROR = "Load data error"

    const val PDF_APPLICATION = "application/pdf"

    const val VIDEO_KEY = "VIDEO_KEY"
}