package vn.deviot.imageslide.repository

import vn.deviot.imageslide.model.ResponseData
import vn.deviot.imageslide.net.ApiService
import javax.inject.Inject

class Repository_Impl @Inject constructor(
    private val apiService: ApiService
) : Repository {
    override suspend fun getListImage(): ResponseData? = apiService.getListImage()
}