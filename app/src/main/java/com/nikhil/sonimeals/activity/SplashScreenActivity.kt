package com.nikhil.sonimeals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.nikhil.sonimeals.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        title = getString(R.string.soni_meals)

        Handler().postDelayed({
            val startActivity = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(startActivity)
        }, 1000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}