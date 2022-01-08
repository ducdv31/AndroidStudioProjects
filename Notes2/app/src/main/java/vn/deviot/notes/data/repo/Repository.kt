package vn.deviot.notes.data.repo

import okhttp3.RequestBody
import vn.deviot.notes.data.common.ResponseData
import vn.deviot.notes.screen.login.model.LoginRp

interface Repository {

    suspend fun logIn(body: RequestBody) : ResponseData<LoginRp>?
}