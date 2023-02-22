package com.example.setting.ui.tabMovie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.core.base.BaseFragment
import com.example.core.network.Resource
import com.example.core.utils.Constants
import com.example.core.utils.Constants.BundleKey.KEY_MOVIE_ID
import com.example.core.utils.initLoadMore
import com.example.core.utils.setStatusBarHeight
import com.example.setting.DemoNavigation
import com.example.setting.R
import com.example.setting.adapter.BannerMovieViewHolder
import com.example.setting.adapter.MovieAdapter
import com.example.setting.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding, MovieViewModel>(R.layout.fragment_movie) {

    @Inject
    lateinit var demoNavigation: DemoNavigation

    private val movieViewModel: MovieViewModel by viewModels()

    override fun getVM() = movieViewModel

    private lateinit var movieAdapter: MovieAdapter

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.statusBarSpace.setStatusBarHeight()
        movieAdapter = MovieAdapter(lifecycleScope) {
            val bundle = Bundle().apply {
                putLong(KEY_MOVIE_ID, it.id)
            }
            demoNavigation.openMovieScreenToMovieDetail(bundle)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerView.apply {
                itemAnimator = null
                hasFixedSize()
                adapter = movieAdapter

                initLoadMore(Constants.LoadMoreThreshold.MOVIE) {
                    movieViewModel.loadMoreMovieIfNeed()
                }
            }

            viewModel = movieViewModel
            executePendingBindings()
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()

        movieViewModel.apply {
            originMovies.observe(viewLifecycleOwner) { result ->
                if (result is Resource.Success && movieViewModel.isNotifyOriginMovies) {
                    movieAdapter.submitList(result.data)
                }
            }

            loadMoreMovies.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Loading -> movieAdapter.addLoadingItem()
                    is Resource.Success -> {
                        movieAdapter.apply {
                            removeLoadingItem()
                            addLoadMoreMovies(result.data)
                        }
                    }
                    else -> return@observe
                }

            }
        }

    }

    override fun setOnClick() {
        super.setOnClick()

        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = false
            recycledBannerViewHolder()
            movieViewModel.fetchMovieInfo()
        }
    }

    private fun recycledBannerViewHolder() {
        val holder = binding.recyclerView.findViewHolderForAdapterPosition(0)
        if (holder is BannerMovieViewHolder) {
            holder.onViewRecycled()
        }
    }

    override fun onDestroyView() {
        recycledBannerViewHolder()
        super.onDestroyView()
    }

}
