package com.grinleaf.onesightdiaryplanner

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grinleaf.onesightdiaryplanner.databinding.FragmentTodolistBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class TodoListFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    val binding by lazy { FragmentTodolistBinding.inflate(layoutInflater) }
    val adapterChecklist by lazy { TodoListMainAdapter(requireContext(),G.matchDateChecklistItem) }
    val adapterLifecycle by lazy { TodoListLifecycleAdapter(requireContext(),G.lifecycleItems) }
    var subContents= mutableListOf<ChecklistSubItem>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var now = ""+LocalDate.now()
        val nowDate:Date= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(now)
        now= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).format(nowDate)
        binding.tvChecklistDay.text= now
        G.dayOfTodolist= now
        binding.ivDatepickerChecklist.setOnClickListener{
            val cal= Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                G.dayOfTodolist = "${year}-${month+1}-${dayOfMonth}"
                val date:Date= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(G.dayOfTodolist)
                G.dayOfTodolist= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).format(date)
                binding.tvChecklistDay.text = G.dayOfTodolist

                displayReset()

                adapterChecklist.notifyDataSetChanged()
                adapterLifecycle.notifyDataSetChanged()
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        displayReset()

        binding.recyclerChecklist.adapter= adapterChecklist
        binding.recyclerTodoLifecycle.adapter= adapterLifecycle

        binding.tvAddDateChecklist.setOnClickListener { clickAddDate() }

//        val rvItem:RecyclerView= requireActivity().findViewById(R.id.recycler_checklist)
//        val layoutManager= LinearLayoutManager(requireContext())
//        val mainAdapterMain?= MainCheckListAdapter(mainCheckListItems())

    }

    override fun onResume() {
        super.onResume()
        displayReset()

    }

    private fun clickAddDate(){
        val intent= Intent(requireContext(), DateEditActivity::class.java)
        startActivity(intent)
    }

    private fun displayReset(){
        Handler(Looper.getMainLooper()).postDelayed({
            adapterChecklist.notifyDataSetChanged()
            G.matchDateChecklistItem.clear()
            G.matchDateLifecycleItem.clear()
            for(item in G.checklistItems) {
                if (G.dayOfTodolist == item.day) G.matchDateChecklistItem.add(item)
            }
            val format= SimpleDateFormat("yyyy-MM-dd")
            val localDayOfTodolist:Date= format.parse(G.dayOfTodolist) as Date
            for(item in G.lifecycleItems) {
                val startDayOfLifecycle= format.parse(item.day)
                val endDayOfLifecycle= format.parse(item.endDay)
                if (localDayOfTodolist.after(startDayOfLifecycle)&&
                    localDayOfTodolist.before(endDayOfLifecycle)) G.matchDateLifecycleItem.add(item)
                Log.i("aaa", "startDay: ${startDayOfLifecycle} + endday : ${endDayOfLifecycle} + localday : ${localDayOfTodolist} + G.matchitem : ${G.matchDateLifecycleItem}")
            }
            if(G.matchDateChecklistItem.size==0) binding.tvNoDate01.visibility= View.VISIBLE
            else binding.tvNoDate01.visibility= View.GONE
            if(G.matchDateLifecycleItem.size==0) binding.tvNoDate02.visibility= View.VISIBLE
            else binding.tvNoDate02.visibility= View.GONE
        },100)
    }
}