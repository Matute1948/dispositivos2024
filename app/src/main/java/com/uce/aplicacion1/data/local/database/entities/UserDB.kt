package com.uce.aplicacion1.data.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDB (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_user")
    val id:Int = 0,
    @ColumnInfo(name = "name_user")
    val name: String,
    @ColumnInfo(name = "password_user")
    val password: String
)