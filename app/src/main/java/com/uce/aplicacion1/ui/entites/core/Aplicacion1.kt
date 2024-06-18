package com.uce.aplicacion1.ui.entites.core

import android.app.Application
import androidx.room.Room
import com.uce.aplicacion1.data.local.repository.DataBaseRepository

// se va a acrear al incion de la aplicaciones
class Aplicacion1 : Application() {

    override fun onCreate() {
        super.onCreate()

        dbConnection = Room.databaseBuilder(applicationContext,
                DataBaseRepository::class.java,
                "Datos"
            ).build()
    }

    //
    companion object{
        private var dbConnection: DataBaseRepository? = null
        fun getDBConnection():DataBaseRepository = dbConnection!!
    }

}