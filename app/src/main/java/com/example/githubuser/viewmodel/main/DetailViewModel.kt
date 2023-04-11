package com.example.githubuser.viewmodel.main

import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.database.repository.FavoriteRepository
import com.example.githubuser.database.entity.User
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
    private val _isLoadingDetail = MutableLiveData<Boolean>()
    val isLoadingDetail: LiveData<Boolean> = _isLoadingDetail

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower


    fun getByUsername(username: String): LiveData<User?>{
        val userFavorite = favoriteRepository.getByUsername(username)
        return Transformations.map(userFavorite) {
            it?.let { User(it.login, it.avatarUrl) }
        }

    }
    fun addFavorite(user: User){
        favoriteRepository.add(user)
    }
    fun deleteFavorite(username: String){
        favoriteRepository.delete(username)
    }


    init{
        getDataUser(USER_ID)
    }

    fun getDataUser(USER_ID: String){
        _isLoadingDetail.value = true
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
                    val following = body?.following.toString()
                    val follower = body?.followers.toString()
                    _dataDetailUser.value = User(username, avatarUrl, name, following, follower)
                }else{
                    Log.e(TAG, "onFailure getDataUser: ${response.message()}")
                }
                _isLoadingDetail.value = false
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoadingDetail.value = false
                Log.e(TAG, "onFailure getDataUser: ${t.message.toString()}")
            }

        })
    }

    fun getDataFollower(USER_ID: String){
        _isLoadingFollower.value = true
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
                _isLoadingFollower.value = false
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollower.value = false
                Log.e(TAG, "onFailure getDataFollower: ${t.message.toString()}")
            }

        })
    }

    fun getDataFollowing(USER_ID: String){
        _isLoadingFollowing.value = true
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
                _isLoadingFollowing.value = false
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollowing.value = false
                Log.e(TAG, "onFailure getDataFollowing: ${t.message.toString()}")
            }

        })
    }

    companion object{
        private const val TAG = "DetailViewModel"
        private val USER_ID = ""

    }

}