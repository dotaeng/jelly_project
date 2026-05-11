<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 검색어 표시 -->
<p><strong>${query}</strong> 검색 결과 (${searchResults.size()})</p>

<!-- 검색 결과 출력 -->
<c:if test="${not empty searchResults}">
   <c:forEach var="product" items="${searchResults}">
     <div class="product-card">
       <img src="${product.imageUrl}" alt="${product.productId}">
		<div class="product-info">
	       <div class="brand">${product.brand}</div>
	       <div class="product-name">${product.productName}</div>
		</div>
     </div>
   </c:forEach>
</c:if>

<!-- 검색 결과가 없을 때 -->
<c:if test="${empty searchResults}">
  <p>검색 결과가 없습니다.</p>
</c:if>