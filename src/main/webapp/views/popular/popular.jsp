<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/home/header.jsp" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>인기 상품</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css">
  <style>
    .popular-image-container {
      position: relative; 
    }

    .popular-sales-badge {
      position: absolute;
      top: 10px; 
      right: 10px;
      background-color: rgba(0, 0, 0, 0.6);
      color: #ffffff; 
      font-size: 14px; 
      padding: 5px 10px;
      border-radius: 8px; 
      font-weight: bold; 
      z-index: 10; 
    }

    .product-card img {
      width: 100%; 
      border-radius: 10px; 
    }
  </style>
</head>
<body>
  <div class="container">
    <h2>인기 상품</h2>

    <!-- 인기 상품 리스트 -->
    <div class="products" id="popular-product-list">
      <c:forEach var="product" items="${popularProducts}">
        <div class="product-card">
          <a href="${pageContext.request.contextPath}/jelly?page=productDetail&productId=${product.productId}">
            <div class="popular-image-container">
              <!-- 이미지 -->
              <img src="${product.imageUrl}" alt="${product.productName}">
              <!-- 판매량 뱃지 -->
              <div class="popular-sales-badge">판매량 ${product.totalSales}개</div>
            </div>
            <!-- 상품 정보 -->
            <div class="brand">${product.brand}</div>
            <div class="product-name">${product.productName}</div>
            <div class="price">${product.initialPrice}원</div>
          </a>
        </div>
      </c:forEach>
    </div>

    <!-- 인기 상품이 없을 때 -->
    <c:if test="${empty popularProducts}">
      <p>현재 인기 상품이 없습니다.</p>
    </c:if>
  </div>

  <%@ include file="/views/home/footer.jsp" %>
</body>
</html>