<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/home/subHeader.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>프로필 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mypageCommon.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/loginInfo.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profileInfo.css" />
    <style>
        .nickname-edit-btn.active {
            background-color: black;
            color: white;
        }
    </style>
</head>
<body>
    <!-- 마이페이지 공통 -->
    <div class="mypage-container">
        <%@ include file="/views/mypage/mypageNavi.jsp"%>
        <!-- 마이페이지 -->
        <div class="mypage-content">
            <div class="mypageSubtitle">프로필 관리</div>
            <div class="profile-box">
				<div>
					<c:choose>
						<c:when test="${not empty userProfile.profileImage}">
							<img src="${userProfile.profileImage}" alt="프로필 사진"
								class="profile-image" />
						</c:when>
						<c:otherwise>
							<img src="${pageContext.request.contextPath}/img/profile.png"
								alt="기본 프로필 사진" class="profile-image" />
						</c:otherwise>
					</c:choose>
				</div>
				<div class="profile-info">
                    <span class="profile-user-nickname" id="nicknameDisplay">
                        <c:out value="${userProfile.nickname}" />
                    </span>
                    <br />
                    <span class="profile-user-email"><c:out value="${userProfile.email}" /></span>
                </div>
                <form action="${pageContext.request.contextPath}/uploadProfile" method="post" enctype="multipart/form-data">
                    <div class="profile-btns">
                        <label for="profileImageUpload" class="nickname-edit-btn">이미지 변경</label>
                        <input type="file" name="profileImage" id="profileImageUpload" accept=".png,.jpg,.jpeg" style="display: none;" />
                        <button type="submit" id="saveProfileImageBtn" class="nickname-edit-btn" style="display: none;">저장</button>
                    </div>
                </form>
            </div>
            <div class="loginInfo-content">
                <h3>프로필 정보</h3>
                <div class="unit">
                    <span class="unit-title">프로필 이름</span>
                    <div class="inputBox">
                        <span class="detailInfo" id="nicknameDisplayDetail">
                            <c:out value="${userProfile.nickname}" />
                        </span>
                        <input
                            type="text"
                            id="nicknameInputDetail"
                            value="<c:out value='${userProfile.nickname}' />"
                            style="display: none;"
                        />
                        <div class="button-group">
                            <button id="editNicknameBtnDetail" class="nickname-edit-btn">변경</button>
                            <button id="saveNicknameBtnDetail" class="nickname-edit-btn" style="display: none;">저장</button>
                            <button id="cancelNicknameBtnDetail" class="nickname-edit-btn" style="display: none;">취소</button>
                        </div>
                    </div>
                </div>
                <div class="unit">
                    <span class="unit-title">이름</span>
                    <div class="inputBox">
                        <span class="detailInfo"><c:out value="${userProfile.userName}" /></span>
                    </div>
                </div>
                <div class="unit">
                    <span class="unit-title">전화번호</span>
                    <div class="inputBox">
                        <span class="detailInfo"><c:out value="${userProfile.phoneNumber}" /></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        const profileImageUpload = document.getElementById('profileImageUpload');
        const saveProfileImageBtn = document.getElementById('saveProfileImageBtn');

        // 이미지 변경 버튼 클릭 시 저장 버튼 보이기
        profileImageUpload.addEventListener('change', () => {
            if (profileImageUpload.files.length > 0) {
                saveProfileImageBtn.style.display = 'inline-block';
                saveProfileImageBtn.classList.add('active');
            } else {
                saveProfileImageBtn.style.display = 'none';
                saveProfileImageBtn.classList.remove('active');
            }
        });

        const editNicknameBtn = document.getElementById('editNicknameBtnDetail');
        const saveNicknameBtn = document.getElementById('saveNicknameBtnDetail');
        const cancelNicknameBtn = document.getElementById('cancelNicknameBtnDetail');
        const nicknameDisplay = document.getElementById('nicknameDisplayDetail');
        const nicknameInput = document.getElementById('nicknameInputDetail');

        // 닉네임 변경 버튼 클릭
        editNicknameBtn.addEventListener('click', () => {
            editNicknameBtn.style.display = 'none';
            nicknameDisplay.style.display = 'none';
            nicknameInput.style.display = 'inline';
            saveNicknameBtn.style.display = 'inline';
            cancelNicknameBtn.style.display = 'inline';
        });

        // 닉네임 저장 버튼 클릭
        saveNicknameBtn.addEventListener('click', async () => {
            const newNickname = nicknameInput.value;

            // 서버로 데이터 전송
            const response = await fetch('${pageContext.request.contextPath}/jelly', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `page=updateProfileInfo&nickname=` + encodeURIComponent(newNickname),
            });

            if (response.ok) {
                alert('닉네임이 성공적으로 변경되었습니다.');
                nicknameDisplay.textContent = newNickname;
            } else {
                alert('닉네임 변경에 실패했습니다.');
            }

            // 원래 상태로 되돌리기
            cancelNicknameBtn.click();
        });

        // 닉네임 취소 버튼 클릭
        cancelNicknameBtn.addEventListener('click', () => {
            editNicknameBtn.style.display = 'inline';
            nicknameDisplay.style.display = 'inline';
            nicknameInput.style.display = 'none';
            saveNicknameBtn.style.display = 'none';
            cancelNicknameBtn.style.display = 'none';
        });
    </script>
</body>
</html>