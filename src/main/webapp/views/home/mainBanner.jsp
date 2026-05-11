<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mainbanner.css">
  <title>Jelly</title>
</head>
<body class="main-banner-page">
  <!-- 배너 컨테이너 -->
  <div class="banner-container">
    <!-- 배너 슬라이드 -->
    <div class="banner-slide">
      <img src="<%= request.getContextPath() %>/img/banner1.png" alt="배너 1">
      <img src="<%= request.getContextPath() %>/img/banner2.png" alt="배너 2">
      <img src="<%= request.getContextPath() %>/img/banner3.png" alt="배너 3">
    </div>

    <!-- 화살표 버튼 -->
    <button class="arrow left" aria-label="이전 슬라이드">&#10094;</button>
    <button class="arrow right" aria-label="다음 슬라이드">&#10095;</button>

    <!-- 인디케이터 -->
    <div class="indicator">
      <div class="active" aria-label="슬라이드 1"></div>
      <div aria-label="슬라이드 2"></div>
      <div aria-label="슬라이드 3"></div>
    </div>
  </div>
  <!-- SCRIPT 불러옴 -->
  <script src="<%= request.getContextPath() %>/js/mainbanner.js"></script>
</body>
</html>