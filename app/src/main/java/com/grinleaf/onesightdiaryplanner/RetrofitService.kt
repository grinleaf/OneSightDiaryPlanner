package com.grinleaf.onesightdiaryplanner

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface RetrofitService {

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

    //사진앱 이미지 업로드
    @Multipart
    @POST("OneSightDiaryPlanner/attachImages.php")
    fun uploadImage(@PartMap dataPart:Map<String, String>, @Part filePart:MultipartBody.Part):Call<String>

    //카테고리 이미지 다운로드
    @GET("OneSightDiaryPlanner/loadCategory.php")
    fun getCategoryImage(): Call<ArrayList<CategoryImage>>
}