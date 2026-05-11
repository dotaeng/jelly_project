<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/views/home/subHeader.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>자주 묻는 질문</title>
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
            padding: 20px;
            position: relative;
        }

        .notice-item:last-child {
            border-bottom: none;
        }

        .notice-item h3 {
            margin: 0;
            font-size: 15px; 
            font-weight: normal;
            color: #222222;
            cursor: pointer;
        }

        .notice-item h3:hover {
            text-decoration: underline;
        }

        .notice-item h3 .notice-label {
            font-weight: bold;
            color: black; 
            font-size: 14px;
            margin-right: 5px;
        }

        .notice-content {
    		display: none;
    		font-size: 14px;
    		margin-top: 10px;
    		padding: 40px 10px;
    		background-color: #f9f9f9;
    		border-top: 1px solid #ddd; 
    		border-bottom: 1px solid #ddd; 
    		border-left: none; 
    		border-right: none; 
		}
        /* 페이지네이션 */
        .pagination {
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
    <div class="notice-page">
        <!-- 네비게이션 바 -->
        <div class="notice-nav">
            <%@ include file="/views/notice/noticeNav.jsp" %>
        </div>

        <!-- 자주 묻는 질문 -->
        <div class="notice-container">
            <div class="notice-header">
                <h1>자주 묻는 질문</h1>
                <div class="header-line"></div>
            </div>

            <!-- 자주 묻는 질문 리스트 -->
            <ul class="notice-list">
                <c:forEach var="faq" items="${faqList}">
                    <li class="notice-item">
                        <h3>
                            <span class="notice-label">${faq.category}</span>
                            ${faq.question}
                        </h3>
                        <div class="notice-content">
                            ${faq.answer}
                        </div>
                    </li>
                </c:forEach>
            </ul>

            <!-- 페이지네이션 -->
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="?page=faq&currentPage=${currentPage - 1}">이전</a>
                </c:if>
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <a href="?page=faq&currentPage=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                </c:forEach>
                <c:if test="${currentPage < totalPages}">
                    <a href="?page=faq&currentPage=${currentPage + 1}">다음</a>
                </c:if>
            </div>
        </div>
    </div>
    <%@ include file="/views/home/footer.jsp" %>

    <script>
        // 클릭 시 공지사항 내용 표시/숨기기
        document.querySelectorAll('.notice-item h3').forEach(item => {
            item.addEventListener('click', function () {
                const content = this.nextElementSibling;
                if (content.style.display === 'none' || !content.style.display) {
                    content.style.display = 'block';
                } else {
                    content.style.display = 'none';
                }
            });
        });
    </script>
</body>
</html>