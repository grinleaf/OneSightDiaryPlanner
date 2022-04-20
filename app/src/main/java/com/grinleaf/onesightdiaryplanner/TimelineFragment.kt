package com.grinleaf.onesightdiaryplanner

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentTimelineBinding

class TimelineFragment:Fragment() {
    var tlItems= mutableListOf<TimelineItem>()
    val binding by lazy { FragmentTimelineBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tlItems.add(TimelineItem("04월 19일","딸기라떼 마시기",R.drawable.ic_test_icon01_foreground,R.drawable.ic_test_icon01_foreground))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerTimeline.adapter= TimelineAdapter(requireContext(),tlItems)
    }
}