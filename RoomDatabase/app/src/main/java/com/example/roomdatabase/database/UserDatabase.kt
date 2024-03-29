package com.example.roomdatabase.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdatabase.User
import com.example.roomdatabase.user2.MasterUser
import com.example.roomdatabase.user2.UserDAO2

@Database(
    entities = [User::class, MasterUser::class],
    version = 1,
)
abstract class UserDatabase() : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME: String = "user.db"
        private var instance: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }

    abstract fun userDAO(): UserDAO

    abstract fun userDAO2(): UserDAO2

}