package com.nikhil.sonimeals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.internshala.higherorderfunctionalitiessolution.util.FORGOT_PASSWORD
import com.nikhil.sonimeals.R
import org.json.JSONObject

class ForgotActivity : AppCompatActivity() {

    private lateinit var btnNext: Button
    private lateinit var etMobileForget: EditText
    private lateinit var etEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        etMobileForget = findViewById(R.id.etMobileForget)
        etEmail = findViewById(R.id.etEmail)
        btnNext = findViewById(R.id.btnNext)

        title = "Forgot"

        btnNext.setOnClickListener {

            val mobile = etMobileForget.text.toString()
            val email = etEmail.text.toString()

            val queue = Volley.newRequestQueue(this@ForgotActivity)

            if (mobile.length != null && email.length != null) {
                val params = JSONObject()
                params.put("mobile_number", mobile)
                params.put("email", email)

                val OTPRequest = object : JsonObjectRequest(
                    Request.Method.POST,
                    FORGOT_PASSWORD,
                    params,
                    Response.Listener {
                        val data = it.getJSONObject("data")
                        if (data.getBoolean("success")) {
                            val intent = Intent(this, Otp::class.java)
                            intent.putExtra("mobile_number", mobile)
                            intent.putExtra("email", email)
                            Toast.makeText(this, "OTP has been sent", Toast.LENGTH_SHORT).show()
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "Some unexpected error occurs!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    Response.ErrorListener {
                        Toast.makeText(this, "Volley Error", Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "9bf534118365f1"
                        return headers
                    }

                }

                queue.add(OTPRequest)
            } else {
                Toast.makeText(this, "Enter valid email and mobile no", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


