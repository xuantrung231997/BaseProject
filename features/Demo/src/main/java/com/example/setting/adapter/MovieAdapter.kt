package com.example.setting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.setting.R
import com.example.setting.databinding.ItemHomeSlideLayoutBinding
import com.example.setting.databinding.ItemMovieBinding
import com.example.setting.databinding.LayoutLoadMoreBinding
import com.example.setting.model.BannerMovie
import com.example.setting.model.ItemLoading
import com.example.setting.model.ItemMovie
import com.example.setting.model.MovieView
import kotlinx.coroutines.*

private const val TIME_AUTO_SCROLL_BANNER_MOVIE = 2000L

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

    private lateinit var bannerMovieAdapter: BannerMovieAdapter

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    private var currentBannerMovie = 1

    fun onViewRecycled() {
        currentBannerMovie = 1
        adapterScope.coroutineContext.cancelChildren()
    }

    fun bindData(data: BannerMovie) {
        bannerMovieAdapter = BannerMovieAdapter(data.populars)
        binding.viewPager.apply {
            adapter = bannerMovieAdapter
            val recyclerView = this.getChildAt(0) as RecyclerView
            recyclerView.apply {
                setPadding(100, 0, 100, 0)
                clipToPadding = false
            }
            // setting the current item of the infinite ViewPager to the actual first element
            currentItem = currentBannerMovie
        }

        // function for registering a callback to update the ViewPager
        // and provide a smooth flow for infinite scroll
        onInfinitePageChangeCallback(data.populars.size + 2)

        adapterScope.launch {
            while (isActive) {
                delay(TIME_AUTO_SCROLL_BANNER_MOVIE)
                var currentPosition = binding.viewPager.currentItem
                if (currentPosition == bannerMovieAdapter.itemCount - 1) currentPosition = -1
                withContext(Dispatchers.Main) {
                    binding.viewPager.setCurrentItem(currentPosition + 1, true)
                }
            }
        }
    }

    private fun onInfinitePageChangeCallback(listSize: Int) {
        binding.viewPager.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)

                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        when (currentItem) {
                            listSize - 1 -> setCurrentItem(1, false)
                            0 -> setCurrentItem(listSize - 2, false)
                        }
                    }
                }
            })
        }
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

