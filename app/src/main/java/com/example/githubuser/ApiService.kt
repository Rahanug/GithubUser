package com.example.githubuser
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
//    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUser(
        @Query("q") username: String
    ): Call<GithubResponse>
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}