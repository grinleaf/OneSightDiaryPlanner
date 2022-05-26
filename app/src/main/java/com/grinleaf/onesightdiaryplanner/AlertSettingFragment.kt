package com.grinleaf.onesightdiaryplanner

import android.app.TimePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import java.util.*

//AlertSettingActivity 의 container 에 배치될 Fragment
class AlertSettingFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_alert,rootKey)
    }
    val pref:SharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(requireContext()) }
    var timeString=""

    override fun onResume() {
        super.onResume()
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        super.onPause()
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    val listener= SharedPreferences.OnSharedPreferenceChangeListener { p0, p1 ->
        when(p1){   //p1 == xml 문서에서 지정한 key 값
            "setting_alert" -> {

            }
            "popup" -> {
                val s= pref.getString(p1, "")
            }
            "sound_alert" -> { Toast.makeText(context, "소리 테스트", Toast.LENGTH_SHORT).show() }
            "vib_alert" -> { Toast.makeText(context, "진동 테스트", Toast.LENGTH_SHORT).show() }
            "message_sleep" -> { }
            "time_sleep" -> {
                val cal= Calendar.getInstance()
                val timepicker= TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                    var hour= timePicker.hour.toString()
                    var minute= timePicker.minute.toString()
                    timeString= hour+ " 시 "+minute+" 분"
                }
                TimePickerDialog(context, timepicker, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false).show()
                val setTimeString= findPreference<Preference>(p1)
                setTimeString?.summary= timeString
            }
        }
    }
}