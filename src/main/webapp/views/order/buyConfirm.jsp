<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="/views/home/subHeader.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>주문확인</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/buyAndSellConfirm.css" />
</head>
<body>
	<div class="order-container">
		<h2>주문 확인</h2>
		<!-- 배송 주소 -->
		<div class="order-content">
			<div class="order-title">
				<h3>즉시 구매가 완료되었습니다.</h3>
			</div>
			<div class="order-info">
				<span> 주문하신 상품은 전문가의 검수완료 후,</span> <span>안전하게 배송될 예정입니다. </span> <img
					src="${product.imageUrl }" alt="" />
				<button class="buyDetailBtn">구매 내역 상세보기</button>
				<div class="order-subInfo"></div>
			</div>

			<div class="paymentSubinfo">
				<div class="order-price">
					<div class="price-detail">
						<div>
							<span style="color: black">상품금액</span> <span style="color: black">${product.initialPrice }원</span>
						</div>
						<div>
							<span>배송비</span> <span><fmt:formatNumber value="${deliveryFee}"
									type="number" />원</span>
						</div>
							<c:if test="${not empty discount }">
						<div>
							<span>쿠폰</span> 
							<span><fmt:formatNumber value="${discountPrice }" type="number" />원</span>
						</div>
						</c:if>
					</div>
					<div class="total-price">
						<span>총 결제금액</span> <span>${formattedPrice}원</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/views/home/footer.jsp" />
</body>
</html>
