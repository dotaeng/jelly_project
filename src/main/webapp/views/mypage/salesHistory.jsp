<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/home/subHeader.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>판매내역 페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypageCommon.css" />
<style>
.page-container {
    display: flex;
    max-width: 1280px;
    margin: 0 auto;
    box-sizing: border-box;
}

.nav-bar {
    width: 240px;
    margin-right: 20px;
    padding-left: 15px;
}

.tabs-container {
    flex: 1;
    display: flex;
    flex-direction: column;
    width: 100%;
}

.tabs {
    display: flex;
    border-bottom: 1px solid #ccc;
    margin-bottom: 20px;
}

.tab {
    flex: 1;
    text-align: center;
    padding: 20px 0;
    font-size: 16px;
    color: #666;
    cursor: pointer;
    position: relative;
    transition: color 0.3s ease;
    display: block;
    width: 100%;
}

.tab.active {
    color: #333;
    font-weight: bold;
}


.tab.active::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 3px;
    background-color: #333;
}

.tab:not(.active)::after {
    content: '';
    display: none;
}

.purchase-table {
    width: 100%;
    border-collapse: collapse;
}

.purchase-table th, .purchase-table td {
    padding: 10px;
    text-align: left;
    border: none;
}

.product-image {
    width: 72px;
    height: 72px;
    margin-right: 15px;
    border-radius: 5px;
    object-fit: cover;
}

.product-info {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: flex-start;
}

.product-name {
    font-size: 13px;
    color: #222222;
    margin-bottom: 5px;
    position: relative;
}

.purchase-content {
    padding: 20px;
    font-size: 16px;
    color: #555;
}

.purchase-list {
    width: 940px;
    margin: 0 auto;
    padding: 0;
}

.purchase-list li {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    padding-bottom: 15px;
    position: relative; /
    width: 940px; 
    padding-left: 10px;
}

.product-price {
    position: absolute;
    right: 15px;
    font-size: 14px;
    color: #666;
}

/* 페이지네이션 */
.pagination {
    text-align: center;
    margin-top: 20px;
}

.pagination a {
    margin: 0 5px;
    text-decoration: none;
    color: #000000; 
}

.pagination a.active {
    font-weight: bold;
    text-decoration: underline;
    color: #000000; 
}

.pagination a.disabled {
    color: #ccc;
    pointer-events: none;
}

.purchase-list li .product-info {
    display: flex;
    flex-direction: column;
    justify-content: center;
    margin-left: 10px;
}

.purchase-list li .product-name {
    text-align: center;
}
</style>
</head>
<body>
<div class="page-container">
    <!-- 네비게이션 바 -->
    <div class="nav-bar">
        <%@ include file="/views/mypage/mypageNavi.jsp"%>
    </div>

    <!-- 탭과 컨텐츠 컨테이너 -->
    <div class="tabs-container">
        <!-- 탭 -->
        <div class="tabs">
            <div class="tab" id="tab-bid">판매 입찰</div>
            <div class="tab" id="tab-in-progress">진행 중</div>
            <div class="tab active" id="tab-completed">종료</div>
        </div>

        <!-- 판매내역 테이블 -->
        <div class="purchase-content">
            <c:choose>
                <c:when test="${empty salesList}">
                    <p>판매내역이 없습니다.</p>
                </c:when>
                <c:otherwise>
                    <ul class="purchase-list">
                        <c:forEach var="vo" items="${salesList}">
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

                    <!-- 페이지네이션 -->
                    <div class="pagination">
                        <c:choose>
                            <c:when test="${currentPage > 1}">
                                <a href="/haribo/jelly?page=salesHistory&currentPage=${currentPage - 1}">이전</a>
                            </c:when>
                            <c:otherwise>
                                <a class="disabled">이전</a>
                            </c:otherwise>
                        </c:choose>

                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <a href="/haribo/jelly?page=salesHistory&currentPage=${i}" class="${currentPage == i ? 'active' : ''}">
                                ${i}
                            </a>
                        </c:forEach>

                        <c:choose>
                            <c:when test="${currentPage < totalPages}">
                                <a href="/haribo/jelly?page=salesHistory&currentPage=${currentPage + 1}">다음</a>
                            </c:when>
                            <c:otherwise>
                                <a class="disabled">다음</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // 모든 탭을 가져옵니다
        const tabs = document.querySelectorAll('.tab');

        tabs.forEach(tab => {
            tab.addEventListener('click', function() {
                // 모든 탭에서 activ 제거
                tabs.forEach(t => t.classList.remove('active'));

                // 클릭한 탭에 active 추가
                this.classList.add('active');
            });
        });
    });
</script>

<%@ include file="/views/home/footer.jsp" %>
</body>
</html>