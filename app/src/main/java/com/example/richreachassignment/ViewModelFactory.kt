package com.example.richreachassignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.richreachassignment.repository.Repository

class ViewModelFactory(private val viewModelProviderRepository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository = viewModelProviderRepository) as T
        }
        throw IllegalArgumentException("viewModel")
    }
}