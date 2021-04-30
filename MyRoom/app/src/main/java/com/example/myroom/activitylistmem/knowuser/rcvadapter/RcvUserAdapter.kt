package com.example.myroom.activitylistmem.knowuser.rcvadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.myroom.R
import com.example.myroom.activitylistmem.model.UserDetail
import java.util.*

class RcvUserAdapter() : RecyclerView.Adapter<RcvUserAdapter.UserViewHolder>(), Filterable {
    interface IClickUser {
        fun onClickUser(userDetail: UserDetail)
        fun onClickUpdateUser(userDetail: UserDetail)
        fun onClickDeleteUser(userDetail: UserDetail)
    }

    private var viewBinderHelper: ViewBinderHelper = ViewBinderHelper()
    private var listUser: List<UserDetail>? = null
    private var listUserOld: List<UserDetail>? = null
    var context: Context? = null
    private var iClickUser: IClickUser? = null

    constructor(context: Context, iClickUser: IClickUser) : this() {
        this.context = context
        this.iClickUser = iClickUser
    }

    fun setData(list: List<UserDetail>) {
        listUser = list
        listUserOld = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View? = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view!!)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userDetail: UserDetail = listUser!![position]
        viewBinderHelper.bind(holder.swipeRevealLayout, userDetail.id)
        holder.name.text = userDetail.name
        holder.room.text = userDetail.room
        holder.rank.text = userDetail.rank.toString()
        holder.id.text = userDetail.id
        holder.cardUser.setOnClickListener(View.OnClickListener {
            /* open detail user fragment */
            iClickUser!!.onClickUser(userDetail)
        })
        holder.delete.setOnClickListener(View.OnClickListener {
            iClickUser!!.onClickDeleteUser(userDetail)
        })
        holder.editUser.setOnClickListener(View.OnClickListener {
            iClickUser!!.onClickUpdateUser(userDetail)
        })
    }

    override fun getItemCount(): Int {
        if (listUser.isNullOrEmpty()) {
            return 0
        } else
            return listUser!!.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardUser: View = itemView.findViewById<View>(R.id.user_card)
        val name: TextView = itemView.findViewById<TextView>(R.id.user_name)
        val id: TextView = itemView.findViewById<TextView>(R.id.id_user)
        val rank: TextView = itemView.findViewById<TextView>(R.id.rank_user)
        val room: TextView = itemView.findViewById<TextView>(R.id.room_user)
        val delete: ImageView = itemView.findViewById<ImageView>(R.id.delete__user)
        val editUser: ImageView = itemView.findViewById<ImageView>(R.id.edit__user)
        val swipeRevealLayout: SwipeRevealLayout = itemView.findViewById(R.id.swipeReveal_list_user)
    }

    @ExperimentalStdlibApi
    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val strSearch: String = constraint.toString()
                if (strSearch.isEmpty()) {
                    listUser = listUserOld
                } else {
                    val list: MutableList<UserDetail> = mutableListOf()
                    for (userDetail: UserDetail in listUser!!) {
                        if (userDetail.name?.toLowerCase(Locale.ROOT)!!
                                .contains(strSearch.toLowerCase(Locale.ROOT))
                        ) {
                            list.add(userDetail)
                        }
                    }
                    listUser = list
                }

                val filterResult: FilterResults = FilterResults()
                filterResult.values = listUser

                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listUser = results?.values as List<UserDetail>?
                notifyDataSetChanged()
            }

        }
    }

}