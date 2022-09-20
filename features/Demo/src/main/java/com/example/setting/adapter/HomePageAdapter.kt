package com.example.setting.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.core.adapter.OnItemClickListener
import com.example.core.utils.loadImage
import com.example.setting.R
import com.example.setting.databinding.ItemAlbumLayoutBinding
import com.example.setting.databinding.ItemHomeSlideLayoutBinding
import com.example.setting.databinding.ItemTitleHomeLayoutBinding
import com.example.setting.model.*
import timber.log.Timber

/**
 * Created by vietbh on 2022-03-17.
 */
class HomePageAdapter(
    private val onItemClickListener: OnItemClickListener? = null
) : ListAdapter<HomePageItem, RecyclerView.ViewHolder>(HomePageDiffUtil()) {

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is SlideHome -> R.layout.item_home_slide_layout
        is SongHome -> R.layout.item_song_layout
        is TitleHome -> R.layout.item_title_home_layout
        is AlbumHome -> R.layout.item_album_layout
        else -> 0
    }

    var numberSong = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_home_slide_layout -> HomeSlideViewHolder(
                ItemHomeSlideLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            R.layout.item_song_layout -> {
                numberSong++
                Timber.tag("ahihi").d(" ahehe  $numberSong")
                HomeSongHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_song_layout,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_title_home_layout -> HomeTitleHolder(
                ItemTitleHomeLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            R.layout.item_album_layout -> HomeAlbumHolder(
                ItemAlbumLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> {
                throw Exception("")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HomeSlideViewHolder -> {
                holder.bindData(getItem(position) as SlideHome)
            }
            is HomeSongHolder -> {
                holder.bindData(getItem(position) as SongHome)
            }

            is HomeTitleHolder -> {
                holder.bindData(getItem(position) as TitleHome)
            }

            is HomeAlbumHolder -> {
                holder.bindData(getItem(position) as AlbumHome)
            }
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is HomeSlideViewHolder) {
            holder.onViewRecycled()
        }
    }

}

class HomeAlbumHolder(val binding: ItemAlbumLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(data: AlbumHome) {
        binding.tv.text = data.album.name
        Glide.with(binding.iv)
            .load(data.album.image)
            .transform(CenterCrop(), RoundedCorners(24))
            .into(binding.iv)
    }
}

class HomeSongHolder(val binding: View) : RecyclerView.ViewHolder(binding) {

    fun bindData(data: SongHome) {
        val textView = binding.findViewById<TextView>(R.id.tv)
        val imageView = binding.findViewById<ImageView>(R.id.iv)
        textView.text = data.song.title
//        binding.tv.text = data.song.title
        imageView.loadImage(data.song.image, false)
    }
}

class HomeTitleHolder(val binding: ItemTitleHomeLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: TitleHome) {
        binding.tv.text = data.title
    }
}

class HomeSlideViewHolder(
    val binding: ItemHomeSlideLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    var currentPosition = 50000

    private val adapter by lazy { BannerSlideAdapter() }

    private var runnable: Runnable? = null

    private fun getRunnable() = Runnable {
        binding.viewPager.currentItem = binding.viewPager.currentItem + 1
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

    private var handler: Handler? = null

    fun bindData(data: SlideHome) {
        binding.viewPager.adapter = adapter
        val recyclerView = binding.viewPager.getChildAt(0) as RecyclerView
        recyclerView.apply {
            setPadding(100, 0, 100, 0)
            clipToPadding = false
        }
        adapter.submitList(data.listImage)
        binding.viewPager.setCurrentItem(currentPosition, false)

        handleLoop()

    }

}

class HomePageDiffUtil : DiffUtil.ItemCallback<HomePageItem>() {
    override fun areItemsTheSame(oldItem: HomePageItem, newItem: HomePageItem): Boolean {
        if (oldItem is SlideHome && newItem is SlideHome) {
            return compareListSlide(oldItem, newItem)
        }
        return false
    }

    override fun areContentsTheSame(oldItem: HomePageItem, newItem: HomePageItem): Boolean {
        return false
    }
}

fun compareListSlide(oldItem: SlideHome, newItem: SlideHome): Boolean {
    val listOld = oldItem.listImage
    val listNew = newItem.listImage
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
