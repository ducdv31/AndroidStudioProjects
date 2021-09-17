package com.example.myhome.ui.viewmodel.storage

import androidx.lifecycle.ViewModel
import com.example.myhome.ui.adapter.storage.pdf.PdfStorageAdapter
import com.example.myhome.utils.Constants
import com.google.firebase.storage.FirebaseStorage

class PdfStorageViewModel : ViewModel() {
    // Create a storage reference from our app
    private var storageRef = FirebaseStorage.getInstance().reference
    private val pathPdf = storageRef.child(Constants.PDF_PATH_STORAGE)

    fun getListPdfName(
        pdfStorageAdapter: PdfStorageAdapter,
        onSuccess: (() -> Unit) = {},
        onFailure: ((exception: Exception) -> Unit) = {}
    ) {
        pathPdf.listAll().addOnSuccessListener {
            onSuccess.invoke()
            pdfStorageAdapter.setData(it.items)
        }.addOnFailureListener {
            onFailure.invoke(it)
        }
    }
}