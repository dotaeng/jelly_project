<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/home/subHeader.jsp" %>       
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>결제정보 관리</title>
    <link rel="stylesheet" href="../css/mypageCommon.css" />
    <link rel="stylesheet" href="../css/paymentInfo.css" />
    <style></style>
  </head>
  <body>

	<!-- 마이페이지 공통부분 시작 -->
	<div class="mypage-container">
		<!-- 마이페이지 네비바 시작 -->
		<%@ include file="/views/mypage/mypageNavi.jsp"%>
		<!-- 마이페이지 컨텐츠 시작 -->
		<div class="mypage-content">
			<div class="mypageSubtitle">
				결제 정보
				<button class="insert-btn">+ 새 카드 추가하기</button>
			</div>
			<div class="payment-content">
				<div class="paymentAlert">수수료(페널티, 착불배송비 등)가 정산되지 않을 경우, 별도 고지
					없이 해당 금액을 결제 시도할 수 있습니다.</div>
				<div class="paymentList">
					<div class="paymentInfo">
						<span class="cardName">현대</span> <span class="cardNum">****-****-****-1234</span>
					</div>
					<button class="deletebutton">삭제</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
