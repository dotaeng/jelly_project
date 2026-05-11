<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/joinForm.css" />
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
        <form class="form">
            <button>
                <svg width="17" height="16" fill="none" xmlns="http://www.w3.org/2000/svg" role="img" aria-labelledby="search">
                    <path d="M7.667 12.667A5.333 5.333 0 107.667 2a5.333 5.333 0 000 10.667zM14.334 14l-2.9-2.9" 
                          stroke="currentColor" stroke-width="1.333" stroke-linecap="round" stroke-linejoin="round"></path>
                </svg>
            </button>
            <input class="input" placeholder="검색어를 입력해주세요" required="" type="text" />
            <button class="reset" type="reset">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"></path>
                </svg>
            </button>
        </form>
    </div>
</div>

<!-- 메인 -->
<div class="container">
    <h1>회원가입</h1>

    <!-- 에러 메시지 표시 -->
     <c:if test="${not empty duplicateError}">
        <div class="error-message" style="color: red; text-align: center; font-weight: 500; margin-bottom: 20px;">
            ${duplicateError}
        </div>
    </c:if>

    <form action="<%= request.getContextPath() %>/jelly?page=joinNum" method="post">
        <div class="form-group">
            <label for="email">이메일 주소</label>
            <input type="email" id="email" name="email" required /> 
            <br/>
    <c:if test="${not empty emailError}">
        <div class="error-message" style="color: red; font-size:11px; text-align: left; font-weight: 500; margin-top:5px; margin-bottom: 20px;">
            ${emailError}
        </div>
    </c:if>
        </div>
        <div class="form-group">
            <label for="username">이름</label>
            <input type="text" id="username" name="user_name" required />
        </div>
        <div class="form-group">
            <label for="phone">휴대폰 번호</label>
            <input type="text" id="phone" name="phone_number" required />
        </div>
        <div class="form-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" placeholder="영문, 숫자, 특수문자 포함 8~16자" required />
           <c:if test="${not empty passwordError}">
        <div class="error-message" style="color: red; font-size:11px; text-align: left; font-weight: 500; margin-top:5px; margin-bottom: 20px;">
            ${passwordError}
        </div>
    </c:if>
        </div>
        <div class="form-group">
            <label for="password">비밀번호 확인</label>
            <input type="password" id="confirmpw" name="confirmpw" required />
        </div>

        <div class="checkbox-group">
            <input type="checkbox" id="all-terms" />
            <label for="all-terms">모두 동의합니다</label>
            <ul class="terms-list">
                <li>✔ 만 14세 이상입니다.</li>
                <li>✔ [필수] 이용약관 동의</li>
                <li>✔ [필수] 개인정보 수집 이용 동의</li>
                <li>✔ [선택] 프로모션 정보 수신 동의</li>
            </ul>
        </div>
        
        <button type="submit" class="submit-btn">이메일 인증하고 가입하기</button>
    </form>
</div>
</body>
<script type="text/javascript">
	
</script>
</html>