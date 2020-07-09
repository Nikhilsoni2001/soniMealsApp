package com.nikhil.sonimeals.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.model.FoodItem
import com.nikhil.sonimeals.model.Order
import java.util.ArrayList

class HistoryOrderAdapter(val context: Context, private val order: ArrayList<Order>) :
    RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resName: TextView = view.findViewById(R.id.resName)
        val date: TextView = view.findViewById(R.id.txtDate)
        val recyclerHistory: RecyclerView = view.findViewById(R.id.recyclerOrderList)
        val txtCost: TextView = view.findViewById(R.id.txtCost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_history_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return order.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = order[position]
        holder.resName.text = item.orderResName
        holder.date.text = item.orderDate
        holder.txtCost.text = item.orderCost

        val orderArray = item.orderArray
        val orderList = ArrayList<FoodItem>()

        for (i in 0 until orderArray.length()) {
            val orderListObject = orderArray.getJSONObject(i)
            orderList.add(
                FoodItem(
                    orderListObject.getString("food_item_id"),
                    orderListObject.getString("name"),
                    orderListObject.getString("cost")
                )
            )
        }
        holder.recyclerHistory.adapter = HistoryListAdapter(context, orderList)
        holder.recyclerHistory.layoutManager = LinearLayoutManager(context)
    }
}