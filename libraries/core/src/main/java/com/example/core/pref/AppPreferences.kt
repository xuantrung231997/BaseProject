package com.example.core.pref

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext context: Context
) : BasePreferencesImpl(context), RxPreferences {

    private object PreferencesKeys {
        val PREF_PARAM_ACCESS_TOKEN = stringPreferencesKey("PREF_PARAM_ACCESS_TOKEN")
        val PREF_PARAM_LANGUAGE = stringPreferencesKey("PREF_PARAM_LANGUAGE")
    }

    override fun getToken(): Flow<String?> = getValue(PreferencesKeys.PREF_PARAM_ACCESS_TOKEN)

    override suspend fun setUserToken(userToken: String) {
        putValue(PreferencesKeys.PREF_PARAM_ACCESS_TOKEN, userToken)
    }

    override fun getLanguage(): Flow<String?> = getValue(PreferencesKeys.PREF_PARAM_LANGUAGE)

    override suspend fun setLanguage(language: String) {
        putValue(PreferencesKeys.PREF_PARAM_LANGUAGE, language)
    }

    override fun logout() {

    }

}