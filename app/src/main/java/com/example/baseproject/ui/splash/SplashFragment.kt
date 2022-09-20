package com.example.baseproject.ui.splash

import androidx.fragment.app.viewModels
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentSplashBinding
import com.example.baseproject.navigation.AppNavigation
import com.example.core.base.BaseFragment
import com.example.core.utils.collectFlowOnView
import com.example.core.utils.setTextCompute
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<FragmentSplashBinding, SplashViewModel>(R.layout.fragment_splash) {

    @Inject
    lateinit var appNavigation: AppNavigation

    private val viewModel: SplashViewModel by viewModels()

    override fun getVM() = viewModel

    override fun bindingAction() {
        super.bindingAction()

        viewModel.actionSPlash.collectFlowOnView(viewLifecycleOwner) {
            appNavigation.openSplashToHomeScreen()
        }
    }

    override fun bindingStateView() {
        super.bindingStateView()
        viewModel.splashTitle.collectFlowOnView(viewLifecycleOwner) {
            binding.text.setTextCompute(getString(it))
        }
    }

}