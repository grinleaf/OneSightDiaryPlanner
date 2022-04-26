package com.grinleaf.onesightdiaryplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager
import com.grinleaf.onesightdiaryplanner.databinding.FragmentCalendarMainBinding


class CalendarFragment:Fragment() {
    val binding by lazy { FragmentCalendarMainBinding.inflate(layoutInflater) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView.isShowDaysOfWeekTitle= false
        binding.calendarView.selectionManager= SingleSelectionManager(OnDaySelectedListener {

        })
    }


}