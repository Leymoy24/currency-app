package com.example.currencyapp.data.api

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

//class NetworkConnectionInterceptor(private val context: Context) : Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        if (!isNetworkAvailable(context)) {
//            throw NoConnectivityException()
//        }
//
//        val request = chain.request()
//        return chain.proceed(request)
//    }
//
//    private fun isNetworkAvailable(context: Context): Boolean {
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo = connectivityManager.activeNetworkInfo
//        return networkInfo != null && networkInfo.isConnected
//    }
//}
//
//class NoConnectivityException : IOException() {
//    override val message: String
//        get() = "Нет соединения с интернетом!"
//}