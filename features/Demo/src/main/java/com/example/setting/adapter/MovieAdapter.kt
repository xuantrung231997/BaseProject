package com.example.setting.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.setting.R
import com.example.setting.databinding.ItemHomeSlideLayoutBinding
import com.example.setting.databinding.ItemMovieBinding
import com.example.setting.databinding.LayoutLoadMoreBinding
import com.example.setting.model.BannerMovie
import com.example.setting.model.ItemLoading
import com.example.setting.model.ItemMovie
import com.example.setting.model.MovieView

class MovieAdapter constructor(
    context: Context,
) : ListAdapter<MovieView, RecyclerView.ViewHolder>(MovieDiffUtil()) {

    private var movieViews = mutableListOf<MovieView>()

    private val layoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun submitList(list: List<MovieView>?) {
        movieViews = list?.map { it.copy() } as MutableList<MovieView>
        super.submitList(movieViews)
    }

    fun addLoadMoreMovies(insertMovies: List<ItemMovie>) {
        if (movieViews.isNotEmpty()) {
            val index = movieViews.size
            movieViews.addAll(insertMovies)
            notifyItemRangeInserted(index, insertMovies.size)
        }
    }

    fun addLoadingItem() {
        val loadingData = ItemLoading()
        movieViews.add(loadingData)
        val index = movieViews.size
        notifyItemRangeInserted(index, 1)
    }

    fun removeLoadingItem() {
        if (movieViews.last() is ItemLoading) {
            movieViews.removeLast()
            val index = movieViews.size
            notifyItemRangeInserted(index, 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_movie ->
                MovieHolder(ItemMovieBinding.inflate(layoutInflater, parent, false))
            R.layout.layout_load_more ->
                LoadingViewHolder(LayoutLoadMoreBinding.inflate(layoutInflater, parent, false))
            R.layout.item_banner_layout ->
                BannerMovieViewHolder(ItemHomeSlideLayoutBinding.inflate(layoutInflater, parent, false))
            else -> {
                throw Exception("")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieHolder -> holder.bindData(getItem(position) as ItemMovie)
            is LoadingViewHolder -> return
            is BannerMovieViewHolder -> holder.bindData(getItem(position) as BannerMovie)
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is ItemMovie -> R.layout.item_movie
        is BannerMovie -> R.layout.item_banner_layout
        is ItemLoading -> R.layout.layout_load_more
        else -> 0
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is BannerMovieViewHolder) {
            holder.onViewRecycled()
        }
    }
}

class LoadingViewHolder(val binding: LayoutLoadMoreBinding) : RecyclerView.ViewHolder(binding.root)

class MovieHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: ItemMovie) {
        binding.itemMovie = data
        binding.executePendingBindings()
    }
}

class BannerMovieViewHolder(val binding: ItemHomeSlideLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    private var currentPosition = 0

    private val adapter by lazy { BannerMovieAdapter() }

    private var runnable: Runnable? = null

    private var handler: Handler? = null

    private fun getRunnable() = Runnable {
        if (binding.viewPager.currentItem >= binding.viewPager.adapter!!.itemCount - 1) {
            binding.viewPager.currentItem = 0
        } else {
            binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        }

        handleLoop()
    }

    private fun stopLoop() {
        runnable?.let { handler?.removeCallbacks(it) }
    }

    fun onViewRecycled() {
        currentPosition = binding.viewPager.currentItem
        stopLoop()
        handler = null
        runnable = null
    }

    private fun handleLoop() {
        if (handler == null) {
            handler = Handler(Looper.getMainLooper())
        }
        if (runnable == null) {
            runnable = getRunnable()
        }
        stopLoop()
        runnable?.let { handler?.postDelayed(it, 2000) }
    }

    fun bindData(data: BannerMovie) {
        binding.viewPager.adapter = adapter
        val recyclerView = binding.viewPager.getChildAt(0) as RecyclerView
        recyclerView.apply {
            setPadding(100, 0, 100, 0)
            clipToPadding = false
        }
        adapter.submitList(data.populars)
        binding.viewPager.setCurrentItem(currentPosition, false)

        handleLoop()
    }

}

class MovieDiffUtil : DiffUtil.ItemCallback<MovieView>() {
    override fun areItemsTheSame(oldItem: MovieView, newItem: MovieView): Boolean {
        if (oldItem is BannerMovie && newItem is BannerMovie) {
            return compareListSlide(oldItem, newItem)
        }
        if (oldItem is ItemMovie && newItem is ItemMovie) {
            return oldItem.movie.id == newItem.movie.id
        }
        return false
    }

    override fun areContentsTheSame(oldItem: MovieView, newItem: MovieView): Boolean {
        return false
    }

}

fun compareListSlide(oldItem: BannerMovie, newItem: BannerMovie): Boolean {
    val listOld = oldItem.populars
    val listNew = newItem.populars
    if (listNew.size != listOld.size) {
        return false
    }
    listNew.forEachIndexed { index, s ->
        if (listOld[index] != s) {
            return false
        }
    }
    return true
}

