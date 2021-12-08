package vn.deviot.imageslide.net

import retrofit2.http.GET
import vn.deviot.imageslide.model.ResponseData

const val BASE_URL = "https://api.imgflip.com/"

interface ApiService {
    @GET("get_memes")
    suspend fun getListImage(): ResponseData?
}