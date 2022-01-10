package vn.deviot.notes.data.repo

import okhttp3.RequestBody
import vn.deviot.notes.data.common.ResponseData
import vn.deviot.notes.screen.login.model.LoginRp
import vn.deviot.notes.screen.notes.model.ResponseNotes

interface Repository {

    suspend fun logIn(body: RequestBody): ResponseData<LoginRp>?

    suspend fun getAllNote(auth: String): ResponseData<ResponseNotes>?

    suspend fun addNote(auth: String, note: String): ResponseData<String?>?
}