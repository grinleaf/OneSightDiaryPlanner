package com.grinleaf.onesightdiaryplanner

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.grinleaf.onesightdiaryplanner.databinding.FragmentTimelineBinding

class TimelineFragment:Fragment() {
    val binding by lazy { FragmentTimelineBinding.inflate(layoutInflater) }
    val adapter by lazy { TimelineAdapter(requireContext(),G.timelineItems) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var dailySize= G.dailyNoteItems.size
        var checkSize= G.checklistItems.size
        var checkSubSize= G.checklistSubItems.size
        var lifeSize= G.lifecycleItems.size

        //G.timelineItems 에 화면에 띄울 데이터 add 하기
        if(dailySize!=0|| checkSize!=0||checkSubSize!=0||lifeSize!=0) {
            G.timelineItems.add(
                TimelineItem(
                    ifnullData(dailySize, G.dailyNoteItems.get(dailySize - 1).content),
                    ifnullData(dailySize, G.dailyNoteItems.get(dailySize - 1).day),
                    ifnullData(dailySize, G.dailyNoteItems.get(dailySize - 1).categoryImage),
                    ifnullData(dailySize, G.dailyNoteItems.get(dailySize - 1).detailContent),
                    ifnullData(dailySize, G.dailyNoteItems.get(dailySize - 1).dayImage),

                    ifnullData(checkSize, G.checklistItems.get(checkSize - 1).content),
                    ifnullData(checkSize, G.checklistItems.get(checkSize - 1).detailContent),
                    ifnullData(checkSize, G.checklistItems.get(checkSize - 1).categoryImage),
                    "",

                    ifnullData(lifeSize, G.lifecycleItems.get(lifeSize - 1).content),
                    ifnullData(lifeSize, G.lifecycleItems.get(lifeSize - 1).day),
                    ifnullData(lifeSize, G.lifecycleItems.get(lifeSize - 1).endDay),
                    ifnullData(lifeSize, G.lifecycleItems.get(lifeSize - 1).repeatCycle),
                    ifnullData(lifeSize, G.lifecycleItems.get(lifeSize - 1).categoryImage)
                )
            )
        }
        Log.i("aaa","데일리 사이즈: "+dailySize)
        Log.i("aaa","체크 사이즈: "+checkSize)
        Log.i("aaa","라이프 사이즈: "+lifeSize)
        Log.i("aaa","타임라인아이템 사이즈: "+G.timelineItems.size)

        binding.recyclerTimeline.adapter= adapter
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

        adapter.notifyDataSetChanged()
    }

    //데이터가 입력되지 않았으면 빈문자
    private fun ifnullData(con:Int, data:String):String{
        if(con==0) return ""
        else return data
    }
}