<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/home/subHeader.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>프로필 관리</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/userAccount.css" />
<script>
function showAlert() {
    var successMessage = "${successMessage}";
    if (successMessage) {
        alert(successMessage);
    }
}
</script>
</head>
<body onload="showAlert()">

	<!-- 마이페이지 공통부분 시작 -->
	<div class="mypage-container">
		<%@ include file="/views/mypage/mypageNavi.jsp"%>
		<!-- 마이페이지 컨텐츠 시작 -->
		<div class="mypage-content">
			<div class="mypageSubtitle">판매 정산 계좌</div>
			<form action="<%=request.getContextPath()%>/jelly?page=userAccount" method="post">
				<%-- <input type="hidden" name="action" value="${empty successMessage ? 'save' : 'modify'}" /> --%>
				<div class="loginInfo-content">
					<div class="unit">
						<span class="unit-title">은행명</span>
						<div class="inputBox">
							<select name="bankname" id="bankname" class="selectstyle">
								<option value="default" ${bankname == 'default' ? 'selected' : ''}>선택하세요</option>
								<option value="ourBank" ${bankname == 'ourBank' ? 'selected' : ''}>우리은행</option>
								<option value="kookminBank" ${bankname == 'kookminBank' ? 'selected' : ''}>국민은행</option>
								<option value="sinhanBank" ${bankname == 'sinhanBank' ? 'selected' : ''}>신한은행</option>
								<option value="NongHyup" ${bankname == 'NongHyup' ? 'selected' : ''}>농협</option>
								<option value="tossBank" ${bankname == 'tossBank' ? 'selected' : ''}>토스뱅크</option>
								<option value="kakaoBank" ${bankname == 'kakaoBank' ? 'selected' : ''}>카카오뱅크</option>
							</select>
						</div>
						<c:if test="${not empty bankNameError}">
							<div id="error-message-num" style="color: red;">${bankNameError}</div>
						</c:if>
					</div>

					<div class="unit">
						<span class="unit-title">계좌번호</span>
						<div class="inputBox">
							<input type="text" name="accountnum" id="accountnum"
								placeholder="계좌번호를 입력하세요" value="${bankname == 'default' ? '' : accountnum}" />
						</div>
						<c:if test="${not empty errorMessage}">
							<div id="error-message-num" style="color: red;">${errorMessage}</div>
						</c:if>
					</div>
					<div class="unit">
						<span class="unit-title">예금주</span>
						<div class="inputBox">
							<input type="text" name="accountname" id="accountname"
								placeholder="예금주명을 입력하세요" value="${bankname == 'default' ? '' : accountname}" />
						</div>
						<c:if test="${not empty userNameError}">
							<div id="error-message-num" style="color: red;">${userNameError}</div>
						</c:if>
					</div>
					<c:choose>
					    <c:when test="${empty bankname or bankname == 'default'}">
					        <button type="submit" class="submit-btn">저장하기</button>
					    </c:when>
					    <c:otherwise>
					        <button type="submit" class="submit-btn">변경하기</button>
					    </c:otherwise>
					</c:choose>
				</div>
			</form>
		</div>
	</div>
</body>
</html>