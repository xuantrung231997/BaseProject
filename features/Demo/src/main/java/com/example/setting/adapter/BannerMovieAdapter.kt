package com.example.setting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.network.movie.Movie
import com.example.setting.databinding.ItemBannerLayoutBinding

class BannerMovieAdapter(originalList: List<Movie>) : RecyclerView.Adapter<BannerMovieAdapter.BannerMovieHolder>() {

    private val newList: List<Movie> =
        listOf(originalList.last()) + originalList + listOf(originalList.first())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BannerMovieHolder(
            ItemBannerLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: BannerMovieHolder, position: Int) {
        holder.bindData(newList[position])
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

    override fun getItemCount(): Int {
        return newList.size
    }

}


