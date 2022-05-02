package com.grinleaf.onesightdiaryplanner

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentBucketlistBinding
import java.time.LocalDate

class BucketListFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    val binding by lazy { FragmentBucketlistBinding.inflate(layoutInflater) }
    val adapter by lazy { BucketlistAdapter(requireContext(),G.bucketlistItems) }

    @RequiresApi(Build.VERSION_CODES.O)
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

        binding.recyclerBucketlist.adapter= adapter
    }

    override fun onResume() {
        super.onResume()

        adapter.notifyDataSetChanged()
    }

}