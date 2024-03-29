package com.nikhil.sonimeals.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.nikhil.sonimeals.util.FETCH_PREVIOUS_ORDERS
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.adapter.HistoryOrderAdapter
import com.nikhil.sonimeals.model.OrderDetails
import com.nikhil.sonimeals.util.DrawerLocker
import org.json.JSONException

class OrderHistoryFragment : Fragment() {

    lateinit var recyclerOrderHistory: RecyclerView
    lateinit var historyOrderAdapter: HistoryOrderAdapter
    private var orderHistoryList = ArrayList<OrderDetails>()
    private lateinit var llHasOrders: LinearLayout
    private lateinit var rlNoOrders: RelativeLayout
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var rlLoading: RelativeLayout
    private var userId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_history, container, false)


        (activity as DrawerLocker).setDrawerEnabled(true)
        llHasOrders = view.findViewById(R.id.llHasOrders)
        rlNoOrders = view.findViewById(R.id.rlNoOrders)
        recyclerOrderHistory = view.findViewById(R.id.recyclerOrderHistory)
        rlLoading = view?.findViewById(R.id.rlLoading) as RelativeLayout
        rlLoading.visibility = View.VISIBLE
        sharedPreferences =
            (activity as Context).getSharedPreferences("FoodApp", Context.MODE_PRIVATE)
        userId = sharedPreferences.getInt("user_id", 0)

        sendServerRequest(userId.toString())
        return view
    }

    private fun sendServerRequest(userId: String) {
        val queue = Volley.newRequestQueue(activity as Context)
        val histRequest =
            object : JsonObjectRequest(
                Request.Method.GET,
                "$FETCH_PREVIOUS_ORDERS$userId",
                null,
                Response.Listener {
                    rlLoading.visibility = View.GONE
                    try {
                        val data = it.getJSONObject("data")
                        val success = data.getBoolean("success")
                        if (success) {
                            val resArray = data.getJSONArray("data")
                            if (resArray.length() == 0) {
                                llHasOrders.visibility = View.GONE
                                rlNoOrders.visibility = View.VISIBLE
                            } else {
                                for (i in 0 until resArray.length()) {
                                    val orderObject = resArray.getJSONObject(i)
                                    val foodItems = orderObject.getJSONArray("food_items")
                                    val order = OrderDetails(
                                        orderObject.getInt("order_id"),
                                        orderObject.getString("restaurant_name"),
                                        orderObject.getString("order_placed_at"),
                                        foodItems
                                    )
                                    orderHistoryList.add(order)
                                    if (orderHistoryList.isEmpty()) {
                                        llHasOrders.visibility = View.GONE
                                        rlNoOrders.visibility = View.VISIBLE
                                    } else {
                                        llHasOrders.visibility = View.VISIBLE
                                        rlNoOrders.visibility = View.GONE
                                        if (activity != null) {
                                            historyOrderAdapter = HistoryOrderAdapter(
                                                activity as Context,
                                                orderHistoryList
                                            )
                                            val mLayoutManager =
                                                LinearLayoutManager(activity as Context)
                                            recyclerOrderHistory.layoutManager = mLayoutManager
                                            recyclerOrderHistory.itemAnimator =
                                                DefaultItemAnimator()
                                            recyclerOrderHistory.adapter = historyOrderAdapter
                                        } else {
                                            queue.cancelAll(this::class.java.simpleName)
                                        }
                                    }
                                }
                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(activity as Context, it.message, Toast.LENGTH_SHORT).show()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "9bf534118365f1"
                    return headers
                }
            }
        queue.add(histRequest)
    }
}


