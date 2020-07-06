package com.nikhil.sonimeals.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.database.CartEntity
import com.nikhil.sonimeals.database.RestaurantDatabase
import com.nikhil.sonimeals.model.MenuItem

class MenuAdapter(val context: Context, val itemList: ArrayList<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtPrice)
        val llParent: TextView = view.findViewById(R.id.llParent)
        val btnAdd: TextView = view.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = itemList[position]
        holder.txtName.text = item.name
        holder.txtPrice.text = "Rs. ${item.cost_for_one}"

        val itemEntity = CartEntity(
            item.id.toInt(),
            item.name.toString(),
            item.cost_for_one.toString()
        )

        val checkItem = CartDb(context, itemEntity, 3).execute().get()
        if (!checkItem) {
            holder.btnAdd.setBackgroundColor(context.resources.getColor(R.color.addFav))
            holder.btnAdd.text = "Add"
        } else {
            holder.btnAdd.setBackgroundColor(context.resources.getColor(R.color.removeFav))
            holder.btnAdd.text = "Remove"
        }

        holder.btnAdd.setOnClickListener {

            val checkItem = CartDb(context, itemEntity, 3).execute().get()
            if (!checkItem) {
                holder.btnAdd.setBackgroundColor(context.resources.getColor(R.color.addFav))
                holder.btnAdd.text = "Add"
            } else {
                holder.btnAdd.setBackgroundColor(context.resources.getColor(R.color.removeFav))
                holder.btnAdd.text = "Remove"
            }
        }

    }

    class CartDb(val context: Context, val item: CartEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            val cartDb =
                Room.databaseBuilder(context, RestaurantDatabase::class.java, "cart-db").fallbackToDestructiveMigration().build()

            when (mode) {
                1 -> {
//                    Insert
                    val add = cartDb.cartDao().insert(item)
                    cartDb.close()
                    return true
                }
                2 -> {
//                    Delete
                    val delete = cartDb.cartDao().delete(item)
                    cartDb.close()
                    return true
                }
                3 -> {
//                    Item
                    val item = cartDb.cartDao().getItem(item.id)
                    cartDb.close()
                    return true
                }
            }
            return false
        }
    }
}