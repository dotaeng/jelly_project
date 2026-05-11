<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mainshop.css">
  <title>Shop Categories</title>
</head>
<body class="main-shop-page">
  <div class="shop-container">
    <div class="shop-header">
      SHOP
      <a href="<%= request.getContextPath() %>/jelly?page=shoes">
        <button>&gt;</button>
      </a>
    </div>
    <div class="shop-grid">
      <!-- 첫 번째 행 -->
      <div class="shop-item">
        <a href="<%= request.getContextPath() %>/jelly?page=shoes">
          <img src="<%= request.getContextPath() %>/img/shoeCategory.png" alt="신발">
        </a>
        <p>신발</p>
      </div>
      <div class="shop-item">
        <a href="<%= request.getContextPath() %>/jelly?page=outer">
          <img src="<%= request.getContextPath() %>/img/outerCategory.png" alt="아우터">
        </a>
        <p>아우터</p>
      </div>
      <div class="shop-item">
        <a href="<%= request.getContextPath() %>/jelly?page=tops">
          <img src="<%= request.getContextPath() %>/img/topCategory.png" alt="상의">
        </a>
        <p>상의</p>
      </div>
      <div class="shop-item">
        <a href="<%= request.getContextPath() %>/jelly?page=bottoms">
          <img src="<%= request.getContextPath() %>/img/bottomCategory.png" alt="하의">
        </a>
        <p>하의</p>
      </div>

      <!-- 두 번째 행 -->
      <div class="shop-item">
        <a href="<%= request.getContextPath() %>/jelly?page=bags">
          <img src="<%= request.getContextPath() %>/img/bagCategory.png" alt="가방">
        </a>
        <p>가방</p>
      </div>
      <div class="shop-item">
        <a href="<%= request.getContextPath() %>/jelly?page=hats">
          <img src="<%= request.getContextPath() %>/img/hatCategory.png" alt="모자">
        </a>
        <p>모자</p>
      </div>
      <div class="shop-item">
        <a href="<%= request.getContextPath() %>/jelly?page=wallets">
          <img src="<%= request.getContextPath() %>/img/walletCategory.png" alt="지갑">
        </a>
        <p>지갑</p>
      </div>
      <div class="shop-item">
        <a href="<%= request.getContextPath() %>/jelly?page=luxury">
          <img src="<%= request.getContextPath() %>/img/luxuryCategory.png" alt="럭셔리">
        </a>
        <p>럭셔리</p>
      </div>
    </div>
  </div>
</body>
</html>