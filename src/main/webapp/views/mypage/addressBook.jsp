<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/views/home/subHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>주소록 관리</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/mypageCommon.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/addressInfo.css" />
<!-- jQuery -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<!-- 카카오 api -->
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

</head>
<body>
	<div class="mypage-container">

		<%@ include file="/views/mypage/mypageNavi.jsp"%>

		<div class="mypage-content">
			<div class="mypageSubtitle">
				주소록
				<button class="insert-btn" id="insert-btn">+ 새 배송지 추가</button>
			</div>
			<div class="addressInfo-content">
				<!-- 기본 배송지 -->
				<c:if test="${not empty defaultAddress }">
				<div class="default-address">
					<div class="defaultAddressInfo">
						<div class="addressee">
							<span>${user.userName }</span> <span class="default-address-mark">기본
								배송지</span>
						</div>
						<div class="addresseeInfo">
							<span>${user.phoneNumber }</span> <br /> <span>[${defaultAddress.postalCode }]
								${ defaultAddress.addressLine1} ${defaultAddress.addressLine2 }</span>
						</div>
					</div>
					<div class="address-btnGroup">
						<button class="modify-btn" value="${defaultAddress.postalCode}">수정</button>
						<button class="delete-btn" value="${defaultAddress.postalCode}">삭제</button>
					</div>
				</div>
				</c:if>

				<!-- 주소 정보 리스트 -->
				<c:if test="${not empty addressList }">
				<div class="addressList">
					<c:forEach var="address" items="${addressList }">
						<div class="addressInfo">
							<span class="addressee">${user.userName }</span>
							<div class="addresseeInfo">
								<span>${user.phoneNumber }</span> <br /> <span>[${address.postalCode }]
									${address.addressLine1 }${address.addressLine2 }</span>
							</div>
						</div>
					<div class="address-btnGroup">
						<button id="checkDefault-btn" value="${address.postalCode}">기본 배송지
						</button>
						<button class="modify-btn" value="${address.postalCode}">수정</button>
						<button class="delete-btn" value="${address.postalCode}">삭제</button>
					</div>
					</c:forEach>
				</div>
				</c:if>
			</div>
		</div>
	</div>

	<!-- 새 주소 추가 모달 -->
	<div class="hidden" id="add-address-modalContainer">
		<div id="modalContent">
			<div class="modalTitle">
				<button class="modal-close" id="add-address-modalCloseButton" >&times;</button>
				<h3 class="modal-title">주소 추가</h3>
			</div>
			<div class="addressInfoList">
				<div class="addressInfo">
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


<!-- 주소 수정 모달 -->
	<div class="hidden" id="modify-address-modalContainer">
		<div id="modalContent">
			<div class="modalTitle">
				<button class="modal-close" id="modify-address-modalCloseButton" >&times;</button>
				<h3 class="modal-title">주소 수정</h3>
			</div>
			<div class="addressInfoList">
				<div class="addressInfo">
					<div class="input-box">
						<div class="sub-title" id="name">이름</div>
						<input type="text" name="" class="inputText" id="input-name"
							placeholder="수령인의 이름" /><br />
						<div id="name-alert"></div>

					</div>
					<div class="input-box">
						<div class="sub-title">
							우편번호
							<button id="searchModifyAddress">우편번호 찾기</button>
						</div>

						<!-- 우편번호 입력 -->
						<input type="text" name="" class="inputText" id="modify-postalCode"
							placeholder="우편 번호를 검색하세요" readonly />
					</div>

					<div class="input-box">
						<div class="sub-title" id="address">주소</div>
						<input type="text" name="" class="inputText" id="modify-address"
							placeholder="우편 번호 검색 후, 자동으로 입력됩니다" readonly />
					</div>

					<div class="input-box">
						<div class="sub-title" id="detail-address">상세주소</div>
						<input type="text" name="" class="inputText"
							id="modify-detailAddress" placeholder="건물, 아파트, 동/호수 입력" />
					</div>
					<div class="checkDefaultAdd">
						<button class="checkBtn">✓</button>
						<span>기본배송지로 설정</span>
					</div>
					<button id="modifyBtn">저장</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 주소 수정 모달 끝 -->

	<%@ include file="/views/home/footer.jsp"%>

	<script>
$(()=>{

// 새주소 추가 모달 여닫기 
const addAddressmodal = $("#add-address-modalContainer")
$("#insert-btn").on("click", ()=>{
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

// alert 올바른 이름 적을 수 있게 메세지	
let nameVal = '';
 $("#input-name").on("input", ()=>{
	nameVal = $("#input-name").val();
	if(nameVal != null){ // nameVal이 공백이 아니면 
		$("#name-alert").text("올바른 이름을 입력해주세요.(2-20자)")
	}
 })



// 주소 api불러오기	
let postalCode = ''; // 전역변수
let addressLine1 = ''; // 전역변수

document.querySelector("#searchAddress").onclick = openKaKaoPostCode;

function openKaKaoPostCode(){
	new daum.Postcode({
		oncomplete: function(data){
			console.log(data);
			document.querySelector("#input-postalCode").value = data.zonecode; // 우편 번호 가져오기
			document.querySelector("#input-address").value = data.roadAddress; // 도로명 주소 가져오기
			postalCode = document.querySelector("#input-postalCode").value;
			addressLine1 = document.querySelector("#input-address").value;
			console.log(document.querySelector("#input-postalCode"));
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

// 새 주소 저장
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
            window.location.href = "${pageContext.request.contextPath}/jelly?page=addressBook";  // 주소록으로 
        },
        error: function(xhr, status, error) { // 함수 형태로 수정
            console.log("주소 저장 실패:", error);
            console.log("응답 상태:", status);
            console.log("서버 응답:", xhr.responseText);
        }
    });
});


// 주소 삭제
$(".delete-btn").on("click", function(e) {
	let postalCode = e.target.value;
	console.log(postalCode);

	$.ajax({
		url: "/haribo/deleteAddress",
		method: "get",
		data:{
			postalCode : postalCode
		},
		success : function(response){
			console.log("주소 삭제 성공");
 			window.location.href = "${pageContext.request.contextPath}/jelly?page=addressBook";
 		},
		error: function(xhr, status, error) { // 함수 형태로 수정
	        console.log("주소 저장 실패:", error);
	        console.log("응답 상태:", status);
	        console.log("서버 응답:", xhr.responseText);
	    } 
		
	}) ;
	
})



// 주소 수정 모달 여닫기
const modifyAddressmodal = $("#modify-address-modalContainer")
$(".modify-btn").on("click", () => {
	// 모달창 열기
	modifyAddressmodal.removeClass("hidden");
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
$("#modify-address-modalCloseButton").on("click", ()=>{
	modifyAddressmodal.addClass("hidden");
	document.body.style.overflow = "auto"; // 모달창 닫으면 다시 움직이게
}); 



//수정 주소 api불러오기	
 let modifyPostalCode = ''; // 전역변수
let modifyAddressLine1 = ''; // 전역변수

document.querySelector("#searchModifyAddress").onclick = 
function openKaKaoPostCode(){
	new daum.Postcode({
		oncomplete: function(data){
			document.querySelector("#modify-postalCode").value = data.zonecode; // 우편 번호 가져오기
			document.querySelector("#modify-address").value = data.roadAddress; // 도로명 주소 가져오기
			modifyPostalCode = document.querySelector("#modify-postalCode").value;
			modifyAddressLine1 = document.querySelector("#modify-address").value;
		}
	}).open();
} 

//주소 수정
$("#modifyBtn").on("click",function(e){
	let postalCode = $(".modify-btn").val();
	console.log($(".modify-btn").val());
    let modifyAddressLine2 = $("#modify-detailAddress").val();

	
	$.ajax({
		url : "/haribo/updateAddress",
		method: "get",
		data:{
			postalCode : postalCode, // 기존 주소의 우편번호
			modifyPostalCode : modifyPostalCode,
			modifyAddressLine1 : modifyAddressLine1,
			modifyAddressLine2 : modifyAddressLine2,
			isDefault : check
		},
		success : function(response){
			console.log("기본 배송지 변경 성공");
 			window.location.href = "${pageContext.request.contextPath}/jelly?page=addressBook";
 		},
		error: function(xhr, status, error) { // 함수 형태로 수정
	        console.log("주소 저장 실패:", error);
	        console.log("응답 상태:", status);
	        console.log("서버 응답:", xhr.responseText);
	    } 
	})
	
})

// 기본 배송지로 설정
$("#checkDefault-btn").on("click", (e)=>{
	let postalCode = e.target.value;
	console.log(postalCode);

	$.ajax({
		url: "/haribo/setDefaultAddress",
		method: "get",
		data:{
			postalCode : postalCode,
		},
		success : function(response){
			console.log("기본 배송지 변경 성공");
 			window.location.href = "${pageContext.request.contextPath}/jelly?page=addressBook";
 		},
		error: function(xhr, status, error) { // 함수 형태로 수정
	        console.log("주소 저장 실패:", error);
	        console.log("응답 상태:", status);
	        console.log("서버 응답:", xhr.responseText);
	    } 
		
	}) ;
		
})

	
	
	
});

</script>
</body>
</html>
