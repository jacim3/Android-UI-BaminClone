package com.example.bamincloneui.presentation.adapters.delivery

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bamincloneui.R
import com.example.bamincloneui.constants.Common
import com.example.bamincloneui.constants.Etc
import com.example.bamincloneui.constants.FILTER
import com.example.bamincloneui.constants.MinPrice
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.stream.Collectors

class FilterRecyclerAdapter(
    private val manager: LinearLayoutManager,
    private val resultFilterData: MutableLiveData<Map<String, Any>>
) :
    RecyclerView.Adapter<FilterRecyclerAdapter.FilterViewHolder>() {

    private val menus = FILTER.values().toMutableList().stream().filter {
        it.enabled
    }.collect(Collectors.toList())

    private val DIALOG_TITLE_ETC = "기타 필터"

    private val priceFilters =
        arrayOf("전체", "5,000원 이하", "10,000원 이하", "12,000원 이하", "15,000원 이하", "20,000원 이하")
    private val etcFilters = arrayOf("쿠폰", "포장/방문", "1인분", "예약가능")
    private val etcChecks = booleanArrayOf(false, false, false, false)
    private var prevButton: View? = null
    private var isInitialized1 = true
    private var isInitialized2 = true
    private var basicButton: View? = null
    private var minPriceButton: View? = null

    private val resultMap = HashMap<String, Any>().apply {
        this["Status"] = FILTER.BASIC.text
        this["MinPrice"] = 0
    }

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
        }

        if (isInitialized1 && item.text == FILTER.BASIC.text) {
            isInitialized1 = false
            holder.buttonArea.background = holder.clicked
            prevButton = holder.buttonArea
            basicButton = holder.buttonArea
        }

        if (isInitialized2 && item.text == FILTER.MIN_PRICE.text) {
            isInitialized2 = false
            minPriceButton = holder.buttonArea
        }
    }

    override fun getItemCount(): Int {
        return menus.size
    }

    //==============================================================================================

    // 생성된 버튼리스트에 대한 클릭관련 이벤트 정의
    private fun setButtonInteraction(position: Int, holder: FilterViewHolder, it: View) {

        val buttonName = findButtonName(it)

        // 이전에 누른 버튼과 현재버튼이 다른 경우에만 수행
        if (prevButton != it) {
            when (buttonName) {

                FILTER.LOW_TIPS.text, FILTER.BASIC.text, FILTER.HIGH_ORDER.text,
                FILTER.HIGH_STARS.text, FILTER.NEAR_LOCATION.text, FILTER.HIGH_FAVORITE.text -> {
                    holder.buttonArea.background = holder.clicked
                    prevButton?.background = holder.unClicked
                    val offset = 100
                    manager.scrollToPositionWithOffset(position, offset)
                    resultMap["Status"] = buttonName
                }
            }
        }

        when (buttonName) {
            FILTER.RESET.text -> {
                // 기타항목 필터 아이템 지우기
                for (i in Etc.values()) {
                    etcChecks[i.position] = false
                    deleteEtcItems(i.text)
                }

                // 최소주문 리셋
                minPriceButton?.post {
                    minPriceButton?.background = holder.unClicked
                    minPriceButton?.findViewById<AppCompatTextView>(R.id.textViewFilterText)?.text = FILTER.MIN_PRICE.text
                }

                basicButton?.post {
                    basicButton?.performClick()
                }

                // 결과데이터 리셋
                resultMap.apply {
                    this["Status"] = FILTER.BASIC.text
                    this["MinPrice"] = 0
                }
            }

            FILTER.MIN_PRICE.text, MinPrice.UNDER_5000.text, MinPrice.UNDER_10000.text, MinPrice.UNDER_12000.text,
            MinPrice.UNDER_15000.text, MinPrice.UNDER_20000.text -> {
                setMinPriceWithDialog(holder)
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
        getFilterResult()
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

    //==================================== 기타 필터 Handler =========================================

    private fun setEtcWithDialog(holder: FilterViewHolder) {

        holder.dialogBuilder.setTitle(DIALOG_TITLE_ETC)
            .setMultiChoiceItems(etcFilters, etcChecks) { dialog, index, checked ->

            }.setOnCancelListener {
                handleEtcMenu()
            }.show()
    }

    private fun handleEtcMenu() {
        for (i in Etc.values()) {
            if (etcChecks[i.position]) {
                insertEtcItems(i.text)
            } else {
                deleteEtcItems(i.text)
            }
        }
        getFilterResult()
    }

    // '기타' 필터메뉴는 중복선택이 가능하며 메뉴마다 우선순위가 존재하여, 이에 따라 별도의 처리를 해주어야 하므로
    // 단순 반복문으로 처리할 수 없음
    private fun insertEtcItems(target: String) {
        var etcItem: FILTER? = null

        when (target) {
            FILTER.COUPON.text -> {

                val position = getPositionByText("쿠폰")
                if (position == -1) {
                    etcItem = FILTER.COUPON
/*                    val couponPosition =
                        if (isResetButtonVisible()) Common.POSITION_WITH_RESET
                        else Common.POSITION_WITHOUT_RESET*/
                    val couponPosition = getPositionByText(FILTER.HIGH_FAVORITE.text)+2 // 쿠폰은 최소주문 바로 뒤.
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
                            getPositionByText(FILTER.HIGH_FAVORITE.text)+2
/*                            if (isResetButtonVisible()) Common.POSITION_WITH_RESET
                            else Common.POSITION_WITHOUT_RESET*/
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

    // 기타메뉴 제거에 따른 처리.
    private fun deleteEtcItems(target: String) {

        for (i in Etc.values()) {
            if (target == i.text) {
                val position = getPositionByText(target)
                if (position != -1) {
                    menus.removeAt(position)
                    notifyItemRemoved(position)
                    etcChecks[i.position] = false
                }
                break
            }
        }
    }

    //=================================== 최소주문금액 Handler =======================================

    private fun setMinPriceWithDialog(holder: FilterViewHolder) {
        holder.dialogBuilder.setTitle(FILTER.MIN_PRICE.text)
            .setItems(priceFilters) { dialog, index ->
                run {

                    handleMinPrice(index, holder)
                }
            }.show()
    }

    @SuppressLint("SetTextI18n")
    private fun handleMinPrice(statusCode: Int, holder: FilterViewHolder) {

        for (i in MinPrice.values()) {
            if (i.position == statusCode) {
                if (i.position == MinPrice.ALL.position) {
                    holder.buttonArea.background = holder.unClicked
                    holder.buttonText.text = FILTER.MIN_PRICE.text
                    resultMap["MinPrice"] = 0
                    break
                } else {
                    holder.buttonArea.background = holder.clicked
                    holder.buttonText.text = i.text
                    resultMap["MinPrice"] = i.price
                    break
                }
            }
        }
        getFilterResult()
    }

    //===================================== Result Handler =========================================

    // 결과 필터 데이터셋을 체크하여, 변경이 이루어졌을때의 처리를 담당.
    private fun getFilterResult() {
        var buttonCheck = false
        var etcCheck = false
        var priceCheck = false

        // 다른 옵션이 선택되었는지 여부 체크
        for (i in etcChecks)
            if (i) {
                etcCheck = true
                break
            }

        // 최소가격 체크
        if (resultMap["MinPrice"] != 0) priceCheck = true
        // '기본순' '최소주문' '기타' 필터버튼 외 다른버튼이 선택되었는지 여부 체크.
        if (resultMap["Status"] != FILTER.BASIC.text) buttonCheck = true

        // '초기하' 외 다른옵션이 선택된 경우 리셋버튼 추가. 그 외에는 리셋버튼 제거.
        if (!buttonCheck && !etcCheck && !priceCheck) removeResetButton()
        else addResetButton()

        resultMap["etc"] = etcChecks
        resultFilterData.postValue(resultMap)
    }
}