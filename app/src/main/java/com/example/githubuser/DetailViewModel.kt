package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailViewModel: ViewModel() {
    private val _dataDetailUser = MutableLiveData<DetailUserResponse>()
    val dataDetailUser : LiveData<DetailUserResponse> = _dataDetailUser
    private val _dataFollowerUser = MutableLiveData<List<ItemsItem>>()
    val dataFollowerUser : LiveData<List<ItemsItem>> = _dataFollowerUser
    private val _dataFollowingUser = MutableLiveData<List<ItemsItem>>()
    val dataFollowingUser : LiveData<List<ItemsItem>> = _dataFollowingUser
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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
                    _dataDetailUser.value = response.body()
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