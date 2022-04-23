package com.example.bamincloneui.presentation.adapters.delivery

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bamincloneui.R
import com.example.bamincloneui.constants.Common
import com.example.bamincloneui.constants.Etc
import com.example.bamincloneui.constants.FILTER
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.stream.Collectors

class FilterRecyclerAdapter :
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
        val filterText: AppCompatTextView = itemView.findViewById(R.id.textViewFilterText)
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
        holder.filterText.text = ""
        holder.buttonArea.setBackgroundColor(0)

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
        holder.filterText.text = item.text

        holder.buttonArea.setOnClickListener {
            buttonInteraction(position, holder, it)
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

    @SuppressLint("SetTextI18n")
    private fun createOptionItems(position: Int, @Nullable str: String?) {
        when (position) {
            /*         FILTER.RESET.code -> {

                     }
                     FILTER.COUPON.code, FILTER.TAKEOUT.code, FILTER.SELF_MEAL.code, FILTER.RESERVATION.code -> {
                         buttonHolders[position].buttonArea.background = buttonHolders[position].clicked
                         buttonHolders[position].filterText.text = menus[position].text
                         buttonHolders[position].iconBack.setImageResource(menus[position].icon)
                     }

                     FILTER.MIN_PRICE.code -> {
                         buttonHolders[position].buttonArea.background = buttonHolders[position].clicked
                         buttonHolders[position].filterText.text = menus[position].text + str
                         buttonHolders[position].iconBack.setImageResource(menus[position].icon)
                     }*/
        }
    }

    /*
         1. '초기화' 를 누르면 '기본순' 으로 이동.
         2. '최소주문' 과 '기타' 는 복수선택 가능.
         3. '기본순' 을 제외한 모든메뉴 클릭시 초기화 메뉴 생성.

         4. 쿠폰 포장/방문 1인분 예약가능 버튼은 '기타' 앞에 차럐로 생성.
         5.
    */
    private fun buttonInteraction(position: Int, holder: FilterViewHolder, it: View) {

        Log.e("asdfasdffads", position.toString())
        Log.e("asdfasdffads", menus[position].text)

        val buttonName = findButtonName(it)

        // 눌렀단 버튼을 또 누를경우.
        if (prevButton == it) {

            when (buttonName) {
                FILTER.MIN_PRICE.text, FILTER.COUPON.text, FILTER.TAKEOUT.text,
                FILTER.SELF_MEAL.text, FILTER.BOOKING.text -> {

                }
                else -> {
                    prevButton = null
                    it.background = holder.unClicked
                    buttonCheckList[position] = false
                    return
                }
            }
        }

        holder.dialogBuilder.setTitle("기타 필터")
            .setMultiChoiceItems(etcFilters, etcChecks) { dialog, index, checked ->

            }.setOnCancelListener {

                for (i in Etc.values()) {
                    if (etcChecks[i.position]) {
                        var etcItem: FILTER? = null

                        when (i.text) {
                            
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

                    } else {
                        when (i.text) {
                            FILTER.COUPON.text -> {
                                val couponPosition = getPositionByText(FILTER.COUPON.text)
                                if (couponPosition != -1) {
                                    menus.removeAt(couponPosition)
                                    notifyItemRemoved(couponPosition)
                                }
                            }
                            FILTER.TAKEOUT.text -> {
                                val takeoutPosition = getPositionByText(FILTER.TAKEOUT.text)
                                if (takeoutPosition != -1) {
                                    menus.removeAt(takeoutPosition)
                                    notifyItemRemoved(takeoutPosition)
                                }
                            }
                            FILTER.SELF_MEAL.text -> {
                                val selfMealPosition = getPositionByText(FILTER.SELF_MEAL.text)
                                if (selfMealPosition != -1) {
                                    menus.removeAt(selfMealPosition)
                                    notifyItemRemoved(selfMealPosition)
                                }
                            }
                            FILTER.BOOKING.text -> {
                                val bookingPosition = getPositionByText(FILTER.BOOKING.text)
                                if (bookingPosition != -1) {
                                    menus.removeAt(bookingPosition)
                                    notifyItemRemoved(bookingPosition)
                                }
                            }
                        }
                    }
                }
            }.show()


        /*    when (position) {
                // 활성화 불가
                FILTER.RESET.code -> {

                }
                // 활성화 불가, 다이얼로그
                FILTER.ETC.code -> {
                    holder.dialogBuilder.setTitle("기타 필터")
                        .setMultiChoiceItems(etcFilters, etcChecks) { dialog, index, checked ->
                            run {
                                when (index) {
                                    COUPON -> {

                                    }

                                    TAKEOUT -> {

                                    }

                                    SELF_MEAL -> {

                                    }

                                    RESERVATION -> {

                                    }
                                }

                                // Result


                                if (etcChecks[COUPON]) createOptionItems(FILTER.COUPON.code, null)
                                if (etcChecks[TAKEOUT]) createOptionItems(FILTER.TAKEOUT.code, null)
                                if (etcChecks[SELF_MEAL]) createOptionItems(
                                    FILTER.SELF_MEAL.code,
                                    null
                                )
                                if (etcChecks[RESERVATION]) createOptionItems(
                                    FILTER.RESERVATION.code,
                                    null
                                )
                            }
                        }.show()
                }

                // 중복클릭 가능 -> 다이얼로그
                FILTER.MIN_PRICE.code -> {

                    holder.dialogBuilder.setTitle("최소 주문").setItems(priceFilters) { dialog, index ->
                        run {
                            when (index) {
                                UNDER_NONE -> {
                                    it.background = holder.unClicked
                                }
                                UNDER_5000 -> {
                                    createOptionItems(position, " 5,000원 이하")
                                }
                                UNDER_10000 -> {
                                    createOptionItems(position, " 10,000원 이하")
                                }
                                UNDER_12000 -> {
                                    createOptionItems(position, " 12,000원 이하")
                                }
                                UNDER_15000 -> {
                                    createOptionItems(position, " 15,000원 이하")
                                }
                                UNDER_20000 -> {
                                    createOptionItems(position, " 20,000원 이하")
                                }
                            }
                        }
                    }.show()
                    // it.background = holder.clicked
                }

                else -> {
                    prevButton?.background = holder.unClicked
                    it.background = holder.clicked
                }
            }*/


        // 2.
        prevButton = it
    }

    companion object {
        const val COUPON = 0
        const val TAKEOUT = 1
        const val SELF_MEAL = 2
        const val RESERVATION = 3

        const val UNDER_NONE = 0
        const val UNDER_5000 = 1
        const val UNDER_10000 = 2
        const val UNDER_12000 = 3
        const val UNDER_15000 = 4
        const val UNDER_20000 = 5
    }
}