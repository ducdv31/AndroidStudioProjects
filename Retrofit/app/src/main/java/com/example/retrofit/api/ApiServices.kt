package com.example.retrofit.api

import com.example.retrofit.model.Currentcy
import com.example.retrofit.model.Post
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServices {
    //link api get:  http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1
    // link api post: https://jsonplaceholder.typicode.com/posts

    companion object {
        private val gson: Gson
            get() = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()

        val apiServices: ApiServices
            get() = Retrofit.Builder()
                .baseUrl("http://apilayer.net/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiServices::class.java)

        val apiServicesPost: ApiServices
            get() = Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiServices::class.java)
    }

    /* set get put */

    /* get */
    @GET("api/live")
    fun convertUusToVnd(
        @Query("access_key") access_key: String,
        @Query("currencies") currencies: String,
        @Query("source") source: String,
        @Query("format") format: Int,
    ): Call<Currentcy>

    /* get with Map */
    @GET("api/live")
    fun convertUusToVndMap(@QueryMap value: Map<String, String>): Call<Currentcy>

    /* get with full url */
    @GET("api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1")
    fun convertUusToVnd2(): Call<Currentcy>

    /* POST */
    @POST("posts")
    fun sendPost(@Body post: Post): Call<Post>
}