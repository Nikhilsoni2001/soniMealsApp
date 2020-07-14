package com.nikhil.sonimeals.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.model.FoodItem
import com.nikhil.sonimeals.model.OrderDetails
import java.text.SimpleDateFormat
import java.util.*

class HistoryOrderAdapter(val context: Context, private val orderHistoryList: ArrayList<OrderDetails>) :
    RecyclerView.Adapter<HistoryOrderAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resName: TextView = view.findViewById(R.id.txtCartResName)
        val date: TextView = view.findViewById(R.id.txtDate)
        val recyclerHistory: RecyclerView = view.findViewById(R.id.recyclerOrderList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_history_order, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderHistoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderHistoryobject = orderHistoryList[position]
        holder.resName.text = orderHistoryobject.resName
        holder.date.text = formatDate(orderHistoryobject.orderDate)
        setUpRecycler(holder.recyclerHistory, orderHistoryobject)
    }

    private fun setUpRecycler(recyclerResHistory: RecyclerView, orderHistoryList: OrderDetails) {
        val foodItemsList = ArrayList<FoodItem>()
        for (i in 0 until orderHistoryList.foodItem.length()) {
            val foodJson = orderHistoryList.foodItem.getJSONObject(i)
            foodItemsList.add(
                FoodItem(
                    foodJson.getString("food_item_id"),
                    foodJson.getString("name"),
                    foodJson.getString("cost").toInt()
                )
            )
        }
        val cartItemAdapter = CartAdapter(context, foodItemsList)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerResHistory.layoutManager = mLayoutManager
        recyclerResHistory.itemAnimator = DefaultItemAnimator()
        recyclerResHistory.adapter = cartItemAdapter
    }

    private fun formatDate(dateString: String): String? {
        val inputFormatter = SimpleDateFormat("dd-MM-yy HH:mm:ss", Locale.ENGLISH)
        val date: Date = inputFormatter.parse(dateString) as Date

        val outputFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        return outputFormatter.format(date)
    }
}