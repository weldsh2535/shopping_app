package com.sartor.data.api

import android.content.Context
import android.util.Log
import com.sartor.data.db.SharedPreference
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class AuthInterceptor(context: Context?) : Interceptor {
    private val sharedPreference = SharedPreference(context!!)

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        // If token has been saved, add it to the request
        sharedPreference.getToken()?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        try {
            return chain.proceed(requestBuilder.build())
        }catch (e: Throwable) {
            Log.e("AUTH intercept::", "intercept: ")
            if (e is IOException) {
                Log.e("TOKEN Exception::", e.toString())
                throw e
            } else {
                Log.e("TOKEN::", e.toString())
                throw IOException(e)
            }
        }

    }
}
