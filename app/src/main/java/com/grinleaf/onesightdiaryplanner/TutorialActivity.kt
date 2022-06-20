package com.grinleaf.onesightdiaryplanner

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.grinleaf.onesightdiaryplanner.databinding.ActivityTutorialBinding
import okhttp3.Call
import retrofit2.Callback
import retrofit2.Response

class TutorialActivity : AppCompatActivity() {

    val binding:ActivityTutorialBinding by lazy { ActivityTutorialBinding.inflate(layoutInflater) }
    var imgIds= mutableListOf<TutorialImage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pref: SharedPreferences = getSharedPreferences("tutorial", MODE_PRIVATE)
        G.isFirst= pref.getBoolean("isFirst",true)
        if(!G.isFirst) skipActivity()

        loadCategoryImage()
        loadEmoImage()

        //샘플 튜토리얼 이미지
        imgIds.add(TutorialImage(R.drawable.ic_flower))
        imgIds.add(TutorialImage(R.drawable.ic_leaf))
        imgIds.add(TutorialImage(R.drawable.ic_bff))
        imgIds.add(TutorialImage(R.drawable.ic_dog_02))

        binding.pagerTutorial.adapter= TutorialAdapter(this, imgIds)

        binding.btnTutorialSkip.setOnClickListener { skipActivity() }
        binding.btnTutorialDestroy.setOnClickListener {
            G.isFirst= false
            val pref: SharedPreferences = getSharedPreferences("tutorial", MODE_PRIVATE)
            val editor= pref.edit()
            editor.putBoolean("isFirst",G.isFirst)
            editor.commit()
            skipActivity()
        }
    }

    private fun skipActivity(){
        val intent= Intent(this@TutorialActivity,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun loadEmoImage(){
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.getemoImage()
        call.enqueue(object : Callback<ArrayList<EmoImage>>{
            override fun onResponse(
                call: retrofit2.Call<ArrayList<EmoImage>>,
                response: Response<ArrayList<EmoImage>>
            ) {
                G.loadEmoImages.clear()
                val list= response.body()
                for(i in list!!){
                    val fileUri= "http://grinleaf.dothome.co.kr/OneSightDiaryPlanner/${i.image}"
                    G.loadEmoImages.add(fileUri)
                }
                Log.i("aaa","DB -> 이모티콘 이미지 가져오기 성공!")
                Log.i("aaa",G.loadEmoImages[0])
            }

            override fun onFailure(call: retrofit2.Call<ArrayList<EmoImage>>, t: Throwable) {
                Log.i("aaa","error: ${t.message}")
            }
        })
    }

    private fun loadCategoryImage(){
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.getCategoryImage()
        call.enqueue(object : Callback<ArrayList<CategoryImage>>{
            override fun onResponse(
                call: retrofit2.Call<ArrayList<CategoryImage>>,
                response: Response<ArrayList<CategoryImage>>
            ) {
                G.categoryImages.clear()
                val list= response.body()     //json 배열로 반환되어 돌아옴
                for(i in list!!){
                    val fileUri= "http://grinleaf.dothome.co.kr/OneSightDiaryPlanner/${i.image}"
                    G.categoryImages.add(fileUri)
                }
                Log.i("aaa","DB -> 카테고리 이미지 가져오기 성공!")
                Log.i("aaa",G.categoryImages[13])
            }

            override fun onFailure(call: retrofit2.Call<ArrayList<CategoryImage>>, t: Throwable) {
                Log.i("aaa","error: ${t.message}")
            }
        })
    }




}