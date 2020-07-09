package com.nikhil.sonimeals.activity

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.internshala.higherorderfunctionalitiessolution.util.FETCH_RESTAURANTS
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.adapter.MenuAdapter
import com.nikhil.sonimeals.database.CartDatabase
import com.nikhil.sonimeals.model.MenuItem
import java.util.HashMap

class MenuActivity : AppCompatActivity() {

    lateinit var recyclerRestaurant: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: MenuAdapter

    val items = arrayListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        recyclerRestaurant = findViewById(R.id.recyclerRestaurant)

        layoutManager = LinearLayoutManager(this)
        val btnProceedToCart: Button = findViewById(R.id.btnProceedToCart)

        btnProceedToCart.visibility = View.GONE

        if (intent != null) {
            val id = intent.getIntExtra("restaurant_id", 1)
            val resName = intent.getStringExtra("restaurant_name")

            if (resName == null || id == null) {
                finish()
                Toast.makeText(this, "Some error occurred!!", Toast.LENGTH_SHORT).show()
            }

            supportActionBar?.title = resName
            val queue = Volley.newRequestQueue(this)
            val url = "$FETCH_RESTAURANTS/$id"


            val restaurantRequest =
                object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                    val data = it.getJSONObject("data")
                    if (data.getBoolean("success")) {

                        val menu = data.getJSONArray("data")

                        for (i in 0 until menu.length()) {
                            val item = menu.getJSONObject(i)
                            val menuObject = MenuItem(
                                item.getInt("id"),
                                item.getString("name"),
                                item.getString("cost_for_one"),
                                item.getString("restaurant_id")
                            )
                            items.add(menuObject)
                            adapter = MenuAdapter(this, items)
                            recyclerRestaurant.adapter = adapter
                            recyclerRestaurant.layoutManager = layoutManager
                        }
                    } else {
                        Toast.makeText(this, data.getString("error_message"), Toast.LENGTH_SHORT)
                            .show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(this, "Error - $it", Toast.LENGTH_SHORT).show()
                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "9bf534118365f1"
                        return headers
                    }
                }
            queue.add(restaurantRequest)
        } else {
            Toast.makeText(
                this@MenuActivity,
                "Some unexpected error occured",
                Toast.LENGTH_SHORT
            ).show()
        }
        val checkCart = Cart(this, 1).execute().get()
        if (!checkCart) {
            btnProceedToCart.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        val cart = Cart(this, 1).execute().get()
        print("Cart $cart")
        if (!cart) {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Confirmation")
            dialog.setMessage("This will clear the cart")
            dialog.setPositiveButton("Yes") { text, listener ->
                val clearCart = Cart(this, 2).execute().get()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            dialog.setNegativeButton("No") { text, listener ->
            }
            dialog.create()
            dialog.show()
        } else {
            val clearCart = Cart(this, 2).execute().get()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }


    class Cart(val context: Context, val mode: Int) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            val db = Room.databaseBuilder(context, CartDatabase::class.java, "cart-db")
                .fallbackToDestructiveMigration().build()
            when (mode) {
                1 -> {
                    val cartItems = db.cartDao().getCart()
                    print("Cart $cartItems")
                    return cartItems.isEmpty()
                }
                2 -> {
                    db.cartDao().clearCart()
                    return true
                }
            }
            return false
        }

    }

    fun proceedToCart() {
        val gson = Gson()
        val orderrList  = gson.toJson(items)
    }
}