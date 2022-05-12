package com.grinleaf.onesightdiaryplanner

import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.annotations.SerializedName

data class DailyItem(
    var no: String,
    var email: String,
    var day:String,
    @SerializedName("title") var content:String,
    @SerializedName("categoryImage") var categoryImage:String,
    @SerializedName("attachImage") var dayImage:String,
    @SerializedName("content") var detailContent:String,
    )
//    :Base(day,content,categoryImage2)

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

data class UserInformationItem(var userLank:Int, var profileImage:String, var userNickname:String){

}
data class TutorialImage constructor(var imgId:Int)

data class CategoryImage(var no:String, var image:String)

data class EmoImage(var no:String, var image:String)

data class SelectedDayEmo(var no:String, var email:String,var day:String, var emo:String)

//공통 데이터 클래스(Base)상속
//0. base       : 일정 날짜, 일정 내용, 카테고리 이미지
//1. TimeLine   : ...
//2. daily      : 타이틀/내용/카테고리이미지/첨부이미지/세부내용
//3. checklist  : 일정 날짜, 일정 내용, 카테고리 이미지 + 일정 수행여부, 하위항목내용, 하위항목 수행여부
//4. lifecycle  : 일정 날짜, 일정 내용, 카테고리 이미지 + 반복주기
//5. Bucketlist : 일정 날짜, 일정 내용, 카테고리 이미지
//6. Userinfo   : 프로필 이미지, 계정 ID, PW, 닉네임, 등급 점수, ...