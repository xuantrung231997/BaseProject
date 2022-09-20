package com.example.core.pref

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface BasePreferences {

    fun <T> getValue(key: Preferences.Key<T>): Flow<T?>
    suspend fun <T> putValue(key: Preferences.Key<T>, value: T)

    suspend fun clear()

    suspend fun remove(key: Preferences.Key<*>)
}