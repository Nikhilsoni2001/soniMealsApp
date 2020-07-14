package com.nikhil.sonimeals.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.model.FoodItem

class MenuAdapter(
    val context: Context,
    private val menuList: ArrayList<FoodItem>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    companion object {
        var isCartEmpty = true
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodItemName: TextView = view.findViewById(R.id.txtItemName)
        val foodItemCost: TextView = view.findViewById(R.id.txtCartPrice)
        val sno: TextView = view.findViewById(R.id.txtSNo)
        val addToCart: Button = view.findViewById(R.id.btnAddToCart)
        val removeFromCart: Button = view.findViewById(R.id.btnRemoveFromCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.menu_custom_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    interface OnItemClickListener {
        fun onAddItemClick(foodItem: FoodItem)
        fun onRemoveItemClick(foodItem: FoodItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuObject = menuList[position]
        holder.foodItemName.text = menuObject.itemName
        val cost = "Rs. ${menuObject.cost?.toString()}"
        holder.foodItemCost.text = cost
        holder.sno.text = (position + 1).toString()
        holder.addToCart.setOnClickListener {
            holder.addToCart.visibility = View.GONE
            holder.removeFromCart.visibility = View.VISIBLE
            listener.onAddItemClick(menuObject)
        }

        holder.removeFromCart.setOnClickListener {
            holder.removeFromCart.visibility = View.GONE
            holder.addToCart.visibility = View.VISIBLE
            listener.onRemoveItemClick(menuObject)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
