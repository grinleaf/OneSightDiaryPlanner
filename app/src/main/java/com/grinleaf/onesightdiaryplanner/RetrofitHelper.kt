package com.grinleaf.onesightdiaryplanner

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitHelper {
    companion object{
        fun getRetrofitInstance():Retrofit{
            val builder= Retrofit.Builder()
            builder.baseUrl("http://grinleaf.dothome.co.kr")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
            val retrofit= builder.build()

            return retrofit
        }
    }
}
