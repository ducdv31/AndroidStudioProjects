package com.example.myroom.activityuserpermission.model

import java.io.Serializable

class UserPermissionImg(
    val uid: String,
    val uri: String,
    val username: String,
    val email: String,
    val perm: Int
) : Serializable {
}