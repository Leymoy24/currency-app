package com.example.currencyapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException

object RetrofitInstance {

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logger)
        .addInterceptor { chain ->
            try {
                chain.proceed(chain.request())
            } catch (e: SocketTimeoutException) {
                // Обработка исключения
                throw IOException("Превышено время ожидания ответа от сервера", e)
            }
        }
        .build()

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://www.cbr-xml-daily.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}