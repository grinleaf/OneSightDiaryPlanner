package com.grinleaf.onesightdiaryplanner

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.grinleaf.onesightdiaryplanner.databinding.ActivityCategoryBinding
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.ByteBuffer
import java.util.*


class CategoryActivity : AppCompatActivity() {
    val binding by lazy { ActivityCategoryBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Glide.with(this).load(G.categoryImages[0]).into(binding.ivInterestCatecory01)
        Glide.with(this).load(G.categoryImages[1]).into(binding.ivInterestCatecory02)
        Glide.with(this).load(G.categoryImages[2]).into(binding.ivInterestCatecory03)
        Glide.with(this).load(G.categoryImages[3]).into(binding.ivInterestCatecory04)
        Glide.with(this).load(G.categoryImages[4]).into(binding.ivInterestCatecory05)
        Glide.with(this).load(G.categoryImages[5]).into(binding.ivInterestCatecory06)
        Glide.with(this).load(G.categoryImages[6]).into(binding.ivInterestCatecory07)
        Glide.with(this).load(G.categoryImages[7]).into(binding.ivAchieveCatecory01)
        Glide.with(this).load(G.categoryImages[8]).into(binding.ivAchieveCatecory02)
        Glide.with(this).load(G.categoryImages[9]).into(binding.ivAchieveCatecory03)
        Glide.with(this).load(G.categoryImages[10]).into(binding.ivAchieveCatecory04)
        Glide.with(this).load(G.categoryImages[11]).into(binding.ivAchieveCatecory05)
        Glide.with(this).load(G.categoryImages[12]).into(binding.ivAchieveCatecory06)
        Glide.with(this).load(G.categoryImages[13]).into(binding.ivAchieveCatecory07)

        binding.ivInterestCatecory01.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory01,G.categoryImages[0]) }
        binding.ivInterestCatecory02.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory02,G.categoryImages[1]) }
        binding.ivInterestCatecory03.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory03,G.categoryImages[2]) }
        binding.ivInterestCatecory04.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory04,G.categoryImages[3]) }
        binding.ivInterestCatecory05.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory05,G.categoryImages[4]) }
        binding.ivInterestCatecory06.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory06,G.categoryImages[5]) }
        binding.ivInterestCatecory07.setOnClickListener { clickSomeCategoryImage(binding.ivInterestCatecory07,G.categoryImages[6]) }
        binding.ivAchieveCatecory01.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory01,G.categoryImages[7]) }
        binding.ivAchieveCatecory02.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory02,G.categoryImages[8]) }
        binding.ivAchieveCatecory03.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory03,G.categoryImages[9]) }
        binding.ivAchieveCatecory04.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory04,G.categoryImages[10]) }
        binding.ivAchieveCatecory05.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory05,G.categoryImages[11]) }
        binding.ivAchieveCatecory06.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory06,G.categoryImages[12]) }
        binding.ivAchieveCatecory07.setOnClickListener { clickSomeCategoryImage(binding.ivAchieveCatecory07,G.categoryImages[13]) }
    }

    //특정 카테고리 이미지 클릭 시 해당 이미지(drawable)를 저장
    fun clickSomeCategoryImage(iv:ImageView,d:String){
        G.selectedCategoryImage= d
        Log.i("aaa",""+G.selectedCategoryImage)

        val intent= intent
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        G.selectedCategoryImage= "http://grinleaf.dothome.co.kr/OneSightDiaryPlanner/./category/ic_category_img.png"
    }
}