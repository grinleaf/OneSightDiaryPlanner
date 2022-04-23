package com.grinleaf.onesightdiaryplanner

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val fragments:MutableList<Fragment> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fragments.add(TimelineFragment())
        fragments.add(DateFragment())
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
                    if(!supportFragmentManager.fragments.contains(fragments[2])) tran.add(R.id.container,fragments[2])
                    tran.show(fragments[2])
                }
                R.id.mypage_tab->{
                    if(!supportFragmentManager.fragments.contains(fragments[3])) tran.add(R.id.container,fragments[3])
                    tran.show(fragments[3])
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

        }

        //업적현황화면 전환 버튼
        binding.btnFloatingAchievementMain.setOnClickListener {

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
            binding.btnFloatingTopMain.setImageResource(R.drawable.ic_baseline_attractions_24)
        } else {    // 플로팅 액션 버튼 열기
            val fc_animation = ObjectAnimator.ofFloat(binding.btnFloatingDateeditMain, "translationY", -140f)
            fc_animation.start()
            val fe_animation = ObjectAnimator.ofFloat(binding.btnFloatingAchievementMain, "translationY", -270f)
            fe_animation.start()
            binding.btnFloatingTopMain.setImageResource(R.drawable.ic_floating_on_state)
        }
        btnFloatingTop_status = !btnFloatingTop_status  // 플로팅 버튼 상태 변경
    }
}
