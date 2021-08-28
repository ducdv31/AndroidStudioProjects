package com.example.myhome.ui.adapter.managerUserAdapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myhome.R
import com.example.myhome.data.model.manageUserModel.UserProfileHasIDModel
import com.example.myhome.utils.Constants
import de.hdodenhof.circleimageview.CircleImageView

class ManageUserAdapter(private val context: Context, private val iClickItemUser: IClickItemUser) :
    RecyclerView.Adapter<ManageUserAdapter.ManageUserViewHolder>() {

    private val TAG = ManageUserAdapter::class.java.simpleName
    private var listUser: MutableList<UserProfileHasIDModel>? = null

    fun interface IClickItemUser {
        fun onClickItemUser(userProfileHasIDModel: UserProfileHasIDModel)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<UserProfileHasIDModel>) {
        listUser =
            list.sortedBy(UserProfileHasIDModel::getPermissionEx) as MutableList<UserProfileHasIDModel>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageUserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ManageUserViewHolder(view)
    }

    override fun onBindViewHolder(holder: ManageUserViewHolder, position: Int) {
        val userProfileHasIDModel = listUser?.get(position) ?: return
        Glide.with(context)
            .load(userProfileHasIDModel.photoUri.toString())
            .placeholder(R.drawable.outline_account_circle_black_48dp)
            .error(R.drawable.outline_account_circle_black_48dp)
            .into(holder.imgUser)
        holder.userName.text = userProfileHasIDModel.displayName
        holder.email.text = userProfileHasIDModel.email
        holder.typeUser.text = when (userProfileHasIDModel.permission) {
            0 -> {
                holder.typeUser.setTextColor(GREEN)
                Constants.MASTER
            }
            1 -> {
                holder.typeUser.setTextColor(BLUE)
                Constants.ROOT
            }
            2 -> {
                holder.typeUser.setTextColor(YELLOW)
                Constants.NORMAL
            }
            else -> {
                holder.typeUser.setTextColor(RED)
                Constants.NONE
            }
        }
        holder.cardUser.setOnLongClickListener {
            iClickItemUser.onClickItemUser(userProfileHasIDModel)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return listUser?.size ?: 0
    }

    class ManageUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.username)
        val email: TextView = itemView.findViewById(R.id.email)
        val typeUser: TextView = itemView.findViewById(R.id.type_user)
        val imgUser: CircleImageView = itemView.findViewById(R.id.img_user)
        val cardUser: FrameLayout = itemView.findViewById(R.id.card_user)
    }
}