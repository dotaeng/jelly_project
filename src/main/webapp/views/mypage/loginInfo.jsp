<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/views/home/subHeader.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>로그인 정보</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypageCommon.css" />
   <link rel="stylesheet" href="<%=request.getContextPath()%>/css/loginInfo.css" />
  <style type="text/css">

.mypage-container {
	margin: auto;
	display: flex;
	justify-content: space-between;
	max-width: 1250px;
	width: 90%;
	height: 1000px;
}

.mypage-content {
	max-width: 1000px;
	width: 90%;
	margin: 24px 0 0 50px;
	display: flex;
	flex-direction: column; /* 세로(열 형태)로 정렬 */
}

/* mypage subtitle과 그 옆에 버튼  */
.mypageSubtitle {
	font-size: 24px;
	font-weight: bold;
	max-width: inherit;
	width: 100%;
	height: 30px;
	border-bottom: 3px black solid;
	padding-bottom: 20px;
	display: flex;
	justify-content: space-between;
	align-items: center;
}

.insert-btn {
	display: flex;
	float: right;
	padding: 10px;
	justify-content: center;
	margin: 10px 0;
	height: 40px;
	align-items: center;
	border: 1px black solid;
	border-radius: 10px;
	color: gray;
}

.loginInfo-box {
	margin: 20px 0 20px 0;
	max-width: int;
	max-width: inherit;
	padding: 20px 0 0 0;
}

.unit {
	width: 450px;
	padding-bottom: 20px;
	margin-top: 30px;
	border: none;
}

.unit-title {
	margin-bottom: 5px;
	display: inline-block;
	color: gray;
	font-size: 13px;
}

.inputBox {
	display: flex;
	justify-content: space-between;
	margin-top: 5px;
}

.detailInfo {
	width: 70%;
	height: 35px;
	border: none;
	display: flex;
	align-items: center;
}

.modifybtn {
	width: 60px;
	border: lightgray 1px solid;
	border-radius: 10px;
	color: gray;
	font-size: 14px;
	height: 40px;
	margin-top: 10px;
	margin-left: 10px;
}

.profile-box {
	border-bottom: lightgray 1px solid;
	height: 100px;
	margin: 10px 0 30px 0;
	padding: 20px 40px;
	display: flex;
	align-items: center;
	justify-content: space-between;
}

.profile-info {
	max-width: 700px;
	width: 70%;
	padding-left: 20px;
}

.profile-image {
	width: 80px;
	height: 80px;
	border-radius: 100%;
}

.profile-btns {
	display: flex;
	justify-content: space-between;
	margin: auto 0;
	max-width: 220px;
	width: 90%;
}

.profile-info-btn {
	width: 100px;
	height: 43px;
	font-size: 14px;
	background-color: white;
	border: lightgray 1px solid;
	border-radius: 10px;
}

.profile-user-nickname {
	font-size: 20px;
	font-weight: bold;
	display: inline-block;
	margin-bottom: 4px;
}

.profile-user-email {
	color: gray;
	font-size: 14px;
}

.mypage-container input {
	width: calc(100% - 10px);
	padding: 0.8rem;
	font-size: 1rem;
	border: none;
	border-radius: 0;
	box-sizing: border-box;
	margin-top: 0.5rem;
	background-color: #fff;
	box-shadow: inset 0 -2px 0 0 #dcdcdc;
	transition: box-shadow 0.3s ease;
}

.mypage-container input:focus {
	outline: none;
	box-shadow: inset 0 -2.5px 0 0 #000;
}

.submit-btn {
	width: 12%;
	padding: 1rem;
	background-color: #000;
	color: #fff;
	border: none;
	border-radius: 15px;
	font-size: 0.8rem;
	cursor: pointer;
	text-align: center;
	font-weight: bolder;
	text-align: center;
	min-width: 70px; /* 버튼의 최소 너비 설정 */
    white-space: nowrap; /* 텍스트 줄바꿈 방지 */
}

.submit-btn:hover {
	background-color: #333;
}

.mypage-sbn a {
	text-decoration: none;
	color: gray; /*  a태그 글씨 푸른색 제거  */
}

/* 모달창 기본 스타일 */
.modal {
  display: none; /* 기본적으로 숨김 */
  position: fixed;
  z-index: 1000;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.5); /* 배경 어둡게 */
}

/* 모달 콘텐츠 박스 */
.modal-content {
  background-color: #fff;
  margin: 15% auto; /* 화면 중앙에 위치 */
  padding: 20px;
  border: 1px solid #888;
  width: 35%;
  border-radius: 10px;
  text-align: center;
}

/* 닫기 버튼 */
.close-btn {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.modal input {
	width: calc(70% - 10px);
	padding: 0.8rem;
	font-size: 1rem;
	border: none;
	border-radius: 0;
	box-sizing: border-box;
	margin-top: 0.5rem;
	background-color: #fff;
	box-shadow: inset 0 -2px 0 0 #dcdcdc;
	transition: box-shadow 0.3s ease;
}

.modal input:focus {
	outline: none;
	box-shadow: inset 0 -2.5px 0 0 #000;
}

.modal input::placeholder {
	color: #D8D8D8;
  }

.close-btn:hover,
.close-btn:focus {
  color: black;
}

.modal-content p {
  text-align: left;
}


  
  </style>
  </head>
  <body>
    <div class="mypage-container">
      <!-- 마이페이지 네비바 시작 -->
		<%@ include file="/views/mypage/mypageNavi.jsp"%>
      <!-- 마이페이지 컨텐츠 시작 -->
      <div class="mypage-content">
        <div class="mypageSubtitle">로그인 정보</div>
        <!-- 관심페이지 공통부분 끝 -->
        <div class="loginInfo-content">
          <h3>내 계정</h3>
          <div class="unit">
          <span class="unit-title">이메일</span>
            <div class="inputBox">
				<input type="text" id="useremail" value="${email}" />
              <button class="modifybtn">변경</button>
			  <!-- 모달창 -->
			 <form action="<%=request.getContextPath()%>/jelly?page=loginInfoEmail" method="post">
			<div id="modal1" class="modal">
			  <div class="modal-content">
			    <span class="close-btn">&times;</span>
			    <h4>이메일 변경하기</h4>
			    <input type="text" name="useremail" id="modal-input" placeholder="변경 할 이메일을 입력하세요" />
			    <br /><br />
			    <button class="submit-btn" id="modal-submit">변경</button>
			  </div>
			</div>
			</form>
            </div>
          </div>
          <div class="unit">
            <span class="unit-title">비밀번호</span>
            <div class="inputBox">
            <input type="text" id="userpassword" value="${password}" />
              <button class="modifybtn">변경</button>
              <!-- 모달창 -->
              <form action="<%=request.getContextPath()%>/jelly?page=loginInfoPw" method="post">
			<div id="modal2" class="modal">
			  <div class="modal-content">
			    <span class="close-btn">&times;</span>
			    <h4>비밀번호 변경하기</h4>
			    <p>현재 비밀번호</p>
			    <input type="text" id="modal-input" placeholder="현재 비밀번호 입력" />
			    <p>변경 후 비밀번호</p>
			    <input type="text" name="userpassword" id="modal-input" placeholder="변경 후 비밀번호 입력" />
			    <!-- <p>비밀번호 확인</p>
			    <input type="text" id="modal-input" placeholder="비밀번호 확인" /> -->
			    <br /><br />
			    <button class="submit-btn" id="modal-submit">변경</button>
			  </div>
			</div>
			</form>
            </div>
          </div>
          <br />
          <br />
          <h3>개인 정보</h3>
          <div class="unit">
            <span class="unit-title">휴대폰 번호</span>
            <div class="inputBox">
            <input type="text" id="userphonenum" value="${phonenumber}"/>
              <button class="modifybtn">변경</button>
              <!-- 모달창 -->
              <form action="<%=request.getContextPath()%>/jelly?page=loginInfoPhoneNumber" method="post">
			<div id="modal3" class="modal">
			  <div class="modal-content">
			    <span class="close-btn">&times;</span>
			    <h4>휴대폰 번호 변경하기</h4>
			    <input type="text" name="userphonenum" id="modal-input" placeholder="새로운 값을 입력하세요" />
			    <br /><br />
			    <button class="submit-btn" id="modal-submit">변경</button>
			  </div>
			</div>
			</form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
  <script type="text/javascript">
  document.addEventListener("DOMContentLoaded", function () {
	  const modifyBtns = document.querySelectorAll(".modifybtn"); 
	  const modals = [document.getElementById("modal1"), document.getElementById("modal2"), document.getElementById("modal3")];

	  // 변경 버튼 클릭 시 해당하는 모달 열기
	  modifyBtns.forEach((btn, index) => {
	    btn.addEventListener("click", () => {
	      modals[index].style.display = "block";
	    });
	  });

	  // 닫기 버튼 클릭 시 모달 닫기
	  const closeBtns = document.querySelectorAll(".close-btn");
	  closeBtns.forEach((closeBtn, index) => {
	    closeBtn.addEventListener("click", () => {
	      modals[index].style.display = "none";
	    });
	  });

	  // 확인 버튼 클릭 시 모달 닫기
	  const submitBtns = document.querySelectorAll(".submit-btn");
	  submitBtns.forEach((submitBtn, index) => {
	    submitBtn.addEventListener("click", (event) => {
	      const modalInput = modals[index].querySelector("#modal-input");
	      const newValue = modalInput.value;

	      if (newValue.trim() === "") {
	        alert("값을 입력해주세요.");
	        return;
	      }
	      
	      const userEmailInput = document.querySelector("#useremail");
	      const userPasswordInput = document.querySelector("#userpassword");
	      const userPhoneInput = document.querySelector("#userphonenum");

	      if (userEmailInput) {
	        userEmailInput.value = newValue; // 이메일
	      }
	      if (userPasswordInput) {
	        userPasswordInput.value = newValue; // 비밀번호
	      }
	      if (userPhoneInput) {
	        userPhoneInput.value = newValue; // 휴대폰 번호
	      }

	      alert("변경되었습니다: " + newValue);
	      modals[index].style.display = "none";
	    });
	  });

	  // 모달 외부 클릭 시 모달 닫기
	  window.addEventListener("click", (event) => {
	    modals.forEach((modal) => {
	      if (event.target === modal) {
	        modal.style.display = "none";
	      }
	    });
	  });
	});
  </script>
</html>
