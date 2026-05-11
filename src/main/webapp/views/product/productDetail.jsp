<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/home/subHeader.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>상품 상세 정보</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/productDetail.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/productStickey.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/productModal.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/upbutton.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>

<body>
  <c:set var="loggedIn" value="${not empty user}" />
  <script>
    var loggedIn = ${loggedIn ? 'true' : 'false'};
    var contextPath = "${pageContext.request.contextPath}";
  </script>

  <%@ include file="/views/product/productStickeybar.jsp" %>
  <script src="<%= request.getContextPath() %>/js/productStickey.js"></script>
  <button class="btn_top" id="btnTop" title="맨 위로 가기"></button>

  <div class="content-wrapper">
    <div class="product-detail-container">
      <!-- 상품 이미지 -->
      <div class="product-image-section">
        <div class="product-image">
          <img src="${product.imageUrl}" alt="${product.productName}">
        </div>
      </div>

      <!-- 상품 정보 -->
      <div class="product-info-section">
        <div class="instant-buy">
          <p class="instant-buy-label">즉시 구매가</p>
          <p class="instant-buy-price">
            <c:choose>
              <c:when test="${averagePurchasePrice > 0}">
                ${averagePurchasePrice}원
              </c:when>
              <c:otherwise>
                가격 정보 없음
              </c:otherwise>
            </c:choose>
          </p>
        </div>

        <div class="product-name">${product.productName}</div>
        <div class="product-description">${product.description}</div>
        
        <!-- 사이즈 버튼 -->
        <div class="size-selector">
          <button class="size-dropdown" id="size-dropdown">
            <span>모든 사이즈</span>
            <svg width="16" height="16" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
              <path d="M7 10l5 5 5-5H7z" fill="currentColor" />
            </svg>
          </button>
        </div>
        
        <!-- 상품 세부 정보 -->
        <dl class="product-details">
          <div class="detail">
            <div class="text">모델번호</div>
            <span>${formattedModelNumber}</span>
          </div>
          <div class="detail">
            <div class="text">출시일</div>
            <span>${product.releaseDate}</span>
          </div>
          <div class="detail">
            <div class="text">발매가</div>
            <span>${initialPrice}원</span>
          </div>
        </dl>

        <!-- 구매 버튼 -->
        <div class="button-group">
          <button class="buy-button" id="buy-btn">
            <div class="buy-text">구매</div>
            <div class="buy-info">
              <span class="price">
                <c:choose>
                  <c:when test="${averagePurchasePrice > 0}">
                    ${averagePurchasePrice}원
                  </c:when>
                  <c:otherwise>
                    ${initialPrice}원
                  </c:otherwise>
                </c:choose>
              </span>
              <span class="subtext">즉시 구매가</span>
            </div>
          </button>
          
          <!-- 판매 버튼 -->
          <button class="sell-button" id="sell-btn">
            <div class="sell-text">판매</div>
            <div class="sell-info">
              <span class="price">
                <c:choose>
                  <c:when test="${averageSellPrice > 0}">
                    ${averageSellPrice}원
                  </c:when>
                  <c:otherwise>
                    ${initialPrice}원
                  </c:otherwise>
                </c:choose>
              </span>
              <span class="subtext">즉시 판매가</span>
            </div>
          </button>
        </div>

        <!-- 관심상품 버튼 -->
        <div class="additional-button">
          <c:choose>
            <c:when test="${not empty user}">
              <!-- 로그인 상태 -->
              <button id="wishlist-button" 
                      data-context-path="<%= request.getContextPath() %>" 
                      data-product-id="${product.productId}" 
                      data-user-id="${user.userId}" 
                      class="btn btn_wish outlinegrey large">
                <div class="wishlist-icon">
                  <img src="<%= request.getContextPath() %>/img/bookmark-icon.png" alt="Bookmark">
                </div>
                <span id="wishlist-text" class="wishlist-text">관심상품</span>
              </button>
            </c:when>
            <c:otherwise>
              <!-- 비로그인 상태 -->
              <button id="wishlist-button-nologin" class="btn btn_wish outlinegrey large">
                <div class="wishlist-icon">
                  <img src="<%= request.getContextPath() %>/img/bookmark-icon.png" alt="Bookmark">
                </div>
                <span class="wishlist-text">관심등록</span>
              </button>
            </c:otherwise>
          </c:choose>
        </div>
        
		<!-- 배송 정보 -->
        <div class="delivery_way_wrap">
          <h3 class="delivery_title">배송 정보</h3>
          <ul class="delivery_options">
            <li class="delivery_option">
              <div class="delivery_icon">
                <img src="<%= request.getContextPath() %>/img/deliveryicon.png" alt="일반배송">
              </div>
              <div class="delivery_details">
                <p class="delivery_type">일반배송 <span class="delivery_price">3,000원</span></p>
                <p class="delivery_desc">검수 후 배송 · 5-7일 내 도착 예상</p>
              </div>
            </li>
            <li class="delivery_option">
              <div class="delivery_icon">
                <img src="<%= request.getContextPath() %>/img/storageicon.png" alt="창고보관">
              </div>
              <div class="delivery_details">
                <p class="delivery_type">창고보관 <span class="delivery_price">첫 30일 무료</span></p>
                <p class="delivery_desc">배송 없이 창고에 보관 · 빠르게 판매 가능</p>
              </div>
            </li>
          </ul>
        </div>

        <!-- 정품 보증 -->
        <div class="authenticity-info-container">
          <ul class="authenticity-list">
            <li class="authenticity-item">
              <div class="authenticity-icon">
                <img src="<%= request.getContextPath() %>/img/x3.png" alt="정품 보증">
              </div>
              <div class="authenticity-details">
                <p class="authenticity-title">100% 정품 보증</p>
                <p class="authenticity-desc">Jelly에서 검수한 상품이 정품이 아닐 경우, 구매가의 3배를 보상함</p>
              </div>
            </li>
            <li class="authenticity-item">
              <div class="authenticity-icon">
                <img src="<%= request.getContextPath() %>/img/check.png" alt="다중 검수">
              </div>
              <div class="authenticity-details">
                <p class="authenticity-title">엄격한 다중 검수</p>
                <p class="authenticity-desc">모든 상품은 검수센터 도착 후, 전문가 그룹의 시스템을 거쳐 검수함</p>
              </div>
            </li>
            <li class="authenticity-item">
              <div class="authenticity-icon">
                <img src="<%= request.getContextPath() %>/img/check2.png" alt="정품 인증">
              </div>
              <div class="authenticity-details">
                <p class="authenticity-title">정품 인증 패키지</p>
                <p class="authenticity-desc">검수 합격 시 Jelly 정품 인증 패키지 포함 상품 배송</p>
              </div>
            </li>
          </ul>
        </div>

        <!-- 사이즈 정보  -->
        <div class="size-info-container">
          <h3 class="size-title">사이즈 정보</h3>
          <table class="size-table">
            <thead>
              <tr>
                <th>KR</th>
                <th>US (M)</th>
                <th>US (W)</th>
                <th>UK</th>
                <th>JP</th>
                <th>EU</th>
              </tr>
            </thead>
            <tbody>
              <tr><td>210</td><td>3.5</td><td>5</td><td>3</td><td>22.5</td><td>35.5</td></tr>
              <tr><td>220</td><td>4</td><td>5.5</td><td>3.5</td><td>23</td><td>36</td></tr>
              <tr><td>230</td><td>4.5</td><td>6</td><td>4</td><td>23.5</td><td>36.5</td></tr>
              <tr><td>240</td><td>5</td><td>6.5</td><td>4.5</td><td>24</td><td>37.5</td></tr>
              <tr><td>250</td><td>5.5</td><td>7</td><td>5</td><td>24.5</td><td>38</td></tr>
              <tr><td>260</td><td>6</td><td>7.5</td><td>5.5</td><td>25</td><td>39</td></tr>
              <tr><td>270</td><td>6.5</td><td>8</td><td>6</td><td>25.5</td><td>40</td></tr>
              <tr><td>280</td><td>7</td><td>8.5</td><td>6.5</td><td>26</td><td>41</td></tr>
              <tr><td>290</td><td>8</td><td>9.5</td><td>7.5</td><td>27</td><td>42.5</td></tr>
            </tbody>
          </table>
          <p class="size-note">
            ※ 해당 사이즈 정보는 참고용이며, 브랜드·상품마다 차이가 있을 수 있음  
            정확한 내용은 실물 상품 기준으로 공식 제조사/유통사에 확인 요망
          </p>
          <br />
          <br />
        </div>
      </div>
    </div>
  </div>

  <!-- 모달 -->
  <div class="modal-overlay" id="size-modal">
    <div class="modal-content">
      <button class="modal-close" id="modal-close">&times;</button>
      <h3 class="modal-title">사이즈</h3>
      <div class="size-grid">
        <c:forEach var="size" items="${sizeList}">
          <div class="size-item">
            <c:choose>
              <c:when test="${buySizeButtons[size] == '구매 입찰'}">
                <a href="${pageContext.request.contextPath}/jelly?page=buyBid&productId=${product.productId}&size=${size}" class="size-button">
                  <span>${size}</span>
                  <span class="price bid-label">구매 입찰</span>
                </a>
              </c:when>
              <c:otherwise>
                <a href="${pageContext.request.contextPath}/jelly?page=buy&productId=${product.productId}&size=${size}&price=${buySizeButtons[size]}" class="size-button">
                  <span>${size}</span>
                  <span class="price">${buySizeButtons[size]}</span>
                </a>
              </c:otherwise>
            </c:choose>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>
    <!-- 구매 모달 -->
  <div class="modal-overlay" id="buy-modal">
    <div class="modal-content">
      <button class="modal-close" id="buy-modal-close">&times;</button>
      <h3 class="modal-title">구매 사이즈 선택</h3>
      <div class="size-grid">
        <c:forEach var="size" items="${sizeList}">
          <div class="size-item">
            <c:choose>
              <c:when test="${buySizeButtons[size] == '구매 입찰'}">
                <a href="${pageContext.request.contextPath}/jelly?page=buyBid&productId=${product.productId}&size=${size}" class="size-button">
                  <span class="size-label">${size}</span>
                  <span class="price bid-label">구매 입찰</span>
                </a>
              </c:when>
              <c:otherwise>
                <a href="${pageContext.request.contextPath}/jelly?page=buy&productId=${product.productId}&size=${size}&price=${buySizeButtons[size]}" class="size-button">
                  <span class="size-label">${size}</span>
                  <span class="price">${buySizeButtons[size]}</span>
                </a>
              </c:otherwise>
            </c:choose>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>

  <!-- 판매 모달 -->
  <div class="modal-overlay" id="sell-modal">
    <div class="modal-content">
      <button class="modal-close" id="sell-modal-close">&times;</button>
      <h3 class="modal-title">판매 사이즈 선택</h3>
      <div class="size-grid">
        <c:forEach var="sizeVal" items="${sizeList}">
          <div class="size-item">
            <c:choose>
              <c:when test="${sellSizeButtons[sizeVal] == '판매 입찰'}">
                <a href="${pageContext.request.contextPath}/jelly?page=sellBid&productId=${product.productId}&size=${sizeVal}" class="size-button">
                  <span>${sizeVal}</span>
                  <span class="price bid-label">판매 입찰</span>
                </a>
              </c:when>
              <c:otherwise>
                <a href="${pageContext.request.contextPath}/jelly?page=sell&productId=${product.productId}&size=${sizeVal}&price=${sellSizeButtons[sizeVal]}" class="size-button">
                  <span>${sizeVal}</span>
                  <span class="price">${sellSizeButtons[sizeVal]}</span>
                </a>
              </c:otherwise>
            </c:choose>
          </div>
        </c:forEach>
      </div>
    </div>
  </div>

  <script src="<%= request.getContextPath() %>/js/upbutton.js"></script>
  <script src="<%= request.getContextPath() %>/js/productModal.js"></script>
  <script src="<%= request.getContextPath() %>/js/productWishButton.js"></script>

  <div class="footer-section-container">
    <%@ include file="/views/home/footer.jsp" %>
  </div>
</body>
</html>