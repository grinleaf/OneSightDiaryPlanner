package com.grinleaf.onesightdiaryplanner

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface RetrofitService {

    //dailyNote 데이터 다운로드
    @GET("OneSightDiaryPlanner/dailyNoteDownload.php")
    fun getDailyNoteDownload(): Call<ArrayList<DailyItem>>

    //checklist 데이터 다운로드
    @GET("OneSightDiaryPlanner/checklistDownload.php")
    fun getChecklistDownload(): Call<ArrayList<ChecklistItem>>

    //lifecycle 데이터 다운로드
    @GET("OneSightDiaryPlanner/lifecycleDownload.php")
    fun getLifecycleDownload(): Call<ArrayList<LifecycleItem>>

    //bucketlist 데이터 다운로드
    @GET("OneSightDiaryPlanner/bucketlistDownload.php")
    fun getBucketlistDownload(): Call<ArrayList<BucketlistItem>>

    //dailyNote 데이터 업로드
    @GET("OneSightDiaryPlanner/dailyNoteUpload.php")
    fun getDailyNoteItem(
        @Query("email") email:String,
        @Query("day") day: String,
        @Query("content") content: String,
        @Query("categoryImage") categoryImage:String,
        @Query("dayImage") dayImage: String,
        @Query("detailContent") detailContent:String
    ): Call<String>

    //checklist 데이터 업로드
    @GET("OneSightDiaryPlanner/checklistUpload.php")
    fun getCheckListItem(
        @Query("email") email:String,
        @Query("day") day: String,
        @Query("content") content: String,
        @Query("categoryImage") categoryImage:String,
        @Query("detailContent") detailContent:String
    ): Call<String>

    //lifecycle 데이터 업로드
    @GET("OneSightDiaryPlanner/lifecycleUpload.php")
    fun getLifecycleItem(
        @Query("email") email:String,
        @Query("content") content: String,
        @Query("startday") startday: String,
        @Query("endday") endday: String,
        @Query("cycle") cycle: String,
        @Query("categoryImage") categoryImage:String,
        @Query("exportother") isBucket: String
    ): Call<String>

    //bucketlist 데이터 업로드
    @GET("OneSightDiaryPlanner/bucketlistUpload.php")
    fun getBucketListItem(
        @Query("email") email:String,
        @Query("content") content: String,
        @Query("day") day: String,
        @Query("categoryImage") categoryImage:String,
        @Query("detailContent") detailContent:String,
//        @Query("importother") isCycle: String
    ): Call<String>

    //사진앱 이미지 업로드
    @Multipart
    @POST("OneSightDiaryPlanner/attachImages.php")
    fun uploadImage(@PartMap dataPart:Map<String, String>, @Part filePart:MultipartBody.Part):Call<String>

    @GET("OneSightDiaryPlanner/selectedEmoUpload.php")
    fun uploadSelectedEmo(
        @Query("email") email:String,
        @Query("day") day:String,
        @Query("emo") emo:String
    ):Call<String>

    //카테고리 이미지 다운로드(default)
    @GET("OneSightDiaryPlanner/loadCategory.php")
    fun getCategoryImage(): Call<ArrayList<CategoryImage>>

    //today's emo 이미지 다운로드(default)
    @GET("OneSightDiaryPlanner/loadEmoImage.php")
    fun getemoImage(): Call<ArrayList<EmoImage>>

    //캘린더 날짜 선택 시 emo 이미지 다운로드 + useremail, day
    @GET("OneSightDiaryPlanner/selectedEmoDownload.php")
    fun getSelectedEmoImage(): Call<ArrayList<SelectedDayEmo>>
}