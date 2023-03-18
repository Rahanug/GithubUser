package com.example.githubuser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")
    @GET("https://api.github.com/search/users")
    fun getUser(
        @Query("q") username: String
    ): Call<GithubResponse>

}