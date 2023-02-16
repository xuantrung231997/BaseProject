package com.example.setting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.network.movie.Movie
import com.example.core.model.network.movie.Movie.Companion.LOADING_MOVIE_ID
import com.example.setting.R
import com.example.setting.databinding.ItemMovieBinding
import com.example.setting.databinding.LayoutLoadMoreBinding

class MovieAdapter constructor(
    context: Context,
) : ListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffUtil()) {

    private var movies = mutableListOf<Movie>()

    private val layoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun submitList(list: List<Movie>?) {
        movies = (list?.map { it.copy() } ?: run { mutableListOf() }) as MutableList<Movie>
        super.submitList(movies)
    }

    fun addLoadMoreMovies(insertMovies: List<Movie>) {
        if (movies.isNotEmpty()) {
            // always remove item loading if any
            if (movies.last().id == LOADING_MOVIE_ID) {
                movies.removeLast()
            }
            val index = movies.size
            movies.addAll(insertMovies)
            notifyItemRangeInserted(index, insertMovies.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_movie) {
            MovieHolder(ItemMovieBinding.inflate(layoutInflater, parent, false))
        } else {
            LoadingViewHolder(LayoutLoadMoreBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieHolder) holder.bindData(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).id == LOADING_MOVIE_ID) R.layout.layout_load_more else R.layout.item_movie
    }
}

class LoadingViewHolder(val binding: LayoutLoadMoreBinding) : RecyclerView.ViewHolder(binding.root)

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
