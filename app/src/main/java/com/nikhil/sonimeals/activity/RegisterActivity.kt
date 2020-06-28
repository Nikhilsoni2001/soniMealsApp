package com.nikhil.sonimeals.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.nikhil.sonimeals.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etMail: EditText
    private lateinit var etMobileNumber: EditText
    private lateinit var etAdrress: EditText
    private lateinit var etPasswrd: EditText
    private lateinit var etConfirmPasswrd: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etName = findViewById(R.id.etName)
        etMail = findViewById(R.id.etMail)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etAdrress = findViewById(R.id.etAdress)
        etPasswrd = findViewById(R.id.etPasswrd)
        etConfirmPasswrd = findViewById(R.id.etConfirmPasswrd)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val mail = etMail.text.toString()
            val mobile = etMobileNumber.text.toString()
            val address = etAdrress.text.toString()
            val passwrd = etPasswrd.text.toString()
            val confirmPasswrd = etConfirmPasswrd.text.toString()
            title = "Register Yourself"

            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            intent.putExtra("Name", name)
            intent.putExtra("Mail", mail)
            intent.putExtra("Mobile", mobile)
            intent.putExtra("Address", address)
            intent.putExtra("Passwrd", passwrd)
            intent.putExtra("ConfirmPasswrd", confirmPasswrd)
            startActivity(intent)
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