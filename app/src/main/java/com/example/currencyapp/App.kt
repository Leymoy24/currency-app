package com.example.currencyapp

import android.app.Application
import android.content.Context
import com.example.currencyapp.data.repository.Repository

class App : Application() {
    val repository by lazy { Repository() }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        lateinit var appContext: Context
    }
}