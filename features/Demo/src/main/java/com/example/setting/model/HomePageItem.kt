package com.example.setting.model

import com.example.core.model.network.Album
import com.example.core.model.network.Song

open class HomePageItem

class SlideHome(val listImage: List<String>) : HomePageItem()
class SongHome(val song: Song) : HomePageItem()
class ArtistHome : HomePageItem()
class AlbumHome(val album: Album) : HomePageItem()
class TitleHome(val title: String) : HomePageItem()