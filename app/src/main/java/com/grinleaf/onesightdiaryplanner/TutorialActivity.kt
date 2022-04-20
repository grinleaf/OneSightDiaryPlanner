package com.grinleaf.onesightdiaryplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grinleaf.onesightdiaryplanner.databinding.ActivityTutorialBinding

class TutorialActivity : AppCompatActivity() {

    val binding:ActivityTutorialBinding by lazy { ActivityTutorialBinding.inflate(layoutInflater) }
    var imgIds= mutableListOf<TutorialImage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //샘플 튜토리얼 이미지
        imgIds.add(TutorialImage(R.drawable.tutorial_sample01))
        imgIds.add(TutorialImage(R.drawable.tutorial_sample02))
        imgIds.add(TutorialImage(R.drawable.tutorial_sample03))
        imgIds.add(TutorialImage(R.drawable.tutorial_sample04))

        binding.pagerTutorial.adapter= TutorialAdapter(this, imgIds)

        binding.btnTutorialSkip.setOnClickListener {
            val intent= Intent(this@TutorialActivity,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}