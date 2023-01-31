package com.example.setting.ui.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.adapter.OnItemClickListener
import com.example.core.base.BaseFragment
import com.example.core.utils.collectFlowOnView
import com.example.core.utils.prefetcher.bindToLifecycle
import com.example.core.utils.prefetcher.setupWithPrefetchViewPool
import com.example.setting.R
import com.example.setting.adapter.HomePageAdapter
import com.example.setting.adapter.HomeSlideViewHolder
import com.example.setting.databinding.FragmentHomePageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment :
    BaseFragment<FragmentHomePageBinding, HomePageViewModel>(R.layout.fragment_home_page) {

    private var adapterHomePage: HomePageAdapter? = null

    private val viewModel: HomePageViewModel by viewModels()

    override fun getVM() = viewModel

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {

        binding.layoutHome.apply {

            adapterHomePage = HomePageAdapter(object : OnItemClickListener {
                override fun onItemClick(position: Int) {
                    //do nothing
                }
            })

            val gridLayoutManager = GridLayoutManager(requireContext(), 2)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapterHomePage?.getItemViewType(position)) {
                        R.layout.item_home_slide_layout -> 2
                        R.layout.item_song_layout -> 2
                        R.layout.item_title_home_layout -> 2
                        R.layout.item_album_layout -> 1
                        else -> 2
                    }
                }
            }

            layoutManager = gridLayoutManager
            setHasFixedSize(true)
            adapter = adapterHomePage

            setupWithPrefetchViewPool {
                setPrefetchBound(viewType = R.layout.item_album_layout, count = 6)
                setPrefetchBound(viewType = R.layout.item_song_layout, count = 16)
                setPrefetchBound(viewType = R.layout.item_home_slide_layout, count = 1)
                setPrefetchBound(viewType = R.layout.item_title_home_layout, count = 2)
            }.bindToLifecycle(viewLifecycleOwner)

        }
    }

    override fun bindingStateView() {
        super.bindingStateView()

        viewModel.listHomePage.collectFlowOnView(viewLifecycleOwner) {
            adapterHomePage?.submitList(it)
        }
    }

    override fun onDestroyView() {
        val holder = binding.layoutHome.findViewHolderForAdapterPosition(0)
        if (holder is HomeSlideViewHolder) {
            holder.onViewRecycled()
        }
        adapterHomePage = null
        super.onDestroyView()
    }

}