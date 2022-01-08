package vn.deviot.notes.data.api

import okhttp3.RequestBody
import retrofit2.http.*
import vn.deviot.notes.data.common.ResponseData
import vn.deviot.notes.screen.login.model.LoginRp
import vn.deviot.notes.screen.notes.model.ResponseNotes

interface ApiService {

    @POST("login")
    suspend fun logIn(
        @Body body: RequestBody
    ): ResponseData<LoginRp>?

    @GET("notes")
    suspend fun getAllNote(
        @Header("Authorization") Authorization: String
    ): ResponseData<ResponseNotes>?
}