package com.example.myhome.data.model.login

import android.net.Uri

data class UserProfileModel(
    val id: String? = null,
    val photoUri: Uri? = null,
    val displayName: String? = null,
    val email: String? = null
) {
}