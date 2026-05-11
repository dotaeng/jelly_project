<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/home/subHeader.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>관심상품 목록</title>
<style>
    .page-container {
        display: flex;
        max-width: 1250px;
        margin: 0 auto;
        box-sizing: border-box;
    }

    .mypage-snb {
        width: 200px;
        margin-right: 20px;
    }

    .wishlist-page {
        font-family: Arial, sans-serif;
        line-height: 1.6;
        flex: 1;
        padding-top: 24px;
    }

    .wishlist-header {
        text-align: left;
        font-size: 24px;
        font-weight: bold;
        padding: 10px 0;
        border-bottom: 2px solid #333;
        max-width: 800px; 
        margin-left: 31px;
    }

    .wishlist-container {
        max-width: 800px;
        min-height: calc(5 * 120px);
        box-sizing: border-box;
        position: relative;
        border-radius: 5px;
        margin-left: 31px;
    }

    .wishlist-item {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 10px 0;
        position: relative;
    }

    .wishlist-item:not(:last-child) {
        border-bottom: 1px solid #ddd; 
    }

    .wishlist-item img {
        width: 80px;
        height: 80px;
        object-fit: cover;
        border-radius: 5px;
        margin-right: 15px;
    }

    .wishlist-item .details {
        flex: 1;
    }

    .wishlist-item .details h3 {
        margin: 0;
        font-size: 13px;
        font-weight: bold;
    }

    .wishlist-item .details p {
        margin: 5px 0;
        color: #666;
        font-size: 13px; 
        font-weight: normal;
    }

    .wishlist-item .actions {
        display: flex;
        flex-direction: column;
        align-items: center;
        position: relative;
    }

    .wishlist-item .actions .buy-button {
        display: flex;
        align-items: center;
        padding: 10px 15px;
        background-color: #FF7F50;
        color: white;
        border: none;
        border-radius: 10px;
        font-size: 14px;
        cursor: pointer;
        width: 164px;
        height: 52px;
        text-align: left;
        position: relative;
    }

    .wishlist-item .actions .buy-button:hover {
        background-color: #FFA07A;
    }

    .wishlist-item .actions .buy-button .title {
        width: 50px;
        font-size: 14px;
        font-weight: bold;
        margin-right: 5px;
        text-align: left;
    }

    .wishlist-item .actions .buy-button .price {
        text-align: left;
        display: flex;
        flex-direction: column;
        gap: 0;
        margin-bottom: 13px;
        line-height: 1;
    }

    .wishlist-item .actions .buy-button .price .text-lookup.num {
        font-weight: bold;
        color: #fff;
        font-size: 14px;
        margin-bottom: 0; 
    }

    .wishlist-item .actions .buy-button .price .text-lookup.desc {
        font-size: 11px;
        color: #ddd;
        margin: 1px; 
    }

    .wishlist-item .actions .delete-button {
        padding: 2px 5px;
        font-size: 12px;
        background: none;
        border: none;
        color: #666;
        cursor: pointer;
        text-decoration: underline;
        position: absolute;
        right: 6px;
        top: 50%;
        margin-top: 38px;
        transform: translateY(-50%);
    }

    .wishlist-item .actions .delete-button:hover {
        color: #333;
    }

    /* 페이지네이션 */
    .pagination {
        position: absolute;
        bottom: 20px;
        left: 50%;
        transform: translateX(-50%);
        text-align: center;
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
</style>
</head>
<body>
<div class="page-container">
    <%@ include file="/views/mypage/mypageNavi.jsp" %>

    <div class="wishlist-page">
        <div class="wishlist-header">관심상품</div>

        <div class="wishlist-container">
            <c:choose>
                <c:when test="${not empty wishlist}">
                    <c:forEach var="item" items="${wishlist}">
                        <div class="wishlist-item">
                            <img src="${item.productImage}" alt="상품 이미지">
                            <div class="details">
                                <h3>${item.productName}</h3>
                                <p>${item.productDescription}</p>
                            </div>
                            <div class="actions">
                                <button class="buy-button" onclick="goToDetail(${item.productId})">
                                    <span class="title">구매</span>
                                    <div class="price">
                                        <p class="text-lookup num">
                                            <c:choose>
                                                <c:when test="${item.lowestPrice > 0}">
                                                    ${item.lowestPrice}원
                                                </c:when>
                                                <c:otherwise>
                                                    ${item.releasePrice}원
                                                </c:otherwise>
                                            </c:choose>
                                        </p>
                                        <p class="text-lookup desc">즉시 구매가</p>
                                    </div>
                                </button>
                                <button class="delete-button" onclick="removeItem(${item.productId})">삭제</button>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p>관심상품이 없습니다.</p>
                </c:otherwise>
            </c:choose>

            <!-- 페이지네이션 -->
            <div class="pagination">
                <c:choose>
                    <c:when test="${currentPage > 1}">
                        <a href="/haribo/jelly?page=wish&currentPage=${currentPage - 1}">이전</a>
                    </c:when>
                    <c:otherwise>
                        <a class="disabled">이전</a>
                    </c:otherwise>
                </c:choose>

                <c:forEach var="i" begin="1" end="${totalPages}">
                    <a href="/haribo/jelly?page=wish&currentPage=${i}" class="${currentPage == i ? 'active' : ''}">
                        ${i}
                    </a>
                </c:forEach>

                <c:choose>
                    <c:when test="${currentPage < totalPages}">
                        <a href="/haribo/jelly?page=wish&currentPage=${currentPage + 1}">다음</a>
                    </c:when>
                    <c:otherwise>
                        <a class="disabled">다음</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
<%@ include file="/views/home/footer.jsp" %>
<script>
    function removeItem(productId) {
        if (confirm("이 상품을 삭제하시겠습니까?")) {
            location.href = "${pageContext.request.contextPath}/jelly?page=wish&deleteProductId=" + productId;
        }
    }

    function goToDetail(productId) {
        location.href = "${pageContext.request.contextPath}/jelly?page=productDetail&productId=" + productId;
    }
</script>
</body>
</html>