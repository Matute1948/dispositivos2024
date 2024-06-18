package com.uce.aplicacion1.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.uce.aplicacion1.R
import com.uce.aplicacion1.databinding.ActivityConstrainBinding
import com.uce.aplicacion1.logic.usercase.GetAllTopsNewUserCase
import com.uce.aplicacion1.logic.usercase.GetOneTopNewUserCase
import com.uce.aplicacion1.ui.adapters.NewsAdapter
import com.uce.aplicacion1.ui.adapters.NewsDiffCallback
import com.uce.aplicacion1.ui.entites.NewsDataUI
import com.uce.aplicacion1.ui.fragments.FavoritesFragment
import com.uce.aplicacion1.ui.fragments.ListNew
import com.uce.aplicacion1.ui.fragments.MenuBottomSheetFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConstrainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConstrainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConstrainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        val x = supportFragmentManager.beginTransaction()
        x.replace(binding.lytFragment.id, ListNew())
        x.commit()

    }

    private fun initListeners(){

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId){
                R.id.listarItem ->{
                    val x = supportFragmentManager.beginTransaction()
                    x.replace(binding.lytFragment.id, ListNew())
                    x.commit()
                    true
                }
                R.id.favItem->{
                    val x = supportFragmentManager.beginTransaction()
                    x.replace(binding.lytFragment.id, FavoritesFragment())
                    x.commit()
                    true
                }
                R.id.noFavItem->{
                    Snackbar.make(binding.lytFragment, "no Fav ", Snackbar.LENGTH_LONG)
                    true
                }
                else -> false
            }
        }
    }


}

