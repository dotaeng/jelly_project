<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="/views/home/subHeader.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>주문확인</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/buyAndSellConfirm.css" />
</head>
<body>
	<div class="order-container">
		<h2>주문 확인</h2>
		<!-- 배송 주소 -->
		<div class="order-content">
			<div class="order-title">
				<h3>즉시 판매가 완료되었습니다.</h3>
			</div>
			<div class="order-info">
				<span>거래가 체결되면 48시간(일, 공휴일 제외) 이내에 상품발송 후,</span> <span>'MY >
					판매내역 > 진행 중'에서 발송정보를 입력하세요 </span> <img src="${product.imageUrl }" alt="" />
				<button class="buyDetailBtn">판매 내역 상세보기</button>
				<span>구매자는 15분 이내에 구매를 결정하게 됩니다.</span>
				<div class="order-subInfo"></div>
			</div>

			<div class="paymentSubinfo">
				<div class="order-price">
					<div class="price-detail">
						<div>
							<span style="color: black">즉시 판매가</span> <span
								style="color: black"><fmt:formatNumber
							value="${price }" type="number" />원</span>
						</div>
						<div>
							<span>검수비</span> <span>무료</span>
						</div>
						<div>
							<span>수수료</span> <span><fmt:formatNumber
							value="${ sellCharge }" type="number" />원</span>
						</div>
						<div>
							<span>배송비</span> <span>선불, 판매자 부담</span>
						</div>
					</div>
					<div class="price-detail">
						<div>
							<span style="color: black; font-weight: bold">보내실 곳</span>
						</div>
						<div>
							<span>받는 분</span> <span>Jelly(젤리)</span>
						</div>
						<div>
							<span>연락처</span> <span>1588-0000</span>
						</div>
						<div>
							<span>주소</span> <span>서울시 종로구 율곡로10길 105 디아망 4층</span>
						</div>
					</div>

				</div>

				<div class="total-price">
					<span>정산금액</span> <span>${price + sellCharge }원</span>
				</div>
			</div>
		</div>
	</div>
	</div>
	<jsp:include page="/views/home/footer.jsp" />
</body>
</html>
