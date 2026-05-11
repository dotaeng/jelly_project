<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/home/header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Jelly</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/product.css">
  <!-- upbutton.css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/upbutton.css" />
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>

  	<!-- 맨 위로 이동 버튼 -->
	<button class="btn_top" id="btnTop" title="맨 위로 가기"></button>
  
  <div class="container">
    <!-- 카테고리 링크 -->
    <div class="categories">
      <a href="<%= request.getContextPath() %>/jelly?page=shoes" class="${currentCategory == '신발' ? 'active' : ''}">신발</a>
      <a href="<%= request.getContextPath() %>/jelly?page=outer" class="${currentCategory == '아우터' ? 'active' : ''}">아우터</a>
      <a href="<%= request.getContextPath() %>/jelly?page=tops" class="${currentCategory == '상의' ? 'active' : ''}">상의</a>
      <a href="<%= request.getContextPath() %>/jelly?page=bottoms" class="${currentCategory == '하의' ? 'active' : ''}">하의</a>
      <a href="<%= request.getContextPath() %>/jelly?page=bags" class="${currentCategory == '가방' ? 'active' : ''}">가방</a>
      <a href="<%= request.getContextPath() %>/jelly?page=hats" class="${currentCategory == '모자' ? 'active' : ''}">모자</a>
      <a href="<%= request.getContextPath() %>/jelly?page=wallets" class="${currentCategory == '지갑' ? 'active' : ''}">지갑</a>
      <a href="<%= request.getContextPath() %>/jelly?page=luxury" class="${currentCategory == '럭셔리' ? 'active' : ''}">럭셔리</a>
      <div class="filter-icon">
        <img src="<%= request.getContextPath() %>/img/filter.png" alt="Filter" id="filter-btn">
      </div>
    </div>

<!-- 상품 리스트 -->
<div class="products" id="product-container">
  <c:forEach var="product" items="${productList}">
    <a href="${pageContext.request.contextPath}/jelly?page=productDetail&productId=${product.productId}" class="product-card">
      <div class="image-wrapper">
        <img src="${product.imageUrl}" alt="${product.productName}">
      </div>
      <div class="brand">${product.brand}</div>
      <div class="product-name">${product.productName}</div>
      
      <!-- 구매 평균가 또는 발매가 표시 -->
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
</div>

    <!-- 로딩 중 -->
	<div id="loading">
  		<svg id="loading-svg" viewBox="25 25 50 50">
    		<circle r="20" cy="50" cx="50"></circle>
  		</svg>
	</div>

<!-- 필터 슬라이드 메뉴 -->
  <div class="filter-menu" id="filter-menu">
    <button class="close-btn" id="close-btn" aria-label="Close filter menu">&times;</button>
    <h2>필터</h2>
    <div class="filter-options">
      <!-- 브랜드 필터 -->
	<h3>브랜드</h3>
		<div class="brand-filters">
  			<label for="brand-nike">
    			<input type="checkbox" id="brand-nike" name="brand" value="NIKE"> NIKE
  			</label>
  			<label for="brand-adidas">
    			<input type="checkbox" id="brand-adidas" name="brand" value="ADIDAS"> ADIDAS
  			</label>
  			<label for="brand-celine">
  			  <input type="checkbox" id="brand-celine" name="brand" value="CELINE"> CELINE
 			 </label>
  			<label for="brand-dior">
  			  <input type="checkbox" id="brand-dior" name="brand" value="DIOR"> DIOR
  			</label>
  			<label for="brand-new-balance">
   			 <input type="checkbox" id="brand-new-balance" name="brand" value="NEW BALANCE"> NEW BALANCE
  			</label>
  			<label for="brand-ugg">
    <input type="checkbox" id="brand-ugg" name="brand" value="UGG"> UGG
  </label>
</div>

      <!-- 하위 카테고리 필터 -->
      <h3>하위 카테고리</h3>
      <div class="subcategory-filters">
        <label for="subcategory-sneakers">
          <input type="checkbox" id="subcategory-sneakers" name="subcategory" value="9"> 스니커즈
        </label>
        <label for="subcategory-slippers">
          <input type="checkbox" id="subcategory-slippers" name="subcategory" value="10"> 슬리퍼
        </label>
        <label for="subcategory-loafers">
          <input type="checkbox" id="subcategory-loafers" name="subcategory" value="11"> 로퍼
        </label>
        <label for="subcategory-boots">
          <input type="checkbox" id="subcategory-boots" name="subcategory" value="12"> 부츠
        </label>
        <label for="subcategory-others">
          <input type="checkbox" id="subcategory-others" name="subcategory" value="13"> 기타
        </label>
      </div>

      <!-- 가격 필터 -->
      <h3>가격</h3>
      <div class="price-filters">
        <label for="price-50000">
          <input type="radio" id="price-50000" name="price" value="50000"> 5만원 이하
        </label>
        <label for="price-100000">
          <input type="radio" id="price-100000" name="price" value="100000"> 5~10만원
        </label>
        <label for="price-200000">
          <input type="radio" id="price-200000" name="price" value="200000"> 10~20만원
        </label>
        <label for="price-300000">
          <input type="radio" id="price-300000" name="price" value="300000"> 20만원 이상
        </label>
      </div>
    </div>
    <!-- 필터 버튼 -->
    <button class="apply-btn" id="apply-filter">적용</button>
    <button class="reset-btn" id="reset-filter">초기화</button>
  </div>

  <!-- 무한 스크롤 AJAX -->
<script>
$(document).ready(function () {
    let currentPage = 1;
    const $productContainer = $("#product-container");
    const $loading = $("#loading");
    let isLoading = false;

    $(window).on("scroll", function () {
        if (!isLoading && $(window).scrollTop() + $(window).height() >= $(document).height() - 100) {
            loadMoreProducts();
        }
    });

    function loadMoreProducts() {
        isLoading = true;
        currentPage++;
        $loading.fadeIn();

        $.ajax({
            url: "<%= request.getContextPath() %>/jelly",
            type: "GET",
            data: { page: "shoes", pageNo: currentPage },
            success: function (data) {
                if ($.trim(data) === "") {
                } else {
                    setTimeout(function () {
                        $productContainer.append(data); // 상품 리스트 HTML 추가
                    }, 700); // 0.7초 로딩
                }
            },
            error: function (xhr, status, error) {
            },
            complete: function () {
                setTimeout(function () {
                    $loading.fadeOut();
                }, 1000); // 1초 후 로딩 숨김
                isLoading = false;
            }
        });
    }
});
</script>

  <script src="<%= request.getContextPath() %>/js/upbutton.js"></script>
  <script src="<%= request.getContextPath() %>/js/filter.js"></script>
  <%@ include file="/views/home/footer.jsp" %>
</body>
</html>