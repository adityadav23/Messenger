package com.example.messenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NewMessageActivity : AppCompatActivity() {


    private lateinit var newMessageRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select User"
        newMessageRecyclerView = findViewById(R.id.recyclerView_newMessage)


        fetchUsers()

    }

    private  fun fetchUsers(){
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
           //Called when we retrieve all of the data from the database
            override fun onDataChange(snapshot: DataSnapshot) {
                var userList = mutableListOf<User>()
                val adapter = UserAdapter()



               snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                   if (user != null) {
                       userList.add(user)
                   }
                }
               adapter.data = userList
               newMessageRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }
}

