<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="currentPage" value="${param.page}" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/header.css">
  <title>Jelly</title>
</head>
<body>
  <div class="header-section">
    <!-- 상단 메뉴 -->
    <div class="header-section-top">
      <ul class="header-section-menu">
        <li><a href="<%= request.getContextPath() %>/jelly?page=notice">고객센터</a></li>
        <li>
          <c:choose>
            <c:when test="${sessionScope.user != null}">
              <a href="<%= request.getContextPath() %>/jelly?page=mypage">마이페이지</a>
            </c:when>
            <c:otherwise>
              <a href="<%= request.getContextPath() %>/jelly?page=login">마이페이지</a>
            </c:otherwise>
          </c:choose>
        </li>
        <li>
          <c:choose>
            <c:when test="${sessionScope.user != null}">
              <a href="<%= request.getContextPath() %>/jelly?page=wish">관심</a>
            </c:when>
            <c:otherwise>
              <a href="<%= request.getContextPath() %>/jelly?page=login">관심</a>
            </c:otherwise>
          </c:choose>
        </li>
        <li>
          <c:choose>
            <c:when test="${sessionScope.user != null}">
            <span style="font-weight: bolder;">안녕하세요, ${sessionScope.user.userName}님!</span>
              <a href="<%= request.getContextPath() %>/jelly?page=logout"> 로그아웃</a>
            </c:when>
            <c:otherwise>
              <a href="<%= request.getContextPath() %>/jelly?page=login">로그인</a>
            </c:otherwise>
          </c:choose>
        </li>
      </ul>
    </div>

    <!-- 로고 -->
    <div class="header-section-main">
      <a href="<%= request.getContextPath() %>/jelly?page=home" class="header-section-logo">
        <img src="<%= request.getContextPath() %>/img/jellyLogo.png" alt="jellyLogo">
      </a>
    </div>

    <!-- 하단 메뉴 -->
    <div class="header-section-bottom">
      <nav class="header-bottom-nav">
        <a href="<%= request.getContextPath() %>/jelly?page=home" class="${currentPage == 'home' ? 'active' : ''}">HOME</a>
        <a href="<%= request.getContextPath() %>/jelly?page=shoes" class="${currentPage == 'shoes' ? 'active' : ''}">SHOP</a>
        <a href="<%= request.getContextPath() %>/jelly?page=brand" class="${currentPage == 'brand' ? 'active' : ''}">BRAND</a>
        <a href="<%= request.getContextPath() %>/jelly?page=styleList" class="${currentPage == 'styleList' ? 'active' : ''}">STYLE</a>
        <a href="<%= request.getContextPath() %>/jelly?page=event1" class="${currentPage == 'event1' ? 'active' : ''}">EVENT</a>
      </nav>
      <form class="header-form" action="<%= request.getContextPath() %>/jelly?page=search" method="get">
        <button>
          <svg width="17" height="16" fill="none" xmlns="http://www.w3.org/2000/svg" role="img" aria-labelledby="search">
            <path d="M7.667 12.667A5.333 5.333 0 107.667 2a5.333 5.333 0 000 10.667zM14.334 14l-2.9-2.9" stroke="currentColor" stroke-width="1.333" stroke-linecap="round" stroke-linejoin="round"></path>
          </svg>
        </button>
        <input class="header-input" placeholder="검색어를 입력해주세요" name="query" required="" type="text">
        <button class="header-reset" type="reset">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
      </form>
    </div>
  </div>
</body>
</html>