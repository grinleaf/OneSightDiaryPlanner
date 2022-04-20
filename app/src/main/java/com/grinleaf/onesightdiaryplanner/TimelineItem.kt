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
    var dayImage:Int
    ):Base(day,content,categoryImage)

data class ChecklistItem(
    override var day:String,
    override var content:String,
    override var categoryImage:Int,
    var subContent:String
    ):Base(day,content,categoryImage)

data class LifecycleItem(
    override var day:String,
    override var content:String,
    override var categoryImage:Int,
    var repeatCycle:Int
    ):Base(day,content,categoryImage)



//공통 데이터 클래스(Base)상속
//1. daily      : 일정 날짜, 일정 내용, 카테고리 이미지 + 일정 이미지
//2. checklist  : 일정 날짜, 일정 내용, 카테고리 이미지 + 일정 수행여부, 하위항목내용, 하위항목 수행여부
//3. lifecycle  : 일정 날짜, 일정 내용, 카테고리 이미지 + 반복주기
//4. TimeLine   : ...