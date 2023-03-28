package com.example.appsqli.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsqli.R
import com.example.appsqli.data.model.User
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val context: Context, private val users: MutableList<User>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName: TextView = itemView.findViewById(R.id.txt_name)
        private val textEmail: TextView = itemView.findViewById(R.id.txt_email)
        private val textPhone: TextView = itemView.findViewById(R.id.txt_phone)
        private val btnUpdate: ImageView = itemView.findViewById(R.id.btn_update)
       private val userImage: CircleImageView = itemView.findViewById<CircleImageView>(R.id.img_profile)



        fun bind(user: User) {
            textName.text = user.first_name
            textEmail.text = "Email : " + user.email
            textPhone.text = "Phone : " + user.mobile
            Glide.with(itemView.context).load(user?.avatar).into(userImage)

            userImage.setOnClickListener({
                onItemClickListener.onItemClick(user,"DETAIL")
                textName.text = user.first_name
                textEmail.text = "Email : " + user.email
                textPhone.text = "Phone : " + user.mobile
                Glide.with(itemView.context).load(user?.avatar).into(userImage)

            })

        btnUpdate.setOnClickListener( {
            onItemClickListener.onItemClick(user, "EDIT")
        })

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])



    }

    override fun getItemCount(): Int {
        return users.size
    }

    interface OnItemClickListener {
        fun onItemClick(user: User, type:String)
    }
    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem == newItem
        }
    }
}
