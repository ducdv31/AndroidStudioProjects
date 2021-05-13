package com.example.myroom.components.`interface`

interface IPermissionRequest {
    fun hasUnKnowUser(has: Boolean)
    fun hasUserInRoom(hasInRoom: Boolean, username: String)
    fun hasRootUser(hasRoot: Boolean)
    fun hasSupperRoot(hasSupperRoot: Boolean)
}