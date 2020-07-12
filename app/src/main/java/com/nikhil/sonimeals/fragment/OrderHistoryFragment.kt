package com.nikhil.sonimeals.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
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
import com.internshala.higherorderfunctionalitiessolution.util.ConnectionManager
import com.internshala.higherorderfunctionalitiessolution.util.FETCH_PREVIOUS_ORDERS
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.adapter.HistoryOrderAdapter
import com.nikhil.sonimeals.model.Order
import org.json.JSONException

class OrderHistoryFragment : Fragment() {

//    lateinit var recyclerOrderHist: RecyclerView
//    lateinit var orderLayoutManager: LinearLayoutManager
//    lateinit var noHistory: TextView
//    lateinit var rlProgress: RelativeLayout
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_order_history, container, false)
//
////        val userId = sharedPreferences?.getString("user_id", null)
//        recyclerOrderHist = view.findViewById(R.id.recyclerHist)
//        noHistory = view.findViewById(R.id.tvNoOrders)
//        rlProgress = view.findViewById(R.id.rlProgress)
//        noHistory.visibility = View.GONE
//        rlProgress.visibility = View.VISIBLE
//
//        orderLayoutManager = LinearLayoutManager(activity as Context)
//
////        if (userId != null) {
//            val queue = Volley.newRequestQueue(activity as Context)
////            val url = "$FETCH_PREVIOUS_ORDERS$userId"
//            if (ConnectionManager().isNetworkAvailable(activity as Context)) {
//                val histRequest =
//                    object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
//                        try {
//                            val data = it.getJSONObject("data")
//                            val success = data.getBoolean("success")
//                            if (success) {
//                                val dataArray = data.getJSONArray("data")
//                                val orderList = ArrayList<Order>()
//                                for (i in 0 until dataArray.length()) {
//                                    val data = dataArray.getJSONObject(i)
//                                    val foodItemArray = data.getJSONArray("food_items")
//
//                                    val order = Order(
//                                        data.getString("order_id"),
//                                        data.getString("restaurant_name"),
//                                        data.getString("order_placed_at"),
//                                        foodItemArray,
//                                        data.getString("total_cost")
//                                    )
//                                    orderList.add(order)
//                                }
//                                recyclerOrderHist.adapter =
//                                    HistoryOrderAdapter(activity as Context, orderList)
//                                recyclerOrderHist.layoutManager = orderLayoutManager
//
//                                if (orderList.isEmpty()) {
//                                    noHistory.visibility = View.VISIBLE
//                                }
//                                rlProgress.visibility = View.GONE
//                            } else {
//                                if (activity != null) {
//                                    Toast.makeText(
//                                        activity as Context,
//                                        data.getString("errorMessage"),
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            }
//                        } catch (e: JSONException) {
//                            if(activity!=null){
//                                Toast.makeText(activity as Context,"Some Error Occurred",Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                    }, Response.ErrorListener {
//                        if (activity != null) {
//                            Toast.makeText(
//                                activity as Context,
//                                "Some unexpected Error Occurred",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }) {
//                        override fun getHeaders(): MutableMap<String, String> {
//                            val headers = HashMap<String, String>()
//                            headers["Content-type"] = "application/json"
//                            headers["token"] = "9bf534118365f1"
//                            return headers
//                        }
//                    }
//                queue.add(histRequest)
//            } else {
//                val alertDialog = AlertDialog.Builder(activity as Context)
//                alertDialog.setTitle("Error")
//                alertDialog.setMessage("Internet Connection Not Found")
//                alertDialog.setPositiveButton("Open Settings"){ textColor, listener ->
//
//                    val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
//                    startActivity(settingsIntent)
//                    activity?.finish()
//
//                }
//                alertDialog.setNegativeButton("Exit"){text,listener ->
//
//                    ActivityCompat.finishAffinity(activity as Activity)
//
//                }
//                alertDialog.create()
//                alertDialog.show()
//            }
//        } else {
//            if(activity!=null){
//                Toast.makeText(activity as Context,"Please restart or reinstall the app",Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        return view
//    }
}