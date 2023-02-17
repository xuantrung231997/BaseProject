package com.example.setting.model

import com.example.core.model.network.movie.Movie

open class MovieView {

    open fun copy(): MovieView {
        return MovieView()
    }
}

class BannerMovie(val populars: List<Movie>) : MovieView() {
    override fun copy(): MovieView {
        return BannerMovie(populars)
    }
}

class ItemMovie(val movie: Movie) : MovieView() {
    override fun copy(): MovieView {
        return ItemMovie(movie)
    }
}

class ItemLoading : MovieView() {
    override fun copy(): MovieView {
        return ItemLoading()
    }
}
