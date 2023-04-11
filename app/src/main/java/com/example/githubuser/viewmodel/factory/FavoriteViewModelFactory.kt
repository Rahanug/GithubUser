package com.example.githubuser.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.database.repository.FavoriteRepository
import com.example.githubuser.viewmodel.main.FavoriteViewModel
import com.example.githubuser.utils.Injection

class FavoriteViewModelFactory private constructor(private val favoriteRepository: FavoriteRepository):
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(favoriteRepository) as T
        }
        throw IllegalArgumentException("Unknown VIewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: FavoriteViewModelFactory? = null
        fun getInstance(context: Context): FavoriteViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}