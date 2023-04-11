package com.example.githubuser.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.database.User
import com.example.githubuser.database.ViewModelFactory
import com.example.githubuser.ui.adapter.SectionPagerAdapter
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private val detailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }
    private lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nameUser = intent.getStringExtra(USER_ID)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        sectionPagerAdapter.username = nameUser.toString()
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        if (nameUser == null) return
        binding.detailNameUser.text = nameUser
        detailViewModel.getDataUser(nameUser)
        detailViewModel.dataDetailUser.observe(this, { dataUser ->
            setDetailUser(dataUser)

        })
        detailViewModel.getByUsername(nameUser).observe(this, { userFavorite ->
            if (userFavorite != null) {
                binding.favoriteDrawable.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailUserActivity,
                        R.drawable.ic__favorite_24
                    )
                )
                binding.favoriteDrawable.setOnClickListener {
                    detailViewModel.deleteFavorite(userFavorite.login)
                    Toast.makeText(this, "User is deleted", Toast.LENGTH_SHORT).show()
                }
            } else {
                binding.favoriteDrawable.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailUserActivity,
                        R.drawable.ic_favorite_border_24
                    )
                )
                binding.favoriteDrawable.setOnClickListener {
                    detailViewModel.addFavorite(user)
                    Toast.makeText(this, "User is Added", Toast.LENGTH_SHORT).show()
                }

            }
        })
        detailViewModel.isLoadingDetail.observe(this, { showLoading(it) })

    }

    private fun setDetailUser(dataUser: User) {
        user = dataUser
        binding.apply {
            detailNameUser.text = dataUser.login
            detailNicknameUser.text = dataUser.name.orEmpty()
            detailFollower.text = resources.getString(R.string.user_follower, dataUser.followers.orEmpty())
            detailFollowing.text = resources.getString(R.string.user_following, dataUser.following.orEmpty())
            Glide.with(this@DetailUserActivity)
                .load(dataUser.avatarUrl)
                .into(profileImage)
        }

    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        val USER_ID = ""

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}