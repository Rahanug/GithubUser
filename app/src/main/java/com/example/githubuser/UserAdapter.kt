package com.example.githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemRowUserBinding

class UserAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder{
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = listUser[position]
        val name = user.login
        val avatar = user.avatarUrl
        viewHolder.binding.tvItemName.text = name
        Glide.with(viewHolder.itemView.context)
            .load(avatar)
            .into(viewHolder.binding.imgItemPhoto)
        viewHolder.itemView.setOnClickListener{
            val intentDetail = Intent(viewHolder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra(DetailUserActivity.USER_ID, name)
            viewHolder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount() = listUser.size

    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)
}