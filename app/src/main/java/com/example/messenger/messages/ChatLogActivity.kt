package com.example.messenger.messages

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.messenger.R
import com.example.messenger.User
import com.example.messenger.models.ChatAdapter
import com.example.messenger.models.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat_log.*


class ChatLogActivity : AppCompatActivity() {

    companion object{
       val TAG = "ChatLogActivity"
    }
    var toUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        val sendButtonChatLog: Button = findViewById(R.id.send_button_chat_log)
        val editTextChatLog: EditText = findViewById(R.id.edittext_chat_log)


        toUser = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        if(toUser!=null) {
            supportActionBar?.title = toUser!!.username
        }
        val adapter = toUser?.let { ChatAdapter(it) }

        if (adapter != null) {
            listenForMessages(adapter)
        }
        //Setting the button to send message to the database
        sendButtonChatLog.setOnClickListener {

            performSendMessage()
            editTextChatLog.text.clear()
        }

    }

    private fun listenForMessages(adapter: ChatAdapter) {
        val ref = FirebaseDatabase.getInstance().getReference("/messages")


        ref.addChildEventListener(object: ChildEventListener{

            var chatMessageList = mutableListOf<ChatMessage>()

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    chatMessageList.add(chatMessage)
                }

                if (adapter != null) {
                    adapter.data = chatMessageList
                }
                recycler_view_chat_log.adapter = adapter

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
/*
This function sends messages with other info like sender and receiver id with timestamp
 */
    private fun performSendMessage(){
       val text = edittext_chat_log.text.toString()
        //This gets us signed in user Uid
        val fromId = FirebaseAuth.getInstance().uid
        //this gives us the receiver's id
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)

        val toId = user!!.uid

        if(fromId == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()
        val chatMessage = ChatMessage(reference.key!!,text,fromId, toId, System.currentTimeMillis()/1000)
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "CHat Message saved at : ${reference.key}")
            }
    }
}
