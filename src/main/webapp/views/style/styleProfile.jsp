<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/views/home/header.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>STYLE PROFILE</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styleProfile.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/styleProfile.js"></script>
<script src="<%= request.getContextPath() %>/js/listStyleLike.js"></script>
</head>
<body>
	<div class="container">
		<!-- 사용자 정보 섹션 -->
		<div class="user-info">
			<div class="profile">
				<img src="${userVo.profileImage}" alt="프로필 이미지" />
			</div>
			<div class="info">
				<div class="name">
					<span>${userVo.nickname}</span>
					<c:choose>
						<c:when test="${sessionScope.user != null && sessionScope.user.userId == userVo.userId}">
							<!-- 본인인 경우 스타일 업로드 버튼 표시 -->
							<div id="upload-style-btn" class="upload-style-btn">
								<a href="<%= request.getContextPath() %>/upload?page=stylePost">스타일 업로드</a>
							</div>
						</c:when>
						<c:otherwise>
							<button id="follow-btn" class="follow-btn ${isFollow ? 'following' : ''}" 
								data-context-path="${pageContext.request.contextPath}" 
								data-user-id="${userVo.userId}">
							  ${isFollow ? '팔로잉' : '팔로우'}
							</button>
						</c:otherwise>
					</c:choose>
				</div>
				<div class="follow-stats">
					<span id="follower-count" 
						data-context-path="${pageContext.request.contextPath}" 
						data-user-id="${userVo.userId}">
						팔로워 ${userVo.followerCount}
					</span>
					<span id="following-count"
						data-context-path="${pageContext.request.contextPath}" 
						data-user-id="${userVo.userId}">
						팔로잉 ${userVo.followingCount}
					</span>
				</div>
			</div>
		</div>
		
		<!-- 팔로워 모달 오버레이 -->
		<div id="follower-modal" class="modal-overlay">
		    <div class="modal-content">
		        <h3>팔로워 리스트</h3>
		        <div class="modal-ajax-content">
		            <!-- 팔로워 리스트 항목 (AJAX에서 데이터를 받아서 여기서 채움) -->		        
		        </div>
				<button class="modal-close" id="modal-close">&times;</button>
		    </div>
		</div>
		<!-- 팔로잉 모달 오버레이 -->
		<div id="following-modal" class="modal-overlay">
		    <div class="modal-content">
		        <h3>팔로잉 리스트</h3>
		        <div class="modal-ajax-content">
		            <!-- 팔로잉 리스트 항목 (AJAX에서 데이터를 받아서 여기서 채움) -->
		        </div>
				<button class="modal-close" id="modal-close">&times;</button>
		    </div>
		</div>

		<!-- 탭 섹션 -->
		<div class="tabs">
			<div class="tab active" data-target="#tab-posts">게시물 (${postList.size()})</div>
			<div class="tab" data-target="#tab-products">태그 상품 (${productSet.size()})</div>
			<c:if test="${sessionScope.user != null && sessionScope.user.userId == userVo.userId}">
				<!-- 본인인 경우에만 관심 스타일 탭 표시 -->
				<div class="tab" data-target="#tab-saved">관심 스타일 (${savedList.size()})</div>
			</c:if>
		</div>

		<!-- 탭 콘텐츠 섹션 -->
		<div id="tab-posts" class="tab-content active">
			<div class="posts">
				<c:forEach var="post" items="${postList}">
					<a href="${pageContext.request.contextPath}/jelly?page=styleDetail&postId=${post.postId}" class="post-card">
						<div class="imageWrapper">
							<img src="${post.thumbnailImageUrl}" alt="게시물 썸네일" />
						</div>
						<div class="underPost">
							<img 
						        id="like-btn-${post.postId}" 
						        class="like-btn"
						        src="<%= request.getContextPath() %>/img/${post.like ? 'after_like.png' : 'before_like.png'}"
						        alt="좋아요 버튼" 
						        data-context-path="<%= request.getContextPath() %>"
						        data-post-id="${post.postId}"
						        data-like-count = "${post.likeCount}"
						        onclick="return false;" />
			        
						    <span id="like-count-${post.postId}" class="like-count">${post.likeCount}</span>
						</div>
					</a>
				</c:forEach>
			</div>
		</div>

		<div id="tab-products" class="tab-content">
			<div class="tagged-products">
				<div class="product-grid">
					<c:forEach var="product" items="${productSet}">
						<a href="${pageContext.request.contextPath}/jelly?page=productDetail&productId=${product.productId}" class="product">
							<img src="${product.imageUrl}" alt="${product.productName}" />
							<p>${product.productName}</p>
							<p>${product.formattedPrice}원</p>
						</a>
					</c:forEach>
				</div>
			</div>
		</div>

		<div id="tab-saved" class="tab-content">
			<div class="posts">
				<c:forEach var="savedPost" items="${savedList}">
					<a href="${pageContext.request.contextPath}/jelly?page=styleDetail&postId=${savedPost.postId}" class="post-card">
						<div class="imageWrapper">
							<img src="${savedPost.thumbnailImageUrl}" alt="저장된 게시물 썸네일" />
						</div>
						<div class="underPost">
							<img 
						        id="like-btn-${savedPost.postId}" 
						        class="like-btn"
						        src="<%= request.getContextPath() %>/img/${savedPost.like ? 'after_like.png' : 'before_like.png'}"
						        alt="좋아요 버튼" 
						        data-context-path="<%= request.getContextPath() %>"
						        data-post-id="${savedPost.postId}"
						        data-like-count = "${savedPost.likeCount}"
						        onclick="return false;" />
						        <span id="like-count-${savedPost.postId}" class="like-count">${savedPost.likeCount}</span>
						</div>
					</a>
				</c:forEach>
			</div>
		</div>
	</div>

	<%@ include file="/views/home/footer.jsp"%>
</body>
</html>
