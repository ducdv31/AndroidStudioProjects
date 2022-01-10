package vn.deviot.notes.data.api

import okhttp3.RequestBody
import retrofit2.http.*
import vn.deviot.notes.data.common.ResponseData
import vn.deviot.notes.screen.login.model.LoginRp
import vn.deviot.notes.screen.notes.model.ResponseNotes
import vn.deviot.notes.utils.AUTHORIZATION
import vn.deviot.notes.utils.NOTE

interface ApiService {

    @POST("login")
    suspend fun logIn(
        @Body body: RequestBody
    ): ResponseData<LoginRp>?

    @GET("notes")
    suspend fun getAllNote(
        @Header(AUTHORIZATION) Authorization: String
    ): ResponseData<ResponseNotes>?

    @FormUrlEncoded
    @POST("add/note")
    suspend fun addNote(
        @Header(AUTHORIZATION) Authorization: String,
        @Field(NOTE) note: String
    ): ResponseData<String?>?
}