package com.example.setting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.core.utils.setTextCompute
import com.example.setting.databinding.AdapterItemTitleSettingBinding
import com.example.setting.model.SettingItem

class SettingAdapter constructor(
    context: Context,
    val onClickListener: (Int, Boolean) -> Unit,
    val onClearListener: (Int) -> Unit
) : ListAdapter<SettingItem, SettingHolder>(SettingDiffUtil()) {

    private val layoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingHolder {
        return SettingHolder(
            AdapterItemTitleSettingBinding.inflate(layoutInflater, parent, false),
            onClickListener,
            onClearListener
        )
    }

    override fun onBindViewHolder(holder: SettingHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun submitList(list: List<SettingItem>?) {
        super.submitList(list?.let { ArrayList(it) })
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).name.hashCode().toLong()
    }

}

class SettingHolder(
    val binding: AdapterItemTitleSettingBinding,
    private val onClickListener: (Int, Boolean) -> Unit,
    private val onClearListener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.btnClear.setTextCompute("Remove")
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            onClickListener.invoke(adapterPosition, isChecked)
        }

        binding.btnClear.setOnClickListener {
            onClearListener.invoke(adapterPosition)
        }
    }

    fun bindData(data: SettingItem) {
        binding.tvTitle.setTextCompute(data.name)

        binding.checkbox.isChecked = data.isSelected
        binding.checkboxEnd.isChecked = data.isSelectedEnd
    }

}

class SettingDiffUtil : DiffUtil.ItemCallback<SettingItem>() {
    override fun areItemsTheSame(oldItem: SettingItem, newItem: SettingItem): Boolean {
        return oldItem.name == newItem.name && oldItem.isSelected == newItem.isSelected && oldItem.isSelectedEnd == newItem.isSelectedEnd
    }

    override fun areContentsTheSame(oldItem: SettingItem, newItem: SettingItem): Boolean {
        return oldItem == newItem
    }

}