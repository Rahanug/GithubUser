package com.example.githubuser.database.repository

import androidx.lifecycle.LiveData
import com.example.githubuser.database.dao.FavoriteDao
import com.example.githubuser.database.entity.FavoriteEntity
import com.example.githubuser.database.entity.User
import com.example.githubuser.networking.api.ApiService
import com.example.githubuser.utils.AppExecutors

class FavoriteRepository constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {

    fun getAll(): LiveData<List<FavoriteEntity>> {
        return favoriteDao.getAll()
    }

    fun add(username: User) {
        val userFavorite = FavoriteEntity(
            login = username.login,
            avatarUrl = username.avatarUrl,
        )
        appExecutors.diskIO.execute { favoriteDao.add(userFavorite) }
    }

    fun delete(username: String) {
        appExecutors.diskIO.execute { favoriteDao.delete(username) }
    }

    fun getByUsername(username: String): LiveData<FavoriteEntity?> {
        return favoriteDao.getByUsername(username)
    }

    fun deleteAll() {
        appExecutors.diskIO.execute { favoriteDao.deleteAll() }
    }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteDao: FavoriteDao,
            appExecutors: AppExecutors,
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, favoriteDao, appExecutors)
            }.also { instance = it }
    }
}