package com.grinleaf.onesightdiaryplanner

import android.view.View
import androidx.core.content.ContextCompat
import com.grinleaf.onesightdiaryplanner.databinding.FragmentCalendarDayBinding
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate

class CalendarBindDay( private val calendarView: CalendarView ): DayBinder<CalendarBindDay.DayContainer> {
    private var calendar: Pair<LocalDate?, LocalDate?> = null to null

    var input: Input? = null

    fun updateCalendar(
        calendar: Pair<LocalDate?, LocalDate?>,
    ) {
        if (this.calendar == calendar) return
        this.calendar = calendar
        this.calendarView.notifyCalendarChanged()
    }
    override fun create(view: View): DayContainer {
        val binding = FragmentCalendarDayBinding.bind(view)
        return DayContainer(binding)
    }

    override fun bind(container: DayContainer, day: CalendarDay) {
        val (startDate,endDate) = this.calendar

        container.binding.tvDay.text= day.date.dayOfMonth.toString()

        container.binding.root.setOnClickListener { _->
            input?.onDayClick(day.date)
        }
        if (day.owner != DayOwner.THIS_MONTH){
            container.binding.tvDay.setTextColor(ContextCompat.getColor(calendarView.context,R.color.black))
        }else {
            container.binding.tvDay.setTextColor(ContextCompat.getColor(calendarView.context,R.color.black))
        }

        if (isInRange(day.date)){
            container.binding.root.setBackgroundColor(ContextCompat.getColor(calendarView.context,R.color.black))
        }
        if (startDate == day.date){
            container.binding.root.background = (ContextCompat.getDrawable(calendarView.context,R.drawable.ic_award_leaf_star))
        } else if(endDate == day.date){
            container.binding.root.background = (ContextCompat.getDrawable(calendarView.context,R.drawable.ic_award_leaf))
        }

    }

    private fun isInRange(date: LocalDate): Boolean {
        val (startDate, endDate) = this.calendar
        return startDate == date || endDate == date
                || (startDate != null && endDate != null && startDate < date && date < endDate)

    }

    class DayContainer( val binding: FragmentCalendarDayBinding ) : ViewContainer(binding.root)

    abstract class Input {
        abstract fun onDayClick(date: LocalDate)
        //day 클릭 시 동작은 여기서 설정!
    }
}