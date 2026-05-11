<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/home/subHeader.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>공지사항</title>
    <style>
        .notice-page {
            display: flex;
            justify-content: space-between;
            gap: 20px;
            width: 1250px;
            margin: 0 auto;
            padding: 20px;
            box-sizing: border-box;
        }

        .notice-container {
            width: 950px;
            font-family: Arial, sans-serif;
            padding: 20px;
            box-sizing: border-box;
        }

        .notice-header {
            display: flex;
            flex-direction: column;
            gap: 10px;
            margin-bottom: 20px;
        }

        .notice-header h1 {
            font-size: 24px;
            font-weight: bold;
            margin: 0;
            color: #333;
        }

        .notice-header .header-line {
            width: 100%;
            height: 2px;
            background-color: #333;
        }

        .notice-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .notice-item {
            border-bottom: 1px solid #ddd;
            padding: 15px 0;
        }

        .notice-item:last-child {
            border-bottom: none;
        }

        .notice-item h3 {
            margin: 0;
            font-size: 15px; 
            font-weight: normal;
            color: #222222;
        }

        .notice-item h3 a {
            text-decoration: none;
        }

        .notice-item h3 a:hover {
            text-decoration: underline;
        }

        .notice-item h3 .notice-label {
            font-weight: bold;
            color: black; 
            font-size: 14px;
            margin-right: 5px;
        }

        .pagination-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
        }

        /* 페이지네이션 */
        .pagination {
            text-align: center;
            padding-left: 400px;
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

        .add-btn {
            background-color: #222;
            color: #fff;
            border: none;
            padding: 10px 15px;
            font-size: 14px;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s ease;
            margin-left: auto;
        }

        .add-btn:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
    <div class="notice-page">
        <!-- 네비게이션 바 -->
        <div class="notice-nav">
            <%@ include file="/views/notice/noticeNav.jsp" %>
        </div>

        <!-- 공지사항 컨텐츠 -->
        <div class="notice-container">
            <div class="notice-header">
                <h1>공지사항</h1>
                <div class="header-line"></div>
            </div>

            <!-- 공지사항 리스트 -->
            <ul class="notice-list">
                <c:forEach var="notice" items="${noticeList}">
                    <li class="notice-item">
                        <h3>
                            <span class="notice-label">공지</span>
                            <a href="${pageContext.request.contextPath}/jelly?page=noticeDetail&noticeId=${notice.noticeId}">
                               ${notice.title}
                            </a>
                        </h3>
                    </li>
                </c:forEach>
            </ul>

            <div class="pagination-container">
                <!-- 페이지네이션 -->
                <div class="pagination">
                    <c:if test="${currentPage > 1}">
                        <a href="${pageContext.request.contextPath}/jelly?page=notice&currentPage=${currentPage - 1}">이전</a>
                    </c:if>
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <a href="${pageContext.request.contextPath}/jelly?page=notice&currentPage=${i}" 
                           class="${i == currentPage ? 'active' : ''}">${i}</a>
                    </c:forEach>
                    <c:if test="${currentPage < totalPages}">
                        <a href="${pageContext.request.contextPath}/jelly?page=notice&currentPage=${currentPage + 1}">다음</a>
                    </c:if>
                </div>

                <!-- 글쓰기 버튼 -->
                <c:if test="${isAdmin}">
                    <button class="add-btn" onclick="location.href='${pageContext.request.contextPath}/jelly?page=noticeWrite'">글쓰기</button>
                </c:if>
            </div>
        </div>
    </div>
    <%@ include file="/views/home/footer.jsp" %>
</body>
</html>