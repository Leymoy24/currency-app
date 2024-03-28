package com.example.currencyapp.data.api.model

data class Currency<T>(
    val ID: String,
    val NumCode: String,
    val CharCode: String,
    val Nominal: Int,
    val Name: String,
    val Value: T,
    val Previous: T
)