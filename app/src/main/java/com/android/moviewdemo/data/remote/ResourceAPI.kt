package com.android.moviewdemo.data.remote

import android.content.Context
import android.text.TextUtils
import android.util.MalformedJsonException
import com.android.moviewdemo.data.remote.error.ErrorResponse
import com.android.moviewdemo.R
import com.android.moviewdemo.app.MovieApplication
import com.google.gson.Gson
import com.trapeze.maas_android.data.remote.Status
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException

data class ResourceAPI<out T>(val status: Status, val data: T?, val error: Throwable?, val message: String?) {
    companion object {
        fun <T> success(data: T): ResourceAPI<T> =
            ResourceAPI(status = Status.SUCCESS, data = data,error = null, message = null)

        fun <T> error(data: T?,error:Throwable, message: String): ResourceAPI<T> =
            ResourceAPI(status = Status.ERROR, data = data,error = error, message = message)

        fun <T> loading(data: T?): ResourceAPI<T> =
            ResourceAPI(status = Status.LOADING, data = data,error = null, message = null)
    }

    var errorCode: String = ""
    var errorMessage: String = ""

    init {
        error?.let { throwable ->
            val context: Context = MovieApplication.applicationContext()
            this.errorMessage = context.getString(R.string.unknownError)
            when (throwable) {
                is SocketTimeoutException -> {
                    this.errorMessage = context.getString(R.string.requestTimeOutError)
                    this.errorCode = context.getString(R.string.networkErrorCode)
                }
                is MalformedJsonException -> {
                    this.errorMessage = context.getString(R.string.responseMalformedJson)
                    this.errorCode = context.getString(R.string.errorCodeMalformedJson)
                }
                is IOException -> {
                    this.errorMessage = context.getString(R.string.error_message)
                    this.errorCode = context.getString(R.string.networkErrorCode)
                }

                is HttpException -> {
                    try {
                        val apiErrorResponse: ErrorResponse = Gson().fromJson(throwable.response()?.errorBody()?.string(), ErrorResponse::class.java)
                        if (!TextUtils.isEmpty(apiErrorResponse.errorCode))
                            this.errorCode = apiErrorResponse.errorCode
                        if (!TextUtils.isEmpty(apiErrorResponse.errorMessage))
                            this.errorMessage = apiErrorResponse.errorMessage
                    } catch (ex: Exception) {
                        this.errorMessage = context.getString(R.string.unknownError)
                    }
                }
            }
            Timber.e("ResourceAPI :: errorCode -> $errorCode , errorMessage -> $errorMessage")
        }
    }
}