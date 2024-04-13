package com.sartor.ui.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.sartor.R
import com.sartor.data.model.Currency
import kotlin.collections.ArrayList


class CurrencyAdapter(private val context: Context?, private val arrayList: ArrayList<Currency>) : BaseAdapter() {

    override fun getCount(): Int {
        return arrayList.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.child_currency, parent, false)
        val textSymbol:TextView = convertView.findViewById(R.id.textSymbol)
        val textCode:TextView = convertView.findViewById(R.id.textCode)
        val textCountry:TextView = convertView.findViewById(R.id.textCountry)
        textSymbol.text = arrayList[position].symbol
        textCode.text =arrayList[position].code
        textCountry.text = arrayList[position].country
        return convertView
    }
}
