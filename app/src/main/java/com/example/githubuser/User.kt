package com.example.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User (
    val name: String,
    val follower: String,
    val photo: String
    ) : Parcelable