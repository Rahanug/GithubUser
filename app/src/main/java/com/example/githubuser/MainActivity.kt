package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    companion object{
        private const val TAG = "MainActivity"
        private const val USER_ID = "Rahanug"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel.listUser.observe(this, {dataUser->setListData(dataUser)})
        mainViewModel.isLoading.observe(this,{showLoading(it)})

    }

    private fun setListData(dataUser: List<ItemsItem>){
        val adapter = UserAdapter(dataUser)
        binding.rvUsers.adapter = adapter

    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }



}