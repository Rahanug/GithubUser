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
        private const val USER_ID = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
//        mainViewModel.items.observe(this,{items->setItemData(items)})
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        mainViewModel.ListUser.observe(this, {dataUser->setListData(dataUser)})
        mainViewModel.isLoading.observe(this,{showLoading(it)})

    }

    private fun setListData(dataUser: List<ItemsItem>){
        val listUser = ArrayList<String>()
        for(user in dataUser){
            listUser.add(
                """${user.login}""".trimIndent()
            )
        }
        val adapter = UserAdapter(listUser)
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