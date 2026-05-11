<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Style and Popular</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/mainstylepopular.css">
  <script src="<%= request.getContextPath() %>/js/listStyleLike.js"></script>
</head>
<body class="main-style-popular-body">

  <div class="main-style-popular-container">
    <!-- STYLE 섹션 -->
    <div class="main-style-popular-section">
      <h2>
        STYLE
        <a href="<%= request.getContextPath() %>/jelly?page=styleList">&gt;</a>
      </h2>
      <div class="main-style-popular-style-items">
        <c:forEach var="post" items="${postList}">
          <div class="main-style-popular-style-item">
            <a href="${pageContext.request.contextPath}/jelly?page=styleDetail&postId=${post.postId}" class="post-card">
              <div class="imageWrapper">
                <img src="${post.postImageUrl}" alt="${post.nickname}'s post">
              </div>
              <div class="underPost">
                <img src="${post.profileImageUrl}" alt="${post.nickname}'s profile picture" class="profilePic" />
                <span class="username">${post.nickname}</span>
                <span class="likebtn">
                  <img 
                    id="like-btn-${post.postId}" 
                    class="like-btn" 
                    src="<%= request.getContextPath() %>/img/${post.like ? 'after_like.png' : 'before_like.png'}"
                    alt="좋아요 버튼" 
                    data-context-path="<%= request.getContextPath() %>" 
                    data-post-id="${post.postId}"
                    data-like-count="${post.likeCount}"
                    onclick="return false;" />
                  <span id="like-count-${post.postId}" class="like-count">${post.likeCount}</span>
                </span>
              </div>
            </a>
          </div>
        </c:forEach>
      </div>
    </div>

    <!-- 세로 구분선 -->
    <div class="main-style-popular-divider"></div>

    <!-- POPULAR 섹션 -->
    <div class="main-style-popular-section">
      <h2>
        POPULAR
        <a href="<%= request.getContextPath() %>/jelly?page=popular">&gt;</a>
      </h2>
      <div class="main-style-popular-popular-items">
        <c:forEach var="product" items="${popularProducts}" varStatus="status">
          <c:if test="${status.index < 4}">
            <div class="main-style-popular-popular-item">
              <a class="image-link" href="<%= request.getContextPath() %>/jelly?page=productDetail&productId=${product.productId}">
                <img src="${product.imageUrl}" alt="${product.productName}">
              </a>
              <div class="info">
                <div class="name">${product.productName}</div>
                <div class="description">${product.description}</div>
                <div class="price">${product.initialPrice}원</div>
              </div>
            </div>
          </c:if>
        </c:forEach>
      </div>
    </div>
  </div>

</body>
</html>