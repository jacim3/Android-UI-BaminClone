package com.example.bamincloneui.presentation.adapters.delivery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bamincloneui.presentation.fragment.main.*

class MainPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = arrayOf(
        ADeliveryFragment(), BaeMin1Fragment(), CTakeOutFragment(),
        DShoppingLiveFragment(), EGiftFragment(), FYummyFragment()
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {

        return fragments[position]
    }
}