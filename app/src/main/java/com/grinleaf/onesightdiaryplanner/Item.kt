package com.grinleaf.onesightdiaryplanner

import android.graphics.drawable.Drawable

abstract class Base(open var day:String,open var content:String,open var categoryImage:String)

data class TimelineItem(
    //dailynote
    var titleDailyNote:String,
    var dayDailyNote:String,
    var categoryDailyNote:Int,
    var attachImageDailyNote:String,
    var contentDailyNote:String,
    //checklist
    var titleCheckList:String,
    var contentCheckList:String,
    var categoryCheckList:Int,
    //checklist-sub
    var subContentCheckList:String,
    //lifecycle
    var titleLifecycle:String,
    var startDayLifecycle:String,
    var endDayLifecycle:String,
    var repeatCycle:String,
    var categoryLifecycle:Int,
    )

data class DailyItem(
    override var day:String,
    override var content:String,
    override var categoryImage:String,
    var dayImage:String,
    var detailContent:String
    ):Base(day,content,categoryImage)

data class ChecklistItem(
    override var day:String,
    override var content:String,
    override var categoryImage:String,
    var detailContent:String
    ):Base(day,content,categoryImage)

data class ChecklistSubItem(var subContent:String)

data class LifecycleItem(
    override var day:String,
    override var content:String,
    override var categoryImage:String,
    var repeatCycle:String,
    var endDay:String,
    var isBucket:String
    ):Base(day,content,categoryImage)

data class BucketlistItem(
    override var day:String,
    override var content:String,
    override var categoryImage:String,
    var detailContent:String
):Base(day,content,categoryImage)

data class MypageGridItem(var mypageImage:Int, var mypageContent:String)

data class RewardGridItem(var rewardImage:Int, var rewardContent:String, var isComplete:Boolean)

data class UserInformationItem(var userLank:Int, var profileImage:String, var userNickname:String){

}
data class TutorialImage constructor(var imgId:Int)

data class CategoryImage(var no:String, var image:String)

//공통 데이터 클래스(Base)상속
//0. base       : 일정 날짜, 일정 내용, 카테고리 이미지
//1. TimeLine   : ...
//2. daily      : 타이틀/내용/카테고리이미지/첨부이미지/세부내용
//3. checklist  : 일정 날짜, 일정 내용, 카테고리 이미지 + 일정 수행여부, 하위항목내용, 하위항목 수행여부
//4. lifecycle  : 일정 날짜, 일정 내용, 카테고리 이미지 + 반복주기
//5. Bucketlist : 일정 날짜, 일정 내용, 카테고리 이미지
//6. Userinfo   : 프로필 이미지, 계정 ID, PW, 닉네임, 등급 점수, ...