package com.nikhil.sonimeals.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.model.FoodItem
import com.nikhil.sonimeals.model.OrderDetails

class CartAdapter(val context: Context, val item: ArrayList<FoodItem>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
//
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val resName: TextView = view.findViewById(R.id.resName)
//        val txtCost: TextView = view.findViewById(R.id.txtCost)
    }
//
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_cart, parent, false)
        return ViewHolder(view)
    }
//
    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = item[position]
//        holder.resName.text = item.name
//        holder.txtCost.text = item.cost_for_one
    }
}