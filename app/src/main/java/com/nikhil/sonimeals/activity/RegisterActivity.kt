package com.nikhil.sonimeals.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.higherorderfunctionalitiessolution.util.ConnectionManager
import com.internshala.higherorderfunctionalitiessolution.util.REGISTER
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.util.SessionManager
import com.nikhil.sonimeals.util.Validations
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class RegisterActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    private lateinit var btnRegister: Button
    private lateinit var etName: EditText
    private lateinit var etMobile: EditText
    private lateinit var etPassword: EditText
    private lateinit var etEmail: EditText
    private lateinit var etDelivery: EditText
    private lateinit var etCnfrmPass: EditText
    lateinit var progress: ProgressBar
    lateinit var rlRegister: RelativeLayout
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setTitleTextAppearance(this, R.style.PoppinsTextAppearance)

//        Session Manager
        sessionManager = SessionManager(this@RegisterActivity)
        sharedPreferences = this@RegisterActivity.getSharedPreferences(
            sessionManager.PREF_NAME,
            sessionManager.PRIVATE_MODE
        )

//        Initializations
        etName = findViewById(R.id.etName)
        etMobile = findViewById(R.id.etMobile)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPass)
        etCnfrmPass = findViewById(R.id.etCnfrmPass)
        etDelivery = findViewById(R.id.etDelivery)
        btnRegister = findViewById(R.id.btnRegister)
        rlRegister = findViewById(R.id.rlRegister)
        progress = findViewById(R.id.progressBar)
        progress.visibility = View.GONE
        rlRegister.visibility = View.VISIBLE

        btnRegister.setOnClickListener {
            rlRegister.visibility = View.INVISIBLE
            progress.visibility = View.VISIBLE

            if (Validations.validateNameLength(etName.text.toString())) {
                etName.error = null
                if (Validations.validateEmail(etEmail.text.toString())) {
                    etEmail.error = null
                    if (Validations.validateMobile(etMobile.text.toString())) {
                        etMobile.error = null
                        if (Validations.validatePasswordLength(etPassword.text.toString())) {
                            etPassword.error = null
                            if (Validations.matchPassword(
                                    etPassword.text.toString(),
                                    etCnfrmPass.text.toString()
                                )
                            ) {
                                etPassword.error = null
                                etCnfrmPass.error = null
                                if (ConnectionManager().isNetworkAvailable(this)) {
                                    sendRegisterRequest(
                                        etName.text.toString(),
                                        etMobile.text.toString(),
                                        etDelivery.text.toString(),
                                        etPassword.text.toString(),
                                        etEmail.text.toString()
                                    )


                                } else {
                                    progress.visibility = View.GONE
                                    Toast.makeText(
                                        this,
                                        "Passwords do not match",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            progress.visibility = View.GONE

                            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun sendRegisterRequest(
        name: String,
        phone: String,
        address: String,
        password: String,
        email: String
    ) {
        val queue = Volley.newRequestQueue(this)

        val params = JSONObject()
        params.put("name", name)
        params.put("mobile_number", phone)
        params.put("password", password)
        params.put("address", address)
        params.put("email", email)

        val registerPostRequest = object :
            JsonObjectRequest(
                Request.Method.POST,
                REGISTER,
                params,
                Response.Listener {
                    try {
                        val data = it.getJSONObject("data")
                        if (data.getBoolean("success")) {
                            val response = data.getJSONObject("data")

                            sharedPreferences.edit()
                                .putString("user_id", response.getString("user_id")).apply()
                            sharedPreferences.edit()
                                .putString("user_name", response.getString("name")).apply()
                            sharedPreferences.edit()
                                .putString(
                                    "user_mobile_number",
                                    response.getString("mobile_number")
                                )
                                .apply()
                            sharedPreferences.edit()
                                .putString("user_address", response.getString("address"))
                                .apply()
                            sharedPreferences.edit()
                                .putString("user_email", response.getString("email")).apply()
                            sessionManager.setLogin(true)

                            progress.visibility = View.GONE

                            val intent =
                                Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            rlRegister.visibility = View.VISIBLE
                            progress.visibility = View.GONE
                            val msg = data.getString("errorMessage")
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } catch (e: JSONException) {
                        rlRegister.visibility = View.VISIBLE
                        progress.visibility = View.INVISIBLE
                        e.printStackTrace()
                    }

                },
                Response.ErrorListener {
                    rlRegister.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    Toast.makeText(
                        this,
                        "Registration failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["token"] = "9bf534118365f1"
                return headers
            }
        }
        queue.add(registerPostRequest)
    }

    override fun onSupportNavigateUp(): Boolean {
        Volley.newRequestQueue(this).cancelAll(this::class.java.simpleName)
        onBackPressed()
        return true
    }
}