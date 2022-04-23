package com.grinleaf.onesightdiaryplanner

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.grinleaf.onesightdiaryplanner.databinding.*
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

class CalendarFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        parentFragmentManager.beginTransaction().add(R.id.calendar_container,fragment).commit()
        return binding.root
    }
    val fragment:Fragment by lazy { CalendarFragment() }
    val binding by lazy { FragmentCalendarMainBinding.inflate(layoutInflater) }
    private val currentMonth = YearMonth.now()
    private val startMonth = currentMonth.minusMonths(1)
    private val endMonth = currentMonth.plusMonths(1)
    private var calendar: Pair<LocalDate?, LocalDate?> = null to null
    private val binder = CalendarBindDay(binding.calendar).apply {
        input = object : CalendarBindDay.Input(){
            override fun onDayClick(date: LocalDate){
                onDayClick(date)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.calendar){
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            dayBinder = binder
            monthScrollListener = {
                    calendarMonth -> onMonthScroll(calendarMonth.yearMonth)
            }
        }
        binding.calendar.setup( startMonth, endMonth, WeekFields.of(Locale.getDefault()).firstDayOfWeek )
    }


    private fun onMonthScroll(currentMonth: YearMonth) {
        val visibleMonth = binding.calendar.findFirstVisibleMonth() ?: return
        if (currentMonth != visibleMonth.yearMonth){
            binding.calendar.smoothScrollToMonth(currentMonth)
        }
    }

    private fun onDayClick(date: LocalDate){
        val (start, end) = calendar

        calendar = when {
            start == null -> {
                date to end
            }
            end == null -> {
                start to date
            }
            else -> {
                null to null
            }
        }
        binder.updateCalendar(calendar)
    }
}