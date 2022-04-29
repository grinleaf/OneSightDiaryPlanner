package com.grinleaf.onesightdiaryplanner

abstract class Base(open var day:String,open var content:String,open var categoryImage:Int)

data class TimelineItem(
    override var day:String,
    override var content:String,
    override var categoryImage:Int,
    var dayImage:Int
    ):Base(day,content,categoryImage)

data class DailyItem(
    override var day:String,
    override var content:String,
    override var categoryImage:Int,
    var dayImage:String,
    var detailContent:String
    ):Base(day,content,categoryImage)

data class ChecklistItem(
    override var day:String,
    override var content:String,
    override var categoryImage:Int,
//    var subContent:String
    ):Base(day,content,categoryImage)

//class ChecklistItem(
//    override var day:String,
//    override var content:String,
//    override var categoryImage:Int,
//    private var subItemList: List<SubChecklistItem>
//    ):Base(day,content,categoryImage) {
//
//    fun getSubItemList(): List<SubChecklistItem> { return subItemList }
//    fun setSubItemList(subItemList: List<SubChecklistItem>) { this.subItemList = subItemList }
//}
//class SubChecklistItem(var subContent:String) {
//    var subItemImage = 0
//}

data class LifecycleItem(
    override var day:String,
    override var content:String,
    override var categoryImage:Int,
    var repeatCycle:Int,
    var startDay:String,
    var endDay:String
    ):Base(day,content,categoryImage)

data class BucketlistItem(
    override var day:String,
    override var content:String,
    override var categoryImage:Int
):Base(day,content,categoryImage)

data class MypageGridItem(var mypageImage:Int, var mypageContent:String)

data class RewardGridItem(var rewardImage:Int, var rewardContent:String, var isComplete:Boolean)

data class UserInformationItem(var userLank:Int, var profileImage:Int, var userNickname:String){

}

data class TutorialImage constructor(var imgId:Int)

//공통 데이터 클래스(Base)상속
//0. base       : 일정 날짜, 일정 내용, 카테고리 이미지
//1. TimeLine   : ...
//2. daily      : 일정 날짜, 일정 내용, 카테고리 이미지 + 일정 이미지
//3. checklist  : 일정 날짜, 일정 내용, 카테고리 이미지 + 일정 수행여부, 하위항목내용, 하위항목 수행여부
//4. lifecycle  : 일정 날짜, 일정 내용, 카테고리 이미지 + 반복주기
//5. Bucketlist : 일정 날짜, 일정 내용, 카테고리 이미지
//6. Userinfo   : 프로필 이미지, 계정 ID, PW, 닉네임, 등급 점수, ...