package com.example.animalsapp.models

import com.google.gson.annotations.SerializedName

data class Environment(
    @SerializedName("_id") val id: String,
    val description: String,
    val image: String,
    val name: String
)