package com.nikhil.sonimeals.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val Price: String
)