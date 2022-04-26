## 배민앱 UI 클론 
- 현재 배달화면 부분만 완성.


- 배달 메뉴 리스트 출력을 위해 사용한 fakeItem


     ![ezgif-4-d22cd79fab](https://user-images.githubusercontent.com/60813834/165234737-2228d360-8438-4eec-8649-c8c8eadee928.gif)

      val fakeDeliveryItemList = listOf(
          DeliveryListItem(
              listOf(IMG1),               // 이미지. 3장이상 등록 시, 나눠서 출력
              "페이크피자집",           // 업체 이름
              5.0,                    // 별 갯수
              listOf("신규"),               // Chips 
              listOf(9, 35),               // 시작시간~종료시간
              mapOf(                       // 최소주문 배달팁 등 리스트
                  Pair("최소주문", "7000"),
                  Pair("배달팁", "0원 ~ 3000원")
              )
          ),
          DeliveryListItem(
              listOf(IMG2, IMG3, IMG5),
              "페이크치킨집",
              5.0,
              listOf("쿠폰", "포장가능"),
              listOf(9, 35),
              mapOf(
                  Pair("최소주문", "7000"),
                  Pair("배달팁", "0원 ~ 3000원")
              )
          ),
          DeliveryListItem(
              listOf(IMG3, IMG4, IMG5),
              "페이크파스타집",
              5.0,
              listOf("신규"),
              listOf(9, 35),
              mapOf(Pair("최소주문", "7000"))
          ),
          DeliveryListItem(
              listOf(IMG3),
              "페이크라면집",
              5.0,
              listOf("신규"),
              listOf(9, 35),
              mapOf(
                  Pair("최소주문", "7000"),
                  Pair("배달팁", "0원 ~ 3000원")
              )
          ),
          DeliveryListItem(
              listOf(IMG5),
              "페이크마라탕집",
              5.0,
              listOf("포장가능", "예약가능"),
              listOf(9, 35),
              mapOf(Pair("최소주문", "7000"))
          ),
          DeliveryListItem(
              listOf(IMG1, IMG3, IMG5),
              "페이크햄버거집",
              5.0,
              null,
              listOf(9, 35),
              mapOf(
                  Pair("최소주문", "7000"),
                  Pair("배달팁", "0원 ~ 3000원")
              )
          )
      )



- 리스트 필터 메뉴 -> RecyclerView 및 Enum 이용.
      

     ![ezgif-4-7632793fc2](https://user-images.githubusercontent.com/60813834/165234741-2a3d49fd-afe7-4e51-b3a4-d4781277b20e.gif)


      enum class FILTER(val code:Int, val text:String, val icon:Int, val iconPosition:String, val enabled:Boolean) {
      RESET(0, "초기화", R.drawable.ic_baseline_subdirectory_arrow_left_24, "left", false),
      LOW_TIPS(1, "배달팁 낮은 순", R.drawable.ic_baseline_arrow_downward_24, "left", true),
      BASIC(2, "기본순", 0, "", true),
      HIGH_ORDER(3, "주문 많은 순", 0, "", true),
      HIGH_STARS(4, "별점 높은 순", 0, "", true),
      NEAR_LOCATION(5, "가까운 순", 0, "", true),
      HIGH_FAVORITE(6, "찜 많은 순",0, "", true),
      MIN_PRICE(7, "최소주문금액", R.drawable.ic_baseline_keyboard_arrow_down_24, "right", true),
      COUPON(8, "쿠폰", R.drawable.ic_baseline_close_24, "right", false),
      TAKEOUT(9, "포장/방문", R.drawable.ic_baseline_close_24, "right", false),
      SELF_MEAL(10, "1인분", R.drawable.ic_baseline_close_24, "right", false),
      BOOKING(11, "예약가능", R.drawable.ic_baseline_close_24, "right", false),
      ETC(12, "기타", R.drawable.ic_baseline_settings_24, "left", true),
      }

      enum class MinPrice(val position:Int, val text:String, val price:Int) {
          ALL(0, "전체", 0),
          UNDER_5000(1, "최소주문 5,000원 이하", 5000),
          UNDER_10000(2, "최소주문 10,000원 이하", 10000),
          UNDER_12000(3, "최소주문 12,000원 이하", 12000),
          UNDER_15000(4, "최소주문 15,000원 이하", 15000),
          UNDER_20000(5, "최소주문 20,000원 이하", 20000),
      }

      enum class Etc(val position:Int, val text:String){
          COUPON(0, "쿠폰"),
          TAKEOUT(1, "포장/방문"),
          SELF_MEAL(2, "1인분"),
          BOOKING(3, "예약가능")
      }
  
  
