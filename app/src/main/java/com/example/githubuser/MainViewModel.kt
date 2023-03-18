package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _items = MutableLiveData<ItemsItem>()
    val items: LiveData<ItemsItem> = _items

    private val _listUser = MutableLiveData<List<ItemsItem>>()
    val ListUser : LiveData<List<ItemsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val USER_ID = "Rahanug"
    }
    init{
        findUser()
    }
    private fun findUser(){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(USER_ID)
        client.enqueue(object : Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _listUser.value = response.body()?.items as List<ItemsItem>?
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}