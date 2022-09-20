package com.example.core.model.network

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("image")
    var image: String? = "",
    @SerializedName("totalSong")
    var totalSong: String? = "",
    @SerializedName("id")
    var id: Int? = null
)
