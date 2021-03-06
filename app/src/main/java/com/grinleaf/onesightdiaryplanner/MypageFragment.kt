package com.grinleaf.onesightdiaryplanner

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.LayoutInflaterCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
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

        binding.tvUseridUserinfoMypage.text= G.userNickname
        binding.tvUseremailUserinfoMypage.text= G.userEmail
        if(G.userProfileImage!="") Glide.with(requireContext()).load(G.userProfileImage).into(binding.ivUserprofileUserinfoMypage)
        binding.layoutUserinfoMypage.setOnClickListener {
            val intent= Intent(requireContext(),MyInfoActivity::class.java)
            startActivity(intent)
        }

        binding.tvLevelExamIconLevelinfoMypage.setOnClickListener { clickExamLevel() }
        binding.ivLevelExamIconLevelinfoMypage.setOnClickListener { clickExamLevel() }

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

    private fun clickExamLevel(){
        AlertDialog.Builder(context)
            .setTitle("등급이란")
            .setMessage("\n무엇일까용 깔깔깔")
            .setIcon(R.drawable.ic_alert)
            .setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i -> })
            .create().show()
    }

    override fun onResume() {
        super.onResume()
        if(G.userProfileImage!="") Glide.with(requireContext()).load(G.userProfileImage).into(binding.ivUserprofileUserinfoMypage)
    }

}