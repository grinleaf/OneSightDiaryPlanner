package com.grinleaf.onesightdiaryplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDailynoteBinding

class DailylNoteFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    val binding by lazy { FragmentDailynoteBinding.inflate(layoutInflater) }
    var dailyItems= mutableListOf<DailyItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dailyItems.add(DailyItem("2022.04.20","Hello",R.drawable.ic_test_icon01_foreground,R.drawable.tutorial_sample01))
        dailyItems.add(DailyItem("2022.04.21","Nice",R.drawable.ic_test_icon01_foreground,R.drawable.tutorial_sample01))
        dailyItems.add(DailyItem("2022.04.30","Kotlin",R.drawable.ic_test_icon01_foreground,R.drawable.tutorial_sample01))

        binding.recyclerDailynote.adapter= DailyNoteAdapter(requireContext(),dailyItems)
    }

    override fun onResume() {
        super.onResume()
    }
}