<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 여기가 상품 무한스크롤 ajax 요청 받아서 상품 리스트 보여주는곳임 -->
<!-- 상품 리스트 추가 렌더링 -->
<c:forEach var="product" items="${productList}">
  <a href="${pageContext.request.contextPath}/jelly?page=productDetail&productId=${product.productId}" class="product-card">
    <div class="image-wrapper">
      <img src="${product.imageUrl}" alt="${product.productName}">
    </div>
    <div class="brand">${product.brand}</div>
    <div class="product-name">${product.productName}</div>
    
    <!-- 가격 처리: 평균가가 0일 경우 발매가 표시 -->
    <div class="price">
      <c:choose>
        <c:when test="${averagePurchasePriceMap[product.productId] == 0}">
          ${product.initialPrice}원  <!-- 평균가가 0이면 발매가 표시 -->
        </c:when>
        <c:otherwise>
          ${averagePurchasePriceMap[product.productId]}원  <!-- 평균가 표시 -->
        </c:otherwise>
      </c:choose>
    </div>
  </a>
</c:forEach>