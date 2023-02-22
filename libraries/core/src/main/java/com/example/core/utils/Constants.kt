package com.example.core.utils

object Constants {
    const val PREF_FILE_NAME = "Preferences"
    const val DEFAULT_TIMEOUT = 30
    const val DURATION_TIME_CLICKABLE = 500L

    // define ui state
    const val MODE_LOADING = 0
    const val MODE_SUCCESS = 1
    const val MODE_NO_DATA = 2
    const val MODE_ERROR = 3

    object NetworkRequestCode {
        const val REQUEST_CODE_200 = 200    //normal
        const val REQUEST_CODE_400 = 400    //parameter error
        const val REQUEST_CODE_401 = 401    //unauthorized error
        const val REQUEST_CODE_403 = 403
        const val REQUEST_CODE_404 = 404    //No data error
        const val REQUEST_CODE_500 = 500    //system error
    }

    object LoadMoreThreshold {
        const val DEFAULT = 1
        const val MOVIE = 5
    }

    object BundleKey {
        const val KEY_MOVIE_ID = "KEY_MOVIE_ID"
    }

    object ApiComponents {
        const val BASE_URL = "https://google.com"
        const val URL_MOVIEDB = "https://api.themoviedb.org/3/movie/"
    }

}
