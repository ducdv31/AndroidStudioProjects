package vn.dv.myvideo.repo

import vn.dv.myvideo.common.response.ResponseVideoPexels
import vn.dv.myvideo.view.main.model.VideosPexels

interface Repository {

    suspend fun getVideoPopular(auth: String): ResponseVideoPexels<List<VideosPexels>>?
}