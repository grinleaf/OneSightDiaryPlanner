package com.grinleaf.onesightdiaryplanner

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface RetrofitService {

    @GET("OneSightDiaryPlanner/dailyNote.php")
    fun getDailyNoteItem(
        @Query("day") day: String,
        @Query("content") content: String,
        @Query("categoryImage") categoryImage:String,
        @Query("dayImage") dayImage: String,
        @Query("detailContent") detailContent:String
    ): Call<String>

    @GET("OneSightDiaryPlanner/dailyNote.php")
    fun getDailyNoteItem2(
        @Query("day") day: String,
        @Query("title") content: String,
        @Query("category_image") categoryImage:Int,
        @Query("attach_image") dayImage: String,
        @Query("content") detailContent:String
    ): Call<String>

    //사진앱 이미지 업로드
    @Multipart
    @POST("OneSightDiaryPlanner/attachImages.php")
    fun uploadImage(@Part file:MultipartBody.Part):Call<String>

    //카테고리 이미지 로드
    @GET("OneSightDiaryPlanner/loadCategory.php")
    fun getCategoryImage(): Call<ArrayList<CategoryImage>>
}