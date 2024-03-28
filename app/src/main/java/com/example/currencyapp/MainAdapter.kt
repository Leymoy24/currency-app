package com.example.currencyapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.data.api.model.CurrenciesObject
import com.example.currencyapp.data.api.model.Currency
import com.example.currencyapp.model.CurrencyModel

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var listItems = emptyList<CurrencyModel>()

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textName: TextView = itemView.findViewById(R.id.textName)
        var textCharCode: TextView = itemView.findViewById(R.id.textCharCode)
        var textValue: TextView = itemView.findViewById(R.id.textValue)
        var textPreviousValue: TextView = itemView.findViewById(R.id.textPreviousValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency_layout, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = listItems[position]
        holder.textName.text = item.name
        holder.textCharCode.text = item.charCode
        "${item.value} ₽".also { holder.textValue.text = it }
        "${item.previous} ₽".also { holder.textPreviousValue.text = it }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(currencyObject: CurrenciesObject<Double>) {
        currencyObject.Valute.values.map { currency ->
            CurrencyModel(
                name = currency.Name,
                charCode = currency.CharCode,
                value = String.format("%.2f", currency.Value).toDouble(),
                previous = String.format("%.2f", currency.Previous).toDouble()
            )
        }.also { listItems = it }
        notifyDataSetChanged()
    }
}