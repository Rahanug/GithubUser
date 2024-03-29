package com.example.githubuser.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.database.entity.FavoriteEntity

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite_user")
    fun getAll(): LiveData<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(user: FavoriteEntity)

    @Query("SELECT * FROM favorite_user WHERE username =:user")
    fun getByUsername(user: String): LiveData<FavoriteEntity?>

    @Query("DELETE FROM favorite_user WHERE username =:user")
    fun delete(user: String)

    @Query("DELETE FROM favorite_user")
    fun deleteAll()
}