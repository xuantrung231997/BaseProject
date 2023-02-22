package com.example.baseproject.navigation

import android.os.Bundle
import com.example.baseproject.R
import com.example.core.navigationComponent.BaseNavigatorImpl
import com.example.setting.DemoNavigation
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AppNavigatorImpl @Inject constructor() : BaseNavigatorImpl(),
    AppNavigation, DemoNavigation {

    override fun openSplashToHomeScreen(bundle: Bundle?) {
        openScreen(R.id.action_splashFragment_to_homeFragment, bundle)
    }

    override fun openMovieScreenToMovieDetail(bundle: Bundle?) {
        openScreen(R.id.action_homeFragment_to_movieDetailFragment, bundle)
    }

}
