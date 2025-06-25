package com.example.mvianimals.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvianimals.api.AnimalApi
import com.example.mvianimals.api.AnimalRepo
import com.example.mvianimals.model.Animal

class ViewModelFactory(private val api : AnimalApi): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnimalViewModel::class.java)) {
            return AnimalViewModel(AnimalRepo(api)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}




