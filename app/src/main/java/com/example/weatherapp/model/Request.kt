package com.example.weatherforecastapp.model.current

data class Request(
    val language: String,
    val query: String,
    val type: String,
    val unit: String
)