package com.nikhil.sonimeals.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.model.FoodItem

class CartAdapter(val context: Context, private val cartList: ArrayList<FoodItem>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
//
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.txtCartResName)
        val itemCost: TextView = view.findViewById(R.id.txtCartPrice)
    }
//
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_custom_row, parent, false)
        return ViewHolder(view)
    }
//
    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartObject = cartList[position]
        holder.itemName.text = cartObject.itemName
        val cost = "Rs. ${cartObject.cost?.toString()}"
        holder.itemCost.text = cost
    }
}