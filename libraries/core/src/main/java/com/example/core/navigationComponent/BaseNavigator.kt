package com.example.core.navigationComponent

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController

interface BaseNavigator {
    fun openScreen(
        @IdRes id: Int,
        bundle: Bundle? = null
    )

    val navController: NavController?
    fun navigateUp(): Boolean?
    fun setStartDestination(@IdRes id: Int)
    fun currentFragmentId(): Int?
    fun bind(navController: NavController)
    fun unbind()
}