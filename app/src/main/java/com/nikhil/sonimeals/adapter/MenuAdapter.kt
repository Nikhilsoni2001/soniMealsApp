package com.nikhil.sonimeals.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.activity.MenuActivity
import com.nikhil.sonimeals.model.MenuItem

class MenuAdapter(val context: Context, val itemList: ArrayList<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtCost)
        val btnAdd: TextView = view.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_menu, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.txtName.text = item.name
        holder.txtPrice.text = "Rs.${item.cost_for_one}"


        holder.btnAdd.text = "Add"
        val fav = ContextCompat.getColor(context, R.color.addFav)
        holder.btnAdd.setBackgroundColor(fav)


        holder.btnAdd.setOnClickListener {
            if (holder.btnAdd.text == "Add") {
                holder.btnAdd.text = "Remove"
                val noFav = ContextCompat.getColor(context, R.color.removeFav)
                holder.btnAdd.setBackgroundColor(noFav)
                MenuActivity().onAddItemClick(item)
            } else if(holder.btnAdd.text == "Remove") {
                holder.btnAdd.text = "Add"
                val fav = ContextCompat.getColor(context, R.color.addFav)
                holder.btnAdd.setBackgroundColor(fav)
                MenuActivity().onRemoveItemClick(item)
            }

        }


    }
}