package com.example.myhome.ui.view.fragment.storage

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.BaseFragment
import com.example.myhome.R
import com.example.myhome.ui.adapter.storage.pdf.PdfStorageAdapter
import com.example.myhome.ui.view.activity.storage.StorageActivity
import com.example.myhome.ui.viewmodel.storage.PdfStorageViewModel

class PdfStorageFragment : BaseFragment() {

    private val pdfStorageViewModel: PdfStorageViewModel by lazy {
        ViewModelProvider(requireActivity())[PdfStorageViewModel::class.java]
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var pdfStorageAdapter: PdfStorageAdapter
    private lateinit var llmn: LinearLayoutManager
    private lateinit var storageActivity: StorageActivity
    private lateinit var progressBar: ProgressBar

    override fun getLayout(): Int {
        return R.layout.fragment_pdf_storage
    }

    override fun initVar(rootView: View) {
        storageActivity = activity as StorageActivity
        progressBar = rootView.findViewById(R.id.progress_bar)
        recyclerView = rootView.findViewById(R.id.rv_storage)
        pdfStorageAdapter = PdfStorageAdapter(storageActivity)
        llmn = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = llmn
        recyclerView.adapter = pdfStorageAdapter
    }

    override fun initListener() {
        showLoading(true)
        pdfStorageViewModel.getListPdfName(pdfStorageAdapter, {
            showLoading(false)
        }, {
            showLoading(false)
        })
    }

    override fun handleLogic() {
    }

    private fun showLoading(isShow: Boolean) {
        if (isShow) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}