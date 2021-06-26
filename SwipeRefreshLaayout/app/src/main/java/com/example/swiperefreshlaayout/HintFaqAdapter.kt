package com.example.swiperefreshlaayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class HintFaqAdapter : RecyclerView.Adapter<HintFaqAdapter.HintViewHolder>() {

    private var listHint: MutableList<Hint> = mutableListOf()


    fun setData(list: MutableList<Hint>) {
        this.listHint = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hint, parent, false)
        return HintViewHolder(view)
    }

    override fun onBindViewHolder(holder: HintViewHolder, position: Int) {
        val hint: Hint = listHint[position] ?: return
        holder.hintTv.text = hint.hint
    }

    override fun getItemCount(): Int {
        return listHint.size
    }

    class HintViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hintTv = itemView.findViewById<TextView>(R.id.faq_hint)
        val cardHint = itemView.findViewById<CardView>(R.id.cardView_hint_faq)
    }
}