package com.example.bamincloneui.presentation.fragment.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.bamincloneui.constants.Common
import com.example.bamincloneui.data.model.BannerItem
import com.example.bamincloneui.databinding.FragmentADeliveryBinding
import com.example.bamincloneui.presentation.adapters.delivery.BannerPagerRecyclerAdapter
import com.example.bamincloneui.presentation.adapters.delivery.GridRecyclerViewAdapter
import com.example.bamincloneui.presentation.adapters.delivery.SubBannerRecyclerAdapter
import com.example.bamincloneui.presentation.fakeBannerList
import com.example.bamincloneui.presentation.fakeGridItemList
import com.example.bamincloneui.presentation.fakeSubBannerList
import com.example.bamincloneui.presentation.fragment.main.viewmodels.ADeliveryViewModel
import com.example.bamincloneui.presentation.interaction.BannerInteraction
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [delivery_1.newInstance] factory method to
 * create an instance of this fragment.
 */
class ADeliveryFragment : Fragment(), BannerInteraction {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentADeliveryBinding? = null
    private val viewModel: ADeliveryViewModel by viewModels()

    private var isBannerAutoPaging = true

    private lateinit var bannerAdapter: BannerPagerRecyclerAdapter
    private lateinit var subBannerAdapter: SubBannerRecyclerAdapter
    private lateinit var gridRecyclerViewAdapter: GridRecyclerViewAdapter

    private var totalBannerCount = 0
    private var totalSubBannerCount = 0
    private var currentBannerPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentADeliveryBinding.inflate(layoutInflater, container, false)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO 아이템 초기화. -> 원래는 서버에서 초기화 되어야 한다.
        viewModel.bannerItemList.value = fakeBannerList
        viewModel.subBannerItemList.value = fakeSubBannerList
        viewModel.gridMenuItems.value = fakeGridItemList

        initBannerPager()
        initSubBannerPager()
        initGridMenuRecyclerView()

        viewModel.bannerItemList.observe(viewLifecycleOwner) {
            bannerAdapter.setImageList(it)
        }

        viewModel.subBannerItemList.observe(viewLifecycleOwner) {
            subBannerAdapter.setSubBannerItems(it)
        }

        viewModel.gridMenuItems.observe(viewLifecycleOwner){
            gridRecyclerViewAdapter.setGridItems(it)
        }

        viewModel.bannerItemPosition.observe(viewLifecycleOwner) {
            binding!!.bannerPager.currentItem = it
        }

        autoScrollBannerItem()
    }

    private fun initBannerPager() {

        bannerAdapter = BannerPagerRecyclerAdapter(this@ADeliveryFragment)

        binding!!.bannerPager.apply {
            adapter = bannerAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        // 자동 스크롤 이벤트
        binding!!.bannerPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                isBannerAutoPaging = true

                Handler(Looper.getMainLooper()).post {
                    binding!!.textViewBannerCursor.text = "${position+1} / 5 "
                }
            }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }

    private fun initSubBannerPager() {
        subBannerAdapter = SubBannerRecyclerAdapter()

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        binding!!.recyclerViewSubBanner.apply {
            this.layoutManager = layoutManager
            adapter = subBannerAdapter
        }
    }

    private fun initGridMenuRecyclerView(){
        binding!!.recyclerViewGridMenus.apply {
            gridRecyclerViewAdapter = GridRecyclerViewAdapter()
            layoutManager = GridLayoutManager(requireContext(), Common.DELIVERY_FRAGMENT_GRID_MENU_ROW)
            adapter = gridRecyclerViewAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun autoScrollBannerItem() {
        lifecycleScope.launch {
            whenResumed {
                while (isBannerAutoPaging) {
                    delay(3000)


                    viewModel.bannerItemPosition.value =
                        viewModel.bannerItemPosition.value?.plus(1)?.rem(5)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        isBannerAutoPaging = false
    }

    override fun onDestroy() {
        super.onDestroy()
        isBannerAutoPaging = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ADeliveryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onBannerItemClicked(bannerItem: BannerItem) {
        Toast.makeText(requireContext(), "배너 클릭", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(p0: View?) {

    }
}