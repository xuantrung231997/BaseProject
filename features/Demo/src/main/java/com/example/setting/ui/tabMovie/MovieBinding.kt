package com.example.setting.ui.tabMovie

import android.widget.ImageView
import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.setting.R

@BindingAdapter("loadImageMovie")
fun ImageView.loadImageMovie(url: String?) {
    val urlConvert = "https://image.tmdb.org/t/p/w300${url ?: ""}"
    Glide.with(this)
        .load(urlConvert)
        .override(this.width, this.height)
        .optionalCenterCrop()
        .placeholder(R.color.gray)
        .into(this)
}

@BindingAdapter("ratingBar")
fun RatingBar.setRatingBar(rating: Double?) {
    rating?.let {
        setRating(((rating / 10) * 5).toFloat())
    }
}
