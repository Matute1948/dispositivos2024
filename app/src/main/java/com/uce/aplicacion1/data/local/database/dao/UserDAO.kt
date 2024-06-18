package com.uce.aplicacion1.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.uce.aplicacion1.data.local.database.entities.UserDB

@Dao
interface UserDAO {

    @Query("select*from users")
   fun getAllUsers() : List<UserDB>

   @Query("select*from users where id_user =:id")
   fun getUserById(id:Int):UserDB

   @Insert
   fun saveUser(user: List<UserDB>)

   @Delete
   fun saveUser(user: UserDB)
}