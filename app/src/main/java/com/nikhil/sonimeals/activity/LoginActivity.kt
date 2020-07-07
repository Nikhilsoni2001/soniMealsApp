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
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.higherorderfunctionalitiessolution.util.ConnectionManager
import com.internshala.higherorderfunctionalitiessolution.util.LOGIN
import com.nikhil.sonimeals.R
import org.json.JSONObject
import java.util.HashMap


class LoginActivity : AppCompatActivity() {

    lateinit var etMobile: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtForgot: TextView
    private lateinit var txtRegister: TextView


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

            val queue = Volley.newRequestQueue(this)

            val mobile = etMobile.text.toString()
            val password = etPassword.text.toString()

            if ((mobile.length >= 10) && (password.length <= 4)) {
                if (ConnectionManager().isNetworkAvailable(this@LoginActivity)) {
                    val params = JSONObject()
                    params.put("mobile_number", mobile)
                    params.put("password", password)
                    val loginRequest = object :
                        JsonObjectRequest(Request.Method.POST, LOGIN, params, Response.Listener {
                            val data = it.getJSONObject("data")
                            if (data.getBoolean("success")) {
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                val msg = data.getString("errorMessage")
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                            }
                        }, Response.ErrorListener {
                            Toast.makeText(this@LoginActivity, "Error $it", Toast.LENGTH_SHORT)
                                .show()
                        }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Content-type"] = "application/json"
                            headers["token"] = "9bf534118365f1"
                            return headers
                        }
                    }
                    queue.add(loginRequest)
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        " No Internet Connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this@LoginActivity, "Invalid Credentials!", Toast.LENGTH_LONG)
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