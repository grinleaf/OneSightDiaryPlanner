package com.grinleaf.onesightdiaryplanner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.grinleaf.onesightdiaryplanner.databinding.FragmentLifecycleBinding
import kotlin.coroutines.coroutineContext


class LifecycleFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    val binding by lazy { FragmentLifecycleBinding.inflate(layoutInflater) }
    val adapter by lazy { LifecycleAdapter(requireContext(), G.lifecycleItems) }
    val indicator by lazy { binding.indicatorDate2 }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(G.lifecycleItems.size==0) {
            binding.recyclerLifecycle.visibility = View.GONE
            binding.layoutCompleteCountMainLifecycle.visibility = View.GONE
        }
        binding.recyclerLifecycle.adapter= adapter
        
        //리사이클러뷰에 페이저 효과
        val snapHelper= PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerLifecycle)
        indicator.attachToRecyclerView(binding.recyclerLifecycle, snapHelper)   //인디케이터

        binding.recyclerLifecycle.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_SCROLL -> {
                    view.requestFocus()
//                    view.parent.
                }
//                MotionEvent.ACTION_OUTSIDE -> view.focusable=

                else -> false
            }
        })
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
    inner class DisallowInterceptTouchEventRecyclerView: RecyclerView(requireContext()) {

        override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
            parent.requestDisallowInterceptTouchEvent(true)
            return super.dispatchTouchEvent(ev)
        }
    }
}

