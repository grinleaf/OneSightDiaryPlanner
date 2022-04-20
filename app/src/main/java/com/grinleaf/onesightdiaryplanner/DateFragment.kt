package com.grinleaf.onesightdiaryplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDateBinding

class DateFragment: Fragment() {    //이 프래그먼트 위에 뷰 페이저 배치
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    val binding by lazy { FragmentDateBinding.inflate(layoutInflater) }
    val adapter by lazy { DateAdapter(requireActivity()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val adapter by lazy { DateAdapter(requireActivity().supportFragmentManager) }
        binding.pagerDate.adapter= adapter
    }
}