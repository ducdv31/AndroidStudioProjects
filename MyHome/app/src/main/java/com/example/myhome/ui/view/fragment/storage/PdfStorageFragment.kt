package com.example.myhome.ui.view.fragment.storage

import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.BaseFragment
import com.example.myhome.R
import com.example.myhome.ui.adapter.storage.pdf.PdfNameAdapter
import com.example.myhome.ui.view.activity.storage.StorageActivity
import com.example.myhome.ui.viewmodel.storage.PdfStorageViewModel

class PdfStorageFragment : BaseFragment() {

    private val pdfStorageViewModel: PdfStorageViewModel by lazy {
        ViewModelProvider(requireActivity())[PdfStorageViewModel::class.java]
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var pdfNameAdapter: PdfNameAdapter
    private lateinit var llmn: LinearLayoutManager
    private lateinit var storageActivity: StorageActivity

    override fun getLayout(): Int {
        return R.layout.fragment_pdf_storage
    }

    override fun initVar(rootView: View) {
        storageActivity = activity as StorageActivity
        recyclerView = rootView.findViewById(R.id.rv_storage)
        pdfNameAdapter = PdfNameAdapter(storageActivity)
        llmn = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = llmn
        recyclerView.adapter = pdfNameAdapter
    }

    override fun initListener() {
        pdfStorageViewModel.getListPdfName(pdfNameAdapter)
    }

    override fun handleLogic() {
    }

}