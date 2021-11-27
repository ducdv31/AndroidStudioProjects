package com.example.entertainment.data.repository

import com.example.entertainment.data.movie.ApiServiceMovie
import com.example.entertainment.data.movie.model.ResponseMovie
import javax.inject.Inject

class Repository_Impl
@Inject constructor(
    private val apiServiceMovie: ApiServiceMovie
) : Repository {
    override suspend fun getDataMovie(): ResponseMovie? = apiServiceMovie.getDataMovie()
}