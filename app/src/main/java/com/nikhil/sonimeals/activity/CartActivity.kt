package com.nikhil.sonimeals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.internshala.higherorderfunctionalitiessolution.util.ConnectionManager
import com.internshala.higherorderfunctionalitiessolution.util.PLACE_ORDER
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.adapter.CartAdapter
import com.nikhil.sonimeals.fragment.OrderPlaced
import org.json.JSONArray
import org.json.JSONObject

class CartActivity : AppCompatActivity() {

//    lateinit var toolbar: androidx.appcompat.widget.Toolbar
//    lateinit var btnPlaceOrder: Button
//    lateinit var frame: FrameLayout
//    lateinit var recyclerCart: RecyclerView
//    lateinit var adapter: CartAdapter
//    lateinit var name: TextView
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_cart)
//
//
//        toolbar = findViewById(R.id.cartToolbar)
//        frame = findViewById(R.id.frame)
//        name = findViewById(R.id.resName)
//        recyclerCart = findViewById(R.id.recyclerCart)
//        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)
//
//        setUpToolbar()
//
//        val items = arrayListOf<MenuItem>()
//
//        val data = intent.getBundleExtra("data")
//        val resId = data?.getString("res_id")
//        val resName = data?.getString("res_name")
//        name.text = resName
//        val foodItems = data?.getString("food_items")
//
//
//        items.addAll(
//            Gson().fromJson(foodItems, Array<MenuItem>::class.java).asList()
//        )
//
//        val layoutManager = LinearLayoutManager(this)
//        adapter = CartAdapter(this@CartActivity, items)
//        recyclerCart.layoutManager = layoutManager
//        recyclerCart.adapter = adapter
//
//        val foodArray = JSONArray()
//        var total = 0
//
//        for (i in 0 until items.size) {
//            val foodIdObject = JSONObject()
//            foodIdObject.put("food_item_id", items[i].id)
//            foodArray.put(i, foodIdObject)
//            total += items[i].cost_for_one.toInt()
//        }
//
//        btnPlaceOrder.text = "Place Order (Rs.${total})"
//        val cartQueue = Volley.newRequestQueue(this@CartActivity)
//
//        btnPlaceOrder.setOnClickListener {
//
//            if (ConnectionManager().isNetworkAvailable(this)) {
//                val params = JSONObject()
////                params.put("user_id", userId)
//                params.put("restaurant_id", resId)
//                params.put("total_cost", total)
//                params.put("food", foodArray)
//
//                val cartRequest =
//                    object : JsonObjectRequest(
//                        Request.Method.POST,
//                        PLACE_ORDER,
//                        params,
//                        Response.Listener {
//                            val data = it.getJSONObject("data")
//                            val success = data.getBoolean("success")
//
//                            if (success) {
//                                supportFragmentManager.beginTransaction()
//                                    .replace(R.id.frame, OrderPlaced()).commit()
//                            } else {
//                                Toast.makeText(
//                                    this@CartActivity, data.getString("errorMessage"),
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//
//                        },
//                        Response.ErrorListener {
//                            Toast.makeText(
//                                this@CartActivity,
//                                "Some unexpected Error Occurred",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }) {
//                        override fun getHeaders(): MutableMap<String, String> {
//                            val headers = HashMap<String, String>()
//                            headers["Content-type"] = "application/json"
//                            headers["token"] = "9bf534118365f1"
//                            return headers
//                        }
//                    }
//                cartQueue.add(cartRequest)
//            } else {
//                val dialog = AlertDialog.Builder(this@CartActivity)
//                dialog.setTitle("Error")
//                dialog.setMessage("No Internet Connection")
//                dialog.setPositiveButton("Open Settings") { text, listener ->
//                    val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
//                    startActivity(intent)
//                    finish()
//                }
//                dialog.setNegativeButton("Exit") { text, listener ->
//                    ActivityCompat.finishAffinity(this@CartActivity)
//                }
//                dialog.create()
//                dialog.show()
//            }
//        }
//
//
//    }

//    private fun setUpToolbar() {
//        setSupportActionBar(toolbar)
//        supportActionBar?.title = "Cart"
//        supportActionBar?.setHomeButtonEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//    }
//
//    override fun onBackPressed() {
//
//        val frag = supportFragmentManager.findFragmentById(R.id.frame)
//
//        when (frag) {
//
//            is OrderPlaced -> {
//                val intent = Intent(this@CartActivity, HomeActivity::class.java)
//                startActivity(intent)
//            }
//
//            else -> super.onBackPressed()
//
//        }
//    }
//
//    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
//
//        onBackPressed()
//
//        return super.onOptionsItemSelected(item)
//    }
}