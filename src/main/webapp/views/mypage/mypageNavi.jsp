<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>mypageNavigation</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/mypageNavi.css"
    />
  </head>
  <body>
      <div class="mypage-snb">
      <p class="mypage-sbn-title">
        <a href="<%=request.getContextPath()%>/jelly?page=mypage" style="text-decoration: none; color: inherit;">마이페이지</a>
      </p>
      <p class="mypage-sbn-subtitle">쇼핑정보</p>

      <!-- 구매내역 -->
      <p class="mypage-sbn-menu">
        <c:choose>
          <c:when test="${param.page == 'purchaseHistory'}">
            <a href="<%=request.getContextPath()%>/jelly?page=purchaseHistory" class="active">구매내역</a>
          </c:when>
          <c:otherwise>
            <a href="<%=request.getContextPath()%>/jelly?page=purchaseHistory">구매내역</a>
          </c:otherwise>
        </c:choose>
      </p>

      <!-- 판매내역 -->
      <p class="mypage-sbn-menu">
        <c:choose>
          <c:when test="${param.page == 'salesHistory'}">
            <a href="<%=request.getContextPath()%>/jelly?page=salesHistory" class="active">판매내역</a>
          </c:when>
          <c:otherwise>
            <a href="<%=request.getContextPath()%>/jelly?page=salesHistory">판매내역</a>
          </c:otherwise>
        </c:choose>
      </p>

      <!-- 관심 -->
      <p class="mypage-sbn-menu">
        <c:choose>
          <c:when test="${param.page == 'wish'}">
            <a href="<%=request.getContextPath()%>/jelly?page=wish" class="active">관심</a>
          </c:when>
          <c:otherwise>
            <a href="<%=request.getContextPath()%>/jelly?page=wish">관심</a>
          </c:otherwise>
        </c:choose>
      </p>
      
      <p class="mypage-sbn-menu">
        <c:choose>
          <c:when test="${param.page == 'rank'}">
            <a href="<%=request.getContextPath()%>/jelly?page=rank" class="active">판매자 등급</a>
          </c:when>
          <c:otherwise>
            <a href="<%=request.getContextPath()%>/jelly?page=rank">판매자 등급</a>
          </c:otherwise>
        </c:choose>
      </p>

      <p class="mypage-sbn-subtitle">내 정보</p>

      <!-- 로그인 관리 -->
      <p class="mypage-sbn-menu">
        <c:choose>
          <c:when test="${param.page == 'loginInfo'}">
            <a href="<%=request.getContextPath()%>/jelly?page=loginInfo" class="active">로그인 관리</a>
          </c:when>
          <c:otherwise>
            <a href="<%=request.getContextPath()%>/jelly?page=loginInfo">로그인 관리</a>
          </c:otherwise>
        </c:choose>
      </p>

      <!-- 프로필 관리 -->
	<p class="mypage-sbn-menu">
  		<c:choose>
    		<c:when test="${param.page == 'viewProfileInfo'}">
      			<a href="<%=request.getContextPath()%>/jelly?page=viewProfileInfo" class="active">프로필 관리</a>
    				</c:when>
    		<c:otherwise>
      				<a href="<%=request.getContextPath()%>/jelly?page=viewProfileInfo">프로필 관리</a>
    		</c:otherwise>
  		</c:choose>
	</p>

      <!-- 주소록 -->
      <p class="mypage-sbn-menu">
        <c:choose>
          <c:when test="${param.page == 'addressBook'}">
            <a href="<%=request.getContextPath()%>/jelly?page=addressBook" class="active">주소록</a>
          </c:when>
          <c:otherwise>
            <a href="<%=request.getContextPath()%>/jelly?page=addressBook">주소록</a>
          </c:otherwise>
        </c:choose>
      </p>

      <!-- 판매 정산 계좌 -->
      <p class="mypage-sbn-menu">
        <c:choose>
          <c:when test="${param.page == 'userAccount'}">
            <a href="<%=request.getContextPath()%>/jelly?page=userAccount" class="active">판매 정산 계좌</a>
          </c:when>
          <c:otherwise>
            <a href="<%=request.getContextPath()%>/jelly?page=userAccount">판매 정산 계좌</a>
          </c:otherwise>
        </c:choose>
      </p>

      <!-- 쿠폰 -->
      <p class="mypage-sbn-menu">
        <c:choose>
          <c:when test="${param.page == 'myCoupon'}">
            <a href="<%=request.getContextPath()%>/jelly?page=myCoupon" class="active">쿠폰</a>
          </c:when>
          <c:otherwise>
            <a href="<%=request.getContextPath()%>/jelly?page=myCoupon">쿠폰</a>
          </c:otherwise>
        </c:choose>
      </p>
    </div>
  </body>
</html>
