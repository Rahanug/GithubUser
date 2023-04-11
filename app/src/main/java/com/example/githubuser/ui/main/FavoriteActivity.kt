package com.example.githubuser.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuser.database.entity.FavoriteEntity
import com.example.githubuser.viewmodel.factory.FavoriteViewModelFactory
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.networking.response.ItemsItem
import com.example.githubuser.ui.adapter.UserAdapter
import com.example.githubuser.viewmodel.main.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListData()
    }

    private fun setListData(){
        favoriteViewModel.getAllFavorite().observe(this) { users: List<FavoriteEntity> ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.login, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            binding.rvUsers.adapter = UserAdapter(items)
        }

    }
}