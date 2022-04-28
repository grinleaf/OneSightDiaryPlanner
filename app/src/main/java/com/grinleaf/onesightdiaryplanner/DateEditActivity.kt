package com.grinleaf.onesightdiaryplanner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.grinleaf.onesightdiaryplanner.databinding.ActivityDateEditBinding

class DateEditActivity:AppCompatActivity() {
    val binding by lazy { ActivityDateEditBinding.inflate(layoutInflater) }
    val fragments: MutableList<Fragment> by lazy { mutableListOf() }
    lateinit var resultLauncher:ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        resultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val selectedImage= it.data?.data
            binding.ivCategoryMainDateEdit.setImageResource(R.drawable.tutorial_sample04)   //수정요 : 카테고리 액티비티에서 선택된 이미지를 가져와 설정하기
        }

        fragments.add(DateEditDailyNoteFragment())
        fragments.add(DateEditCheckListFragment())
        fragments.add(DateEditLifecycleFragment())
        fragments.add(DateEditBucketListFragment())

        supportFragmentManager.beginTransaction().add(R.id.date_edit_container,fragments[0]).commit()

        val spinnerItems= resources.getStringArray(R.array.spinner_array)
        val adapter= ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,spinnerItems)
        binding.spinnerDateEdit.adapter= adapter
        binding.spinnerDateEdit.onItemSelectedListener= object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                supportFragmentManager.fragments.forEach{
                    supportFragmentManager.beginTransaction().hide(it).commit()
                }
                var tran= supportFragmentManager.beginTransaction()
                if(fragments[p2] is DateEditDailyNoteFragment){
                    tran.show(fragments[p2])
                    binding.btnRegistDateedit.setOnClickListener {
                        val dailylNoteFragment= DailylNoteFragment()
                        val bundle= Bundle(2)   //파라미터 : 전달할 값의 개수
                        bundle.putString("dateTitle",binding.tvTitleMainDateEdit.text.toString())
                        bundle.putInt("categoryImage",binding.ivCategoryMainDateEdit.id)
                        dailylNoteFragment.arguments= bundle
                        Log.i("aaa",binding.tvTitleMainDateEdit.text.toString()+","+binding.ivCategoryMainDateEdit.id)
                    }
                }else if(fragments[p2] is CheckListFragment){
                    if(!supportFragmentManager.fragments.contains(fragments[p2])) tran.add(R.id.date_edit_container,fragments[p2])
                    tran.show(fragments[p2])
                }else if(fragments[p2] is LifecycleFragment){
                    if(!supportFragmentManager.fragments.contains(fragments[p2])) tran.add(R.id.date_edit_container,fragments[p2])
                    tran.show(fragments[p2])
                }else{
                    if(!supportFragmentManager.fragments.contains(fragments[p2])) tran.add(R.id.date_edit_container,fragments[p2])
                    tran.show(fragments[p2])
                }
                tran.commit()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        binding.btnRegistDateedit.setOnClickListener { clickRegistDate() }
        binding.ivCategoryMainDateEdit.setOnClickListener {
            val intent= Intent(this@DateEditActivity, CategoryActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    fun clickRegistDate(){
        val intent= Intent(this@DateEditActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}