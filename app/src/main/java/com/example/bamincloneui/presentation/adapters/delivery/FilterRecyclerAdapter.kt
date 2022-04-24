package com.example.bamincloneui.presentation.adapters.delivery

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bamincloneui.R
import com.example.bamincloneui.constants.Common
import com.example.bamincloneui.constants.Etc
import com.example.bamincloneui.constants.FILTER
import com.example.bamincloneui.constants.MinPrice
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.stream.Collectors

class FilterRecyclerAdapter(private val manager: LinearLayoutManager) :
    RecyclerView.Adapter<FilterRecyclerAdapter.FilterViewHolder>() {

    private val menus = FILTER.values().toMutableList().stream().filter {
        it.enabled
    }.collect(Collectors.toList())

    private var resetButton = FILTER.values()[0]
    private var buttonCheckList = Array(Common.DELIVERY_FILTER_COUNT) { false }     // 버튼 활성화 체크 변수.

    private val priceFilters =
        arrayOf("전체", "5,000원 이하", "10,000원 이하", "12,000원 이하", "15,000원 이하", "20,000원 이하")
    private val etcFilters = arrayOf("쿠폰", "포장/방문", "1인분", "예약가능")
    private val etcChecks = booleanArrayOf(false, false, false, false)
    private var prevButton: View? = null
    private var isInitialized = true
    private var basicButton: View? = null

    private val resultMap = HashMap<String, Any>().apply {
        this["Status"] = FILTER.BASIC.text
        this["MinPrice"] = "전체"
    }
    // private var buttonHolders = emptyList<FilterViewHolder>().toMutableList()

    class FilterViewHolder(
        itemView: View,
        drawable: Drawable?,
        drawable1: Drawable?,
        val dialogBuilder: MaterialAlertDialogBuilder
    ) :
        RecyclerView.ViewHolder(itemView) {
        val iconFront: AppCompatImageView = itemView.findViewById(R.id.imageViewIconStart)
        val iconBack: AppCompatImageView = itemView.findViewById(R.id.imageViewIconEnd)
        val buttonText: AppCompatTextView = itemView.findViewById(R.id.textViewFilterText)
        val buttonArea: LinearLayoutCompat =
            itemView.findViewById(R.id.linearLayoutButtonBackground)
        val clicked = drawable
        val unClicked = drawable1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_delivery_list_filter_item, parent, false),
            ContextCompat.getDrawable(parent.context, R.drawable.delivery_filter_item_click_accent),
            ContextCompat.getDrawable(parent.context, R.drawable.delivery_filter_item_click_none),
            MaterialAlertDialogBuilder(parent.context)
        )
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        Log.e("onBIndViewHodler", "onBVindViewHolder")
        Log.e("item : ", menus[position].text)
        Log.e("item : ", menus[position].name)

        // TODO RecyclerView 는 해당 뷰가 보이지 않게 되었다 다시 보이게 되면, 해당 뷰 인스턴스를 재 생성하는것이 아니라,
        // TODO 기존에 생성된 인스턴스를 재사용 하므로, 리스트의 형태가 일괄적이지 않은 경우, 이를 리셋해 주어야 한다.
        holder.iconFront.setImageResource(0)
        holder.iconBack.setImageResource(0)
        holder.buttonText.text = ""
        holder.buttonArea.setBackgroundColor(0)


        if (menus[holder.adapterPosition].text == FILTER.COUPON.text ||
            menus[holder.adapterPosition].text == FILTER.TAKEOUT.text ||
            menus[holder.adapterPosition].text == FILTER.SELF_MEAL.text ||
            menus[holder.adapterPosition].text == FILTER.BOOKING.text
        )
            holder.buttonArea.background = holder.clicked
        else
            holder.buttonArea.background = holder.unClicked

        val item = menus[position]
        if (item.icon != 0) {
            when (item.iconPosition) {
                "left" -> {
                    holder.iconFront.setImageResource(item.icon)
                }
                "right" -> {
                    holder.iconBack.setImageResource(item.icon)
                }
            }
        }
        holder.buttonText.text = item.text

        holder.buttonArea.setOnClickListener {

            setButtonInteraction(holder.adapterPosition, holder, it)
            menus.stream().forEach { isa ->
                Log.e("!@#!@#!@#", isa.text)
            }
        }

        if (isInitialized && item.text == FILTER.BASIC.text) {
            isInitialized = false
            holder.buttonArea.background = holder.clicked
            prevButton = holder.buttonArea
            basicButton = holder.buttonArea
        }
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    private fun findButtonName(parentView: View) =
        parentView.findViewById<AppCompatTextView>(R.id.textViewFilterText).text.toString()

    private fun getPositionByText(text: String): Int {
        for (i in menus.indices) {
            if (menus[i].text == text) {
                return i
            }
        }
        return -1
    }

    private fun isResetButtonVisible(): Boolean {
        if (menus[0].text == "초기화")
            return true
        return false
    }

    private fun addResetButton() {
        if (getPositionByText(FILTER.RESET.text) == -1) {
            menus.add(0, FILTER.RESET)
            notifyItemInserted(0)
        }
    }

    private fun removeResetButton() {
        if (getPositionByText(FILTER.RESET.text) != -1) {
            menus.removeAt(0)
            notifyItemRemoved(0)
        }
    }

    /*
         1. '초기화' 를 누르면 '기본순' 으로 이동.
         2. '최소주문' 과 '기타' 는 복수선택 가능.
         3. '기본순' 을 제외한 모든메뉴 클릭시 초기화 메뉴 생성.

         4. 쿠폰 포장/방문 1인분 예약가능 버튼은 '기타' 앞에 차럐로 생성.
         5.
    */


    private fun setButtonInteraction(position: Int, holder: FilterViewHolder, it: View) {

        val buttonName = findButtonName(it)

        // 눌렀단 버튼을 또 누를경우.
        if (prevButton == it) {

        }
        // 다른 버튼을 누름
        else {

            when (buttonName) {

                FILTER.LOW_TIPS.text, FILTER.BASIC.text, FILTER.HIGH_ORDER.text,
                FILTER.HIGH_STARS.text, FILTER.NEAR_LOCATION.text, FILTER.HIGH_FAVORITE.text -> {
                    holder.buttonArea.background = holder.clicked
                    prevButton?.background = holder.unClicked
                    buttonCheckList[position] = false
                    val offset = 100
                    manager.scrollToPositionWithOffset(position, offset)
                    resultMap["Status"] = buttonName
                }
            }
        }

        when (buttonName) {

            FILTER.RESET.text -> {
                resultMap["Status"] = FILTER.BASIC.text
                basicButton?.performClick()
                removeResetButton()

                // 기타항목 필터 아이템 지우기
                for (i in Etc.values()) {
                    etcChecks[i.position] = false
                    deleteEtcItems(i.text)
                }

                // 최소주문 금액 필터 아이템 지우기
                handleMinPrice(MinPrice.ALL.position, holder, buttonName)
            }

            FILTER.MIN_PRICE.text, MinPrice.UNDER_5000.text, MinPrice.UNDER_10000.text, MinPrice.UNDER_12000.text,
            MinPrice.UNDER_15000.text, MinPrice.UNDER_20000.text -> {
                setMinPriceWithDialog(holder, buttonName)
                val offset = 100
                manager.scrollToPositionWithOffset(position, offset)
            }

            FILTER.ETC.text -> {
                setEtcWithDialog(holder)
                val offset = 100
                manager.scrollToPositionWithOffset(position, offset)
            }

            FILTER.COUPON.text, FILTER.TAKEOUT.text, FILTER.SELF_MEAL.text, FILTER.BOOKING.text -> {
                deleteEtcItems(buttonName)
            }

            else -> {
                prevButton = it
            }
        }
        getResultWithSortItem()
    }


    private fun getResultWithSortItem() {
        var buttonCheck = false
        var etcCheck = false
        var priceCheck = false


        for (i in etcChecks)
            if (i) {
                etcCheck = true
                break
            }

        if (resultMap["MinPrice"] != "전체") priceCheck = true

        if (resultMap["Status"] != FILTER.BASIC.text) buttonCheck = true

        if (!buttonCheck && !etcCheck && !priceCheck) removeResetButton()
        else addResetButton()

        Log.e("result :", etcChecks.toString())
        Log.e("result :", resultMap.toString())
    }

    private fun setEtcWithDialog(holder: FilterViewHolder) {

        holder.dialogBuilder.setTitle(DIALOG_TITLE_ETC)
            .setMultiChoiceItems(etcFilters, etcChecks) { dialog, index, checked ->

            }.setOnCancelListener {

                for (i in Etc.values()) {
                    if (etcChecks[i.position]) {
                        insertEtcItems(i.text)
                    } else {
                        deleteEtcItems(i.text)
                    }
                    if (etcChecks[i.position]) addResetButton()
                }
            }.show()
    }


    private fun insertEtcItems(target: String) {
        var etcItem: FILTER? = null
        when (target) {

            FILTER.COUPON.text -> {
                if (getPositionByText("쿠폰") == -1) {
                    etcItem = FILTER.COUPON
                    val couponPosition =
                        if (isResetButtonVisible()) Common.POSITION_WITH_RESET
                        else Common.POSITION_WITHOUT_RESET
                    menus.add(couponPosition, etcItem)    // 맨끝 위치의 항목을 한칸 밀어내고 삽입
                    notifyItemInserted(couponPosition)    // 맨끝의 앞자리에 삽입되므로 여기를 새로고침
                }
            }

            FILTER.TAKEOUT.text -> {
                if (getPositionByText("포장/방문") == -1) {
                    etcItem = FILTER.TAKEOUT

                    val couponPosition = getPositionByText("쿠폰")
                    val takeoutPosition =
                        if (couponPosition == -1) {
                            if (isResetButtonVisible()) Common.POSITION_WITH_RESET
                            else Common.POSITION_WITHOUT_RESET
                        } else
                            couponPosition + 1
                    menus.add(takeoutPosition, etcItem)
                    notifyItemInserted(takeoutPosition)
                }
            }

            FILTER.SELF_MEAL.text -> {
                if (getPositionByText(FILTER.SELF_MEAL.text) == -1) {
                    etcItem = FILTER.SELF_MEAL
                    val bookingPosition = getPositionByText(FILTER.BOOKING.text)
                    val selfMealPosition = if (bookingPosition == -1) {
                        menus.size - 1
                    } else
                        bookingPosition - 1
                    menus.add(selfMealPosition, etcItem)
                    notifyItemInserted(selfMealPosition)
                }
            }

            FILTER.BOOKING.text -> {
                if (getPositionByText(FILTER.BOOKING.text) == -1) {
                    etcItem = FILTER.BOOKING
                    val bookingPosition = menus.size - 1
                    menus.add(bookingPosition, etcItem)
                    notifyItemInserted(bookingPosition)
                }
            }
        }
    }

    private fun deleteEtcItems(target: String) {
        when (target) {
            FILTER.COUPON.text -> {
                val couponPosition = getPositionByText(FILTER.COUPON.text)
                if (couponPosition != -1) {
                    menus.removeAt(couponPosition)
                    notifyItemRemoved(couponPosition)
                    etcChecks[Etc.COUPON.position] = false
                }
            }
            FILTER.TAKEOUT.text -> {
                val takeoutPosition = getPositionByText(FILTER.TAKEOUT.text)
                if (takeoutPosition != -1) {
                    menus.removeAt(takeoutPosition)
                    notifyItemRemoved(takeoutPosition)
                    etcChecks[Etc.TAKEOUT.position] = false
                }
            }
            FILTER.SELF_MEAL.text -> {
                val selfMealPosition = getPositionByText(FILTER.SELF_MEAL.text)
                if (selfMealPosition != -1) {
                    menus.removeAt(selfMealPosition)
                    notifyItemRemoved(selfMealPosition)
                    etcChecks[Etc.SELF_MEAL.position] = false
                }
            }
            FILTER.BOOKING.text -> {
                val bookingPosition = getPositionByText(FILTER.BOOKING.text)
                if (bookingPosition != -1) {
                    menus.removeAt(bookingPosition)
                    notifyItemRemoved(bookingPosition)
                    etcChecks[Etc.BOOKING.position] = false
                }
            }
        }
    }

    private fun setMinPriceWithDialog(holder: FilterViewHolder, buttonName: String) {
        holder.dialogBuilder.setTitle("최소 주문").setItems(priceFilters) { dialog, index ->
            run {

                handleMinPrice(index, holder, buttonName)

                if (index == 0) removeResetButton()
                else addResetButton()
            }
        }.show()
    }

    private fun handleMinPrice(statusCode: Int, holder: FilterViewHolder, buttonName: String) {

        for (i in MinPrice.values()) {
            if (i.position == statusCode) {
                if (i.position == MinPrice.ALL.position)
                    holder.buttonArea.background = holder.unClicked
                else
                    holder.buttonArea.background = holder.clicked

                holder.buttonText.text = FILTER.MIN_PRICE.text
                resultMap["MinPrice"] = buttonName
            }
        }

/*        when (statusCode) {
            MinPrice.ALL.position -> {
                holder.buttonArea.background = holder.unClicked
                holder.buttonText.text = FILTER.MIN_PRICE.text
                resultMap["MinPrice"] = buttonName
            }
            MinPrice.UNDER_5000.position -> {
                holder.buttonArea.background = holder.clicked
                holder.buttonText.text = MinPrice.UNDER_5000.text
                resultMap["MinPrice"] = buttonName
            }
            MinPrice.UNDER_10000.position -> {
                holder.buttonArea.background = holder.clicked
                holder.buttonText.text = MinPrice.UNDER_10000.text
                resultMap["MinPrice"] = buttonName
            }
            MinPrice.UNDER_12000.position -> {
                holder.buttonArea.background = holder.clicked
                holder.buttonText.text = MinPrice.UNDER_12000.text
                resultMap["MinPrice"] = buttonName
            }
            MinPrice.UNDER_15000.position -> {
                holder.buttonArea.background = holder.clicked
                holder.buttonText.text = MinPrice.UNDER_15000.text
                resultMap["MinPrice"] = buttonName
            }
            MinPrice.UNDER_20000.position -> {
                holder.buttonArea.background = holder.clicked
                holder.buttonText.text = MinPrice.UNDER_20000.text
                resultMap["MinPrice"] = buttonName
            }
        }*/
    }

    companion object {
        const val COUPON = 0
        const val TAKEOUT = 1
        const val SELF_MEAL = 2
        const val RESERVATION = 3

        private const val DIALOG_TITLE_ETC = "기타 필터"
        private const val DIALOG_TITLE_MIN_PRICE = "최소주문금액"
    }
}