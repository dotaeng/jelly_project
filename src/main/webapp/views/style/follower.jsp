<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="follower" items="${followerUserList}">
    <div class="follow-item">
    	<a href="<%= request.getContextPath() %>/jelly?page=styleProfile&userId=${follower.userId}">
    		<img src="${follower.profileImage}" alt="${follower.nickname}의 프로필 이미지" class="follow-image">
    		<span class="follow-name">${follower.nickname}</span>    	
    	</a>
	</div>
</c:forEach>