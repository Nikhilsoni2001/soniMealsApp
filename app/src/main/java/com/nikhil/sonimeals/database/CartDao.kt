package com.nikhil.sonimeals.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CartDao {

    @Insert
    fun insert(cartEntity: CartEntity)

    @Delete
    fun delete(cartEntity: CartEntity)

    @Query("SELECT * FROM cart")
    fun getAllCartItems(): List<CartEntity>

    @Query("SELECT * FROM cart WHERE(id= :id)")
    fun getItem(id: Int): CartEntity

    @Query("DELETE FROM cart")
    fun clearCart()
}