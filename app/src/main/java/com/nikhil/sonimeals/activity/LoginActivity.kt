package com.nikhil.sonimeals.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.nikhil.sonimeals.R


class LoginActivity : AppCompatActivity() {

    lateinit var etMobile: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtForgot: TextView
    private lateinit var txtRegister: TextView
    private val validmobile = "0123456789"
    private val validpassword = arrayOf("soniMeals", "nikhil", "pankaj", "pooja")
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)

        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        setContentView(R.layout.activity_login)

        if (isLoggedIn) {
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            sharedPreferences.edit().clear().apply()
            startActivity(intent)
            finish()
        }

        etMobile = findViewById(R.id.etMobile)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtForgot = findViewById(R.id.txtForgot)
        txtRegister = findViewById(R.id.txtSignup)


        title = "Login"

        btnLogin.setOnClickListener {
            val mobile = etMobile.text.toString()
            val password = etPassword.text.toString()
            if (mobile == validmobile) {
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                when (password) {
                    validpassword[0], validpassword[1], validpassword[2], validpassword[3] -> {
//                        intent.putExtra("Mobile",mobile)
//                        intent.putExtra("Password",password)
                        savePreferences()
                        startActivity(intent)
                    }
                    else -> Toast.makeText(
                        this@LoginActivity,
                        "Incorrect Password",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else {
                Toast.makeText(this@LoginActivity, "Incorrect Credentials!", Toast.LENGTH_LONG)
                    .show()
            }
        }

        txtForgot.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotActivity::class.java)
            startActivity(intent)
        }

        txtRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    fun savePreferences() {
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}