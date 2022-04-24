package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDateeditBucketlistBinding


class DateEditBucketListFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    val binding by lazy { FragmentDateeditBucketlistBinding.inflate(layoutInflater) }
    val subPlans = mutableListOf<String>()
    val adapter by lazy { DateEditBucketListAdapter(requireContext(),subPlans) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivAddDateBucketlistDateedit.setOnClickListener {
            subPlans.add(binding.etBucketlistDateedit.text.toString())
            adapter.notifyItemInserted(subPlans.size)
            binding.etBucketlistDateedit.text.clear()
        }

        binding.etBucketlistDateedit.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                subPlans.add(binding.etBucketlistDateedit.text.toString())
                adapter.notifyItemInserted(subPlans.size)
                binding.etBucketlistDateedit.text.clear()
                return@OnKeyListener true
            }
            false
        })
        binding.recyclerBucketlistDateedit.adapter= adapter
    }
}