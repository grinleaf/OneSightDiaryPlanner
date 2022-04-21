package com.grinleaf.onesightdiaryplanner

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class DateAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    val fragments: MutableList<Fragment> by lazy { mutableListOf() }
        init{
            fragments.add(DailylNoteFragment())
            fragments.add(CheckListFragment())
            fragments.add(LifecycleFragment())
            fragments.add(BucketListFragment())
    }

    override fun getItemCount(): Int{ return fragments.size }

    override fun createFragment(position: Int): Fragment { return fragments[position] }


}