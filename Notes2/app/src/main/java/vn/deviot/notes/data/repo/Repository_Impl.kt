package vn.deviot.notes.data.repo

import okhttp3.RequestBody
import vn.deviot.notes.data.api.ApiService
import vn.deviot.notes.data.common.ResponseData
import vn.deviot.notes.screen.login.model.LoginRp
import vn.deviot.notes.screen.notes.model.ResponseNotes
import javax.inject.Inject

class Repository_Impl
@Inject constructor(private val apiService: ApiService) : Repository {
    override suspend fun logIn(body: RequestBody): ResponseData<LoginRp>? {
        return apiService.logIn(body)
    }

    override suspend fun getAllNote(auth: String): ResponseData<ResponseNotes>? {
        return apiService.getAllNote(auth)
    }
}
