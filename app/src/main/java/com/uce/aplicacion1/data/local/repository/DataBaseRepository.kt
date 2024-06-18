package com.uce.aplicacion1.data.local.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uce.aplicacion1.data.local.database.dao.UserDAO
import com.uce.aplicacion1.data.local.database.entities.UserDB

//la version es importante para actualizaciones
@Database(
    entities = [UserDB::class],
    version = 1
)
abstract class DataBaseRepository:RoomDatabase(){
    abstract fun getUserDao():UserDAO
}