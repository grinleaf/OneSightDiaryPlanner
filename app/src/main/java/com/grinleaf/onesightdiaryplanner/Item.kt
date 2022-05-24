package com.grinleaf.onesightdiaryplanner

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyItem(
    var no: String,
    var email: String,
    var day:String,
    @SerializedName("title") var content:String,
    @SerializedName("categoryImage") var categoryImage:String,
    @SerializedName("attachImage") var dayImage:String,
    @SerializedName("content") var detailContent:String,
    )

data class ChecklistItem(
    var no: String,
    var email:String,
    var day:String,
    @SerializedName("title") var content:String,
    var categoryImage:String,
    @SerializedName("content") var detailContent:String,
    var isChecked:String
    )

data class ChecklistSubItem(var subContent:String)

data class LifecycleItem(
    var no: String,
    var email:String,
    @SerializedName("startday") var day:String,
    @SerializedName("title") var content:String,
    var categoryImage:String,
    @SerializedName("cycle") var repeatCycle:String,
    @SerializedName("endday") var endDay:String,
    @SerializedName("exportother") var isBucket:String,
    var isChecked:String
    )

data class BucketlistItem(
    var no: String,
    var email:String,
    var day:String,
    @SerializedName("title") var content:String,
    var categoryImage:String,
    @SerializedName("content") var detailContent:String
)

data class MypageGridItem(var mypageImage:Int, var mypageContent:String)

data class RewardGridItem(var rewardImage:Int, var rewardContent:String, var isComplete:Boolean)

data class UserInformationItem(var userLank:Int, var profileImage:String, var userNickname:String)

data class TutorialImage constructor(var imgId:Int)

data class CategoryImage(var no:String, var image:String)

data class EmoImage(var no:String, var image:String)

data class SelectedDayEmo(var no:String, var email:String,var day:String, var emo:String)