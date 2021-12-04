package com.example.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var userName : EditText
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var registerButton : Button
    private lateinit var alreadyHaveAnAccount : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Connecting views
        userName = findViewById(R.id.userName_editText_register)
        email = findViewById(R.id.email_editText_register)
        password = findViewById(R.id.password_editText_register)
        registerButton = findViewById(R.id.register_button_register)
        alreadyHaveAnAccount = findViewById(R.id.already_have_an_acoount_register)

        registerButton.setOnClickListener {
            val userNameInput = userName.text.toString()
            val emailInput = userName.text.toString()
            val passwordInput = userName.text.toString()
            Toast.makeText(
                applicationContext,
                "$userNameInput :$emailInput :$passwordInput", Toast.LENGTH_SHORT
            ).show()
        }

        alreadyHaveAnAccount.setOnClickListener {
           val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
        }


    }


}