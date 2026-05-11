<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/home/subHeader.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>공지사항 상세보기</title>
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

        .notice-nav {
            padding-right: 115px;
        }

        .notice-detail-container {
            flex: 3;
            font-family: Arial, sans-serif;
            background: #fff;
            border-radius: 8px;
            padding: 20px;
        }

        .notice-header {
            font-size: 24px;
            font-weight: bold;
            color: #222;
            border-bottom: 2px solid #222;
            padding-bottom: 10px;
            margin-bottom: 20px;
        }

        .notice-meta {
            display: flex;
            align-items: flex-start;
            margin-bottom: 20px;
        }

        .notice-label {
            font-weight: bold;
            font-size: 14px;
            color: #222;
            margin-top: 9px;
            margin-right: 25px;
        }

        .notice-info {
            flex: 1;
        }

        .notice-info div:first-child {
            font-size: 12px;
            color: #222222;
            margin-bottom: 5px;
        }

        .notice-info div:last-child {
            font-size: 15px;
            font-weight: bold;
            color: #222222;
        }

        .notice-content {
            font-size: 14px;
            color: #333;
            height: 500px;
            overflow-y: auto;
            line-height: 1.6;
            margin-bottom: 20px;
            border-top: 1px solid #ddd;
            padding-top: 20px;
        }

        .notice-actions {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            border-top: 1px solid #ddd;
            padding-top: 20px;
        }

        .notice-actions button, .notice-actions a {
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            background-color: #f8f9fa;
            color: #000;
            border: 1px solid #ddd;
            transition: background-color 0.3s ease, color 0.3s ease;
        }

    </style>
</head>
<body>
    <div class="notice-page">
        <!-- 네비게이션 바 -->
        <div class="notice-nav">
            <%@ include file="/views/notice/noticeNav.jsp" %>
        </div>

        <!-- 공지사항 상세 컨텐츠 -->
        <div class="notice-detail-container">
            <!-- 상단 공지사항 헤더 -->
            <div class="notice-header">공지사항</div>

            <div class="notice-meta">
                <div class="notice-label">공지</div>
                <div class="notice-info">
                    <div>${notice.createdAt}</div>
                    <div>${notice.title}</div>
                </div>
            </div>

            <!-- 공지 내용 -->
            <div class="notice-content">
                ${notice.content}
            </div>

            <!-- 버튼 -->
            <div class="notice-actions">
                <a href="${pageContext.request.contextPath}/jelly?page=notice" class="list-btn">목록보기</a>
                <c:if test="${isAdmin}">
                    <button class="edit-btn" onclick="location.href='${pageContext.request.contextPath}/jelly?page=noticeEdit&noticeId=${notice.noticeId}'">수정</button>
                    <form action="${pageContext.request.contextPath}/jelly?page=noticeDelete" method="post" style="display:inline;">
                        <input type="hidden" name="noticeId" value="${notice.noticeId}">
                        <button type="submit" class="delete-btn" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
    <%@ include file="/views/home/footer.jsp" %>
</body>
</html>