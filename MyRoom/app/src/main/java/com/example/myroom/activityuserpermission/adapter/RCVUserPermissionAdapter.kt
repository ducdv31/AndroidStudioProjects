package com.example.myroom.activityuserpermission.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myroom.R
import com.example.myroom.activityuserpermission.model.UserPermissionImg
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class RCVUserPermissionAdapter() :
    RecyclerView.Adapter<RCVUserPermissionAdapter.UserPermissionViewHolder>(), Filterable {
    private var listUser: MutableList<UserPermissionImg> = mutableListOf()
    private var listUserOld: MutableList<UserPermissionImg> = mutableListOf()

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
        this.listUserOld = list
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
        holder.level.text =
            when (userPermission.perm) {
                0 -> "Super-Root user"
                1 -> "Root user"
                2 -> "Member"
                3 -> "None"
                else -> "UnKnown"
            }
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

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                listUser = if (constraint.toString().isEmpty()) {
                    listUserOld
                } else {
                    val list: MutableList<UserPermissionImg> = mutableListOf()
                    for (user: UserPermissionImg in listUser) {
                        if (user.email.toLowerCase(Locale.ROOT)
                                .contains(constraint.toString().toLowerCase(Locale.ROOT))
                        ) {
                            list.add(user)
                        }
                    }
                    list
                }
                val filterResult = FilterResults()
                filterResult.values = listUser

                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listUser = results?.values as MutableList<UserPermissionImg>
                notifyDataSetChanged()
            }

        }
    }
}