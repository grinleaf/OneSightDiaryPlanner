package com.grinleaf.onesightdiaryplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grinleaf.onesightdiaryplanner.databinding.FragmentLifecycleBinding


class LifecycleFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    val binding by lazy { FragmentLifecycleBinding.inflate(layoutInflater) }
    val lifecycleItems= mutableListOf<LifecycleItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleItems.add(LifecycleItem("2022.04.21","안녕하세용",0,0,"",""))
        lifecycleItems.add(LifecycleItem("2022.04.24","반가워용",0,0,"",""))
        lifecycleItems.add(LifecycleItem("2022.04.25","할게 너무",0,0,"",""))
        lifecycleItems.add(LifecycleItem("2022.04.30","많아용",0,0,"",""))

        binding.recyclerLifecycle.adapter= LifecycleAdapter(requireContext(), lifecycleItems)
       
    }

    override fun onResume() {
        super.onResume()
    }
}