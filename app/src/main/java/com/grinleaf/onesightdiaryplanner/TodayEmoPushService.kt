package com.grinleaf.onesightdiaryplanner

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodayEmoPushService:FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

    }

    override fun onNewToken(token: String) {    //디바이스 별 고유 식별 토큰 가져오기 (앱 설치시 최초 한번만 실행)
        super.onNewToken(token)
        Log.i("TOKEN",token)
        //토큰 값을 닷홈 DB 에 업로드
        userDeviceTokenUpload(token)
    }

    private fun userDeviceTokenUpload(token:String){
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.userDeviceTokenUpload(token)
        call.enqueue(object: Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val s= response.body()
                Log.i("aaa","sendToken succeed : $s")
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("aaa","sendToken failed : ${t.message}")
            }
        })
    }
}