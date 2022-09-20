package com.example.core.network

import com.example.core.model.network.Album
import com.example.core.model.network.Artist
import com.example.core.model.network.Genrees
import com.example.core.model.network.Song
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {
    @GET
    suspend fun getMusic(@Url url: String = "https://6218ef9681d4074e859c7eb8.mockapi.io/api/v1/songs"): ArrayList<Song>

    @GET
    suspend fun getAlbum(@Url url: String = "https://6215b273c9c6ebd3ce2f03ca.mockapi.io/albums"): ArrayList<Album>

    @GET
    suspend fun getArtist(@Url url: String = "https://621ed50b849220b1fca26484.mockapi.io/api/v1/artists"): ArrayList<Artist>

    @GET
    suspend fun getTopSong(@Url url: String = "https://6218ef9681d4074e859c7eb8.mockapi.io/api/v1/top"): ArrayList<Song>

    @GET
    suspend fun getGenrees(@Url url: String = "https://621f24a1311a70591401d374.mockapi.io/genrees"): ArrayList<Genrees>
}
