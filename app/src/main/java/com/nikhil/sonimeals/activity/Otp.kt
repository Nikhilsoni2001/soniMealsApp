package com.nikhil.sonimeals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import com.nikhil.sonimeals.util.ConnectionManager
import com.nikhil.sonimeals.util.RESET_PASSWORD
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.util.Validations
import org.json.JSONException
import org.json.JSONObject

class Otp : AppCompatActivity() {

    private lateinit var etOTP: EditText
    private lateinit var elOTP: TextInputLayout
    private lateinit var etNewPass: EditText
    private lateinit var elNewPass: TextInputLayout
    private lateinit var etCnfrmPass: EditText
    private lateinit var elCnfrmPass: TextInputLayout
    private lateinit var btnSubmit: Button
    private lateinit var clOTP: ConstraintLayout
    private lateinit var progress: ProgressBar
    private lateinit var mobile: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        etOTP = findViewById(R.id.etOTP)
        elOTP = findViewById(R.id.elOTP)
        etNewPass = findViewById(R.id.etNewPass)
        elNewPass = findViewById(R.id.elNewPass)
        etCnfrmPass = findViewById(R.id.etCnfrmPass)
        elCnfrmPass = findViewById(R.id.elCnfrmPass)
        btnSubmit = findViewById(R.id.btnSubmit)
        clOTP = findViewById(R.id.llOTP)
        progress = findViewById(R.id.progressBar)

        clOTP.visibility = View.VISIBLE
        progress.visibility = View.GONE

        if (intent != null) {
            mobile = intent.getStringExtra("user_mobile")
        }
        btnSubmit.setOnClickListener {
            clOTP.visibility = View.GONE
            progress.visibility = View.VISIBLE

            if (ConnectionManager().isNetworkAvailable(this)) {
                if (etOTP.text.length == 4) {
                    elOTP.error = null
                    if (Validations.validatePasswordLength(etNewPass.text.toString())) {
                        elNewPass.error = null
                        if (Validations.matchPassword(
                                etNewPass.text.toString(),
                                etCnfrmPass.text.toString()
                            )
                        ) {
                            elCnfrmPass.error = null
                            resetPassword(mobile, etOTP.text.toString(), etNewPass.text.toString())
                        } else {
                            clOTP.visibility = View.VISIBLE
                            progress.visibility = View.GONE
                            elCnfrmPass.error = "Passwords do not match"
                        }
                    } else {
                        clOTP.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        elNewPass.error = "Invalid Password"
                    }
                } else {
                    clOTP.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    elOTP.error = "Incorrect OTP"
                }
            } else {
                clOTP.visibility = View.VISIBLE
                progress.visibility = View.GONE
                Toast.makeText(
                    this@Otp,
                    "No Internet Connection!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }

    private fun resetPassword(mobileNumber: String, otp: String, password: String) {

        val queue = Volley.newRequestQueue(this)

        val params = JSONObject()
        params.put("mobile_number", mobileNumber)
        params.put("password", password)
        params.put("otp", otp)

        val resetReq = object : JsonObjectRequest(
            Method.POST,
            RESET_PASSWORD,
            params,
            Response.Listener {
                try {
                    val data = it.getJSONObject("data")
                    if (data.getBoolean("success")) {
                        progress.visibility = View.INVISIBLE
                        val builder = AlertDialog.Builder(this@Otp)
                        builder.setTitle("Confirmation")
                        builder.setMessage("Your password has been successfully changed")
                        builder.setIcon(R.drawable.ic_action_success)
                        builder.setCancelable(false)
                        builder.setPositiveButton("Ok") { _, _ ->
                            startActivity(
                                Intent(
                                    this@Otp,
                                    LoginActivity::class.java
                                )
                            )
                            ActivityCompat.finishAffinity(this@Otp)
                        }
                        builder.create().show()
                    } else {
                        clOTP.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        val error = data.getString("errorMessage")
                        Toast.makeText(this, error, Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    clOTP.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    Toast.makeText(
                        this@Otp,
                        "Incorrect Response!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            },
            Response.ErrorListener {
                clOTP.visibility = View.VISIBLE
                progress.visibility = View.GONE
                VolleyLog.e("Error::::", "/post request fail! Error: ${it.message}")
                Toast.makeText(this, "Error- ${it.message}", Toast.LENGTH_LONG).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["token"] = "9bf534118365f1"
                return headers
            }
        }
        queue.add(resetReq)
    }
}