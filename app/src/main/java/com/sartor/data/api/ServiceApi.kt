package com.sartor.data.api

import android.content.Context
import com.google.gson.JsonObject
import com.sartor.data.api.response.ReviewResponse
import com.sartor.util.constants.Constant


class ApiUtils {


    fun client(
        context: Context?
    ): ApiService {
        return ApiClient.getClient(context).create(ApiService::class.java)

    }
}