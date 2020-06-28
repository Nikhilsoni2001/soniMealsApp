package com.nikhil.sonimeals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.nikhil.sonimeals.R

class ForgotActivity : AppCompatActivity() {

    private lateinit var etMobileForget: EditText
    private lateinit var etPasswordForget: EditText
    private lateinit var btnNext: Button
    private val validMobile = "0123456789"
    private val validPassword = mutableListOf("soniMeals", "nikhil", "pankaj", "pooja")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        etMobileForget = findViewById(R.id.etMobileForget)
        etPasswordForget = findViewById(R.id.etPasswordForget)
        btnNext = findViewById(R.id.btnNext)

        title = "Forgot"
        btnNext.setOnClickListener{
            val mobile = etMobileForget.text.toString()
            val password = etPasswordForget.text.toString()
            val intent = Intent(this@ForgotActivity, LoginActivity:: class.java)

            if(mobile == validMobile) {
                validPassword.add(password)
                intent.putExtra("Mobile",mobile)
                intent.putExtra("Password",password)
                startActivity(intent)
            } else Toast.makeText(this@ForgotActivity, "Invalid Mobile Number!", Toast.LENGTH_LONG).show()
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }
}