package com.example.mvianimals

import android.R.attr.button
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvianimals.api.AnimalService
import com.example.mvianimals.view.AnimalAdapter
import com.example.mvianimals.view.AnimalViewModel
import com.example.mvianimals.view.MainIntent
import com.example.mvianimals.view.UiState
import com.example.mvianimals.view.ViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: AnimalViewModel

    private var adapter = AnimalAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUi()
        setupObservables()


    }



    private fun setupUi() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val button : Button = findViewById<Button>(R.id.button)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(AnimalService.api)
        )[AnimalViewModel::class.java]
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.run {
            val divider = DividerItemDecoration(
                context,
                (layoutManager as LinearLayoutManager).orientation
            )
            addItemDecoration(divider)
        }
        recyclerView.adapter = adapter
        button.setOnClickListener {
           lifecycleScope.launch{
               mainViewModel.userIntent.send(MainIntent.FetchAnimals)
           }
        }
    }

    private fun setupObservables() {
        val button : Button = findViewById<Button>(R.id.button)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
lifecycleScope.launch {
    mainViewModel.uiState.collect { collector ->
        when(collector){
            is UiState.Idle -> {

            }
            is UiState.Loading -> {
                button.visibility = View.GONE
                progressBar.visibility = View.VISIBLE

            }
            is UiState.Animals ->{
                button.visibility = View.GONE
                progressBar.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                collector.animals.let{
                    adapter.newAnimals(it)
                }
            }
            is UiState.Error ->{
                progressBar.visibility = View.GONE
                button.visibility = View.GONE
            }

        }

    }
}
    }
}