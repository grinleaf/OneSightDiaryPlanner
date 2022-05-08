package com.grinleaf.onesightdiaryplanner

import android.app.Activity.RESULT_CANCELED
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.grinleaf.onesightdiaryplanner.databinding.FragmentDateeditDailynoteBinding
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class DateEditDailyNoteFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    val binding by lazy { FragmentDateeditDailynoteBinding.inflate(layoutInflater) }
    var dateString= ""
    lateinit var imageUriPath:String
    lateinit var resultLauncher:ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //일정 날짜 설정 + 포맷 지정
        binding.tvTodayAutoDailynoteDateEdit.text= ""+LocalDate.now()
        binding.tvTodayAutoDailynoteDateEdit.setOnClickListener {
            val cal= Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{
                    view, year, month, dayOfMonth -> dateString = "${year}-${month+1}-${dayOfMonth}"
                val date:Date= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).parse(dateString)
                dateString= SimpleDateFormat("yyyy-MM-dd", Locale("ko","KR")).format(date)
                binding.tvTodayAutoDailynoteDateEdit.text = dateString
            }
            DatePickerDialog(requireContext(),dateSetListener,cal.get(Calendar.YEAR),cal.get(
                Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        //이미지첨부 (2)
        resultLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode!=RESULT_CANCELED){
                var uri= it.data?.data!!
                G.selectedattachImage= getRealPathFromUri(uri)
                Glide.with(requireContext()).load(uri).into(binding.ivAttachImageDailynoteDateEdit)
            }else{
                G.selectedattachImage= ""
            }
        }

        //이미지첨부 (1)
        binding.ivAttachImageDailynoteDateEdit.setOnClickListener{
            val intent= Intent().setAction(Intent.ACTION_PICK).setType("image/*")
            resultLauncher.launch(intent)
        }
    }
    
    // Uri(Resource DB 주소) --> 절대 경로(String) 변환 코드
    fun getRealPathFromUri(uri: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(requireContext(),uri, proj,null,null,null)
        //uri == FROM board, proj == {no, name...} 등 컬럼명(String) 데이터가 나열된 배열
        //selectionArgs, sortOrder(오름차순, 내림차순 설정) 등
        val cursor = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_index)
        cursor.close()
        return result
    }
}