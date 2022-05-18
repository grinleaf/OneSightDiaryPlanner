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
        loadSelectedEmo()
        loadDateData()

        reset()

        //샘플 튜토리얼 이미지
        imgIds.add(TutorialImage(R.drawable.tutorial_sample01))
        imgIds.add(TutorialImage(R.drawable.tutorial_sample02))
        imgIds.add(TutorialImage(R.drawable.tutorial_sample03))
        imgIds.add(TutorialImage(R.drawable.tutorial_sample04))

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

    private fun loadSelectedEmo(){
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.getSelectedEmoImage()
        call.enqueue(object:Callback<ArrayList<SelectedDayEmo>>{
            override fun onResponse(
                call: retrofit2.Call<ArrayList<SelectedDayEmo>>,
                response: Response<ArrayList<SelectedDayEmo>>
            ) {
                G.loadSelectedEmoImages.clear()
                val list= response.body()
                for(i in list!!) G.loadSelectedEmoImages.add(SelectedDayEmo(i.no,i.email,i.day,i.emo))
                Log.i("aaa","DB -> 선택되었던 이모티콘에 해당하는 유저이메일, 날짜, 이미지 가져오기 성공!")
                Log.i("aaa",G.loadSelectedEmoImages[0].email+", "+G.loadSelectedEmoImages[0].day+", "+G.loadSelectedEmoImages[0].emo)
            }
            override fun onFailure(call: retrofit2.Call<ArrayList<SelectedDayEmo>>, t: Throwable) {
                Log.i("aaa","downloadEmo error: "+t.message)
            }
        })
    }

    private fun loadDateData(){
        dailyCallback()
        checklistCallback()
        lifecycleCallback()
        bucketlistCallback()
    }

    private fun dailyCallback(){
        lateinit var noDailyNote:String
        lateinit var emailDailyNote:String
        lateinit var titleDailyNote:String
        lateinit var dayDailyNote:String
        lateinit var categoryDailyNote:String
        lateinit var attachImageDailyNote:String
        lateinit var contentDailyNote:String
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.getDailyNoteDownload()
        call.enqueue(object : Callback<ArrayList<DailyItem>>{
            override fun onResponse(
                call: retrofit2.Call<ArrayList<DailyItem>>,
                response: Response<ArrayList<DailyItem>>
            ) {
                G.dailyNoteItems.clear()
                val list= response.body()
                if (list != null) {
                    for(i in list.indices){
                        noDailyNote= list.get(i).no
                        emailDailyNote= list.get(i).email
                        titleDailyNote= list.get(i).content
                        dayDailyNote= list.get(i).day
                        categoryDailyNote= list.get(i).categoryImage
                        attachImageDailyNote= list.get(i).dayImage
                        contentDailyNote= list.get(i).detailContent
                        if(emailDailyNote == G.userEmail) {
                            G.dailyNoteItems.add(DailyItem(
                                noDailyNote,emailDailyNote,dayDailyNote,titleDailyNote,categoryDailyNote,attachImageDailyNote,contentDailyNote
                            )
                            )
                        }
                    }
                    Log.i("aaa", "데일리 사이즈: " + G.dailyNoteItems.size)
                    Log.i("aaa", "emailDailyNote: $emailDailyNote   G.userEmail: ${G.userEmail}")
                }
            }
            override fun onFailure(call: retrofit2.Call<ArrayList<DailyItem>>, t: Throwable) {

            }
        })
    }

    private fun checklistCallback(){
        lateinit var noChecklist:String
        lateinit var emailChecklist:String
        lateinit var titleCheckList:String
        lateinit var dayChecklist:String
        lateinit var categoryCheckList:String
        lateinit var contentCheckList:String
        lateinit var isCheckedCheckList:String
//        lateinit var subContentCheckList:String
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.getChecklistDownload()
        call.enqueue(object : Callback<ArrayList<ChecklistItem>>{
            override fun onResponse(
                call: retrofit2.Call<ArrayList<ChecklistItem>>,
                response: Response<ArrayList<ChecklistItem>>
            ) {
                G.checklistItems.clear()
                val list= response.body()
                if (list != null) {
                    for(i in list.indices){
                        noChecklist= list.get(i).no
                        emailChecklist= list.get(i).email
                        titleCheckList= list.get(i).content
                        dayChecklist= list.get(i).day
                        categoryCheckList= list.get(i).categoryImage
                        contentCheckList= list.get(i).detailContent
                        isCheckedCheckList= list.get(i).isChecked
                        if(emailChecklist == G.userEmail) {
                            G.checklistItems.add(ChecklistItem(
                                    noChecklist,emailChecklist,dayChecklist,titleCheckList,categoryCheckList,contentCheckList,isCheckedCheckList
                                )
                            )
                        }
                    }
                    Log.i("aaa","체크 사이즈: "+G.checklistItems.size)
                    Log.i("aaa", "emailChecklist: $emailChecklist   G.userEmail: ${G.userEmail}")
                }
            }
            override fun onFailure(call: retrofit2.Call<ArrayList<ChecklistItem>>, t: Throwable) {

            }
        })
    }

    private fun lifecycleCallback(){
        lateinit var noLifecycle:String
        lateinit var emailLifecycle:String
        var titleLifecycle:String= "Abcd"
        lateinit var startDayLifecycle:String
        lateinit var endDayLifecycle:String
        lateinit var repeatCycle:String
        lateinit var categoryLifecycle:String
        lateinit var exportOther:String
        var isChecked:String
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.getLifecycleDownload()
        call.enqueue(object : Callback<ArrayList<LifecycleItem>>{
            override fun onResponse(
                call: retrofit2.Call<ArrayList<LifecycleItem>>,
                response: Response<ArrayList<LifecycleItem>>
            ) {
                G.lifecycleItems.clear()
                val list= response.body()
                if (list != null) {
                    for(i in list.indices){
                        noLifecycle= list.get(i).no
                        emailLifecycle= list.get(i).email
                        titleLifecycle= list.get(i).content
                        startDayLifecycle= list.get(i).day
                        categoryLifecycle= list.get(i).categoryImage
                        endDayLifecycle= list.get(i).endDay
                        repeatCycle= list.get(i).repeatCycle
                        categoryLifecycle= list.get(i).categoryImage
                        exportOther= list.get(i).isBucket
                        isChecked= list.get(i).isChecked
                        if(emailLifecycle == G.userEmail) {
                            G.lifecycleItems.add(
                                LifecycleItem(
                                    noLifecycle,emailLifecycle,startDayLifecycle,titleLifecycle,categoryLifecycle,repeatCycle,endDayLifecycle,exportOther,isChecked
                                )
                            )
                        }
                    }
                    Log.i("aaa","라이프 사이즈: "+G.lifecycleItems.size)
                    Log.i("aaa", "emailLifecycle: $emailLifecycle   G.userEmail: ${G.userEmail}")
                }
            }
            override fun onFailure(call: retrofit2.Call<ArrayList<LifecycleItem>>, t: Throwable) {

            }
        })
    }

    private fun bucketlistCallback(){
        lateinit var noBucketlist:String
        lateinit var emailBucketlist:String
        lateinit var titleBucketlist:String
        lateinit var dayBucketlist:String
        lateinit var categoryBucketlist:String
        lateinit var contentBucketlist:String
        val retrofit= RetrofitHelper.getRetrofitInstance()
        val retrofitService= retrofit.create(RetrofitService::class.java)
        val call= retrofitService.getBucketlistDownload()
        call.enqueue(object : Callback<ArrayList<BucketlistItem>>{
            override fun onResponse(
                call: retrofit2.Call<ArrayList<BucketlistItem>>,
                response: Response<ArrayList<BucketlistItem>>
            ) {
                G.bucketlistItems.clear()
                val list= response.body()
                if (list != null) {
                    for(i in list.indices){
                        noBucketlist= list.get(i).no
                        emailBucketlist= list.get(i).email
                        titleBucketlist= list.get(i).content
                        dayBucketlist= list.get(i).day
                        categoryBucketlist= list.get(i).categoryImage
                        contentBucketlist= list.get(i).detailContent
                        if(emailBucketlist == G.userEmail) {
                            G.bucketlistItems.add(
                                BucketlistItem(
                                    noBucketlist,emailBucketlist,dayBucketlist,titleBucketlist,categoryBucketlist,contentBucketlist
                                )
                            )
                        }
                    }
                    Log.i("aaa","버킷 사이즈: "+G.bucketlistItems.size)
                    Log.i("aaa", "emailBucketlist: $emailBucketlist   G.userEmail: ${G.userEmail}")
                }
            }
            override fun onFailure(call: retrofit2.Call<ArrayList<BucketlistItem>>, t: Throwable) {

            }
        })
    }

    private fun reset(){
        G.isNotEmptyChecklistRecyclerItem.clear()
        G.isNotEmptyLifecycleRecyclerItem.clear()

        G.visibleCountDaily.clear()
        G.visibleCountCheck.clear()
        G.visibleCountLife.clear()
    }
}