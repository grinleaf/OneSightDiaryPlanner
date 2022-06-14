package com.grinleaf.onesightdiaryplanner

import android.Manifest
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val fragments:MutableList<Fragment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //1. 외부저장소 사용에 대한 동적퍼미션 + 퍼미션을 허가받은 상태인지 여부 확인(받지 않았을 경우 다이얼로그를 띄우는 requestPermissions();
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED) requestPermissions(permissions,0)

        //계정별 데이터 로드
        loadDateData()
        loadSelectedEmo()
        reset()

        fragments.add(TimelineFragment())
        fragments.add(DateFragment())
        fragments.add(Fragment())
        fragments.add(CalendarFragment())
        fragments.add(MypageFragment())

        supportFragmentManager.beginTransaction().add(R.id.container,fragments[0]).commit()

        binding.bottomnavigationMain.background= null   //음영제거
        binding.bottomnavigationMain.menu.getItem(2).isEnabled = false  //네비게이션 뷰 가운데 비우기
        binding.bottomnavigationMain.setOnItemSelectedListener {
            supportFragmentManager.fragments.forEach{
                supportFragmentManager.beginTransaction().hide(it).commit()
            }
            val tran= supportFragmentManager.beginTransaction()
            when(it.itemId){
                R.id.timeline_tab->{
                    tran.show(fragments[0])
                    binding.mainBgColor.visibility= View.VISIBLE
                }
                R.id.date_tab->{
                    if(!supportFragmentManager.fragments.contains(fragments[1])) tran.add(R.id.container,fragments[1])
                    tran.show(fragments[1])
                    binding.mainBgColor.visibility= View.GONE
                }
                R.id.calendar_tab->{
                    if(!supportFragmentManager.fragments.contains(fragments[3])) tran.add(R.id.container,fragments[3])
                    tran.show(fragments[3])
                    binding.mainBgColor.visibility= View.GONE
                }
                R.id.mypage_tab->{
                    if(!supportFragmentManager.fragments.contains(fragments[4])) tran.add(R.id.container,fragments[4])
                    tran.show(fragments[4])
                    binding.mainBgColor.visibility= View.GONE
                }
            }
            tran.commit()
            true
        }

        binding.btnFloatingTopMain.imageTintList= null
        binding.btnFloatingDateeditMain.imageTintList= null
        binding.btnFloatingAchievementMain.imageTintList= null

        binding.btnFloatingTopMain.setOnClickListener{
            toggleFab()
        }

        //일정편집화면 전환 버튼
        binding.btnFloatingDateeditMain.setOnClickListener {
            val intent= Intent(this@MainActivity, DateEditActivity::class.java)
            startActivity(intent)
        }

        //업적현황화면 전환 버튼
        binding.btnFloatingAchievementMain.setOnClickListener {
            val intent= Intent(this@MainActivity, AchievementActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "외부저장소 허용", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

    }

    var backKeyPressedTime: Long= 0L
    override fun onBackPressed() {
        if(System.currentTimeMillis()>backKeyPressedTime+2000){
            backKeyPressedTime= System.currentTimeMillis()
            Toast.makeText(this@MainActivity, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if(System.currentTimeMillis()<=backKeyPressedTime+2000){
            ActivityCompat.finishAffinity(this)
        }

    }
    
    // 플로팅 액션 버튼 클릭시 애니메이션 효과
    private var btnFloatingTop_status = false  //플로팅 버튼 상태 저장
    fun toggleFab() {
        if (btnFloatingTop_status) {    // 플로팅 액션 버튼 닫기
            val fc_animation = ObjectAnimator.ofFloat(binding.btnFloatingDateeditMain, "translationY", 0f)
            fc_animation.start()
            val fe_animation = ObjectAnimator.ofFloat(binding.btnFloatingAchievementMain, "translationY", 0f)
            fe_animation.start()
            binding.btnFloatingTopMain.setImageResource(R.drawable.ic_star)
            binding.btnFloatingDateeditMain.isClickable= false
            binding.btnFloatingAchievementMain.isClickable= false
        } else {    // 플로팅 액션 버튼 열기
            val fc_animation = ObjectAnimator.ofFloat(binding.btnFloatingDateeditMain, "translationY", -140f)
            fc_animation.start()
            val fe_animation = ObjectAnimator.ofFloat(binding.btnFloatingAchievementMain, "translationY", -270f)
            fe_animation.start()
            binding.btnFloatingTopMain.setImageResource(R.drawable.ic_cross)
            binding.btnFloatingDateeditMain.isClickable= true
            binding.btnFloatingAchievementMain.isClickable= true
        }
        btnFloatingTop_status = !btnFloatingTop_status  // 플로팅 버튼 상태 변경
    }

    private fun loadDateData(){
        dailyCallback()
        checklistCallback()
        lifecycleCallback()
        bucketlistCallback()
    }

    private fun reset(){
        G.visibleCountDaily.clear()
        G.visibleCountCheck.clear()
        G.visibleCountLife.clear()
        G.dayOfTimelines.clear()
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
        lateinit var titleLifecycle:String
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

    //MainActivity 에서 DateEditActivity 로 데이터 요청/////////////////////////////////////////////////////////////////
//    fun requestEditActivity(){
//        val intent= Intent(this@MainActivity, DateEditActivity::class.java)
//        resultLauncher.launch(intent)
//    }
//
//    val resultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//        if(it.resultCode==RESULT_OK) {
//            val dailyDatas = it.data?.getBundleExtra("dailyDatas")  //DateEditAc --> MainAc 으로 데이터 전달
//            //데이터 최초 입력 시
//            if(G.dailyNoteItems.size==0) {
//                G.dailyNoteItems.add(DailyItem(
//                        dailyDatas?.get("todayAuto").toString(),
//                        dailyDatas?.get("dateTitle").toString(),
//                        dailyDatas?.get("categoryImage") as Int,
//                        dailyDatas?.get("attachImage").toString(),
//                        dailyDatas?.get("detailContent").toString())
//                )
//                Log.i("aaa",G.dailyNoteItems.size.toString()+"resultLauncher 값 받은 곳! if")
//            }else{
//                //프래그먼트로 값 전달하는 코드
//                val fragment= DailyNoteFragment()
//                fragment.arguments= dailyDatas
//                Log.i("aaa",G.dailyNoteItems.size.toString()+"resultLauncher 값 받은 곳! else")
//            }
//
//        }
//  }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
