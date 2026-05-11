<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.mypage-snb {
  margin-top: -25px;
  max-width: 200px;
  width: 90%;
  height: 300px;
  padding: 20px;
}

.mypage-sbn-title {
  font-size: 24px;
  font-weight: bold;
}
.mypage-sbn-subtitle {
  font-size: 20px;
  font-weight: bold;
  margin-top: 30px;
}

.mypage-sbn a {
  text-decoration: none;
  color: gray;
}
.mypage-sbn-menu {
  color: gray;
  margin-bottom: 20px;
  font-size: 15px;
}
.mypage-sbn-menu a.active {
    font-weight: bold;
    color: black;
}
</style>
</head>
  <body>
      <div class="mypage-snb">
      <p class="mypage-sbn-title">
        <a href="${pageContext.request.contextPath}/jelly?page=notice" style="text-decoration: none; color: inherit;">고객센터</a>
      </p>

      <!-- 공지사항 -->
      <p class="mypage-sbn-menu">
        <c:choose>
          <c:when test="${param.page == 'notice'}">
            <a href="${pageContext.request.contextPath}/jelly?page=notice" class="active">공지사항</a>
          </c:when>
          <c:otherwise>
            <a href="${pageContext.request.contextPath}/jelly?page=notice">공지사항</a>
          </c:otherwise>
        </c:choose>
      </p>

      <!-- 자주 묻는 질문 -->
      <p class="mypage-sbn-menu">
        <c:choose>
          <c:when test="${param.page == 'faq'}">
            <a href="${pageContext.request.contextPath}/jelly?page=faq" class="active">자주 묻는 질문</a>
          </c:when>
          <c:otherwise>
            <a href="${pageContext.request.contextPath}/jelly?page=faq">자주 묻는 질문</a>
          </c:otherwise>
        </c:choose>
      </p>

    </div>
  </body>
</html>