package com.nikhil.sonimeals.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.internshala.higherorderfunctionalitiessolution.util.ConnectionManager
import com.internshala.higherorderfunctionalitiessolution.util.FETCH_RESTAURANTS
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.adapter.MenuAdapter
import org.json.JSONException
import java.util.ArrayList
import java.util.HashMap

class MenuActivity : AppCompatActivity() {
//
//    lateinit var toolbar: Toolbar
//    lateinit var rlProgress: RelativeLayout
//    lateinit var progress: ProgressBar
//    lateinit var btnProceedToCart: Button
//
//    var resId: Int? = null
//    var resName: String? = ""
//    private var orderList = ArrayList<MenuItem>()
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_menu)
//
//        toolbar = findViewById(R.id.ResMenuToolbar)
//        btnProceedToCart = findViewById(R.id.btnProceedToCart)
//        rlProgress = findViewById(R.id.rlProgress)
//        progress = findViewById(R.id.progressBar)
//        btnProceedToCart.visibility = View.GONE
//        rlProgress.visibility = View.VISIBLE
//        progress.visibility = View.VISIBLE
//
//        val recyclerRestaurant: RecyclerView = findViewById(R.id.recyclerRestaurant)
//        val layoutManager = LinearLayoutManager(this)
//
//        val items = ArrayList<MenuItem>()
//
//        if (intent != null) {
//            resId = intent.getIntExtra("restaurant_id", 1)
//            resName = intent.getStringExtra("restaurant_name")
//
//        } else if (resName == "" || resId == null) {
//            finish()
//            Toast.makeText(this, "Some error occurred!!", Toast.LENGTH_SHORT).show()
//        } else {
//            finish()
//            Toast.makeText(
//                this@MenuActivity,
//                "Some unexpected error occured",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//
//        setUpToolbar()
//
//        val queue = Volley.newRequestQueue(this)
//        val url = "$FETCH_RESTAURANTS/$resId"
//
//        if (ConnectionManager().isNetworkAvailable(this as Context)) {
//            val restaurantRequest =
//                object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
//                    try {
//
//                        rlProgress.visibility = View.GONE
//                        progress.visibility = View.GONE
//
//                        val data = it.getJSONObject("data")
//                        val result = data.getBoolean("success")
//
//                        if (result) {
//                            val menu = data.getJSONArray("data")
//
//                            for (i in 0 until menu.length()) {
//                                val item = menu.getJSONObject(i)
//                                val menuObject = MenuItem(
//                                    item.getInt("id"),
//                                    item.getString("name"),
//                                    item.getString("cost_for_one"),
//                                    item.getString("restaurant_id")
//                                )
//                                items.add(menuObject)
//                                val adapter = MenuAdapter(this as Context, items)
//                                recyclerRestaurant.layoutManager = layoutManager
//                                recyclerRestaurant.adapter = adapter
//                            }
//                        } else {
//                            Toast.makeText(
//                                this,
//                                data.getString("error_message"),
//                                Toast.LENGTH_SHORT
//                            )
//                                .show()
//                        }
//                    } catch (e: JSONException) {
//                        Toast.makeText(
//                            this, "Some Unexpected Error Occurred", Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                }, Response.ErrorListener {
//                    Toast.makeText(this, "Error - $it", Toast.LENGTH_SHORT).show()
//                }) {
//                    override fun getHeaders(): MutableMap<String, String> {
//                        val headers = HashMap<String, String>()
//                        headers["Content-type"] = "application/json"
//                        headers["token"] = "9bf534118365f1"
//                        return headers
//                    }
//                }
//            queue.add(restaurantRequest)
//        } else {
//            val dialog = AlertDialog.Builder(this)
//            dialog.setTitle("Error")
//            dialog.setMessage("No Internet Connection")
//            dialog.setPositiveButton("Open Settings") { text, listener ->
//                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
//                startActivity(intent)
//                finish()
//            }
//            dialog.setNegativeButton("Exit") { text, listener ->
//                ActivityCompat.finishAffinity(this)
//            }
//            dialog.create()
//            dialog.show()
//        }
//
//
//        btnProceedToCart.setOnClickListener {
//
//            val gson = Gson()
//
//            val items = gson.toJson(orderList)
//
//            val data = Bundle()
//            data.putString("res_id", resId.toString())
//            data.putString("res_name", resName)
//            data.putString("food_items", items)
//            val cartIntent = Intent(this, CartActivity::class.java)
//            cartIntent.putExtra("data", data)
//            startActivity(cartIntent)
//        }
//
//    }
//
//    fun setUpToolbar() {
//        setSupportActionBar(toolbar)
//        supportActionBar?.title = resName
//        supportActionBar?.setHomeButtonEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//    }
//
//    fun onAddItemClick(foodItem: MenuItem) {
//        orderList.add(foodItem)
//        if (orderList.size > 0) {
//            btnProceedToCart.visibility = View.VISIBLE
//        }
//    }
//
//    fun onRemoveItemClick(foodItem: MenuItem) {
//        orderList.remove(foodItem)
//        if (orderList.isEmpty()) {
//            btnProceedToCart.visibility = View.GONE
//        }
//    }
//
////    override fun onBackPressed() {
////        val cart = Cart(this, 1).execute().get()
////        print("Cart $cart")
////        if (!cart) {
////            val dialog = AlertDialog.Builder(this)
////            dialog.setTitle("Confirmation")
////            dialog.setMessage("This will clear the cart")
////            dialog.setPositiveButton("Yes") { text, listener ->
////                val clearCart = Cart(this, 2).execute().get()
////                val intent = Intent(this, HomeActivity::class.java)
////                startActivity(intent)
////            }
////            dialog.setNegativeButton("No") { text, listener ->
////            }
////            dialog.create()
////            dialog.show()
////        } else {
////            val clearCart = Cart(this, 2).execute().get()
////            val intent = Intent(this, HomeActivity::class.java)
////            startActivity(intent)
////        }
////    }
//
//
////    class Cart(val context: Context, val mode: Int) : AsyncTask<Void, Void, Boolean>() {
////        override fun doInBackground(vararg params: Void?): Boolean {
////            val db = Room.databaseBuilder(context, CartDatabase::class.java, "cart-db")
////                .fallbackToDestructiveMigration().build()
////            when (mode) {
////                1 -> {
////                    val cartItems = db.cartDao().getCart()
////                    print("Cart $cartItems")
////                    return cartItems.isEmpty()
////                }
////                2 -> {
////                    db.cartDao().clearCart()
////                    return true
////                }
////            }
////            return false
////        }
////
////    }
//
//    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
//        val itemId = item.itemId
//        if (itemId == android.R.id.home) {
//            super.onBackPressed()
//            return true
//        }
//        return onOptionsItemSelected(item)
//    }

}