package com.grinleaf.onesightdiaryplanner

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
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
        binding.recyclerTimeline.adapter= adapter
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

        adapter.notifyDataSetChanged()
    }
}