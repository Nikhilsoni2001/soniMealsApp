package com.nikhil.sonimeals.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nikhil.sonimeals.database.RestaurantDao
import com.nikhil.sonimeals.database.RestaurantEntity

@Database(entities = [RestaurantEntity::class], version = 1)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao

}