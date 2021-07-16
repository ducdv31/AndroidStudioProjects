package com.example.bluetooth.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.bluetooth.R
import com.example.bluetooth.activityPsControl.PsControlActivity
import com.example.bluetooth.activitymain.MainActivity
import com.example.bluetooth.activitymain.fragment.PsControlFragment
import com.example.bluetooth.initbluetooth.InitBluetooth

class DialogSetting : DialogFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val mainActivity = activity as MainActivity
            val builder = AlertDialog.Builder(it)

            val inflate = requireActivity().layoutInflater
            val view = inflate.inflate(R.layout.setting_dialog, null, false)

            builder.setView(view)

            /* frag content */
            val about: TextView = view.findViewById(R.id.tv_about)
            val lnAbout: LinearLayout = view.findViewById(R.id.ln_about)
            val psController: TextView = view.findViewById(R.id.tv_ps_controller)

            about.setOnClickListener {
                dialog?.cancel()
                mainActivity.gotoAboutFragment()
            }

            psController.setOnClickListener {
                if (InitBluetooth.getInstance().hasBluetooth()) {
                    dialog?.cancel()
                    mainActivity.gotoFragment(PsControlFragment(), true)
                } else {
                    Toast.makeText(requireContext(), "No BT Adapter", Toast.LENGTH_SHORT).show()
                }
                //mainActivity.gotoActivity(PsControlActivity::class.java)
            }

            builder.create()
        }!!
    }
}