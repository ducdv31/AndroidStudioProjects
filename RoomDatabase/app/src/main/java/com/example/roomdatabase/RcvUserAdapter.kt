package com.example.roomdatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RcvUserAdapter() : RecyclerView.Adapter<RcvUserAdapter.UserViewHolder>() {
    private var listUser = mutableListOf<User>()

    interface IClickUser {
        fun onClickUpdate(user: User)
        fun onClickDeleteUser(user: User)
    }

    private lateinit var iClickUser: IClickUser

    constructor(iClickUser: IClickUser) : this() {
        this.iClickUser = iClickUser
    }

    fun setData(list: MutableList<User>) {
        listUser = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: User = listUser[position]
        holder.name.text = user.name
        holder.age.text = user.age.toString()
        holder.update.setOnClickListener {
            iClickUser.onClickUpdate(user)
        }
        holder.delete.setOnClickListener {
            iClickUser.onClickDeleteUser(user)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val age: TextView = itemView.findViewById(R.id.age)
        val update: Button = itemView.findViewById(R.id.update_user)
        val delete: Button = itemView.findViewById(R.id.delete_user)
    }
}