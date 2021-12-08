package vn.deviot.imageslide.repository

import vn.deviot.imageslide.model.ResponseData

interface Repository {
    suspend fun getListImage() : ResponseData?
}