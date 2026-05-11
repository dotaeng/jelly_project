<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css" />
<style type="text/css">
.error-message {
	color: red;
	font-size: 0.9em;
	margin-top: 5px; 
	margin-bottom: 5px; 
}

#error-message-email {
	color: red;
	font-size: 0.9em; 
	margin-top: 5px;
}

#error-message-password {
    color: red;
    font-size: 0.9em; 
    margin-top: 5px; 
}
</style>
</head>
<body>
<div class="header">
    <!-- 헤더 TOP -->
    <div class="header-top">
        <ul class="header-menu">
            <li><a href="<%= request.getContextPath() %>/jelly?page=notice">고객센터</a></li>
            <li><a href="<%= request.getContextPath() %>/jelly?page=mypage">마이페이지</a></li>
            <li><a href="<%= request.getContextPath() %>/jelly?page=wish">관심</a></li>
            <li><a href="<%= request.getContextPath() %>/jelly?page=login">로그인</a></li>
        </ul>
    </div>

    <!-- 헤더 MAIN -->
    <div class="header-main">
        <a href="<%= request.getContextPath() %>/jelly?page=home" class="logo">
            <img src="<%= request.getContextPath() %>/img/jellyLogo.png" alt="jellyLogo" />
        </a>
    </div>
</div>
<div class="container">
    <img src="<%= request.getContextPath() %>/img/logo2.png" alt="" id="logo2" />
    <br /><br /><br />
    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>
    <form action="<%= request.getContextPath() %>/jelly?page=login" method="post" id="login-form">
        <div class="form-group">
            <label for="email">이메일 주소</label>
            <input type="email" id="email" name="email" required />
    <c:if test="${not empty emailError}">
        <div id="error-message-email">${emailError}</div>
    </c:if>
        </div>
        <br />
        <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" required />
    <c:if test="${not empty passwordError}">
        <div id="error-message-password">${passwordError}</div>
    </c:if>
        </div>
        <button type="submit" class="login-btn">로그인</button>
    </form>
    <div class="links">
        <a href="<%= request.getContextPath() %>/jelly?page=joinForm" class="custom-link">이메일로 가입</a>
        <a href="<%= request.getContextPath() %>/jelly?page=findEmail" class="custom-link">이메일 찾기</a>
        <a href="<%= request.getContextPath() %>/jelly?page=findPw" class="custom-link">비밀번호 찾기</a>
    </div>
    <br /><br />
    <div class="social-login">
        <button>
            <img src="<%= request.getContextPath() %>/img/naver.png" alt="네이버 아이콘" />
            네이버로 로그인
        </button>
        <button>
            <img src="<%= request.getContextPath() %>/img/kakao.png" alt="카카오 아이콘" />
            카카오로 로그인
        </button>
    </div>
</div>
</body>
</html>