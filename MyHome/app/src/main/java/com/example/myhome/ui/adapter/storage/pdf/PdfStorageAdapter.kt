package com.example.myhome.ui.adapter.storage.pdf

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.utils.Constants
import com.google.firebase.storage.StorageReference

class PdfStorageAdapter(private val activity: BaseActivity) :
    RecyclerView.Adapter<PdfStorageAdapter.PdfNameViewHolder>() {

    private val TAG = PdfStorageAdapter::class.java.simpleName
    private var listData: MutableLiveData<MutableList<StorageReference>> = MutableLiveData()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<StorageReference>) {
        listData.value = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfNameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_name_file_storage, parent, false)
        return PdfNameViewHolder(view)
    }

    override fun onBindViewHolder(holder: PdfNameViewHolder, position: Int) {
        val data = listData.value?.get(position) ?: return
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
        return listData.value?.size ?: 0
    }

    class PdfNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_file)
        val lnName: LinearLayout = itemView.findViewById(R.id.ln_name_file)
    }
}