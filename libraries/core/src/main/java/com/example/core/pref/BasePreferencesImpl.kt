package com.example.core.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.core.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.PREF_FILE_NAME
)

abstract class BasePreferencesImpl constructor(context: Context) : BasePreferences {

    private val dataStore by lazy {
        context.userPreferencesDataStore
    }

    override fun <T> getValue(key: Preferences.Key<T>): Flow<T?> =
        dataStore.data.map { it[key] }

    override suspend fun <T> putValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit {
            it[key] = value
        }
    }

    override suspend fun clear() {
        dataStore.edit {
            it.clear()
        }
    }

    override suspend fun remove(key: Preferences.Key<*>) {
        dataStore.edit {
            it.remove(key)
        }
    }
}