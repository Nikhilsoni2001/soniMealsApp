package com.nikhil.sonimeals.adapter

import android.content.Context
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.database.CartDatabase
import com.nikhil.sonimeals.database.CartEntity
import com.nikhil.sonimeals.model.MenuItem

class MenuAdapter(val context: Context, val itemList: ArrayList<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtName: TextView = view.findViewById(R.id.txtName)
        val txtPrice: TextView = view.findViewById(R.id.txtCost)
        val llParent: LinearLayout = view.findViewById(R.id.llParent)
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

        val itemEntity = CartEntity(
            item.id,
            item.name,
            item.cost_for_one
        )

        val checkItem = CartDb(context, itemEntity, 3).execute().get()
        if (!checkItem) {
            holder.btnAdd.setBackgroundColor(context.resources.getColor(R.color.addFav))
            holder.btnAdd.text = "Add"
        } else {
            holder.btnAdd.text = "Remove"
            holder.btnAdd.setBackgroundColor(context.resources.getColor(R.color.removeFav))
        }

        holder.btnAdd.setOnClickListener {

            val checkItem = CartDb(context, itemEntity, 3).execute().get()
            if (!checkItem) {
                val remove = CartDb(context, itemEntity, 2).execute()
                holder.btnAdd.setBackgroundColor(context.resources.getColor(R.color.addFav))
                holder.btnAdd.text = "Add"
            } else {
                val add = CartDb(context, itemEntity, 1).execute()
                holder.btnAdd.text = "Remove"
                holder.btnAdd.setBackgroundColor(context.resources.getColor(R.color.removeFav))
            }
        }

    }

    class CartDb(val context: Context, val item: CartEntity, val mode: Int) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            val cartDb =
                Room.databaseBuilder(context, CartDatabase::class.java, "cart-db")
                    .fallbackToDestructiveMigration().build()

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
                    return item != null
                }
            }
            return false
        }
    }
}