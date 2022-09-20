package com.example.core.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

fun Context.setLanguage(language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val configuration: Configuration = resources.configuration
    configuration.setLocale(locale)
    createConfigurationContext(configuration)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}