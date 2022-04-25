package com.grinleaf.onesightdiaryplanner

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.LayoutInflaterCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.grinleaf.onesightdiaryplanner.databinding.FragmentMypageBinding
import com.grinleaf.onesightdiaryplanner.databinding.FragmentTimelineBinding

class MypageFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    val binding by lazy { FragmentMypageBinding.inflate(layoutInflater) }
    val mypageGridItems= mutableListOf<MypageGridItem>()
    val adapter by lazy { MypageAdapter(requireContext(),mypageGridItems) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mypageGridItems.add(MypageGridItem(R.drawable.ic_alert_grid,"알림 설정"))
        mypageGridItems.add(MypageGridItem(R.drawable.ic_theme_grid,"테마 설정"))
        mypageGridItems.add(MypageGridItem(R.drawable.ic_award_leaf_star,"업적 현황"))
        mypageGridItems.add(MypageGridItem(R.drawable.ic_alert_grid,"알림 설정"))
        mypageGridItems.add(MypageGridItem(R.drawable.ic_theme_grid,"테마 설정"))
        mypageGridItems.add(MypageGridItem(R.drawable.ic_award_leaf_star,"업적 현황"))

        binding.recyclerMypage.adapter= adapter
        binding.recyclerMypage.layoutManager = GridLayoutManager(context,3)
        adapter.setItemClickListener(object :MypageAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                when(mypageGridItems.get(position).mypageContent){
                    "알림 설정" -> {
                        //알림 설정 탭 클릭 시
                        val intent= Intent(context, AlertSettingActivity::class.java)
                        startActivity(intent)
                    }
                    "테마 설정" -> {
                        //테마 설정 탭 클릭 시
                        val intent= Intent(context, ThemeSettingActivity::class.java)
                        startActivity(intent)
                    }
                    "업적 현황" -> {
                        //업적 현황 탭 클릭 시
                        val intent= Intent(context, AchievementActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        })
    }


}