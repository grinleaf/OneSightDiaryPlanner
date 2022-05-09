package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toDrawable
import com.bumptech.glide.Glide

class G {
    companion object{
        var userId= ""
        var userNickname= ""
        var userEmail= ""
        var userProfileImage= ""
        var userPassword= ""

        var editBucketlistTitle= "${G.userNickname} 님의 버킷리스트"

        var isFirst= true   //튜토리얼 스킵
        var isLogin= false

        var dailyNoteItems= mutableListOf<DailyItem>()
        var checklistItems= mutableListOf<ChecklistItem>()
        var checklistSubItems= mutableListOf<ChecklistSubItem>()
        var lifecycleItems= mutableListOf<LifecycleItem>()
        var bucketlistItems= mutableListOf<BucketlistItem>()
        var bucketlistSubItems= mutableListOf<ChecklistSubItem>()
//        var timelineItems= mutableListOf<TimelineItem>()
        var categoryImages= mutableListOf<String>()

        var selectedCategoryImage:String= ""
        var selectedattachImage:String= ""

        var dayOfDailyNote= ""
        var dayOfCheckList= ""
        var dayOfTimeline= ""
        var dayOfCalendar= ""


    }
}