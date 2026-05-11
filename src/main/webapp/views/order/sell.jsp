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

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/sell.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<!-- 카카오 api -->
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

</head>
<body>
	<div class="order-container">
		<h2>주문/정산</h2>
		<!-- 주문 상품 -->
		<div class="order-content">
			<div class="order-title">
				<span>주문 상품</span>
			</div>
			<div class="order-info">
				<img src="${product.imageUrl}" alt="상품사진" />
				<div class="order-subInfo">
					<div class="productSubInfo">
						<span style="font-weight: bold">${product.modelNumber }</span><br />
						<span>${product.productName }</span>
						<div class="fontGray">
							<span>${product.productName } </span>
						</div>
						<span>${size }</span>
					</div>
				</div>
			</div>
		</div>

		<!-- 판매 정산계좌 -->
		<div class="order-content">
			<div class="order-title">
				<span>판매 정산 계좌</span>
			</div>
			<div class="order-info">
				<div class="order-subInfo">
					<div class="fontGray">
						<span>계좌</span><br /> <span>예금주</span>
					</div>
					<div>
						<span>${defaultAccount.accountHolder}</span><br /> <span>${defaultAccount.accountNumber }</span><br />
					</div>
				</div>
			</div>
		</div>
		<!-- 배송 주소 -->
		<div class="order-content">
			<div class="order-title">
				반송 주소
				<button class="modalOpen">주소 변경</button>
			</div>
			<c:choose>
				<c:when test="${not empty defaultAddress }">
					<div class="order-info">
						<div class="order-subInfo">
							<div class="fontGray">
								<span>받는 분</span><br /> <span>연락처</span><br /> <span>주소</span>
							</div>
							<div>
								<span>${user.userName}</span><br /> <span>${user.phoneNumber }</span><br />
								<span>[${defaultAddress.postalCode}]
									${defaultAddress.addressLine1 } ${defaultAddress.addressLine2 }</span>
							</div>
						</div>
				</c:when>
				<c:otherwise>
					<h4>
						등록된 기본 배송지가 없습니다.<br> 새 주소를 추가해주세요!
					</h4>
				</c:otherwise>
			</c:choose>
		</div>
		<select name="요청사항" id="" class="order-selection">
			<option value="op1">요청사항 없음</option>
			<option value="op2">문 앞에 놓아주세요</option>
			<option value="op3">경비실에 맡겨 주세요</option>
			<option value="op4">파손 위험 상품입니다. 배송 시 주의해주세요</option>
			<option value="op5">직접 입력</option>
		</select>
	</div>

	<!-- 결제 방법 -->
	<div class="order-content">
		<div class="order-title">
			<span>결제방법</span>
		</div>
		<div class="order-info">
			<div class="paymentSubinfo">
				<span>계좌 간편결제</span> <a href="${pageContext.request.contextPath}/jelly?page=userAccount"><button
						class="addCard">+ 계좌 추가하기</button></a>
				<div class="account-btn">${defaultAccount.bankName }&nbsp;
					${defaultAccount.accountNumber }</div>
				<!-- <span>페널티는 일시불만 지원합니다. 체결 후 결제 정보 변경은 불가하며 분할 납부 변경은 카드사 문의
					바랍니다.단, 카드사별 정책에 따라 분할 납부 변경 시 수수료가 발생할 수 있습니다.</span> -->
			</div>
		</div>
	</div>

	<!-- 결제 금액 -->
	<div class="order-content">
		<div class="order-title">
			<span>최종 주문정보</span>
		</div>
		<div class="order-info">
			<div class="order-price">
				<div class="price-detail">
					<div>
						<span>판매가</span> <span><fmt:formatNumber value="${price}"
								type="number" />원
						</span>
					</div>
					<div>
						<span>검수비</span> <span>무료</span>
					</div>
					<div>
						<span>수수료</span> <span><fmt:formatNumber
								value="${sellCharge}" type="number" />원</span>
					</div>
					<div>
						<span>배송비</span> <span>선불.판매자 부담</span>
					</div>
				</div>
				<div class="total-price">
					<span>정산금액</span> <span> <fmt:formatNumber
							value="${price + sellCharge }" type="number" />원
					</span>
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
						</div>
				</c:if>
			</div>
		</div>
	<!-- 주소 모달 끝 -->

	<!-- 새 주소 추가 모달 -->
	<div class="hidden" id="add-address-modalContainer">
		<div id="modalContent1">
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
	<!-- 결제 푸터 -->
	<div class="payment-footer">
		<button class="payment-submit-btn">
			<fmt:formatNumber value="${price + sellCharge }" type="number" />
			원 판매하기
		</button>
</div>
	<jsp:include page="/views/home/footer.jsp" />
	
	<script>
$(()=>{
	const addressmodal= $("#address-modalContainer")
	$(".modalOpen").on("click",function(){
			addressmodal.removeClass("hidden");	
			document.body.style.overflow = "hidden"; // 모달창 열었을 때 뒷 부분 움직이지 않음
		});
		$("#address-modalCloseButton").on("click", ()=>{
			addressmodal.addClass("hidden");
			document.body.style.overflow = "auto";  // 모달창 닫으면 다시 움직이게
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
	
	
	
	$(".payment-submit-btn").on("click", async function requestPayment(){
		const now = new Date(); 
 		const year = now.getFullYear() % 100;
 		const month = ("0" + (now.getMonth() + 1)).slice(-2); // 월 앞자리에 0을 추가 (예: 01, 02)
 		const date = ("0" + now.getDate()).slice(-2); // 일 앞자리에 0을 추가 (예: 01, 02)
 		const rnd = Math.floor(Math.random()*1000);
 		const tradeId = "Jelly" + year + month + date + rnd;

		$.ajax({
		    url: '/haribo/sellData', // 판매 정보를 DAO에 넣는 서블릿으로 이동
		    method: "GET", // HTTP 메서드는 대문자로 작성
		    data: {
		    	tradeId: tradeId,
		        productName: "${product.productName}", 
		        totalPrice: ${price + sellCharge}, 
		        price : ${price}, // 사용자가 입력한 판매 입찰가
		        productId: "${product.productId}",
		        size: "${size}"
		    },
		    success: function (response) {
		        console.log("결제 성공");
		        window.location.href = "${pageContext.request.contextPath}/jelly?page=sellConfirm&productId=${product.productId}&price=${price}"; // 성공 시 페이지 이동
		    },
		    error: function(xhr, status, error) { 
		        console.error("결제 실패:", error); // console.log 대신 console.error 사용
		        console.error("응답 상태:", status);
		        console.error("서버 응답:", xhr.responseText);
		    }
		});

	});
});


</script>
</body>
</html>
