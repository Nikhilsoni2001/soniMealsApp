package com.nikhil.sonimeals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.adapter.CartAdapter
import com.nikhil.sonimeals.model.MenuItem

class CartActivity : AppCompatActivity() {

    lateinit var recyclerCart: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: CartAdapter


    val items = arrayListOf<MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerCart = findViewById(R.id.recyclerCart)
        layoutManager = LinearLayoutManager(this)
        val btnProceed: Button = findViewById(R.id.btnProceed)

        //get name and id from intent
        btnProceed.setOnClickListener {
            val intent = Intent(this, OrderPlaced::class.java)
            startActivity(intent)
        }

    }
}