package com.nikhil.sonimeals.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RestaurantEntity::class, OrderEntity::class], version = 4, exportSchema = false)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao

    abstract fun orderDao(): OrderDao

}