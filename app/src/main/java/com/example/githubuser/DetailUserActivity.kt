package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    companion object{
         val USER_ID = ""
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nameUser = intent.getStringExtra(USER_ID)
        binding.detailNameUser.text = nameUser
        if(nameUser != null) detailViewModel.getDataUser(nameUser)
        detailViewModel.dataDetailUser.observe(this, {dataUser->setDetailUser(dataUser)})
        detailViewModel.isLoading.observe(this,{showLoading(it)})

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        sectionPagerAdapter.username = nameUser.toString()
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager){ tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setDetailUser(dataUser: DetailUserResponse){
        binding.detailNameUser.text = dataUser.login.toString()
        binding.detailNicknameUser.text = dataUser.name.toString()
        binding.detailFollower.text = dataUser.followers.toString() + " Follower"
        binding.detailFollowing.text = dataUser.following.toString() + " Following"
        Glide.with(this@DetailUserActivity)
            .load(dataUser.avatarUrl)
            .into(binding.profileImage)
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}