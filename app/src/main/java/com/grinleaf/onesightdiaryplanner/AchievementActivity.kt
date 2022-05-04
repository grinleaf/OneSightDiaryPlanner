package com.grinleaf.onesightdiaryplanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grinleaf.onesightdiaryplanner.databinding.ActivityAchievementBinding

class AchievementActivity : AppCompatActivity() {
    val binding by lazy { ActivityAchievementBinding.inflate(layoutInflater) }
    val userInformationItems= mutableListOf<UserInformationItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tvUserNicknameLevelAchievement.text= G.userNickname
        
        userInformationItems.add(UserInformationItem(1,"","검은콩두유"))
        userInformationItems.add(UserInformationItem(2,"","비타민"))
        userInformationItems.add(UserInformationItem(3,"","나초"))
        userInformationItems.add(UserInformationItem(4,"","감자깡"))
        userInformationItems.add(UserInformationItem(5,"","민트초코"))

        binding.recyclerAchievement.adapter= AchievementAdapter(this,userInformationItems)
        
        //플로팅버튼
        binding.btnFloatingAchievement.imageTintList= null
        binding.btnFloatingAchievement.setOnClickListener {
            val intent= Intent(this@AchievementActivity, RewardActivity::class.java)
            startActivity(intent)
        }
    }
}