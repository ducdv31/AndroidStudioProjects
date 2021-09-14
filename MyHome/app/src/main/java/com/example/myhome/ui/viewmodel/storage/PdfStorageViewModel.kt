package com.example.myhome.ui.viewmodel.storage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage

class PdfStorageViewModel : ViewModel() {
    // Create a storage reference from our app
    private var storageRef = FirebaseStorage.getInstance().reference
    private val pathPdf = storageRef.child("PDF")

    @SuppressLint("SetJavaScriptEnabled")
    fun loadPdf(
        activity: Activity,
        nameFile: String,
    ) {
        pathPdf.child(nameFile).downloadUrl.addOnSuccessListener {
            val intent = Intent(Intent.ACTION_QUICK_VIEW)
            intent.setDataAndType(it, "application/pdf")
            activity.startActivity(intent)
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    fun getListData() {
        pathPdf.listAll().addOnSuccessListener {
            Log.e("items name", "getListData: ${it.items[0].name}")
            Log.e("items path", "getListData: ${it.items[0].path}")
        }
    }
}