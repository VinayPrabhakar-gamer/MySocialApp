package com.example.mysocialapp.data

// Every post will have a Unique Id
data class Post (
    val imageUrl: String = "",
    val likes: MutableList<String> = mutableListOf(),
    val description: String = "",
    val createdAt: Long = 0
)