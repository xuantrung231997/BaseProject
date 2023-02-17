package com.example.setting.ui.tabMovie

import com.example.core.base.BaseRepository
import com.example.core.database.dao.MovieDao
import com.example.core.model.network.movie.Movie
import com.example.core.network.MovieDBApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

private const val DEFAULT_MOVIE_PAGE_INDEX = 1L

class MovieRepository @Inject constructor(
    private val movieDBApiInterface: MovieDBApiInterface,
    private val movieDao: MovieDao
) : BaseRepository() {

    fun getMovieInfo(pageIndex: Long = DEFAULT_MOVIE_PAGE_INDEX) =
        flow { emit(movieDBApiInterface.getNowPlaying(page = pageIndex)) }.flowOn(Dispatchers.IO)

    fun getPopular() = flow { emit(movieDBApiInterface.getPopular()) }.flowOn(Dispatchers.IO)

    fun getListMovie() = movieDao.getListMovie()

    suspend fun insertListMovie(movies: List<Movie>) = movieDao.insertListMovie(movies)
}

