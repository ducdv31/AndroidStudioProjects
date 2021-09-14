package com.example.myhome.ui.adapter.storage.pdf

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.utils.Constants
import com.google.firebase.storage.StorageReference

class PdfNameAdapter(private val activity: BaseActivity) :
    RecyclerView.Adapter<PdfNameAdapter.PdfNameViewHolder>() {

    private val TAG = PdfNameAdapter::class.java.simpleName
    private var listData: MutableList<StorageReference> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<StorageReference>) {
        listData = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfNameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_name_file_storage, parent, false)
        return PdfNameViewHolder(view)
    }

    override fun onBindViewHolder(holder: PdfNameViewHolder, position: Int) {
        val data = listData[position]
        holder.name.text = data.name
        holder.lnName.setOnClickListener {
            data.downloadUrl.addOnSuccessListener {
                val intent = Intent(Intent.ACTION_QUICK_VIEW)
                intent.setDataAndType(it, Constants.PDF_APPLICATION)
                activity.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class PdfNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_file)
        val lnName: LinearLayout = itemView.findViewById(R.id.ln_name_file)
    }
}