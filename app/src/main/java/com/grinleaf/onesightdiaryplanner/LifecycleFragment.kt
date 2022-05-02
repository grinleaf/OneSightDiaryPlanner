package com.grinleaf.onesightdiaryplanner

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grinleaf.onesightdiaryplanner.databinding.FragmentLifecycleBinding


class LifecycleFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    val binding by lazy { FragmentLifecycleBinding.inflate(layoutInflater) }
    val adapter by lazy { LifecycleAdapter(requireContext(), G.lifecycleItems) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(G.lifecycleItems.size==0) {
            binding.recyclerLifecycle.visibility = View.GONE
            binding.layoutCompleteCountMainLifecycle.visibility = View.GONE
        }

        binding.recyclerLifecycle.adapter= adapter
    }

    override fun onResume() {
        super.onResume()
        if(G.lifecycleItems.size!=0) {
            binding.recyclerLifecycle.visibility= View.VISIBLE
            binding.layoutCompleteCountMainLifecycle.visibility= View.VISIBLE
        }
        binding.tvDatecountCountLifecycle.text= (G.lifecycleItems.size).toString()

        adapter.notifyDataSetChanged()
    }
}