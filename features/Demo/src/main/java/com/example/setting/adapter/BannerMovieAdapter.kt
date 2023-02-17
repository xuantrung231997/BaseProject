package com.example.setting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.network.movie.Movie
import com.example.setting.databinding.ItemBannerLayoutBinding

class BannerMovieAdapter : ListAdapter<Movie, BannerMovieAdapter.BannerMovieHolder>(BannerMovieDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BannerMovieHolder(
            ItemBannerLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BannerMovieHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    class BannerMovieHolder(val binding: ItemBannerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {

            }
        }

        fun bindData(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
        }
    }


}

class BannerMovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}

