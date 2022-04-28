package com.grinleaf.onesightdiaryplanner

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.marginLeft
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

        val defaultTitle= "${G.userNickname} 님의 버킷리스트"
        binding.tvBucketlistTitle.text= defaultTitle

        binding.ivTitleeditBucketlist.setOnClickListener {
            var editTitle= EditText(context)
            editTitle.setHint(defaultTitle)
            AlertDialog.Builder(context)
                .setView(editTitle)
                .setTitle("타이틀 수정\n\n")
                .setPositiveButton("확인", DialogInterface.OnClickListener {
                        dialogInterface, i ->
                    if(editTitle.text.toString()=="") binding.tvBucketlistTitle.text= defaultTitle
                    else binding.tvBucketlistTitle.text= editTitle.text.toString()
                    G.editBucketlistTitle= editTitle.text.toString()
                }
            ).create().show()
        }
        
        bucketlistItems.add(BucketlistItem("2022.04.21","야호",R.drawable.tutorial_sample04))
        bucketlistItems.add(BucketlistItem("2022.04.21","야야호",R.drawable.tutorial_sample01))
        bucketlistItems.add(BucketlistItem("2022.04.21","ㅇ3ㅇ",R.drawable.tutorial_sample02))
        bucketlistItems.add(BucketlistItem("2022.04.21","집보내줘",R.drawable.tutorial_sample03))

        binding.recyclerBucketlist.adapter= BucketlistAdapter(requireContext(),bucketlistItems)
    }

}