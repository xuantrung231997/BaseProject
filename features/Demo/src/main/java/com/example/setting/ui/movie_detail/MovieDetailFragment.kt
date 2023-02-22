package com.example.setting.ui.movie_detail

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewTreeObserver
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.base.BaseFragment
import com.example.core.network.Resource
import com.example.core.utils.setStatusBarHeight
import com.example.setting.R
import com.example.setting.adapter.ActorAdapter
import com.example.setting.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>(R.layout.fragment_movie_detail) {

    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    override fun getVM() = movieDetailViewModel

    private val actorAdapter: ActorAdapter by lazy {
        ActorAdapter()
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        binding.statusBarSpace.setStatusBarHeight()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            recyclerView.apply {
                itemAnimator = null
                hasFixedSize()
                layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
                adapter = actorAdapter

                viewModel = movieDetailViewModel
                executePendingBindings()
            }
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()

        movieDetailViewModel.apply {
            movieDetail.observe(viewLifecycleOwner) {
                if (it is Resource.Success) {
                    binding.apply {
                        movie = it.data
                        executePendingBindings()
                        handleReadMoreState()
                    }
                }
            }

            actors.observe(viewLifecycleOwner) {
                if (it is Resource.Success) actorAdapter.submitList(it.data)
            }
        }
    }

    override fun setOnClick() {
        super.setOnClick()
        binding.apply {
            readMore.setOnClickListener {
                content.maxLines = Integer.MAX_VALUE
                it.visibility = GONE
            }
        }
    }

    private fun handleReadMoreState() {
        binding.content.also {
            it.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val isEllipsized = (it.layout?.getEllipsisCount(it.lineCount - 1) ?: 0) > 0
                    if (isEllipsized) {
                        binding.readMore.visibility = VISIBLE
                    }
                    it.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }
}
