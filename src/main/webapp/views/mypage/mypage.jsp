<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/home/subHeader.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mypage.css" />
    <title>마이페이지</title>
    <style>
        .mypage-snb {
            max-width: 200px;
            width: 90%;
            height: 600px;
            padding-left: 35px !important;
        }
    </style>
</head>
<body>
    <div class="mypage-container">
        <%@ include file="/views/mypage/mypageNavi.jsp" %>
        
        <!-- mypage content -->
        <div class="mypage-content">
            
            <!-- 프로필 정보 (mypage-box1) -->
            <div class="mypage-box1">
				<div>
					<c:choose>
						<c:when test="${not empty uVo.profileImage}">
							<img src="${uVo.profileImage}" alt="profile"
								class="profile-image" />
						</c:when>
						<c:otherwise>
							<img src="${pageContext.request.contextPath}/img/profile.png"
								alt="profile" class="profile-image" />
						</c:otherwise>
					</c:choose>
				</div>
				<div class="profile-info">
                    <span class="profile-user-nickname">${uVo.userName}</span><br />
                    <span class="profile-user-email">${uVo.email}</span>
                </div>
                <div class="profile-btns">
                    <!-- 프로필 관리 버튼 -->
                    <input type="button" value="프로필 관리" class="profile-info-btn" id="btn1" onclick="location.href='/haribo/jelly?page=viewProfileInfo'" />
                    <!-- 내 스타일 버튼 -->
                    <input type="button" value="내스타일" class="profile-info-btn" id="btn2" onclick="location.href='/haribo/jelly?page=styleProfile&userId=${uVo.userId}'" />
                </div>
            </div>

            <!-- 최근 구매내역 (mypage-box2) -->
            <div class="mypage-box2">
                <h3>최근 구매내역</h3>
                <c:choose>
                    <c:when test="${empty recentPurchases}">
                        <p>구매내역이 없습니다.</p>
                    </c:when>
                    <c:otherwise>
                        <ul class="purchase-list">
                            <c:forEach var="vo" items="${recentPurchases}">
                                <li>
                                    <a href="/haribo/jelly?page=purchaseHistoryDetail&trade_id=${vo.tradeId}">
                                        <img class="product-image" src="${vo.imageUrl}" alt="상품이미지">
                                    </a>
                                    <div class="product-info">
                                        <p class="product-name">
                                            <a href="/haribo/jelly?page=purchaseHistoryDetail&trade_id=${vo.tradeId}">
                                                ${vo.productName}
                                            </a>
                                        </p>
                                        <p class="product-price">${vo.purchasePrice}원</p>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- 최근 판매내역 (mypage-box3) -->
            <div class="mypage-box3">
                <h3>최근 판매내역</h3>
                <c:choose>
                    <c:when test="${empty recentSales}">
                        <p>판매내역이 없습니다.</p>
                    </c:when>
                    <c:otherwise>
                        <ul class="purchase-list">
                            <c:forEach var="vo" items="${recentSales}">
                                <li>
                                    <a href="/haribo/jelly?page=salesHistoryDetail&trade_id=${vo.tradeId}">
                                        <img class="product-image" src="${vo.imageUrl}" alt="상품이미지">
                                    </a>
                                    <div class="product-info">
                                        <p class="product-name">
                                            <a href="/haribo/jelly?page=salesHistoryDetail&trade_id=${vo.tradeId}">
                                                ${vo.productName}
                                            </a>
                                        </p>
                                        <p class="product-price">${vo.salePrice}원</p>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </div>

        </div>
    </div>
    <%@ include file="/views/home/footer.jsp" %>
</body>
</html>