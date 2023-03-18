package com.example.githubuser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
//    TEsti
    @Headers("Authorization: token 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")
    @GET("https://api.github.com/users/{username}")
    fun getUser(
        @Path("username") username: String
    ): Call<GithubResponse>

}