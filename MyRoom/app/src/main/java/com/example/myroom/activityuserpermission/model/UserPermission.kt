package com.example.myroom.activityuserpermission.model

import java.io.Serializable

open class UserPermission(val uid: String, val username: String, val email: String, val perm: Int) :
    Serializable {
}