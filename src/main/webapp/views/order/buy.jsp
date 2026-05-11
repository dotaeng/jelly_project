<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="/views/home/subHeader.jsp" />
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Jelly</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/buy.css">
<!-- 포트원 결제 -->
<script src="https://cdn.portone.io/v2/browser-sdk.js" async defer></script>
<!-- 카카오 api -->
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<!-- jQuery -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<div class="order-container">
		<h2>배송/결제</h2>
		<!-- 배송 주소 -->
		<div class="order-content">
			<div class="order-title">
				배송 주소
				<button id="address-modalOpenButton">주소 설정</button>
			</div>
			<c:choose>
				<c:when test="${not empty defaultAddress}">
					<div class="order-info">
						<div class="order-subInfo">
							<div class="fontGray">
								<span>받는 분</span><br /> <span>연락처</span><br /> <span>주소</span>
							</div>
							<div>
								<span>${user.userName }</span><br /> <span>${user.phoneNumber }</span><br />
								<span>[${defaultAddress.postalCode}]
									${defaultAddress.addressLine1 } ${defaultAddress.addressLine2 }</span>
							</div>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<h4>
						등록된 기본 배송지가 없습니다.<br> 새 주소를 추가해주세요!
					</h4>
				</c:otherwise>
			</c:choose>
			<select name="요청사항" id="" class="order-selection">
				<option value="op1">요청사항 없음</option>
				<option value="op2">문 앞에 놓아주세요</option>
				<option value="op3">경비실에 맡겨 주세요</option>
				<option value="op4">파손 위험 상품입니다. 배송 시 주의해주세요</option>
				<option value="op5">직접 입력</option>
			</select>
		</div>

		<!-- 주문 상품 -->
		<div class="order-content">
			<div class="order-title">
				<span>주문 상품</span> <span>총 1건</span>
			</div>
			<div class="order-info">
				<img src="${product.imageUrl }" alt="" class="productimg" />
				<div class="order-subInfo">
					<div class="productSubInfo">
						<span>${product.productName }</span>
						<div class="fontGray">
							<span>${product.description }</span>
						</div>
						<span>${size }</span>
					</div>
				</div>
			</div>
			<br>
			<hr />
			<br />
			<!-- 쿠폰 -->
			<div class="order-title">
				<span>쿠폰</span>
			</div>
			<div class="coupon-info">
				<button class="coupon-selection" id="coupon-modalOpenButton">사용가능한
					쿠폰보기</button>
			</div>
		</div>

		<!-- 결제 방법 -->
		<div class="order-content">
			<div class="order-title">
				<span>결제방법</span>
			</div>
			<div class="order-info">
				<div class="paymentSubinfo">
					<span>계좌 간편결제</span><br />
					<button class="account-btn">계좌로 간편결제</button>
					<span>일반 결제</span><br />
					<div class="payment-method">
						<button class="payment-btn" id="credit-card">신용카드</button>
						<button class="payment-btn" style="color: #00c73c" id="naver-pay">
							<img src="<%=request.getContextPath()%>/img/naver.png" alt=""
								class="logoimg" />NaverPay
						</button>
						<button class="payment-btn" style="color: #ffcc00" id="kakao-pay">
							<img src="<%=request.getContextPath()%>/img/kakao.png" alt=""
								class="logoimg" />KakaoPay
						</button>
					</div>
				</div>
			</div>
		</div>

		<!-- 결제 금액 -->
		<div class="order-content">
			<div class="order-title">
				<span>결제금액</span>
			</div>
			<div class="order-info">
				<div class="order-price">
					<div class="price-detail">
						<div>
							<span>상품금액</span> <span><fmt:formatNumber value="${price}"
									type="number" />원</span>
						</div>
						<div>
							<span>배송비</span> <span><fmt:formatNumber value="${deliveryFee}"
									type="number" />원</span>
						</div>
						<div>
							<span>쿠폰</span> <span><fmt:formatNumber
									value="${discountPrice }" type="number" />원</span>
						</div>
					</div>
					<div class="total-price">
						<span>총 결제금액</span> <span><fmt:formatNumber
								value="${totalPrice+deliveryFee}" type="number" />원</span>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 주소 모달 -->
	<div class="hidden" id="address-modalContainer">
		<div id="modalContent">
			<div class="modalTitle">
				<button class="modal-close" id="address-modalCloseButton">&times;</button>
				<h3 class="modal-title">주소록</h3>
			</div>
			<button class="addBtn">+ 새 주소 추가하기</button>
			<c:if test="${not empty addressList }">
				<div class="addressInfoList">
					<c:forEach var="address" items="${addressList }">
						<button class="addressInfo" value="${address.postalCode }">
							<div class="name">${user.userName }</div>
							<div class="address">[${address.postalCode }]
								${address.addressLine1 } ${address.addressLine2 }</div>
							<div class="phone">${user.phoneNumber }</div>
						</button>
					</c:forEach>
			</c:if>
		</div>
	</div>
	</div>
	<!-- 주소 모달 끝 -->

	<!-- 새 주소 추가 모달 -->
	<div class="hidden" id="add-address-modalContainer">
		<div id="modalContent">
			<div class="modalTitle">
				<button class="modal-close" id="add-address-modalCloseButton">&times;</button>
				<h3 class="modal-title">주소 추가</h3>
			</div>
			<div class="addressInfoList">
				<div class="add-addressInfo">
					<div class="input-box">
						<div class="sub-title" id="name">이름</div>
						<input type="text" name="" class="inputText" id="input-name"
							placeholder="수령인의 이름" /><br />
						<div id="name-alert"></div>

					</div>
					<div class="input-box">
						<div class="sub-title">
							우편번호
							<button id="searchAddress">우편번호 찾기</button>
						</div>

						<!-- 우편번호 입력 -->
						<input type="text" name="" class="inputText" id="input-postalCode"
							placeholder="우편 번호를 검색하세요" readonly />
					</div>

					<div class="input-box">
						<div class="sub-title" id="address">주소</div>
						<input type="text" name="" class="inputText" id="input-address"
							placeholder="우편 번호 검색 후, 자동으로 입력됩니다" readonly />
					</div>

					<div class="input-box">
						<div class="sub-title" id="detail-address">상세주소</div>
						<input type="text" name="" class="inputText"
							id="input-detailAddress" placeholder="건물, 아파트, 동/호수 입력" />
					</div>
					<div class="checkDefaultAdd">
						<button class="checkBtn">✓</button>
						<span>기본배송지로 설정</span>
					</div>
					<button id="saveBtn">저장</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 새주소 추가 모달 끝 -->


	<!-- 쿠폰 모달 -->
	<div class="hidden" id="coupon-modalContainer">
		<div id="modalContent">
			<div class="modalTitle">
				<button class="modal-close" id="coupon-modalCloseButton">&times;</button>
				<h3 class="modal-title">쿠폰</h3>
			</div>
				<div class="couponInfoList">
			<c:forEach var="coupon" items="${userCouponList }">
					<button class="couponInfo" value="${coupon.couponId}">
						<div class="name">${coupon.couponCode }</div>
						<div class="detail">${coupon.description }</div>
						<div class="expiredate">${coupon.expiryDate }</div>
					</button>
			</c:forEach>
				</div>
		</div>
	</div>
	</div>
	<!-- 쿠폰 모달 끝 -->


	<!-- 결제 푸터 -->
	<div class="payment-footer">
		<button class="payment-submit-btn">
			<fmt:formatNumber value="${totalPrice+deliveryFee}" type="number" />
			원 결제하기
		</button>
	</div>
 	<jsp:include page="/views/home/footer.jsp" />
	<script>
$(()=> {
	
	// 주소 모달 열기/닫기
	const addressmodal = $("#address-modalContainer")
	$("#address-modalOpenButton").on("click", ()=>{
		addressmodal.removeClass("hidden");	
		document.body.style.overflow = "hidden"; // 모달창 열었을 때 뒷 부분 움직이지 않음
	});
	$("#address-modalCloseButton").on("click", ()=>{
		addressmodal.addClass("hidden");
		document.body.style.overflow = "auto";  // 모달창 닫으면 다시 움직이게
	});
	
	// 쿠폰 모달 열기/닫기
	const couponmodal = $("#coupon-modalContainer")
	$("#coupon-modalOpenButton").on("click", ()=>{
		couponmodal.removeClass("hidden");	
		document.body.style.overflow = "hidden"; // 모달창 열었을 때 뒷 부분 움직이지 않음

	});
	$("#coupon-modalCloseButton").on("click", ()=>{
		couponmodal.addClass("hidden");
		document.body.style.overflow = "auto"; // 모달창 닫으면 다시 움직이게
	});
	
	 // 새주소 추가 모달 열기/닫기
	const addAddressmodal = $("#add-address-modalContainer")
	$(".addBtn").on("click", ()=>{
		addAddressmodal.removeClass("hidden");
		document.body.style.overflow = "hidden"; // 모달창 열었을 때 뒷 부분 움직이지 않음
		// 모달창 열면 모든 입력값, css 초기화
		$(".inputText").val("");
		$("#name-alert").text("");
		$(".checkBtn").css({
			background : "white",
			color : "black",
			border: "#ddd 1px solid"
		});
	});
	$("#add-address-modalCloseButton").on("click", ()=>{
		addAddressmodal.addClass("hidden");
		document.body.style.overflow = "auto"; // 모달창 닫으면 다시 움직이게

	}); 
	// alert 올바른 이름 적을 수 있게 메세지 -> 완료
	let nameVal = '';
	 $("#input-name").on("input", ()=>{
		nameVal = $("#input-name").val();
		if(nameVal != null){ // nameVal이 공백이 아니면 
			$("#name-alert").text("올바른 이름을 입력해주세요.(2-20자)")
		}
	 })
	 
	 
	// 주소 api불러오기 -> 완료
	let postalCode = '';
	let addressLine1 = '';

	document.querySelector("#searchAddress").onclick = openKaKaoPostCode;

	function openKaKaoPostCode(){
		new daum.Postcode({
			oncomplete: function(data){
				document.querySelector("#input-postalCode").value = data.zonecode; // 우편 번호 가져오기
				document.querySelector("#input-address").value = data.roadAddress; // 도로명 주소 가져오기
				postalCode = document.querySelector("#input-postalCode").value;
				addressLine1 = document.querySelector("#input-address").value;
			}
		}).open();
	}
	
	//기본 배송지 체크 여부 확인 -> 완료
	let cnt = 0; // 기본 배송지 체크 여부를 위한 변수
	let check = false; // 기본배송지라고 체크했으면 true
		
	$(".checkBtn").on("click", ()=>{
			console.log("cnt : " + cnt);
			cnt ++;
			
		if (cnt % 2 !== 0){ 
			console.log("기본 배송지 체크");
			check = true;
			console.log("check : " + check);

			$(".checkBtn").css({
				background : "black",
				color : "white",
				border: "black 1px solid"
			});	
			
		}else{
			console.log("기본 배송지 해제");
			check = false;
			console.log("check : " + check);
			$(".checkBtn").css({
				background : "white",
				color : "black",
				border: "#ddd 1px solid"
			});	
		}
		
	});
	
	
	// 모달창에서 저장버튼 누르면 ajax 써서 address db에 넣기 -> 화면에 반영  -> 완료
	// 이미 기본 주소가 있는 상태일경우 모달창에서 기본주소로 설정하기 체크 누르고 하면 자동으로 기본주소 -> 일반, 새로 입력한 기본 주소가 view 에 보임
	$("#saveBtn").on("click", function() { // 클릭 이벤트에 대한 익명 함수 추가
	    let addressLine2 = $("#input-detailAddress").val();
	    
	    $.ajax({
	        url: "/haribo/insertAddress",
	        method: "get",
	        data: {
	            postalCode: postalCode,
	            addressLine1: addressLine1,
	            addressLine2: addressLine2,
	            isDefault: check
	        },
	        success: function(response) {
	            console.log("주소 저장 성공");
	            window.location.href = "${pageContext.request.contextPath}/jelly?page=buy&productId=${product.productId}&size=${size}&price=${price}"; 
	        },
	        error: function(xhr, status, error) { // 함수 형태로 수정
	            console.log("주소 저장 실패:", error);
	            console.log("응답 상태:", status);
	            console.log("서버 응답:", xhr.responseText);
	        }
	    });
	});

	// 주소 클릭하면 그 주소로 변하게 ... 
	$(".addressInfo").on("click", (e)=>{
	let postalCode = e.target.value;
	console.log(postalCode);

	$.ajax({
		url: "/haribo/changeAddressOrCoupon",
		method: "get",
		data:{
			postalCode : postalCode,
		},
		success : function(response){
			console.log("기본 배송지 변경 성공");
 			window.location.href = "${pageContext.request.contextPath}/jelly?page=buy&productId=${product.productId}&size=${size}&price=${price}&postalCode="+postalCode;
 		},
		error: function(xhr, status, error) { // 함수 형태로 수정
	        console.log("주소 저장 실패:", error);
	        console.log("응답 상태:", status);
	        console.log("서버 응답:", xhr.responseText);
	    } 
		
	}) ;
		
})

// 쿠폰 클릭하면 쿠폰 적용되게 
$(".couponInfo").on("click",function(e){
	console.log($(this).val());
		let couponId = $(this).val();
	console.log(couponId);
 	 window.location.href = "${pageContext.request.contextPath}/jelly?page=buy&productId=${product.productId}&size=${size}&price=${price}&couponId="+ couponId;
});

	// 결제 수단
	let paymentMethod = '';
	function setPaymentMethod(payment){
		paymentMethod = payment;
		$(".payment-submit-btn").css("background-color","rgba(255, 127, 80, 1)" );
	}
	$("#credit-card").on("click", ()=>{
		setPaymentMethod("creditCard");
	});
	$("#naver-pay").on("click", ()=>{
		setPaymentMethod("naverPay");
	});
	$("#kakao-pay").on("click", ()=>{
		setPaymentMethod("kakaoPay");
	});
	$(".account-btn").on("click", ()=>{
		setPaymentMethod("account");
	});
	$(".payment-submit-btn").on("click", async function requestPayment(){
 		const now = new Date(); 
 		const year = now.getFullYear() % 100;
 		const month = ("0" + (now.getMonth() + 1)).slice(-2); // 월 앞자리에 0을 추가 (예: 01, 02)
 		const date = ("0" + now.getDate()).slice(-2); // 일 앞자리에 0을 추가 (예: 01, 02)
 		const rnd = Math.floor(Math.random()*1000);
 		const tradeId = "Jelly" + year + month + date + rnd;
 		console.log("tradeId: " + tradeId);
 		let response = '';
 		let payMethod = '';
 		if(paymentMethod === "creditCard"){
 		response = await PortOne.requestPayment({
			// 상점 ID
			storeId : "store-95142755-e5cd-4125-b376-f7a90f93248b",
			// 채널 key
			channelKey : "channel-key-6b669b9c-926d-448b-bc47-4c8cdce14767",
			// 결제 승인 ID = 주문 번호
			paymentId : tradeId ,
			orderName : "${product.productName}", // 상품명
			totalAmount : ${totalPrice-deliveryFee}, // 총 결제 금액
			currency : "CURRENCY_KRW",
			payMethod : "CARD"
			});
 			paymentMethod = "CARD";
 		} if(paymentMethod === "account"){
 	 		response = await PortOne.requestPayment({
 				// 상점 ID
 				storeId : "store-95142755-e5cd-4125-b376-f7a90f93248b",
 				// 채널 key
 				channelKey : "channel-key-6b669b9c-926d-448b-bc47-4c8cdce14767",
 				// 결제 승인 ID = 주문 번호
 				paymentId : tradeId,
 				orderName : "${product.productName}", // 상품명
 				totalAmount : ${totalPrice-deliveryFee}, // 총 결제 금액
 				currency : "CURRENCY_KRW",
 				payMethod : "TRANSFER"
 				});
 	 			payMethod = "TRANSFER";
 	 		} if (paymentMethod === "naverPay"){ // 네이버 페이는 진짜 안됨 	
 				response = await PortOne.requestPayment({
 				// 상점 ID
 				storeId : "store-95142755-e5cd-4125-b376-f7a90f93248b",
 				// 채널 key
 				channelKey : "channel-key-6b669b9c-926d-448b-bc47-4c8cdce14767",
 				// 결제 승인 ID = 주문 번호
 				paymentId : tradeId,
 				orderName : "${formattedPrice}",
 				totalAmount : ${totalPrice-deliveryFee},
 				currency : "CURRENCY_KRW",
 				payMethod : "EASY_PAY",
 				easyPay : {easyPayProvider : NAVERPAY},
 				});
 				payMethod = "EASY_PAY";
 		} if (paymentMethod === "kakaoPay"){
 			response = await PortOne.requestPayment({
 				// 상점 ID
 				storeId : "store-95142755-e5cd-4125-b376-f7a90f93248b",
 				// 채널 key
 				channelKey : "channel-key-38371db4-4cc5-40f0-8c4a-f6c37106abdc",
 				// 결제 승인 ID = 주문 번호
 				paymentId : tradeId,
 				orderName : "${product.productName}", // 상품명
 				totalAmount : ${totalPrice-deliveryFee}, // 총 결제 금액
 				currency : "CURRENCY_KRW",
 				payMethod: "EASY_PAY",
 				});
 				payMethod = "EASY_PAY";
 		} 
 		$.ajax({
		    url: '/haribo/buyData', // 결제 정보를 dao에 넣는 서블릿으로 이
		    method: "get",
		    data: {
		    	orderNo: tradeId,
		        txId: response.txId,
		        productName: "${product.productName}", 
		        totalPrice: ${totalPrice-deliveryFee}, // 쿠폰까지 먹인 최종가 나중에 변경해야 함
		        price : ${price}, // 사용자가 입력한 판매 입찰가
		        productId: "${product.productId}",
		        size: "${size}",
		       	payMethod : payMethod,
		       	couponId: ${selectCouponId}
		       	
		    },
		    success: function(response) {
		        console.log("결제 성공");
		        window.location.href = "${pageContext.request.contextPath}/jelly?page=buyConfirm&productId=${product.productId}&price=${totalPrice}&discountPrice=${discountPrice }";  // 결제 완료 페이지로 이동
		    },
		    error: function(xhr, status, error) { // 함수 형태로 수정
		        console.log("결제 실패:", error);
		        console.log("응답 상태:", status);
		        console.log("서버 응답:", xhr.responseText);
		    }
		});
 			
	});
})
</script>
</body>

</html>
