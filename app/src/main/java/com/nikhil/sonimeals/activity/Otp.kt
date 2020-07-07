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
import com.internshala.higherorderfunctionalitiessolution.util.RESET_PASSWORD
import com.nikhil.sonimeals.R
import org.json.JSONObject

class Otp : AppCompatActivity() {

    lateinit var etOTP: EditText
    lateinit var etNewPass: EditText
    lateinit var etCnfrmPass: EditText
    lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        etOTP = findViewById(R.id.etOTP)
        etNewPass = findViewById(R.id.etNewPass)
        etCnfrmPass = findViewById(R.id.etCnfrmPass)
        btnSubmit = findViewById(R.id.btnSubmit)

        if (intent != null) {
            val mobile = intent.getStringExtra("mobile_number")
            val email = intent.getStringExtra("email")

            btnSubmit.setOnClickListener {
                val otp = etOTP.text.toString()
                val pass = etNewPass.text.toString()
                val cnfrmPass = etCnfrmPass.text.toString()
                if (pass == cnfrmPass) {

                    val queue = Volley.newRequestQueue(this)
                    val params = JSONObject()
                    params.put("mobile_number", mobile)
                    params.put("password", pass)
                    params.put("otp", otp)

                    val resetReq = object : JsonObjectRequest(
                        Request.Method.POST,
                        RESET_PASSWORD,
                        params,
                        Response.Listener {
                            val data = it.getJSONObject("data")
                            if (data.getBoolean("success")) {
                                Toast.makeText(
                                    this,
                                    data.getString("successMessage"),
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                                    .show()
                            }
                        },
                        Response.ErrorListener {
                            Toast.makeText(this, "Error- $it", Toast.LENGTH_LONG).show()
                        }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Content-type"] = "application/json"
                            headers["token"] = "9bf534118365f1"
                            return headers
                        }
                    }
                    queue.add(resetReq)

                } else {
                    Toast.makeText(this, "Password do not match!!", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}