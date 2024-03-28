package com.example.currencyapp.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.data.api.model.CurrenciesObject
import com.example.currencyapp.data.api.model.Currency
import com.example.currencyapp.data.repository.Repository
import com.example.currencyapp.model.CurrencyModel
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(application: Application, repository: Repository) : ViewModel() {
    private var repository = Repository()
    val listCurrency: MutableLiveData<Response<CurrenciesObject<Double>>> = MutableLiveData()

    fun getCurrency() {
        viewModelScope.launch {
            listCurrency.value = repository.getCurrencies()
        }
    }


    class MainViewModelFactory(
        private val application: Application,
        private val repository: Repository
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(application, repository) as T
    }
}