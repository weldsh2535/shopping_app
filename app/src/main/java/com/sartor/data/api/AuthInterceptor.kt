package com.sartor.data.api

import android.content.Context
import android.util.Log
import com.sartor.data.db.SharedPreference
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class AuthInterceptor(
    private val token:String ="Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY2MWQyMzlkYTUyNzFhMDA2NTdhYTIwMiIsImVtYWlsIjoid2VsZHNoQGdtYWlsLmNvbSIsInNjb3BlcyI6WyJjdXN0b21lcnMiXSwiaWF0IjoxNzEzMTg1OTE5fQ.Ei8ukv7Ifa80mhobyofYE_AKYcHT5joDpC4v11rU78U"
) : Interceptor {
   // private val sharedPreference = SharedPreference(context!!)

    override fun intercept(chain: Interceptor.Chain): Response {
        var requestBuilder = chain.request()

        //        sharedPreference.getToken()?.let {
//            requestBuilder.addHeader("Authorization", "Bearer $it")
//        }
        requestBuilder = requestBuilder.newBuilder().addHeader("Authorization","$token")
            .build()
        return chain.proceed(requestBuilder)
        // If token has been saved, add it to the request
//        sharedPreference.getToken()?.let {
//            requestBuilder.addHeader("Authorization", "Bearer $it")
//        }
//        try {
//            return chain.proceed(requestBuilder.build())
//        }catch (e: Throwable) {
//            Log.e("AUTH intercept::", "intercept: ")
//            if (e is IOException) {
//                Log.e("TOKEN Exception::", e.toString())
//                throw e
//            } else {
//                Log.e("TOKEN::", e.toString())
//                throw IOException(e)
//            }
//        }
   }
}
