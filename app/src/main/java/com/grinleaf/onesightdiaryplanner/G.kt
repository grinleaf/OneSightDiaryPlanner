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

        var categoryImages= mutableListOf<String>()
        var loadEmoImages= mutableListOf<String>()
        var loadSelectedEmoImages= mutableListOf<SelectedDayEmo>()
        var saveEmoImages:String= ""

        var selectedCategoryImage:String= ""
        var selectedattachImage:String= ""

        var dayOfDailyNote= ""
        var dayOfTodolist= ""
        var dayOfTimeline= ""   //기존
        var dayOfTimelines= mutableListOf<String>() //타임라인용
        var dayOfCalendar= ""

        var isNotEmptyChecklistRecyclerItem= mutableListOf<String>()
        var isNotEmptyLifecycleRecyclerItem= mutableListOf<String>()

        var visibleCountDaily= mutableListOf<String>()
        var visibleCountCheck= mutableListOf<String>()
        var visibleCountLife= mutableListOf<String>()

//        var lastVisibilityDaily= mutableListOf<Int>()
        var lastVisibilityDaily= 0

        var matchDateChecklistItem= mutableListOf<ChecklistItem>()
        var matchDateLifecycleItem= mutableListOf<LifecycleItem>()
    }
}