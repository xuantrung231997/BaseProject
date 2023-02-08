package com.example.setting.ui.tabMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.core.model.network.movie.Movie
import com.example.core.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : BaseViewModel() {

    private val _movies = MutableLiveData<Resource<List<Movie>>>()
    val movies: LiveData<Resource<List<Movie>>> get() = _movies

    init {
        fetchMovieInfo()
    }

    fun fetchMovieInfo() {
        repository.getMovieInfo()
            .flowOn(Dispatchers.IO)
            .onStart {
                _movies.value = Resource.Loading
                // TODO: to showing loading layout ahihi
                delay(2000)
            }.map {
                repository.insertListMovie(it.results)
                getMovieListToDatabase()
            }.catch { e ->
                _movies.value = Resource.Error(e)
            }.launchIn(viewModelScope)
    }

    private fun getMovieListToDatabase() {
        repository.getListMovie()
            .flowOn(Dispatchers.IO)
            .map {
                _movies.value = Resource.Success(it)
            }.catch { e ->
                _movies.value = Resource.Error(e)
            }.launchIn(viewModelScope)
    }
}


