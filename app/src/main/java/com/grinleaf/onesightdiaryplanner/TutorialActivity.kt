package com.grinleaf.onesightdiaryplanner

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grinleaf.onesightdiaryplanner.databinding.ActivityTutorialBinding

class TutorialActivity : AppCompatActivity() {

    val binding:ActivityTutorialBinding by lazy { ActivityTutorialBinding.inflate(layoutInflater) }
    var imgIds= mutableListOf<TutorialImage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pref: SharedPreferences = getSharedPreferences("tutorial", MODE_PRIVATE)
        G.isFirst= pref.getBoolean("isFirst",true)
        if(!G.isFirst) skipActivity()

        //샘플 튜토리얼 이미지
        imgIds.add(TutorialImage(R.drawable.tutorial_sample01))
        imgIds.add(TutorialImage(R.drawable.tutorial_sample02))
        imgIds.add(TutorialImage(R.drawable.tutorial_sample03))
        imgIds.add(TutorialImage(R.drawable.tutorial_sample04))

        binding.pagerTutorial.adapter= TutorialAdapter(this, imgIds)

        binding.btnTutorialSkip.setOnClickListener { skipActivity() }
        binding.btnTutorialDestroy.setOnClickListener {
            G.isFirst= false
            val pref: SharedPreferences = getSharedPreferences("tutorial", MODE_PRIVATE)
            val editor= pref.edit()
            editor.putBoolean("isFirst",G.isFirst)
            editor.commit()
            skipActivity()
        }
    }

    fun skipActivity(){
        val intent= Intent(this@TutorialActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}