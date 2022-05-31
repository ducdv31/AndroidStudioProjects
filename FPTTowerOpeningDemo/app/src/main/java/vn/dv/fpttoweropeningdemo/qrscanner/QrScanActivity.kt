package vn.dv.fpttoweropeningdemo.qrscanner

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureManager
import vn.dv.fpttoweropeningdemo.R
import vn.dv.fpttoweropeningdemo.databinding.ActivityQrScanBinding

class QrScanActivity : AppCompatActivity() {
    private lateinit var capture: CaptureManager

    private lateinit var binding: ActivityQrScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        capture = CaptureManager(this, binding.bcScanner)
        capture.apply {
            initializeFromIntent(intent, savedInstanceState)
            decode()
        }
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        capture.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return binding.bcScanner.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }
}