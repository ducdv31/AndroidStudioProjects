package com.example.myroom.activityuserpermission.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myroom.R
import com.example.myroom.activityuserpermission.model.UserPermissionImg
import de.hdodenhof.circleimageview.CircleImageView

class RCVUserPermissionAdapter() :
    RecyclerView.Adapter<RCVUserPermissionAdapter.UserPermissionViewHolder>() {
    private var listUser: MutableList<UserPermissionImg> = mutableListOf()

    interface IClickUserPermission {
        fun onClickUserPermission(userPermissionImg: UserPermissionImg)
        fun onClickEditPermissionUser(userPermissionImg: UserPermissionImg)
    }

    private lateinit var iClickUserPermission: IClickUserPermission
    private lateinit var context: Activity

    constructor(context: Activity, iClickUserPermission: IClickUserPermission) : this() {
        this.context = context
        this.iClickUserPermission = iClickUserPermission
    }

    fun setData(list: MutableList<UserPermissionImg>) {
        this.listUser = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPermissionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_permission_list, parent, false)
        return UserPermissionViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserPermissionViewHolder, position: Int) {
        val userPermission = listUser[position]
        holder.username.text = userPermission.username
        holder.email.text = userPermission.email
        holder.level.text = userPermission.perm.toString()
        Glide.with(context).load(userPermission.uri.toUri()).into(holder.imageUser)
        holder.editPermission.setOnClickListener {
            iClickUserPermission.onClickEditPermissionUser(userPermission)
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    class UserPermissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.user_name_user_permission)
        val email: TextView = itemView.findViewById(R.id.email_user_permission)
        val level: TextView = itemView.findViewById(R.id.level_user_permission)
        val cardView: CardView = itemView.findViewById(R.id.cardView_user_permission_display)
        val editPermission: ImageView = itemView.findViewById(R.id.edit_permission_user)
        val imageUser: CircleImageView = itemView.findViewById(R.id.image_user_permission)
    }
}