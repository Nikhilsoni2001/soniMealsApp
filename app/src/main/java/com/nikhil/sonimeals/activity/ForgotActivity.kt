package com.nikhil.sonimeals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.higherorderfunctionalitiessolution.util.ConnectionManager
import com.internshala.higherorderfunctionalitiessolution.util.FORGOT_PASSWORD
import com.nikhil.sonimeals.R
import com.nikhil.sonimeals.util.Validations
import org.json.JSONException
import org.json.JSONObject

class ForgotActivity : AppCompatActivity() {

    private lateinit var etForgotMobile: EditText
    private lateinit var etForgotEmail: EditText
    private lateinit var btnForgotNext: Button
    private lateinit var progress: ProgressBar
    private lateinit var rlContentMain: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        etForgotMobile = findViewById(R.id.etMobileForget)
        etForgotEmail = findViewById(R.id.etEmail)
        btnForgotNext = findViewById(R.id.btnNext)
        rlContentMain = findViewById(R.id.rlContentMain)
        progress = findViewById(R.id.progressBar)
        rlContentMain.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE

        title = "Forgot"

        btnForgotNext.setOnClickListener {

            val forgotMobileNumber = etForgotMobile.text.toString()
            if (Validations.validateMobile(forgotMobileNumber)) {
                etForgotMobile.error = null
                if (Validations.validateEmail(etForgotEmail.text.toString())) {
                    if (ConnectionManager().isNetworkAvailable(this@ForgotActivity)) {
                        rlContentMain.visibility = View.GONE
                        progress.visibility = View.VISIBLE
                        sendOtp(etForgotMobile.text.toString(), etForgotEmail.text.toString())
                    } else {
                        rlContentMain.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        Toast.makeText(
                            this@ForgotActivity,
                            "No Internet Connection!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    rlContentMain.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    etForgotEmail.error = "Invalid Email"
                }
            } else {
                rlContentMain.visibility = View.VISIBLE
                progress.visibility = View.GONE
                etForgotMobile.error = "Invalid Mobile Number"
            }
        }
    }

    private fun sendOtp(mobileNumber: String, email: String) {

        val queue = Volley.newRequestQueue(this@ForgotActivity)

        val params = JSONObject()
        params.put("mobile_number", mobileNumber)
        params.put("email", email)
        val OTPRequest = object : JsonObjectRequest(
            Request.Method.POST,
            FORGOT_PASSWORD,
            params,
            Response.Listener {
                try {
                    val data = it.getJSONObject("data")
                    if (data.getBoolean("success")) {
                        val firstTry = data.getBoolean("first_try")
                        if (firstTry) {
                            val builder = AlertDialog.Builder(this@ForgotActivity)
                            builder.setTitle("Information")
                            builder.setMessage("Please check your registered Email for the OTP.")
                            builder.setCancelable(false)
                            builder.setPositiveButton("Ok") { _, _ ->
                                val intent = Intent(
                                    this@ForgotActivity,
                                    Otp::class.java
                                )
                                intent.putExtra("user_mobile", mobileNumber)
                                startActivity(intent)
                            }
                            builder.create().show()
                        } else {
                            val builder = AlertDialog.Builder(this@ForgotActivity)
                            builder.setTitle("Information")
                            builder.setMessage("Please refer to the previous email for the OTP.")
                            builder.setCancelable(false)
                            builder.setPositiveButton("Ok") { _, _ ->
                                val intent = Intent(
                                    this@ForgotActivity,
                                    Otp::class.java
                                )
                                intent.putExtra("user_mobile", mobileNumber)
                                startActivity(intent)
                            }
                            builder.create().show()
                        }
                    } else {
                        rlContentMain.visibility = View.VISIBLE
                        progress.visibility = View.GONE
                        Toast.makeText(
                            this@ForgotActivity,
                            "Mobile number not registered!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    rlContentMain.visibility = View.VISIBLE
                    progress.visibility = View.GONE
                    Toast.makeText(
                        this@ForgotActivity,
                        "Incorrect response error!!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            Response.ErrorListener {
                rlContentMain.visibility = View.VISIBLE
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
        queue.add(OTPRequest)
    }
}


