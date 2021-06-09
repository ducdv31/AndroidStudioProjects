package com.example.roomdatabase.user2

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface UserDAO2 {

    @Insert
    fun insertUser23(masterUser: MasterUser)

}