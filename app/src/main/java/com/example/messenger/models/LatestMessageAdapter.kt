package com.example.messenger.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class LatestMessageAdapter(): RecyclerView.Adapter<LatestMessageAdapter.LatestMessageViewHolder>() {

    var chatMessageData = listOf<ChatMessage>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMessageViewHolder {
       return LatestMessageViewHolder( LayoutInflater.from(parent.context).inflate(
           R.layout.latest_message_row_item, parent, false
       ))
    }

    override fun onBindViewHolder(holder: LatestMessageViewHolder, position: Int) {
      //  val user = userData[position]
        val chatMessage = chatMessageData[position]
        val chatPartnerId: String
        if(chatMessage.fromId == FirebaseAuth.getInstance().uid){
            chatPartnerId = chatMessage.toId
        }else{
            chatPartnerId = chatMessage.fromId
        }
        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                if (user != null) {
                    holder.bind(chatMessage, user)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun getItemCount(): Int {
        return chatMessageData.size
    }

    class LatestMessageViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nameTextView: TextView = view.findViewById(R.id.textView_latest_message_row_name)
        val messageTextView: TextView = view.findViewById(R.id.textView_latest_message_row_message)
        val profileImageView: ImageView = view.findViewById(R.id.imageView_latest_message_row)

        fun bind( chatMessage: ChatMessage, user: User ) {
            nameTextView.text = user.username
            messageTextView.text = chatMessage.text
            val uri = user.profileImageUrl
            Picasso.get().load(uri).into(profileImageView)
        }
    }
}