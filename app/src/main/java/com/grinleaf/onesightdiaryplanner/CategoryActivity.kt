package com.grinleaf.onesightdiaryplanner

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grinleaf.onesightdiaryplanner.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    val binding by lazy { ActivityCategoryBinding.inflate(layoutInflater) }
    val categoryImages= mutableListOf<Drawable>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        categoryImages.add(binding.ivInterestCatecory01.drawable)
        categoryImages.add(binding.ivInterestCatecory02.drawable)
        categoryImages.add(binding.ivInterestCatecory03.drawable)
        categoryImages.add(binding.ivInterestCatecory04.drawable)
        categoryImages.add(binding.ivInterestCatecory05.drawable)
        categoryImages.add(binding.ivInterestCatecory06.drawable)
        categoryImages.add(binding.ivInterestCatecory07.drawable)
        categoryImages.add(binding.ivAchieveCatecory01.drawable)
        categoryImages.add(binding.ivAchieveCatecory02.drawable)
        categoryImages.add(binding.ivAchieveCatecory03.drawable)
        categoryImages.add(binding.ivAchieveCatecory04.drawable)
        categoryImages.add(binding.ivAchieveCatecory05.drawable)
        categoryImages.add(binding.ivAchieveCatecory06.drawable)
        categoryImages.add(binding.ivAchieveCatecory07.drawable)
    }

    fun saveImage(){
        val intent= intent
        intent.putExtra("selectedImage", 0) //값 수정요 (기본자료형만 가능)
        setResult(RESULT_OK)
        finish()
    }
}