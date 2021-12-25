package com.example.messenger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class UserAdapter() : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

     var data = listOf<User>()
        set(value){
            field = value
            notifyDataSetChanged()
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind( item)

    }



    override fun getItemCount() = data.size


    class UserViewHolder private constructor(view: View): RecyclerView.ViewHolder(view){
        val usernameText = view.findViewById<TextView>(R.id.username_textView)
        val usernameImage: ImageView = view.findViewById(R.id.username_imageView)

        fun bind(
            item: User) {
            usernameText.text = item.username
            Picasso.get().load(item.profileImageUrl).into(usernameImage)
        }

        companion object {
            fun from(parent: ViewGroup): UserViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.user_row_new_message, parent, false)
                return UserViewHolder(view)
            }
        }
    }

}

