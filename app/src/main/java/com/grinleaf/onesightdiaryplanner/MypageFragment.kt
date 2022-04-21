package com.grinleaf.onesightdiaryplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    val gridItems= mutableListOf<GridItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridItems.add(GridItem(R.drawable.ic_alert_grid,"알림 설정"))
        gridItems.add(GridItem(R.drawable.ic_theme_grid,"테마 설정"))
        gridItems.add(GridItem(R.drawable.ic_baseline_attractions_24,"업적 현황"))
        gridItems.add(GridItem(R.drawable.ic_alert_grid,"알림 설정"))
        gridItems.add(GridItem(R.drawable.ic_theme_grid,"테마 설정"))
        gridItems.add(GridItem(R.drawable.ic_baseline_attractions_24,"업적 현황"))

        binding.recyclerMypage.adapter= MypageAdapter(requireContext(),gridItems)
        binding.recyclerMypage.layoutManager = GridLayoutManager(context,3)

    }
}