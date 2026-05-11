<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mainbrand.css">
  <title>Main Brand</title>
</head>
<body>
  <!-- Main Brand Header -->
  <div class="main-brand-header">
    <h2>BRAND</h2>
    <a href="<%= request.getContextPath() %>/jelly?page=brand">&gt;</a>
  </div>

  <!-- Main Brand 컨테이너 -->
  <div class="main-brand-container">
    <!-- 첫 번째 브랜드 카드 -->
    <div class="main-brand-card">
      <a href="https://www.nike.com" target="_blank">
        <img src="<%= request.getContextPath() %>/img/brandnike.png" alt="Nike Logo">
      </a>
      <div class="main-brand-name">
        <a href="https://www.nike.com" target="_blank">NIKE</a>
      </div>
      <div class="main-brand-actions">
        <button class="main-brand-button" onclick="toggleInterest(this)">관심 저장</button>
      </div>
    </div>

    <!-- 두 번째 브랜드 카드 -->
    <div class="main-brand-card">
      <a href="https://www.adidas.com" target="_blank">
        <img src="<%= request.getContextPath() %>/img/brandadidas.png" alt="Adidas Logo">
      </a>
      <div class="main-brand-name">
        <a href="https://www.adidas.com" target="_blank">ADIDAS</a>
      </div>
      <div class="main-brand-actions">
        <button class="main-brand-button" onclick="toggleInterest(this)">관심 저장</button>
      </div>
    </div>

    <!-- 세 번째 브랜드 카드 -->
    <div class="main-brand-card">
      <a href="https://www.asics.com" target="_blank">
        <img src="<%= request.getContextPath() %>/img/brandasics.png" alt="Asics Logo">
      </a>
      <div class="main-brand-name">
        <a href="https://www.asics.com" target="_blank">ASICS</a>
      </div>
      <div class="main-brand-actions">
        <button class="main-brand-button" onclick="toggleInterest(this)">관심 저장</button>
      </div>
    </div>

    <!-- 네 번째 브랜드 카드 -->
    <div class="main-brand-card">
      <a href="https://www.newbalance.com" target="_blank">
        <img src="<%= request.getContextPath() %>/img/brandnewbalance.png" alt="New Balance Logo">
      </a>
      <div class="main-brand-name">
        <a href="https://www.newbalance.com" target="_blank">NEW BALANCE</a>
      </div>
      <div class="main-brand-actions">
        <button class="main-brand-button" onclick="toggleInterest(this)">관심 저장</button>
      </div>
    </div>

    <!-- 다섯 번째 브랜드 카드 -->
    <div class="main-brand-card">
      <a href="https://www.puma.com" target="_blank">
        <img src="<%= request.getContextPath() %>/img/brandpuma.png" alt="Puma Logo">
      </a>
      <div class="main-brand-name">
        <a href="https://www.puma.com" target="_blank">PUMA</a>
      </div>
      <div class="main-brand-actions">
        <button class="main-brand-button" onclick="toggleInterest(this)">관심 저장</button>
      </div>
    </div>
  </div>
  <script src="<%= request.getContextPath() %>/js/mainbrand.js"></script>
</body>
</html>