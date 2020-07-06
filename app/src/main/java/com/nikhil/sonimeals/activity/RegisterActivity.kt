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
import com.internshala.higherorderfunctionalitiessolution.util.REGISTER
import com.nikhil.sonimeals.R
import org.json.JSONObject
import java.util.HashMap

class RegisterActivity : AppCompatActivity() {

    private lateinit var btnRegister: Button
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etMobile: EditText
    private lateinit var etDelivery: EditText
    private lateinit var etPass: EditText
    private lateinit var etCnfrmPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etMobile = findViewById(R.id.etMobile)
        etDelivery = findViewById(R.id.etDelivery)
        etPass = findViewById(R.id.etPass)
        etCnfrmPass = findViewById(R.id.etCnfrmPass)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val mobile = etMobile.text.toString()
            val email = etEmail.text.toString()
            val delivery = etDelivery.text.toString()
            val pass = etPass.text.toString()
            val cnfrmPass = etCnfrmPass.text.toString()
            title = "Register Yourself"

            if (name.length == null || mobile.length == null || pass.length == null || email.length == null || delivery.length == null || cnfrmPass.length == null) {
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                if (pass == cnfrmPass) {
                    val queue = Volley.newRequestQueue(this)
                    val params = JSONObject()
                    params.put("name", name)
                    params.put("mobile_number", mobile)
                    params.put("password", pass)
                    params.put("address", delivery)
                    params.put("email", email)

                    val registerPostRequest = object :
                        JsonObjectRequest(Request.Method.POST, REGISTER, params, Response.Listener {
                            val data = it.getJSONObject("data")
                            if (data.getBoolean("success")) {
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                            } else {
                                val msg = data.getString("errorMessage")
                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                            }
                        }, Response.ErrorListener {
                            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                        }) {
                        override fun getHeaders(): MutableMap<String, String> {
                            val headers = HashMap<String, String>()
                            headers["Content-type"] = "application/json"
                            headers["token"] = "9bf534118365f1"
                            return headers
                        }
                    }
                    queue.add(registerPostRequest)
                } else {
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}