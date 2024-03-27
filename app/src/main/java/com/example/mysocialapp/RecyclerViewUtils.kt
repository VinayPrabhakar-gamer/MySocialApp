package com.example.mysocialapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.mysocialapp.databinding.ListItemPostBinding

class PostViewHolder(private val binding: ListItemPostBinding) : ViewHolder(binding.root) {
    fun bind(url: String, context: Context){
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .into(binding.itemIv)
    }

}

class PostAdapter(
    private val urls: List<String>,
    private val context: Context
): Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ListItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount() = urls.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(urls[position], context)
    }

}