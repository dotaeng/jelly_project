<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/views/home/subHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>주소록 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypageCommon.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addressInfo.css" />
    <style></style>
  </head>
  <body>
	<div class="mypage-container">
		<!-- 마이페이지 네비바 시작 -->
		<%@ include file="/views/mypage/mypageNavi.jsp"%>
		<!-- 마이페이지 컨텐츠 시작 -->
		<div class="mypage-content">
			<div class="mypageSubtitle">
				주소록
				<button class="insert-btn">+ 새 배송지 추가</button>
			</div>
			<!-- 관심페이지 공통부분 끝 -->
			<div class="addressInfo-content">
				<div class="default-addressList">
					<div class="addressInfo">
						<span class="addressee">천세진</span>
						<div class="addresseeInfo">
							<span>010-1111-2222</span> <br /> <span>(우편번호)
								경기 성남시 분당구 판교역로 4 (백현동) 10층</span>
						</div>
					</div>
					<div class="address-btnGroup">
						<button class="default-address-btn">기본 배송지</button>
						<button class="address-btn">수정</button>
						<button class="address-btn">삭제</button>
					</div>
				</div>
				<div class="addressList">
					<div class="addressInfo">
						<span class="addressee">천세진</span>
						<div class="addresseeInfo">
							<span>010-1111-2222</span> <br /> <span>(우편번호)
								경기 성남시 분당구 판교역로 4 (백현동) 10층</span>
						</div>
					</div>
					<div class="address-btnGroup">
						<button class="address-btn">기본 배송지</button>
						<button class="address-btn">수정</button>
						<button class="address-btn">삭제</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
