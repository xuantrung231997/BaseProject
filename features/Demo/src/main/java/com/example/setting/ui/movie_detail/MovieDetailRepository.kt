package com.example.setting.ui.movie_detail

import com.example.core.base.BaseRepository
import com.example.core.network.MovieDBApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(
    private val movieDBApiInterface: MovieDBApiInterface
) : BaseRepository() {

    fun getMovieDetail(movieID: Long) =
        flow { emit(movieDBApiInterface.getMovieDetail(movieID)) }.flowOn(Dispatchers.IO)

    fun getActors(movieID: Long) = flow { emit(movieDBApiInterface.getActors(movieID)) }.flowOn(Dispatchers.IO)
}
