package com.example.setting.ui.movie_detail

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.core.model.network.movie.Genre
import com.example.core.model.network.movie.MovieDetailResult
import kotlin.math.roundToLong

@BindingAdapter("movie_time")
fun TextView.setMovieTime(movie: MovieDetailResult?) {
    movie?.let {
        text = "${it.releaseDate ?: ""} | ${it.runtime} minutes"
    }
}

@BindingAdapter("movie_language")
fun TextView.setMovieLanguage(language: String?) {
    language?.let {
        text = "Language: ${language.uppercase()}"
    }
}

@BindingAdapter("movie_rating")
fun TextView.setMovieRating(rating: Double?) {
    rating?.let {
        text = "${rating.roundToLong()}/10"
    }
}

@BindingAdapter("movie_category")
fun TextView.setMovieCategory(genres: List<Genre>?) {
    text = if (!genres.isNullOrEmpty()) {
        val category = StringBuilder()
        genres.forEach {
            if (it == genres.last()) {
                category.append(it.name)
            } else {
                category.append(it.name + " | ")
            }
        }
        category
    } else {
        ""
    }
}

