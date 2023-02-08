package com.example.setting.ui.tabMovie

import com.example.core.base.BaseRepository
import com.example.core.database.dao.MovieDao
import com.example.core.model.network.movie.Movie
import com.example.core.network.MovieDBApiInterface
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDBApiInterface: MovieDBApiInterface,
    private val movieDao: MovieDao
) : BaseRepository() {

    fun getMovieInfo() = flow { emit(movieDBApiInterface.getNowPlaying()) }

    fun getListMovie() = movieDao.getListMovie()

    suspend fun insertListMovie(movies: List<Movie>) = movieDao.insertListMovie(movies)
}

