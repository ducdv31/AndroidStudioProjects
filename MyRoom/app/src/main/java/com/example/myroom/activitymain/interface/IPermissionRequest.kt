package com.example.myroom.activitymain.`interface`

interface IPermissionRequest {
    fun hasUserUID(has: Boolean, username: String)
    fun hasUserInRoom(hasInRoom: Boolean, username: String)
    fun hasRootUser(hasRoot: Boolean)
    fun hasSupperRoot(hasSupperRoot: Boolean)
}