package com.example.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemRowUserBinding

class UserAdapter(private val listUser: ArrayList<String>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder{
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//        val(name, description, photo) = listUser[position]
        viewHolder.binding.tvItemName.text = listUser[position]
//        viewHolder.binding.tvItemDescription.text = description
//        Glide.with(this@MainActivity)
//            .load("https://api.github.com/users/${name}")
//            .into(binding.img_item)

    }

    override fun getItemCount() = listUser.size

    class ViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)
}