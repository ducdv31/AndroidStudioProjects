package com.example.roomdatabase.user2

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Table 2")
data class MasterUser(
    @PrimaryKey(autoGenerate = true) val id:Int,
    var name2:String,
    var age2:Int
) {
}