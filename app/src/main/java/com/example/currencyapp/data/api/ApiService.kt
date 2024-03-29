package com.example.currencyapp.data.api

import com.example.currencyapp.data.api.model.CurrenciesObject
import com.example.currencyapp.data.api.model.Currency
import com.example.currencyapp.ui.model.CurrencyModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("daily_json.js")
    suspend fun getCurrencies(): Response<CurrenciesObject<Double>>
}