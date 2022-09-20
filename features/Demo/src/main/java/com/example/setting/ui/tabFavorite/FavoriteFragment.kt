package com.example.setting.ui.tabFavorite

import androidx.fragment.app.viewModels
import com.example.core.base.BaseFragment
import com.example.core.utils.setLanguage
import com.example.core.utils.setOnSafeClickListener
import com.example.setting.R
import com.example.setting.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment :
    BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>(R.layout.fragment_favorite) {

    private val viewModel: FavoriteViewModel by viewModels()

    override fun getVM(): FavoriteViewModel = viewModel

    override fun setOnClick() {
        super.setOnClick()

        binding.btnVietNam.setOnSafeClickListener {
            changeLanguage("vi")
        }

        binding.btnEnglish.setOnSafeClickListener {
            changeLanguage("en")
        }
    }

    private fun changeLanguage(language: String) {
        requireContext().setLanguage(language)
        viewModel.setLanguage(language)

        binding.btnVietNam.text = getString(R.string.viet_nam)
        binding.btnEnglish.text = getString(R.string.english)
    }

}