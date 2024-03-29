package com.example.currencyapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyapp.R
import com.example.currencyapp.data.api.model.CurrenciesObject
import com.example.currencyapp.ui.model.CurrencyModel

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var listItems = emptyList<CurrencyModel>()

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textName: TextView = itemView.findViewById(R.id.textName)
        var textCharCode: TextView = itemView.findViewById(R.id.textCharCode)
        var textValue: TextView = itemView.findViewById(R.id.textValue)
        var textPreviousValue: TextView = itemView.findViewById(R.id.textPreviousValue)
        var imageViewArrow: ImageView = itemView.findViewById(R.id.imageViewArrow)
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

        if (item.value > item.previous) {
            holder.imageViewArrow.setImageResource(R.drawable.arrow_top_icon)
        } else if (item.value < item.previous) {
            holder.imageViewArrow.setImageResource(R.drawable.arrow_bottom_icon)
        } else {
            holder.imageViewArrow.visibility = View.GONE
        }
    }

    fun setList(currencyObject: CurrenciesObject<Double>) {
        val currencyList = currencyObject.Valute.values.map { currency ->
            CurrencyModel(
                name = currency.Name,
                charCode = currency.CharCode,
                value = currency.Value,
                previous = currency.Previous
            )
        }
        listItems = currencyList
        notifyItemRangeChanged(0, itemCount)
    }
}