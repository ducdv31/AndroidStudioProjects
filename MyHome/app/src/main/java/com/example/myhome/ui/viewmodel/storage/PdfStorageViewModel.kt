package com.example.myhome.ui.viewmodel.storage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.myhome.ui.adapter.storage.pdf.PdfNameAdapter
import com.example.myhome.utils.Constants
import com.google.firebase.storage.FirebaseStorage

class PdfStorageViewModel : ViewModel() {
    // Create a storage reference from our app
    private var storageRef = FirebaseStorage.getInstance().reference
    private val pathPdf = storageRef.child(Constants.PDF_PATH_STORAGE)

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

    fun getListPdfName(
        pdfNameAdapter: PdfNameAdapter,
        onSuccess: (() -> Unit) = {},
        onFailure: ((exception: Exception) -> Unit) = {}
    ) {
        pathPdf.listAll().addOnSuccessListener {
            onSuccess.invoke()
            pdfNameAdapter.setData(it.items)
        }.addOnFailureListener {
            onFailure.invoke(it)
        }
    }
}