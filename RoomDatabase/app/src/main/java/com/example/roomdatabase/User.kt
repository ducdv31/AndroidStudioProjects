package com.example.roomdatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "User table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var name: String,
    var age: Int
) : Serializable {
}