package com.example.core.model.network

import com.google.gson.annotations.SerializedName

data class Genrees(
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("id")
    val id: Int? = null
)
