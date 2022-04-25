package com.grinleaf.onesightdiaryplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grinleaf.onesightdiaryplanner.databinding.ActivityAlertSettingBinding

class AlertSettingActivity : AppCompatActivity() {
    val binding by lazy { ActivityAlertSettingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}