package com.nikhil.sonimeals.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.nikhil.sonimeals.util.ConnectionManager
import com.nikhil.sonimeals.util.REGISTER
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.util.SessionManager
import com.nikhil.sonimeals.util.Validations
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class RegisterActivity : AppCompatActivity() {

    private lateinit var btnRegister: Button
    private lateinit var etName: TextInputEditText
    private lateinit var elName: TextInputLayout
    private lateinit var etMobile: TextInputEditText
    private lateinit var elMobile: TextInputLayout
    private lateinit var etPassword: TextInputEditText
    private lateinit var elPassword: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var elEmail: TextInputLayout
    private lateinit var etDelivery: TextInputEditText
    private lateinit var elDelivery: TextInputLayout
    private lateinit var etCnfrmPass: TextInputEditText
    private lateinit var elCnfrmPass: TextInputLayout
    lateinit var progress: ProgressBar
    lateinit var clRegister: ConstraintLayout
    lateinit var sharedPreferences: SharedPreferences
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        Toolbar
        supportActionBar?.title = "Register Yourself"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        Session Manager
        sessionManager = SessionManager(this@RegisterActivity)
        sharedPreferences = this@RegisterActivity.getSharedPreferences(
            sessionManager.PREF_NAME,
            sessionManager.PRIVATE_MODE
        )

//        Initializations
        etName = findViewById(R.id.etName)
        elName = findViewById(R.id.elName)
        etMobile = findViewById(R.id.etMobile)
        elMobile = findViewById(R.id.elMobile)
        etEmail = findViewById(R.id.etEmail)
        elEmail = findViewById(R.id.elEmail)
        etPassword = findViewById(R.id.etNewPass)
        elPassword = findViewById(R.id.elNewPass)
        etCnfrmPass = findViewById(R.id.etCnfrmPass)
        elCnfrmPass = findViewById(R.id.elCnfrmPass)
        etDelivery = findViewById(R.id.etDelivery)
        elDelivery = findViewById(R.id.elDelivery)
        btnRegister = findViewById(R.id.btnRegister)
        clRegister = findViewById(R.id.rlRegister)
        progress = findViewById(R.id.progressBar)
        progress.visibility = View.GONE
        clRegister.visibility = View.VISIBLE

        btnRegister.setOnClickListener {
            progress.visibility = View.VISIBLE

            if (Validations.validateNameLength(etName.text.toString())) {
                elName.error = null
                if (Validations.validateEmail(etEmail.text.toString())) {
                    elEmail.error = null
                    if (Validations.validateMobile(etMobile.text.toString())) {
                        elMobile.error = null
                        if (Validations.validatePasswordLength(etPassword.text.toString())) {
                            elPassword.error = null
                            if (Validations.matchPassword(
                                    etPassword.text.toString(),
                                    etCnfrmPass.text.toString()
                                )
                            ) {
                                elPassword.error = null
                                elCnfrmPass.error = null
                                if (ConnectionManager().isNetworkAvailable(this)) {
                                    clRegister.visibility = View.INVISIBLE
                                    sendRegisterRequest(
                                        etName.text.toString(),
                                        etMobile.text.toString(),
                                        etDelivery.text.toString(),
                                        etPassword.text.toString(),
                                        etEmail.text.toString()
                                    )


                                } else {
                                    clRegister.visibility = View.VISIBLE
                                    progress.visibility = View.GONE
                                    Toast.makeText(this@RegisterActivity, "Internet Unavailable", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                clRegister.visibility = View.VISIBLE
                                progress.visibility = View.GONE
                                elCnfrmPass.error = "Invalid Password"
                            }
                        } else {
                            clRegister.visibility = View.VISIBLE
                            progress.visibility = View.GONE
                            elPassword.error = "Invalid Password"
                        }
                    } else {
                        clRegister.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        elMobile.error = "Incorrect Mobile Number"
                    }
                } else {
                    clRegister.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    elEmail.error = "Incorrect Email"
                }
            } else {
                clRegister.visibility = View.VISIBLE
                progress.visibility = View.GONE
                elName.error = "Invalid Name"
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
                Method.POST,
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
                            clRegister.visibility = View.VISIBLE
                            progress.visibility = View.GONE
                            val msg = data.getString("errorMessage")
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } catch (e: JSONException) {
                        clRegister.visibility = View.VISIBLE
                        progress.visibility = View.INVISIBLE
                        e.printStackTrace()
                    }

                },
                Response.ErrorListener {
                    clRegister.visibility = View.VISIBLE
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