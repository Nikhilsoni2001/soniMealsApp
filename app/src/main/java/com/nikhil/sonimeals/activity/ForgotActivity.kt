package com.nikhil.sonimeals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.nikhil.sonimeals.util.ConnectionManager
import com.nikhil.sonimeals.util.FORGOT_PASSWORD
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.util.Validations
import org.json.JSONException
import org.json.JSONObject

@Suppress("NAME_SHADOWING")
class ForgotActivity : AppCompatActivity() {

    private lateinit var etForgotMobile: TextInputEditText
    private lateinit var elForgotMobile: TextInputLayout
    private lateinit var etForgotEmail: TextInputEditText
    private lateinit var elForgetEmail: TextInputLayout
    private lateinit var btnForgotNext: MaterialButton
    private lateinit var progress: ProgressBar
    private lateinit var clContentMain: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        etForgotMobile = findViewById(R.id.etMobileForget)
        etForgotEmail = findViewById(R.id.etEmail)
        btnForgotNext = findViewById(R.id.btnNext)
        clContentMain = findViewById(R.id.rlContentMain)
        elForgotMobile = findViewById(R.id.elMobileForget)
        elForgetEmail = findViewById(R.id.elForgetEmail)
        progress = findViewById(R.id.progressBar)
        clContentMain.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE


        btnForgotNext.setOnClickListener {

            val forgotMobileNumber = etForgotMobile.text.toString()
            if (Validations.validateMobile(forgotMobileNumber)) {
                elForgotMobile.error = null
                if (Validations.validateEmail(etForgotEmail.text.toString())) {
                    elForgetEmail.error = null
                    if (ConnectionManager().isNetworkAvailable(this@ForgotActivity)) {
                        clContentMain.visibility = View.GONE
                        progress.visibility = View.VISIBLE
                        sendOtp(etForgotMobile.text.toString(), etForgotEmail.text.toString())
                    } else {
                        clContentMain.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        Toast.makeText(
                            this@ForgotActivity,
                            "No Internet Connection!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    clContentMain.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    elForgetEmail.error = "Invalid Email"
                }
            } else {
                clContentMain.visibility = View.VISIBLE
                progress.visibility = View.GONE
                elForgotMobile.error = "Invalid Mobile Number"
            }
        }
    }

    private fun sendOtp(mobileNumber: String, email: String) {

        val queue = Volley.newRequestQueue(this@ForgotActivity)

        val params = JSONObject()
        params.put("mobile_number", mobileNumber)
        params.put("email", email)
        val otpRequest = object : JsonObjectRequest(
            Method.POST,
            FORGOT_PASSWORD,
            params,
            Response.Listener {
                try {
                    val data = it.getJSONObject("data")
                    if (data.getBoolean("success")) {
                        val firstTry = data.getBoolean("first_try")
                        val intent = Intent(this@ForgotActivity, Otp::class.java)
                        if (firstTry) {
                            val builder = AlertDialog.Builder(this@ForgotActivity)
                            builder.setTitle("Information")
                            builder.setMessage("Please check your registered Email for the OTP.")
                            builder.setCancelable(false)
                            builder.setPositiveButton("Ok") { _, _ ->
                                intent.putExtra("user_mobile", mobileNumber)
                                startActivity(intent)
                            }
                            builder.create().show()
                        } else {
                            val builder = AlertDialog.Builder(this@ForgotActivity)
                            builder.setTitle("Information")
                            builder.setMessage("Please refer to the previous email for the OTP.")

                            builder.setPositiveButton("Ok") { _, _ ->
                                intent.putExtra("user_mobile", mobileNumber)
                                startActivity(intent)
                            }
                            val alertDialog: AlertDialog = builder.create()
                            alertDialog.setCancelable(false)
                            alertDialog.show()

                            builder.create().show()
                        }
                    } else {
                        clContentMain.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        Toast.makeText(
                            this@ForgotActivity,
                            "Mobile number not registered!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    Log.d("Error ->", "$e")
                    e.printStackTrace()
                    clContentMain.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    Toast.makeText(
                        this@ForgotActivity,
                        "Incorrect response error!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            Response.ErrorListener {
                clContentMain.visibility = View.VISIBLE
                progress.visibility = View.GONE
                VolleyLog.e("Error::::", "/post request fail! Error: ${it.message}")
                Toast.makeText(this@ForgotActivity, it.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-type"] = "application/json"
                headers["token"] = "9bf534118365f1"
                return headers
            }
        }
        queue.add(otpRequest)
    }
}


