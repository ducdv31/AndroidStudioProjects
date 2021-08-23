package com.example.myhome.ui.view.fragment.preferences

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.myhome.R

class PreferenceFragment : PreferenceFragmentCompat() {
    companion object {
        const val KEY_SWITCH_MAP = "turn_on_map_preference"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)

        val sharePrefs: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireContext())

        val isShowMap: Boolean = sharePrefs.getBoolean(KEY_SWITCH_MAP, false)
        Toast.makeText(requireContext(), isShowMap.toString(), Toast.LENGTH_SHORT).show()
    }
}