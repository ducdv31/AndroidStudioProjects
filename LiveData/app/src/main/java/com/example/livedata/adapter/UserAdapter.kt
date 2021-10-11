package com.example.livedata.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.livedata.R
import com.example.livedata.model.User

class UserAdapter(private val context: Context) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val listUser: MutableLiveData<ArrayList<User>> = MutableLiveData()

    fun setData(list: ArrayList<User>) {
        listUser.value = list
        notifyDataSetChanged()
    }

    fun notifyDataUpdate(list: ArrayList<User>) {
        listUser.value = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser.value?.get(position) ?: return
        val llmn = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val idAdapter = IdAdapter(context)
        holder.name.text = user.name
        holder.recyclerView.apply {
            layoutManager = llmn
            adapter = idAdapter
        }
        holder.checkBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                holder.recyclerView.visibility = View.VISIBLE
            } else {
                holder.recyclerView.visibility = View.GONE
            }
        }
        idAdapter.setData(user.ids)

    }

    override fun getItemCount(): Int {
        return listUser.value?.size ?: 0
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        val name: TextView = itemView.findViewById(R.id.name)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView_id)
    }
}