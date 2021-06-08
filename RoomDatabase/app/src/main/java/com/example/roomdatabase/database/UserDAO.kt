package com.example.roomdatabase.database

import androidx.room.*
import com.example.roomdatabase.User


@Dao
interface UserDAO {

    @Insert
    fun insertUser(user: User)

    @Query("SELECT * From `user table`")
    fun getListUser(): MutableList<User>

    /* check user exist */
    @Query("SELECT * FROM `user table` WHERE name = :username")
    fun checkUser(username: String): MutableList<User>

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE From `user table`")
    fun deleteAllUser()

    @Query("SELECT * From `user table` WHERE name LIKE '%' || :username || '%'")
    fun searchUser(username:String):MutableList<User>
}