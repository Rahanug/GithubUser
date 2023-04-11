package com.example.githubuser.utils

import android.content.Context
import com.example.githubuser.database.db.FavoriteDatabase
import com.example.githubuser.database.repository.FavoriteRepository
import com.example.githubuser.networking.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteUserDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(apiService, dao, appExecutors)
    }
}