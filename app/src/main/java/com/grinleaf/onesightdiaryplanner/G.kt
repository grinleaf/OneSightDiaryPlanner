package com.grinleaf.onesightdiaryplanner

import android.content.Context
import com.bumptech.glide.Glide

class G {
    companion object{
        var userId= ""
        var userNickname= ""
        var userEmail= ""
        var userProfileImage= R.drawable.tutorial_sample04
        var userPassword= ""

        var editBucketlistTitle= "${G.userNickname} 님의 버킷리스트"

        var isFirst= true
        var isLogin= false

        var dailyNoteItems= mutableListOf<DailyItem>()
        var checklistItems= mutableListOf<ChecklistItem>()
        var lifecycleItems= mutableListOf<LifecycleItem>()
        var bucketlistItem= mutableListOf<BucketlistItem>()
    }
}