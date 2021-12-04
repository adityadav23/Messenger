package com.example.messenger

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var userName : EditText
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var registerButton : Button
    private lateinit var alreadyHaveAnAccount : TextView
    private lateinit var selectPhotoButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Connecting views
        userName = findViewById(R.id.userName_editText_register)
        email = findViewById(R.id.email_editText_register)
        password = findViewById(R.id.password_editText_register)
        registerButton = findViewById(R.id.register_button_register)
        alreadyHaveAnAccount = findViewById(R.id.already_have_an_acoount_register)
        selectPhotoButton = findViewById(R.id.selectphoto_button_register)

        registerButton.setOnClickListener {
            performRegister()
        }

        //Photo selector intent
        selectPhotoButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent ,0)
        }

        // Move to LoginActivity using intent
        alreadyHaveAnAccount.setOnClickListener {
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private var selectedPhotoUri: Uri? = null
    //Adding photo to the button which is selected.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null)
        {   // Proceed and check what the selected image was...
            // getting the uri of selected photo
             selectedPhotoUri = data.data
            //getting image using above uri
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            val bitmapDrawable = BitmapDrawable(bitmap)

            //Adding photo to button
            selectPhotoButton.setBackgroundDrawable(bitmapDrawable)


        }
    }

    //Performs registration of user
    private fun performRegister(){
        val userNameInput = userName.text.toString()
        val emailInput = email.text.toString()
        val passwordInput = password.text.toString()

        //Check if email or password is empty
        if(emailInput.isEmpty() && passwordInput.isEmpty() ){
            Toast.makeText(this, " Please enter Email Address and Password", Toast.LENGTH_SHORT).show()
            return
        }
        if(emailInput.isEmpty() ){
            Toast.makeText(this, " Please enter Email Address", Toast.LENGTH_SHORT).show()
            return
        }
        if(passwordInput.isEmpty() ){
            Toast.makeText(this, " Please enter Password", Toast.LENGTH_SHORT).show()
            return
        }

        //Firebase Authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailInput,passwordInput)
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener

                //else if Successful
                Log.d("Main", "Login successful created user with uid: ${it.result?.user?.uid} ")
                Toast.makeText(this, "Login successful created user with uid: ${it.result?.user?.uid} "
                                          , Toast.LENGTH_SHORT).show()

                uploadImageToFirebaseStorage()

            }
            .addOnFailureListener {
                Log.d("Main", "Failed to create user ${it.message}")

                Toast.makeText(this, "Failed to create user ${it.message}", Toast.LENGTH_SHORT).show()

            }

    }

    private fun uploadImageToFirebaseStorage() {

    }

}



