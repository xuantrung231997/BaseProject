package com.example.setting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.network.movie.Movie
import com.example.setting.databinding.ItemMovieBinding

class MovieAdapter constructor(
    context: Context,
) : ListAdapter<Movie, MovieHolder>(MovieDiffUtil()) {

    private val layoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun submitList(list: List<Movie>?) {
        val result = list?.map { it.copy() }
        super.submitList(result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(ItemMovieBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindData(getItem(position))
    }

}

class MovieHolder(
    val binding: ItemMovieBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: Movie) {
        binding.movie = data
        binding.executePendingBindings()
    }
}

class MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}
