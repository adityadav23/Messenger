package com.example.messenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

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
            val emailInput = email.text.toString()
            val passwordInput = password.text.toString()

            //Firebase Authentication to create a user with email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailInput,passwordInput)
                .addOnCompleteListener {
                    if(!it.isSuccessful) return@addOnCompleteListener

                    //else if Successful
                    Log.d("Main", "Login successful created user with uid: ${it.result?.user?.uid} ")
                }

        }

        alreadyHaveAnAccount.setOnClickListener {
           val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
        }


    }


}