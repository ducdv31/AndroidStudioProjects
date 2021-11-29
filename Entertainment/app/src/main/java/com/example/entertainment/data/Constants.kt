package com.example.entertainment.data

const val BASE_URL_MOVIE = "https://api.apify.com/v2/key-value-stores/"
const val BASE_URL_BITCOIN = "https://api.coindesk.com/v1/bpi/"
const val BASE_URL_WEATHER = "https://community-open-weather-map.p.rapidapi.com/"
const val HOST_WEATHER = "community-open-weather-map.p.rapidapi.com"
const val KEY_WEATHER = "347343136bmsh7a512efd2cc65f4p127eebjsna0e4064b4a5e"
const val EMPTY = ""
const val PHIM_BO = "Phim bộ"
const val PHIM_LE = "Phim lẻ"
const val PHIM_CHIEU_RAP = "Phim chiếu rạp"
const val PHIM_HOAT_HINH = "Phim hoạt hình"

const val CATEGORY_ITEM_KEY = "categoryItem"

enum class UnitWeather {
    metric,
    imperial
}

enum class LangWeather(fullName: String) {
    en("English "),
    ru("Russian "),
    it("Italian "),
    sp("Spanish "),
    ua("Ukrainian "),
    de("German "),
    pt("Portuguese "),
    ro("Romanian "),
    pl("Polish "),
    fi("Finnish "),
    nl("Dutch "),
    fr("French "),
    bg("Bulgarian "),
    se("Swedish "),
    tr("Turkish "),
    zhtw("Chinese Traditional"),
    zhcn("Chinese Simplified ")
}

enum class Q {
    Hanoi,
    Vietnam
}