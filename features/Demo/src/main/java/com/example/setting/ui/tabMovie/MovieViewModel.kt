package com.example.setting.ui.tabMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.core.model.network.movie.Movie
import com.example.core.model.network.movie.MovieResult
import com.example.core.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : BaseViewModel() {

    private val _originMovies = MutableLiveData<Resource<MovieResult>>()
    val originMovies: LiveData<Resource<MovieResult>> get() = _originMovies

    private val _loadMoreMovies = MutableLiveData<Resource<List<Movie>>>()
    val loadMoreMovies: LiveData<Resource<List<Movie>>> get() = _loadMoreMovies

    private var _isNotifyOriginMovies = true
    val isNotifyOriginMovies: Boolean get() = _isNotifyOriginMovies

    private var currentMoviePageIndex = 1L

    init {
        fetchMovieInfo()
    }

    fun fetchMovieInfo() {
        resetPageIndex()
        repository.getMovieInfo()
            .onStart {
                _originMovies.value = Resource.Loading
            }.map {
                _originMovies.value = Resource.Success(it)
            }.catch { e ->
                _originMovies.value = Resource.Error(e)
            }.launchIn(viewModelScope)
    }

    fun loadMoreMovieIfNeed() {
        val resourceMovie = _originMovies.value
        if (resourceMovie is Resource.Success) {
            val totalPage = resourceMovie.data.totalPages
            if (currentMoviePageIndex <= totalPage && _loadMoreMovies.value !is Resource.Loading) {
                currentMoviePageIndex++
                repository.getMovieInfo(currentMoviePageIndex)
                    .onStart {
                        _loadMoreMovies.value = Resource.Loading
                    }.map {
                        val moviesResponse = it.results
                        _loadMoreMovies.value = Resource.Success(moviesResponse)
                        addMoviesLoadMoreToOriginMovies(resourceMovie, moviesResponse)
                    }.catch { e ->
                        handleError(e)
                    }.launchIn(viewModelScope)
            }
        }
    }

    private fun addMoviesLoadMoreToOriginMovies(resourceMovie: Resource.Success<MovieResult>, movies: List<Movie>) {
        _isNotifyOriginMovies = false
        resourceMovie.data.results.addAll(movies)
        _originMovies.value = resourceMovie
        _isNotifyOriginMovies = true
    }

    private fun resetPageIndex() {
        currentMoviePageIndex = 1L
    }
}



