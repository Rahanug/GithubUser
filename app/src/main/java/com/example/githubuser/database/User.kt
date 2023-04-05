package com.example.githubuser.database

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class User (
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val following: Int?,
    val followers: Int?,

)