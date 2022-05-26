package com.grinleaf.onesightdiaryplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grinleaf.onesightdiaryplanner.databinding.ActivityThemeSettingBinding

class ThemeSettingActivity : AppCompatActivity() {
    val binding by lazy { ActivityThemeSettingBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.backpressTheme.setOnClickListener { finish() }

        //팔레트 API 개발가이드 : https://developer.android.com/training/material/palette-colors?hl=ko

    }
}