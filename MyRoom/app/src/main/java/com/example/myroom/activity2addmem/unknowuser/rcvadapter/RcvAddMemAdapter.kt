package com.example.myroom.activity2addmem.unknowuser.rcvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.myroom.R
import com.example.myroom.activity2addmem.unknowuser.model.UserID

class RcvAddMemAdapter() : RecyclerView.Adapter<RcvAddMemAdapter.AddMemViewHolder>() {
    var listIdAdd : MutableList<UserID> = mutableListOf()
    var viewBinderHelper:ViewBinderHelper = ViewBinderHelper()
    interface IClickUserAdd{
        fun onClickUser(userID: UserID)
        fun onClickDeleteUser(userID: UserID)
    }
    private var iClickUserAdd: IClickUserAdd? = null


    constructor(context: Context, iClickUserAdd: IClickUserAdd) : this() {
        this.iClickUserAdd = iClickUserAdd
    }

    fun setData(list: MutableList<UserID>){
        listIdAdd = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMemViewHolder {
        val view:View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_add_user, parent, false)
        return AddMemViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddMemViewHolder, position: Int) {
        val userID: UserID = listIdAdd[position]
        viewBinderHelper.bind(holder.swipeRevealLayout, userID.id)
        holder.id.text = userID.id
        holder.cardView.setOnClickListener(View.OnClickListener {
            /* open setup user fragment */
            iClickUserAdd?.onClickUser(userID)
        })
        holder.delete.setOnClickListener(View.OnClickListener {
            /* Delete user */
            iClickUserAdd?.onClickDeleteUser(userID)
        })
    }

    override fun getItemCount(): Int {
        return listIdAdd.size
    }

    class AddMemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val id:TextView = itemView.findViewById(R.id.id_card_add_member)
        val swipeRevealLayout:SwipeRevealLayout = itemView.findViewById(R.id.swipeReveal)
        val cardView:CardView = itemView.findViewById(R.id.card_id_add_member)
        val delete:ImageView = itemView.findViewById(R.id.delete_unKnown_user)
    }
}