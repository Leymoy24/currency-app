package com.example.currencyapp

import android.app.Application
import android.content.Context
import com.example.currencyapp.data.repository.Repository
import com.example.currencyapp.util.NetworkConnection

class App : Application() {
    val repository by lazy { Repository() }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        networkConnection = NetworkConnection(appContext)
    }

    companion object {
        lateinit var appContext: Context
        lateinit var networkConnection: NetworkConnection
    }
}