package com.grinleaf.onesightdiaryplanner

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.grinleaf.onesightdiaryplanner.databinding.ActivityAlertSettingBinding

class AlertSettingActivity : AppCompatActivity() {
    val binding by lazy { ActivityAlertSettingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.container_fragment_alert, AlertSettingFragment()).commit()
        binding.backpressAlert.setOnClickListener { finish() }
    }

}