package com.example.core.model.network

import com.google.gson.annotations.SerializedName

data class Song(
    @SerializedName("source")
    var source: String? = "",
    @SerializedName("image")
    var image: String? = "",
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("title")
    var title: String? = "",
    @SerializedName("albumId")
    var albumId: Int? = null,
    @SerializedName("artistId")
    var artistId: String? = null,
    @SerializedName("genreId")
    var genreId: String? = "",
    @SerializedName("duration")
    var duration: Int? = null
)
