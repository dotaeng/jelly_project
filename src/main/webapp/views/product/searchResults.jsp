<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/views/home/header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>검색 결과</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/product.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <style>
  body {
  	width: 1280;
  }
  </style>
</head>
<body>
  <div class="container">
    <!-- 검색어 표시 -->
    <h1>검색 결과</h1>
    <p>검색어: <strong>${query}</strong></p>

    <!-- 검색 결과 출력 -->
    <c:if test="${not empty searchResults}">
      <div class="products" id="product-container">
        <c:forEach var="product" items="${searchResults}">
          <a href="${pageContext.request.contextPath}/jelly?page=productDetail&productId=${product.productId}" class="product-card">
            <div class="image-wrapper">
              <img src="${product.imageUrl}" alt="${product.productName}">
            </div>
            <div class="brand">${product.brand}</div>
            <div class="product-name">${product.productName}</div>
            <div class="price">${product.initialPrice}원</div>
          </a>
        </c:forEach>
      </div>
    </c:if>

    <!-- 검색 결과가 없을 때 -->
    <c:if test="${empty searchResults}">
      <p>검색 결과가 없습니다.</p>
    </c:if>

    <!-- 홈으로 돌아가기 -->
    <a href="<%= request.getContextPath() %>/jelly">홈으로 돌아가기</a>
  </div>

  <%@ include file="/views/home/footer.jsp" %>
</body>
</html>