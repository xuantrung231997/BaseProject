package com.example.setting.ui.home

import com.example.core.base.BaseRepository
import com.example.core.model.network.Album
import com.example.core.model.network.Artist
import com.example.core.model.network.Song
import com.example.core.network.ApiInterface
import com.example.setting.model.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : BaseRepository() {

//    private fun getListSong() = flow { emit(apiInterface.getMusic()) }
//
//    private fun getListAlbums() = flow { emit(apiInterface.getAlbum()) }
//
//    private fun getListArtist() = flow { emit(apiInterface.getArtist()) }

    fun getDataAsync() = flow {
        coroutineScope {
            val listSong = async { apiInterface.getMusic() }
            val listAlbum = async { apiInterface.getAlbum() }
            val listArtist = async { apiInterface.getArtist() }
            emit(HomeResponse(listSong.await(), listAlbum.await(), listArtist.await()))
        }
    }

//    fun getData() = getListSong().zip(getListAlbums()) { r1, r2 ->
//        return@zip HomeResponse(r1, r2)
//    }.zip(getListArtist()) { r1, r2 ->
//        return@zip r1.apply { listArtist = r2 }
//    }.flowOn(Dispatchers.IO)

    private fun getListImage() = listOf(
        "https://ieltsrewind.com/wp-content/uploads/2021/01/Describe-your-favorite-singer-image.jpg?ezimgfmt=ng%3Awebp%2Fngcb21%2Frs%3Adevice%2Frscb21-2",
        "https://musicapppp.000webhostapp.com/image/dropand.jpg",
        "https://file.tinnhac.com/resize/600x-/music/2017/03/29/sontung05-177a.jpg",
        "https://avatar-ex-swe.nixcdn.com/playlist/2017/05/31/4/9/7/8/1496224788597_500.jpg"
    )

    fun delayFlow(time: Long) = flow {
        delay(time)
        emit(time)
    }


    fun handleResponse(response: HomeResponse): ArrayList<HomePageItem> {
        val listHomePageData = ArrayList<HomePageItem>()

        listHomePageData.add(SlideHome(getListImage()))

        listHomePageData.add(TitleHome("Songs"))
        response.listSong?.take(100)?.forEach {
            listHomePageData.add(SongHome(it))
        }

        listHomePageData.add(TitleHome("Albums"))
        response.listAlbum?.take(100)?.forEach {
            listHomePageData.add(AlbumHome(it))
        }

        return listHomePageData
    }
}

data class HomeResponse(
    var listSong: ArrayList<Song>? = null,
    var listAlbum: ArrayList<Album>? = null,
    var listArtist: ArrayList<Artist>? = null,
)