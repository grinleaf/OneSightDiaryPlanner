package com.grinleaf.onesightdiaryplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentBucketlistBinding

class BucketListFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    val binding by lazy { FragmentBucketlistBinding.inflate(layoutInflater) }
    val bucketlistItems= mutableListOf<BucketlistItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        bucketlistItems.add(BucketlistItem("2022.04.21","야호",R.drawable.tutorial_sample04))
        bucketlistItems.add(BucketlistItem("2022.04.21","야야호",R.drawable.tutorial_sample01))
        bucketlistItems.add(BucketlistItem("2022.04.21","ㅇ3ㅇ",R.drawable.tutorial_sample02))
        bucketlistItems.add(BucketlistItem("2022.04.21","집보내줭",R.drawable.tutorial_sample03))

        binding.recyclerBucketlist.adapter= BucketlistAdapter(requireContext(),bucketlistItems)
    }

}