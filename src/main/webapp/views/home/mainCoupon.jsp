<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Main Coupon</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/maincoupon.css">
</head>
<body class="coupon-body">
  <div class="coupon-container">
    <!-- 첫 번째 쿠폰 -->
    <div class="coupon-box">
      <a href="<%= request.getContextPath() %>/jelly?page=event1" target="_self">
        <img src="<%= request.getContextPath() %>/img/coupon1.png" alt="시크릿 쿠폰">
      </a>
    </div>

    <!-- 두 번째 쿠폰 -->
    <div class="coupon-box">
      <a href="<%= request.getContextPath() %>/jelly?page=event2" target="_self">
        <img src="<%= request.getContextPath() %>/img/coupon2.png" alt="10% 할인 혜택">
      </a>
    </div>
  </div>
</body>
</html>