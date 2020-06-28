package com.nikhil.sonimeals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.nikhil.sonimeals.R

lateinit var etMobile: EditText
private lateinit var etPassword: EditText
private lateinit var btnLogin: Button
private lateinit var txtForgot: TextView
private lateinit var txtSignup: TextView
private val validmobile = "0123456789"
private val validpassword = arrayOf("soniMeals", "nikhil", "pankaj", "pooja")

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etMobile = findViewById(R.id.etMobile)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtForgot = findViewById(R.id.txtForgot)
        txtSignup = findViewById(R.id.txtSignup)


        title = "Login"

        btnLogin.setOnClickListener {
            val mobile = etMobile.text.toString()
            val password =etPassword.text.toString()
            if (mobile == validmobile) {
                val intent = Intent(this@LoginActivity, HomeActivity:: class.java)
                when (password) {
                    validpassword[0], validpassword[1],validpassword[2],validpassword[3] -> {
//                        intent.putExtra("Mobile",mobile)
//                        intent.putExtra("Password",password)
                        startActivity(intent)
                    } else -> Toast.makeText(this@LoginActivity,"Incorrect Password", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@LoginActivity,"Incorrect Credentials!", Toast.LENGTH_LONG).show()
            }
        }

        txtForgot.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotActivity:: class.java)
            startActivity(intent)
        }

        txtSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity:: class.java)
            startActivity(intent)
        }

    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}