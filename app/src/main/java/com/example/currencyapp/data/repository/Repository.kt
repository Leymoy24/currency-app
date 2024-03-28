package com.example.currencyapp.data.repository

import com.example.currencyapp.data.api.RetrofitInstance
import com.example.currencyapp.data.api.model.CurrenciesObject
import retrofit2.Response

class Repository {
    suspend fun getCurrencies(): Response<CurrenciesObject<Double>> {
        return RetrofitInstance.api.getCurrencies()
    }
}