package com.nikhil.sonimeals.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.model.FoodItem
import kotlinx.android.synthetic.main.activity_cart.view.*
import java.util.ArrayList

class HistoryListAdapter(
    val context: Context,
    val itemList: ArrayList<FoodItem>
) : RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resName: TextView = view.findViewById(R.id.resName)
        val txtCost: TextView = view.findViewById(R.id.txtCost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_cart, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.resName.text = item.itemName
        holder.txtCost.text = item.itemCostForOne
    }
}