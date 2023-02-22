package com.example.setting

import android.os.Bundle
import com.example.core.navigationComponent.BaseNavigator

interface DemoNavigation : BaseNavigator {

    fun openMovieScreenToMovieDetail(bundle: Bundle? = null)
}
