package com.grinleaf.onesightdiaryplanner

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.grinleaf.onesightdiaryplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val fragments:MutableList<Fragment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

                }
                R.id.date_tab->{
                    if(!supportFragmentManager.fragments.contains(fragments[1])) tran.add(R.id.container,fragments[1])
                    tran.show(fragments[1])
                }
                R.id.calendar_tab->{
                    if(!supportFragmentManager.fragments.contains(fragments[3])) tran.add(R.id.container,fragments[3])
                    tran.show(fragments[3])
                }
                R.id.mypage_tab->{
                    if(!supportFragmentManager.fragments.contains(fragments[4])) tran.add(R.id.container,fragments[4])
                    tran.show(fragments[4])
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
            finish()
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
