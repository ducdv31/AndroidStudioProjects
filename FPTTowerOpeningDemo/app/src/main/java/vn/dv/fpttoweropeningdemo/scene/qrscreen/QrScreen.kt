package vn.dv.fpttoweropeningdemo.scene.qrscreen

import android.os.Bundle
import android.widget.Toast
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import vn.dv.fpttoweropeningdemo.base.BaseFragment
import vn.dv.fpttoweropeningdemo.databinding.FragmentQrScreenBinding


class QrScreen : BaseFragment<FragmentQrScreenBinding>(FragmentQrScreenBinding::inflate) {
    override fun initData(data: Bundle?) {
    }

    override fun initViews() {
    }

    override fun initActions() {
    }

    override fun initListener() {
        with(binding) {
            btnScan.setOnClickListener {
                val option = ScanOptions().apply {
                    setBeepEnabled(false)
                    setCameraId(0)
                    setPrompt("Prompt QR")
                    setOrientationLocked(false)
                    setTorchEnabled(true)
                    setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
                }
                barcodeLauncher.launch(option)
            }
        }
    }

    override fun initObservers() {
    }

    // Register the launcher and result handler
    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents == null) {
            Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(
                context,
                "Scanned: " + result.contents,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}