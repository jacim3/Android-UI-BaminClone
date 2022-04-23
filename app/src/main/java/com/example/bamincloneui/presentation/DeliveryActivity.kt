package com.example.bamincloneui.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.bamincloneui.R
import com.example.bamincloneui.databinding.ActivityDeliveryBinding
import com.example.bamincloneui.presentation.adapters.MainPagerAdapter
import com.example.bamincloneui.presentation.viewmodels.DeliveryViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeliveryActivity : AppCompatActivity() {

    private val deliveryViewModel: DeliveryViewModel by viewModels()
    lateinit var binding: ActivityDeliveryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery)
        binding.lifecycleOwner = this
        binding.viewModel = deliveryViewModel


        // TODO Fragment 의 ViewPager 를 스크롤할 때, Activity 의 ViewPager 스크롤을 잠시 막을것 !
        val pagerAdapter = MainPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewpager2.apply {
            adapter = pagerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            isUserInputEnabled = false
        }

        val tabTexts = arrayOf("배달", "배민1", "포장", "쇼핑라이브", "선물하기", "전국별미")

        TabLayoutMediator(
            binding.tabLayout, binding.viewpager2
        ) { tab: TabLayout.Tab?, position: Int ->
            tab!!.text = tabTexts[position]
        }.attach()
    }
}