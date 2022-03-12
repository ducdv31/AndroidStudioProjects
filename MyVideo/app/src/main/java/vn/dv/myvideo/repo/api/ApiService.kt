package vn.dv.myvideo.repo.api

import retrofit2.http.GET
import retrofit2.http.Header
import vn.dv.myvideo.common.response.ResponseVideoPexels
import vn.dv.myvideo.utils.Const
import vn.dv.myvideo.view.main.model.VideosPexels

interface ApiService {

    @GET("videos/popular")
    suspend fun getVideoPopular(
        @Header(Const.Authorization) auth: String = Const.Authorization
    ): ResponseVideoPexels<List<VideosPexels>>?
}