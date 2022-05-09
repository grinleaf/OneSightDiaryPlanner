package com.grinleaf.onesightdiaryplanner

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class AlertSettingFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_alert,rootKey)
        val pref= PreferenceManager.getDefaultSharedPreferences(requireContext())
    }
    val pref:SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(requireContext()) }
    override fun onResume() {
        super.onResume()
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        super.onPause()
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    val listener= object :SharedPreferences.OnSharedPreferenceChangeListener{
        override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
            when(p1){
//                "popup" -> {
//                    val s= pref.getString(p1, "")
//                }
            }
        }

    }
}