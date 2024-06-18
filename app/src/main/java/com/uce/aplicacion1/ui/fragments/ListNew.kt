package com.uce.aplicacion1.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.uce.aplicacion1.R
import com.uce.aplicacion1.databinding.ActivityConstrainBinding
import com.uce.aplicacion1.databinding.FragmentListNewBinding
import com.uce.aplicacion1.logic.usercase.GetAllTopsNewUserCase
import com.uce.aplicacion1.logic.usercase.GetOneTopNewUserCase
import com.uce.aplicacion1.ui.activitys.DetailItemActivity
import com.uce.aplicacion1.ui.adapters.NewsAdapter
import com.uce.aplicacion1.ui.adapters.NewsDiffCallback
import com.uce.aplicacion1.ui.entites.NewsDataUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListNew : Fragment() {

    private lateinit var binding : FragmentListNewBinding
    private var items : MutableList<NewsDataUI> = mutableListOf()
    private lateinit var newsAdapter : NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        //me va a devolver la vista
    ): View? {
        //aqui recien inflamos y toca poner en un contenedor
        //y tengo que rellenar
        binding = FragmentListNewBinding.bind(
            inflater.inflate(
                R.layout.fragment_list_new,
                container,
                false
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVariables()
        initData()
    }

    private fun initVariables() {
        newsAdapter = NewsAdapter(
            {descriptionItem(it)},
            {deleteItem(it)},
            {addItem()})

        binding.rvTopNews.adapter = newsAdapter


        binding.rvTopNews.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )


        //binding.rvTopNews.layoutManager = CarouselLayoutManager()
//        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.adapterPosition
//                deleteItem(position)
//            }
//        }
//
//        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
//        itemTouchHelper.attachToRecyclerView(binding.rvTopNews)
    }


    private fun initData(){
        binding.pgbarLoadData.visibility = View.VISIBLE
        lifecycleScope.launch( Dispatchers.IO){

            val result = GetAllTopsNewUserCase().invoke()
            withContext(Dispatchers.Main){
                binding.pgbarLoadData.visibility = View.INVISIBLE
                result.onSuccess {
                    items = it.toMutableList()
                    newsAdapter.listItems=items
                    newsAdapter.notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado
                }
                result.onFailure {
                    Snackbar.make(binding.refreshRV,it.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun descriptionItem(news: NewsDataUI){

        //Snackbar.make(binding.refreshRV, news.name, Snackbar.LENGTH_LONG).show()
        val intent = Intent(
            requireContext(),
            DetailItemActivity::class.java
        ).apply {
            putExtra("id", news.id)
        }
        startActivity(intent)


    }

//    private fun deleteItem(position: Int){
//        Toast.makeText(this, position.toString(), Toast.LENGTH_SHORT).show()
//        val newList = items.toMutableList().apply { removeAt(position) }
//        val diffCallback = NewsDiffCallback(items, newList)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//
//        items = newList
//        newsAdapter.listItems = items
//        diffResult.dispatchUpdatesTo(newsAdapter)
//    }

    private fun deleteItem(position: Int){
        items.removeAt(position)
        newsAdapter.listItems = items
        newsAdapter.notifyItemRemoved(position)
    }



    private fun addItem(){
        binding.pgbarLoadData.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO){
            val addNew = GetOneTopNewUserCase().invoke()
            withContext(Dispatchers.Main){
                binding.pgbarLoadData.visibility = View.INVISIBLE
                addNew.onSuccess { newItem ->
                    val newList = items.toMutableList().apply { add(newItem) }
                    val diffCallback = NewsDiffCallback(items, newList)
                    val diffResult = DiffUtil.calculateDiff(diffCallback)

                    items = newList
                    newsAdapter.listItems = items
                    diffResult.dispatchUpdatesTo(newsAdapter)
                }
                addNew.onFailure {
                    Snackbar.make(binding.refreshRV, it.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun showMenuBottomSheet() {
        val menuBottomSheetFragment = MenuBottomSheetFragment()

    }


//    private fun addItem(){
//        binding.pgbarLoadData.visibility = View.VISIBLE
//        lifecycleScope.launch( Dispatchers.IO){
//            val addNew = GetOneTopNewUserCase().invoke()
//            withContext(Dispatchers.Main){
//                binding.pgbarLoadData.visibility = View.INVISIBLE
//                addNew.onSuccess {
//                    items.add(it)
//                    newsAdapter.listItems=items
//                    newsAdapter.notifyItemInserted(items.size-1)
//                }
//                addNew.onFailure {
//                    Snackbar.make(binding.refreshRV,it.message.toString(),Snackbar.LENGTH_LONG).show()
//                }
//            }
//        }
//
//
//    }


}