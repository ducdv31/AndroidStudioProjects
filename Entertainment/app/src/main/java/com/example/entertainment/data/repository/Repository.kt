package com.example.entertainment.data.repository

import com.example.entertainment.data.movie.model.ResponseMovie

interface Repository {
    suspend fun getDataMovie(): ResponseMovie?
}