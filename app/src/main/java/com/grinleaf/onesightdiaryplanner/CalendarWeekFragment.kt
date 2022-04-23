package com.grinleaf.onesightdiaryplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentCalendarWeekBinding
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.*

class CalendarWeekFragment:Fragment() {
    val binding= FragmentCalendarWeekBinding.inflate(layoutInflater)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            val locale = Locale.getDefault()
            var dayOfWeek = WeekFields.of(locale).firstDayOfWeek
            val iterator = (binding.root as ViewGroup).children.iterator()
            while (iterator.hasNext()) {
                val textView = iterator.next() as TextView
                textView.text = dayOfWeek.getDisplayName(TextStyle.SHORT, locale)
                dayOfWeek = dayOfWeek.plus(1)
            }
        }

}