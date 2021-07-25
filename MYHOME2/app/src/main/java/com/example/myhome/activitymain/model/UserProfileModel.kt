package com.example.myhome.activitymain.model

import android.net.Uri

data class UserProfileModel(
    val id: String?,
    val photo: Uri?,
    val name: String?,
    val email: String?
) {
}