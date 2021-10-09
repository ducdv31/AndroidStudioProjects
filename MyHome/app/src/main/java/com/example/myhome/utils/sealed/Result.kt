package com.example.myhome.utils.sealed

sealed class Result {
    data class Success(val t: Any) : Result()
    data class Error(val throwable: Throwable) : Result()
}