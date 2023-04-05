package com.example.githubuser.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.githubuser.database.FavoriteRepository
import com.example.githubuser.database.User
import com.example.githubuser.networking.api.ApiConfig
import com.example.githubuser.networking.response.DetailUserResponse
import com.example.githubuser.networking.response.ItemsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val favoriteRepository: FavoriteRepository): ViewModel() {
    private val _dataDetailUser = MutableLiveData<User>()
    val dataDetailUser : LiveData<User> = _dataDetailUser
    private val _dataFollowerUser = MutableLiveData<List<ItemsItem>>()
    val dataFollowerUser : LiveData<List<ItemsItem>> = _dataFollowerUser
    private val _dataFollowingUser = MutableLiveData<List<ItemsItem>>()
    val dataFollowingUser : LiveData<List<ItemsItem>> = _dataFollowingUser
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getByUsername(username: String): LiveData<User?>{
        val userFavorite = favoriteRepository.getByUsername(username)
        return Transformations.map(userFavorite) {
            it?.let { User(it.login, it.avatarUrl, null, null, null) }
        }

    }
    fun addFavorite(user: User){
        favoriteRepository.add(user)
    }
    fun deleteFavorite(username: String){
        favoriteRepository.delete(username)
    }

    companion object{
        private const val TAG = "DetailViewModel"
        private val USER_ID = ""

    }
    init{
        getDataUser(USER_ID)
    }

    fun getDataUser(USER_ID: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(USER_ID)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if(response.isSuccessful){
                    val body = response.body()
                    val username = body?.login.orEmpty()
                    val avatarUrl = body?.avatarUrl.orEmpty()
                    val name = body?.name.orEmpty()
                    val following = body?.following
                    val follower = body?.followers
                    _dataDetailUser.value = User(username, avatarUrl, name, following, follower)
                }else{
                    Log.e(TAG, "onFailure getDataUser: ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure getDataUser: ${t.message.toString()}")
            }

        })
    }

    fun getDataFollower(USER_ID: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(USER_ID)
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if(response.isSuccessful){
                    _dataFollowerUser.value = response.body()
                }else{
                    Log.e(TAG, "onFailure getDataFollower: ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure getDataFollower: ${t.message.toString()}")
            }

        })
    }

    fun getDataFollowing(USER_ID: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(USER_ID)
        client.enqueue(object : Callback<List<ItemsItem>>{
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if(response.isSuccessful){
                    _dataFollowingUser.value = response.body()
                }else{
                    Log.e(TAG, "onFailure getDataFollowing: ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure getDataFollowing: ${t.message.toString()}")
            }

        })
    }


}