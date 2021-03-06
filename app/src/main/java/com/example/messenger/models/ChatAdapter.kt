package com.example.messenger.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.R
import com.example.messenger.User
import com.example.messenger.messages.LatestMessagesActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class ChatAdapter(val user: User): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data = listOf<ChatMessage>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    private val VIEW_TYPE_ONE = 1
    private val VIEW_TYPE_TWO = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == VIEW_TYPE_ONE) {

             ChatToViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_to_row,
                parent, false))}
        else{
            ChatFromViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_from_row,
                parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chatMessage = data[position]
        if (data[position].fromId == FirebaseAuth.getInstance().uid) {
            (holder as ChatToViewHolder)
                .bind(chatMessage)
        } else {
            (holder as ChatFromViewHolder)
                .bind(chatMessage, user)
        }
    }
    override fun getItemViewType(position: Int): Int {
        return   if (data[position].fromId == FirebaseAuth.getInstance().uid) {
            VIEW_TYPE_ONE
        } else {
            VIEW_TYPE_TWO
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ChatFromViewHolder(view: View): RecyclerView.ViewHolder(view){
        val chatFromTextview: TextView = view.findViewById(R.id.textView_chat_from_row)
        val chatFromImageView: ImageView = view.findViewById(R.id.imageView_chat_from_row)


        fun bind(chatMessage: ChatMessage, user: User) {

            chatFromTextview.text = chatMessage.text
            val uri = user.profileImageUrl
            Picasso.get().load(uri).into(chatFromImageView)
        }
    }

    class ChatToViewHolder(view: View): RecyclerView.ViewHolder(view){
        val chatToTextview: TextView = view.findViewById(R.id.textView_chat_to_row)
        val chatToImageview: ImageView = view.findViewById(R.id.imageView_chat_to_row)

        fun bind(chatMessage: ChatMessage) {
            chatToTextview.text = chatMessage.text
           val uri = LatestMessagesActivity.currentUser?.profileImageUrl
            Picasso.get().load(uri).into(chatToImageview)


        }
    }
}



