package com.example.myhome.ui.view.fragment.storage

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.myhome.BaseFragment
import com.example.myhome.R
import com.example.myhome.ui.viewmodel.storage.PdfStorageViewModel

class PdfStorageFragment : BaseFragment() {

    private val pdfStorageViewModel: PdfStorageViewModel by lazy {
        ViewModelProvider(requireActivity())[PdfStorageViewModel::class.java]
    }

    override fun getLayout(): Int {
        return R.layout.fragment_pdf_storage
    }

    override fun initVar(rootView: View) {

    }

    override fun initListener() {

    }

    override fun handleLogic() {
        pdfStorageViewModel.loadPdf(requireActivity(), "kotlin-reference.pdf")
    }

}