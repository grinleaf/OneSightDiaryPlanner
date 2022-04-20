package com.grinleaf.onesightdiaryplanner

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.btnFloatingMain.imageTintList= null
    }
}
