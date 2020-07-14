package com.nikhil.sonimeals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.nikhil.sonimeals.util.ConnectionManager
import com.nikhil.sonimeals.util.RESET_PASSWORD
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.util.Validations
import org.json.JSONException
import org.json.JSONObject

class Otp : AppCompatActivity() {

    private lateinit var etOTP: EditText
    private lateinit var etNewPass: EditText
    private lateinit var etCnfrmPass: EditText
    private lateinit var btnSubmit: Button
    private lateinit var llOTP: LinearLayout
    private lateinit var progress: ProgressBar
    private lateinit var mobile: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        etOTP = findViewById(R.id.etOTP)
        etNewPass = findViewById(R.id.etNewPass)
        etCnfrmPass = findViewById(R.id.etCnfrmPass)
        btnSubmit = findViewById(R.id.btnSubmit)
        llOTP = findViewById(R.id.llOTP)
        progress = findViewById(R.id.progressBar)

        llOTP.visibility = View.VISIBLE
        progress.visibility = View.GONE

        if (intent != null) {
            mobile = intent.getStringExtra("user_mobile")
        }
        btnSubmit.setOnClickListener {
            llOTP.visibility = View.GONE
            progress.visibility = View.VISIBLE

            if (ConnectionManager().isNetworkAvailable(this)) {
                if (etOTP.text.length == 4) {
                    if (Validations.validatePasswordLength(etNewPass.text.toString())) {
                        if (Validations.matchPassword(
                                etNewPass.text.toString(),
                                etCnfrmPass.text.toString()
                            )
                        ) {
                            resetPassword(
                                mobile,
                                etOTP.text.toString(),
                                etNewPass.text.toString()
                            )
                        } else {
                            llOTP.visibility = View.VISIBLE
                            progress.visibility = View.GONE
                            Toast.makeText(
                                this@Otp,
                                "Passwords do not match",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        llOTP.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        Toast.makeText(
                            this@Otp,
                            "Invalid Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    llOTP.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    Toast.makeText(this@Otp, "Incorrect OTP", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                llOTP.visibility = View.VISIBLE
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
            Request.Method.POST,
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
                        llOTP.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        val error = data.getString("errorMessage")
                        Toast.makeText(this, error, Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    llOTP.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    Toast.makeText(
                        this@Otp,
                        "Incorrect Response!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            },
            Response.ErrorListener {
                llOTP.visibility = View.VISIBLE
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