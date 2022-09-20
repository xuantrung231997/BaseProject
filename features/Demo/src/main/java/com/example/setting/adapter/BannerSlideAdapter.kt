package com.example.setting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.setting.databinding.ItemBannerLayoutBinding

class BannerSlideAdapter :
    ListAdapter<String, BannerSlideAdapter.BannerSlideHolder>(BannerSlideDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BannerSlideHolder(
            ItemBannerLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return 100000
    }

    override fun onBindViewHolder(holder: BannerSlideHolder, position: Int) {
        holder.bindData(currentList[position.rem(currentList.size)])
    }

    class BannerSlideHolder(val binding: ItemBannerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {

            }
        }

        fun bindData(image: String) {
            Glide.with(binding.itemBanner)
                .load(image)
                .transform(CenterCrop(), RoundedCorners(24))
                .into(binding.itemBanner)
        }
    }


}

class BannerSlideDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}