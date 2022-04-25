package com.grinleaf.onesightdiaryplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.grinleaf.onesightdiaryplanner.databinding.ActivityRewardBinding

class RewardActivity : AppCompatActivity() {
    val binding by lazy { ActivityRewardBinding.inflate(layoutInflater) }
    val rewardItems= mutableListOf<RewardGridItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        rewardItems.add(RewardGridItem(R.drawable.ic_uni_01,"달성과제 01"))
        rewardItems.add(RewardGridItem(R.drawable.ic_leaf,"달성과제 02"))
        rewardItems.add(RewardGridItem(R.drawable.ic_search,"달성과제 03"))
        rewardItems.add(RewardGridItem(R.drawable.ic_friends,"달성과제 04"))
        rewardItems.add(RewardGridItem(R.drawable.ic_girl_01,"달성과제 05"))
        rewardItems.add(RewardGridItem(R.drawable.ic_flower,"달성과제 06"))
        rewardItems.add(RewardGridItem(R.drawable.ic_dog_02,"달성과제 07"))
        rewardItems.add(RewardGridItem(R.drawable.ic_bff,"달성과제 08"))
        rewardItems.add(RewardGridItem(R.drawable.ic_grape,"달성과제 09"))
        rewardItems.add(RewardGridItem(R.drawable.ic_car,"달성과제 10"))
        rewardItems.add(RewardGridItem(R.drawable.ic_dog_03,"달성과제 11"))
        rewardItems.add(RewardGridItem(R.drawable.store_icon_bee,"달성과제 12"))
        rewardItems.add(RewardGridItem(R.drawable.ic_cat_01,"달성과제 13"))
        rewardItems.add(RewardGridItem(R.drawable.ic_dog_01,"달성과제 14"))

        binding.recyclerReward.adapter= RewardAdapter(this,rewardItems)
    }
}