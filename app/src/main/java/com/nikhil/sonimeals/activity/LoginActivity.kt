package com.nikhil.sonimeals.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import com.nikhil.sonimeals.util.ConnectionManager
import com.nikhil.sonimeals.util.LOGIN
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.util.SessionManager
import com.nikhil.sonimeals.util.Validations
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap


class LoginActivity : AppCompatActivity() {

    //    Declaring All the views present in activity_login.xml
    private lateinit var etMobile: EditText
    private lateinit var elMobile: TextInputLayout
    private lateinit var etPassword: EditText
    private lateinit var elPassword: TextInputLayout
    private lateinit var btnLogin: Button
    private lateinit var txtForgot: TextView
    private lateinit var txtRegister: TextView
    lateinit var progress: ProgressBar


    //    For Handling Shared Preference
    lateinit var sessionManager: SessionManager
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        Initializations
        etMobile = findViewById(R.id.etMobile)
        elMobile = findViewById(R.id.elMobile)
        etPassword = findViewById(R.id.etPassword)
        elPassword = findViewById(R.id.elPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtForgot = findViewById(R.id.txtForgot)
        txtRegister = findViewById(R.id.txtSignup)
        progress = findViewById(R.id.progressBar)
        progress.visibility = View.GONE

//        Initializing Session Variables
        sessionManager = SessionManager(this)
        sharedPreferences =
            this.getSharedPreferences(sessionManager.PREF_NAME, sessionManager.PRIVATE_MODE)

        /*Clicking on this text takes you to the forgot password activity*/
        txtForgot.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotActivity::class.java)
            startActivity(intent)
        }

        /*Clicking on this text takes you to the Register activity*/
        txtRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }


        btnLogin.setOnClickListener {

            progress.visibility = View.VISIBLE
            val mobile = etMobile.text.toString()
            val password = etPassword.text.toString()

            /*First validate the mobile number and password length*/
            if (Validations.validateMobile(mobile)) {
                elMobile.error = null
                if (Validations.validatePasswordLength(password)) {
                    elPassword.error = null
                    if (ConnectionManager().isNetworkAvailable(this@LoginActivity)) {

                        /*Create the queue for the request*/
                        val queue = Volley.newRequestQueue(this)

//                    Params
                        val params = JSONObject()
                        params.put("mobile_number", mobile)
                        params.put("password", password)

                        /*Finally send the json object request*/
                        val loginRequest = object :
                            JsonObjectRequest(Method.POST, LOGIN, params, Response.Listener {

                                try {
                                    val data = it.getJSONObject("data")
                                    if (data.getBoolean("success")) {
                                        val response = data.getJSONObject("data")

                                        sharedPreferences.edit()
                                            .putInt(
                                                "user_id",
                                                response.getString("user_id").toInt()
                                            )
                                            .apply()
                                        sharedPreferences.edit()
                                            .putString("user_name", response.getString("name"))
                                            .apply()
                                        sharedPreferences.edit()
                                            .putString(
                                                "user_mobile_number",
                                                response.getString("mobile_number")
                                            )
                                            .apply()
                                        sharedPreferences.edit()
                                            .putString(
                                                "user_address",
                                                response.getString("address")
                                            )
                                            .apply()
                                        sharedPreferences.edit()
                                            .putString("user_email", response.getString("email"))
                                            .apply()
                                        sessionManager.setLogin(true)

                                        val intent =
                                            Intent(this@LoginActivity, HomeActivity::class.java)
                                        progress.visibility = View.GONE
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        progress.visibility = View.GONE
                                        val msg = data.getString("errorMessage")
                                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                                    }
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "Some unexpected Error occurred",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }, Response.ErrorListener {
                                progress.visibility = View.GONE
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
                        progress.visibility = View.GONE
                        Toast.makeText(
                            this@LoginActivity,
                            " No Internet Connection",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    progress.visibility = View.GONE
                    elPassword.error = "Incorrect Password"
                }
            } else {
                progress.visibility = View.GONE
                elMobile.error = "Incorrect Mobile Number"
            }
        }

    }
}