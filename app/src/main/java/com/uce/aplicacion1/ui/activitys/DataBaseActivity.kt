package com.uce.aplicacion1.ui.activitys

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.uce.aplicacion1.R
import com.uce.aplicacion1.data.local.database.entities.UserDB
import com.uce.aplicacion1.data.local.repository.DataBaseRepository
import com.uce.aplicacion1.databinding.ActivityConstrainBinding
import com.uce.aplicacion1.databinding.ActivityDataBaseBinding
import com.uce.aplicacion1.ui.entites.core.Aplicacion1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataBaseActivity : AppCompatActivity() {
    lateinit var binding: ActivityDataBaseBinding
    private lateinit var con: DataBaseRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        con = Aplicacion1.getDBConnection()

        initVariables()
        initListeners()
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener{
            val user = binding.etUsername.text.toString()
            val pass = binding.etPassword.text.toString()
            val userDB = UserDB(name=user, password = pass)

            lifecycleScope.launch(Dispatchers.IO){
                con.getUserDao().saveUser(listOf(userDB))
            }

        }
    }

    private fun initVariables() {

    }
}