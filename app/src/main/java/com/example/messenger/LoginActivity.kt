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

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText : EditText
    private lateinit var loginButton : Button
    private lateinit var backToRegistrationTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.email_editText_login)
        passwordEditText = findViewById(R.id.password_editText_login)
        loginButton = findViewById(R.id.login_button_login)
        backToRegistrationTextView = findViewById(R.id.back_to_register_textView_login)

        //Moving back to registration activity
        backToRegistrationTextView.setOnClickListener {
            finish()
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            //Check if email or password is empty
            if(email.isEmpty() && password.isEmpty() ){
                Toast.makeText(this, " Please enter Email Address and Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(email.isEmpty() ){
                Toast.makeText(this, " Please enter Email Address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password.isEmpty() ){
                Toast.makeText(this, " Please enter Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //Signing in with Firebase Auth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(!it.isSuccessful)return@addOnCompleteListener

                    //else if successful
                    Toast.makeText(this, "Successful Login ${it.result?.user?.uid}", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this , LatestMessagesActivity::class.java)
                    //This FLAG clears backStack and doesn't bring us back to RegisterActivity
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Log.d("Main", "Failed to create user ${it.message}")

                    Toast.makeText(this, "Failed to login : ${it.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }

    }
}