package com.grinleaf.onesightdiaryplanner

import android.content.DialogInterface
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.grinleaf.onesightdiaryplanner.databinding.DialogEmoBinding

class DialogEmoActivity: AppCompatActivity() {
    val binding by lazy { DialogEmoBinding.inflate(layoutInflater) }
    var selectEmo:Int = 0
    val grayscale by lazy { floatArrayOf(
        0.2989f, 0.5870f, 0.1140f, 0f, 0f,
        0.2989f, 0.5870f, 0.1140f, 0f, 0f,
        0.2989f, 0.5870f, 0.1140f, 0f, 0f,
        0.0000f, 0.0000f, 0.0000f, 1f, 0f ) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //클릭여부에 따른 이미지 컬러/흑백처리
        binding.emo01Dialog.colorFilter = ColorMatrixColorFilter(grayscale)
        binding.emo02Dialog.colorFilter = ColorMatrixColorFilter(grayscale)
        binding.emo03Dialog.colorFilter = ColorMatrixColorFilter(grayscale)
        binding.emo04Dialog.colorFilter = ColorMatrixColorFilter(grayscale)
        binding.emo05Dialog.colorFilter = ColorMatrixColorFilter(grayscale)

        binding.emo01Dialog.setOnClickListener { clickEmo(R.id.emo_01_dialog) }
        binding.emo02Dialog.setOnClickListener { clickEmo(R.id.emo_02_dialog) }
        binding.emo03Dialog.setOnClickListener { clickEmo(R.id.emo_03_dialog) }
        binding.emo04Dialog.setOnClickListener { clickEmo(R.id.emo_04_dialog) }
        binding.emo05Dialog.setOnClickListener { clickEmo(R.id.emo_05_dialog) }
    }

    private fun clickEmo(emo:Int){
        binding.emo01Dialog.colorFilter = ColorMatrixColorFilter(grayscale)
        binding.emo02Dialog.colorFilter = ColorMatrixColorFilter(grayscale)
        binding.emo03Dialog.colorFilter = ColorMatrixColorFilter(grayscale)
        binding.emo04Dialog.colorFilter = ColorMatrixColorFilter(grayscale)
        binding.emo05Dialog.colorFilter = ColorMatrixColorFilter(grayscale)
        when(emo){
            R.id.emo_01_dialog -> {
                selectEmo = 1
                binding.emo01Dialog.colorFilter = null
                setDialog()
                Log.i("aaa","emo1 filter: ${binding.emo01Dialog.colorFilter}")
                return
            }
            R.id.emo_02_dialog -> {
                selectEmo = 2
                binding.emo02Dialog.colorFilter = null
                setDialog()
                Log.i("aaa","emo2 filter: ${binding.emo02Dialog.colorFilter}")
                return
            }
            R.id.emo_03_dialog -> {
                selectEmo = 3
                binding.emo03Dialog.colorFilter = null
                setDialog()
                Log.i("aaa","emo3 filter: ${binding.emo03Dialog.colorFilter}")
                return
            }
            R.id.emo_04_dialog -> {
                selectEmo = 4
                binding.emo04Dialog.colorFilter = null
                setDialog()
                Log.i("aaa","emo4 filter: ${binding.emo04Dialog.colorFilter}")
                return
            }
            R.id.emo_05_dialog -> {
                selectEmo= 5
                binding.emo05Dialog.colorFilter = null
                setDialog()
                Log.i("aaa","emo5 filter: ${binding.emo05Dialog.colorFilter}")
                return
            }
        }
    }
    var clickTime: Long= 0L
    private fun setDialog(){
            if(System.currentTimeMillis()>clickTime+2000){
                clickTime= System.currentTimeMillis()
                return
            }
            if(System.currentTimeMillis()<=clickTime+2000){
                finish()
            }
//        AlertDialog.Builder(baseContext).setTitle("이 표정을 선택하시겠습니까?").setPositiveButton("예", DialogInterface.OnClickListener { dialogInterface, i ->
//            //마지막으로 선택된 이모티콘을 G.emo에 저장 --> calendar 프래그먼트 표정 반영
            if(binding.emo01Dialog.colorFilter==null){
                G.saveEmoImages= G.loadEmoImages[0]
            }else if(binding.emo02Dialog.colorFilter==null){
                G.saveEmoImages= G.loadEmoImages[1]
            }else if(binding.emo03Dialog.colorFilter==null){
                G.saveEmoImages= G.loadEmoImages[2]
            }else if(binding.emo04Dialog.colorFilter==null){
                G.saveEmoImages= G.loadEmoImages[3]
            }else if(binding.emo05Dialog.colorFilter==null){
                G.saveEmoImages= G.loadEmoImages[4]
            }
            Log.i("aaa",G.saveEmoImages)
//        }).setNegativeButton("아니오", DialogInterface.OnClickListener { dialogInterface, i ->  }).create().show()
    }
}

