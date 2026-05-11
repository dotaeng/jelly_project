<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%= request.getContextPath() %>/js/listStyleLike.js"></script>
<!-- post list -->
<c:forEach var="post" items="${postList}">
	<div class="post-card">
	<a href="${pageContext.request.contextPath}/jelly?page=styleDetail&postId=${post.postId}">
		<div class="imageWrapper">
			<img src="${post.postImageUrl}" alt="${post.nickname}'s post">
		</div>
	</a>
		<div class="underPost">
		<a href="${pageContext.request.contextPath}/jelly?page=styleProfile&userId=${post.userId}" class="profileInfo">
			<img src="${post.profileImageUrl}" alt="${post.nickname}'s profile picture" class="profilePic"/>
			<span class="username">${post.nickname}</span>
		</a>
			<span class="likebtn">
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
			</span>
		</div>
		<div class="title">
			<p>${post.title}</p>
		</div>
		</div>
	
</c:forEach>





 
 
 

	
