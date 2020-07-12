package com.nikhil.sonimeals.util

import android.content.Context

class SessionManager(context: Context) {

    val PRIVATE_MODE = 0
    val PREF_NAME = "FoodApp"

    val KEY_IS_LOGGEDIN = "isLoggedIn"
    val pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    val editor = pref.edit()

    fun setLogin(isLoggedIn: Boolean) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn)
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false)
    }

}