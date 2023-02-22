package com.example.setting.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.model.network.movie.Actor
import com.example.setting.databinding.ItemActorBinding

class ActorAdapter : ListAdapter<Actor, ActorHolder>(ActorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder =
        ActorHolder(ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun submitList(list: List<Actor>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

}

class ActorHolder(val binding: ItemActorBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(data: Actor) {
        binding.apply {
            actor = data
            executePendingBindings()
        }
    }

}

class ActorDiffUtil : DiffUtil.ItemCallback<Actor>() {
    override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean {
        return oldItem == newItem
    }

}
