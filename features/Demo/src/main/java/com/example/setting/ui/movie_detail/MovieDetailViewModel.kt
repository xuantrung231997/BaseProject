package com.example.setting.ui.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.core.model.network.movie.Actor
import com.example.core.model.network.movie.ActorsResult
import com.example.core.model.network.movie.MovieDetailResult
import com.example.core.network.Resource
import com.example.core.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    stateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _movieDetail = MutableLiveData<Resource<MovieDetailResult>>()
    val movieDetail: LiveData<Resource<MovieDetailResult>> get() = _movieDetail

    private val _actors = MutableLiveData<Resource<List<Actor>>>()
    val actors: LiveData<Resource<List<Actor>>> get() = _actors

    init {
        val movieId = stateHandle.get<Long>(Constants.BundleKey.KEY_MOVIE_ID) ?: 0
        fetchMovieDetailAndActor(movieId)
    }

    private fun fetchMovieDetailAndActor(movieId: Long) {
        merge(repository.getMovieDetail(movieId), repository.getActors(movieId))
            .onStart {
                _movieDetail.value = Resource.Loading
                _actors.value = Resource.Loading
            }.onEach {
                when (it) {
                    is MovieDetailResult -> _movieDetail.value = Resource.Success(it)
                    is ActorsResult -> _actors.value = Resource.Success(it.getActorList())
                }
            }.catch { e ->
                handleError(e)
            }.launchIn(viewModelScope)
    }


}