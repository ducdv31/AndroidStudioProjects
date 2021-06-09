package com.example.retrofit.model

data class Currentcy(
    var success: Boolean,
    var terms: String,
    var privacy: String,
    var timestamp: Double,
    var source: String,
    var quotes: Quote
) {
}