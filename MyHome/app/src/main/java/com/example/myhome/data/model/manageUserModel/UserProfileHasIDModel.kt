package com.example.myhome.data.model.manageUserModel

import android.net.Uri
import java.io.Serializable

data class UserProfileHasIDModel(
    val id: String? = null,
    val photoUri: String? = null,
    val displayName: String? = null,
    val email: String? = null,
    val permission: Int? = null
) : Serializable {

    fun getPermissionEx(): Int {
        return permission ?: 99
    }
}