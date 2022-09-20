package com.example.core.module

import com.example.core.pref.AppPreferences
import com.example.core.pref.RxPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SharedPrefsModule {

    @Binds
    @Singleton
    abstract fun provideRxPreference(preferences: AppPreferences): RxPreferences
}