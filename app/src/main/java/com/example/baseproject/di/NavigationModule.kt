package com.example.baseproject.di

import com.example.baseproject.navigation.AppNavigation
import com.example.baseproject.navigation.AppNavigatorImpl
import com.example.core.navigationComponent.BaseNavigator
import com.example.setting.DemoNavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
abstract class NavigationModule {

    @Binds
    @ActivityScoped
    abstract fun provideBaseNavigation(navigation: AppNavigatorImpl): BaseNavigator

    @Binds
    @ActivityScoped
    abstract fun provideAppNavigation(navigation: AppNavigatorImpl): AppNavigation

    @Binds
    @ActivityScoped
    abstract fun provideDemoNavigation(navigation: AppNavigatorImpl): DemoNavigation
}