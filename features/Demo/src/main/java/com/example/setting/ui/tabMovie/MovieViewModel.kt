package com.example.setting.ui.tabMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.base.BaseViewModel
import com.example.core.model.network.movie.Movie
import com.example.core.model.network.movie.MovieResult
import com.example.core.network.Resource
import com.example.setting.model.BannerMovie
import com.example.setting.model.ItemMovie
import com.example.setting.model.MovieView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : BaseViewModel() {

    private val _originMovies = MutableLiveData<Resource<ArrayList<MovieView>>>()
    val originMovies: LiveData<Resource<ArrayList<MovieView>>> get() = _originMovies

    private val _loadMoreMovies = MutableLiveData<Resource<List<ItemMovie>>>()
    val loadMoreMovies: LiveData<Resource<List<ItemMovie>>> get() = _loadMoreMovies

    private var _isNotifyOriginMovies = true
    val isNotifyOriginMovies: Boolean get() = _isNotifyOriginMovies

    private var currentMoviePageIndex = 1L
    private var totalPageMovieNowPlaying = 0L

    init {
        fetchMovieInfo()
    }

    fun fetchMovieInfo() {
        resetPageIndex()
        repository.getPopular()
            .zip(repository.getMovieInfo()) { popularResult, movieResult ->
                totalPageMovieNowPlaying = movieResult.totalPages
                handleMovieViewList(popularResult, movieResult)
            }.onStart {
                _originMovies.value = Resource.Loading
            }.map { movieViews ->
                _originMovies.value = Resource.Success(movieViews)
            }.catch { e ->
                _originMovies.value = Resource.Error(e)
            }.launchIn(viewModelScope)
    }

    private fun handleMovieViewList(popularResult: MovieResult, movieResult: MovieResult): ArrayList<MovieView> {
        val movieViews = ArrayList<MovieView>()
        movieViews.add(BannerMovie(popularResult.results))
        movieResult.results.forEach {
            movieViews.add(ItemMovie(it))
        }
        return movieViews
    }

    fun loadMoreMovieIfNeed() {
        val resourceMovie = _originMovies.value
        if (resourceMovie is Resource.Success) {
            if (currentMoviePageIndex <= totalPageMovieNowPlaying &&
                _loadMoreMovies.value !is Resource.Loading
            ) {
                currentMoviePageIndex++
                repository.getMovieInfo(currentMoviePageIndex)
                    .onStart {
                        _loadMoreMovies.value = Resource.Loading
                    }.map {
                        val moviesResponse = it.results
                        addMoviesLoadMoreToOriginMovies(resourceMovie, moviesResponse)
                        val itemMovies = arrayListOf<ItemMovie>()
                        moviesResponse.forEach { movie ->
                            itemMovies.add(ItemMovie(movie))
                        }
                        _loadMoreMovies.value = Resource.Success(itemMovies)
                    }.catch { e ->
                        handleError(e)
                    }.launchIn(viewModelScope)
            }
        }
    }

    private fun addMoviesLoadMoreToOriginMovies(
        resourceMovie: Resource.Success<ArrayList<MovieView>>,
        movies: List<Movie>
    ) {
        _isNotifyOriginMovies = false
        movies.forEach {
            resourceMovie.data.add(ItemMovie(it))
        }
        _originMovies.value = resourceMovie
        _isNotifyOriginMovies = true
    }

    private fun resetPageIndex() {
        currentMoviePageIndex = 1L
    }
}



