package com.example.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.FavoriteEntity
import com.example.githubuser.database.FavoriteRepository
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.networking.response.ItemsItem

class FavoriteViewModel(private val favoriteRepository: FavoriteRepository): ViewModel() {
    private val _listUser = MutableLiveData<List<ItemsItem>>()

    fun getAllFavorite(): LiveData<List<FavoriteEntity>> = favoriteRepository.getAll()

}