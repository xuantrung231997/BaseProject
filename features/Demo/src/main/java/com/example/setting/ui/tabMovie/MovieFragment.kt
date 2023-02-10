package com.example.setting.ui.tabMovie

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.core.base.BaseFragment
import com.example.core.network.Resource
import com.example.core.utils.setLayoutBelowStatusBar
import com.example.setting.R
import com.example.setting.adapter.MovieAdapter
import com.example.setting.databinding.FragmentMovieBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BaseFragment<FragmentMovieBinding, MovieViewModel>(R.layout.fragment_movie) {

    private val movieViewModel: MovieViewModel by viewModels()

    override fun getVM() = movieViewModel

    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(requireContext())
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.apply {
            statusBarSpace.setLayoutBelowStatusBar()

            recyclerView.apply {
                itemAnimator = null
                hasFixedSize()
                adapter = movieAdapter
            }

            viewModel = movieViewModel
            executePendingBindings()
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()

        movieViewModel.movies.observe(viewLifecycleOwner) { result ->
            if (result is Resource.Success)
                movieAdapter.submitList(result.data)
        }
    }

    override fun setOnClick() {
        super.setOnClick()

        binding.refreshLayout.setOnRefreshListener {
            binding.refreshLayout.isRefreshing = false
            movieViewModel.fetchMovieInfo()
        }
    }
}