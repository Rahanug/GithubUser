package com.example.githubuser.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_user")
data class FavoriteEntity(
    @field:PrimaryKey
    @field:ColumnInfo(name = "username")
    val login: String,

    @field:ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
)