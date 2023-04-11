package com.example.githubuser.database


data class User (
    val login: String,
    val avatarUrl: String,
    val name: String? = null,
    val following: String? = null,
    val followers: String? = null,
    val isFavorite: Boolean = false,

)