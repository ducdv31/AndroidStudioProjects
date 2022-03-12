package vn.dv.myvideo.repo

import vn.dv.myvideo.common.response.ResponseVideoPexels
import vn.dv.myvideo.repo.api.ApiService
import vn.dv.myvideo.view.main.model.VideosPexels
import javax.inject.Inject

class Repository_Impl @Inject constructor(
    private val apiService: ApiService
) : Repository {
    override suspend fun getVideoPopular(auth: String): ResponseVideoPexels<List<VideosPexels>>? {
        return apiService.getVideoPopular(auth)
    }
}