package com.example.core.network

import com.example.core.utils.Constants.NetworkRequestCode.REQUEST_CODE_400
import com.example.core.utils.Constants.NetworkRequestCode.REQUEST_CODE_401
import com.example.core.utils.Constants.NetworkRequestCode.REQUEST_CODE_403
import com.example.core.utils.Constants.NetworkRequestCode.REQUEST_CODE_404
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class NetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        when (response.code) {
            REQUEST_CODE_400, REQUEST_CODE_401, REQUEST_CODE_403, REQUEST_CODE_404 -> {
                Timber.d("network error code ${response.code}")
            }
        }
        return response
    }
}