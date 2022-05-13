package com.grinleaf.onesightdiaryplanner

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import com.grinleaf.onesightdiaryplanner.databinding.FragmentTodolistBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class TodoListFragment:Fragment() {
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return binding.root
    }
    val binding by lazy { FragmentTodolistBinding.inflate(layoutInflater) }
    val adapter by lazy { TodoListMainAdapter(requireContext(),G.checklistItems) }
    var subContents= mutableListOf<ChecklistSubItem>()

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun mainCheckListItems(): List<ChecklistItem> {
//        val mainList: MutableList<ChecklistItem> = mutableListOf()
//        for (i in 0..9) {
//            val item = ChecklistItem(""+LocalDate.now(), subCheckListItems())
//            mainList.add(item)
//        }
//        return mainList
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var now = ""+LocalDate.now()
        val nowDate:Date= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(now)
        now= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).format(nowDate)
        binding.tvChecklistDay.text= now
        G.dayOfCheckList= now
        binding.ivDatepickerChecklist.setOnClickListener{
            val cal= Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth -> G.dayOfCheckList = "${year}-${month+1}-${dayOfMonth}"
                val date:Date= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(G.dayOfCheckList)
                G.dayOfCheckList= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).format(date)
                binding.tvChecklistDay.text = G.dayOfCheckList
                adapter.notifyDataSetChanged()
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        G.isNotEmptyRecyclerItem= 0 //리사이클러뷰가 아무 뷰도 VISIBLE 상태로 만들지 못했을 때
        binding.recyclerChecklist.adapter= adapter

        adapter.notifyDataSetChanged()
        if(G.isNotEmptyRecyclerItem==0) binding.tvNoDate01.visibility= View.VISIBLE
        else binding.tvNoDate01.visibility= View.GONE


        binding.tvAddDateChecklist.setOnClickListener { clickAddDate() }

//        binding.recyclerChecklist.

//        val rvItem:RecyclerView= requireActivity().findViewById(R.id.recycler_checklist)
//        val layoutManager= LinearLayoutManager(requireContext())
//        val mainAdapterMain?= MainCheckListAdapter(mainCheckListItems())

    }

    override fun onResume() {
        super.onResume()

        adapter.notifyDataSetChanged()
        if(G.isNotEmptyRecyclerItem==0) binding.tvNoDate01.visibility= View.VISIBLE
        else binding.tvNoDate01.visibility= View.GONE
        Log.i("aaa","G.isNotEmptyRecyclerItem: ${G.isNotEmptyRecyclerItem}")

        if(binding.recyclerTodoLifecycle.isEmpty()) binding.tvNoDate02.visibility= View.VISIBLE
        else binding.tvNoDate02.visibility= View.GONE
        adapter.notifyDataSetChanged()
    }

    private fun clickAddDate(){
        val intent= Intent(requireContext(), DateEditActivity::class.java)
        startActivity(intent)
    }

    private fun isChecked(){

    }

}