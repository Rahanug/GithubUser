package com.example.githubuser.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.entity.FavoriteEntity
import com.example.githubuser.database.repository.FavoriteRepository
import com.example.githubuser.networking.response.ItemsItem

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository): ViewModel() {
    private val _listUser = MutableLiveData<List<ItemsItem>>()

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = favoriteRepository.getAll()

}