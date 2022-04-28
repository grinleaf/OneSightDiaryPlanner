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
        
        userInformationItems.add(UserInformationItem(1,R.drawable.store_icon_bee,"검은콩두유"))
        userInformationItems.add(UserInformationItem(2,R.drawable.ic_cat_01,"비타민"))
        userInformationItems.add(UserInformationItem(3,R.drawable.ic_dog_01,"나초"))
        userInformationItems.add(UserInformationItem(4,R.drawable.ic_dog_03,"감자깡"))
        userInformationItems.add(UserInformationItem(5,R.drawable.ic_girl_01,"민트초코"))

        binding.recyclerAchievement.adapter= AchievementAdapter(this,userInformationItems)
        
        //플로팅버튼
        binding.btnFloatingAchievement.imageTintList= null
        binding.btnFloatingAchievement.setOnClickListener {
            val intent= Intent(this@AchievementActivity, RewardActivity::class.java)
            startActivity(intent)
        }
    }
}