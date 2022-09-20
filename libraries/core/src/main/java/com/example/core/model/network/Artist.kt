package com.example.core.model.network

import com.google.gson.annotations.SerializedName

data class Artist(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("image")
    var image: String? = "",
    @SerializedName("id")
    var id: Int? = null
)
