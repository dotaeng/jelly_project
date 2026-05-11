<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/home/header.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>STYLE MODIFY</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/postNewStyle.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/postNewStyle.js"></script>
  	<style>
  		/* Black & White Themed Form Styles */
		#formContainer {
		    max-width: 600px;
		    margin: 50px auto;
		    padding: 20px;
		    border-radius: 10px;
		    background-color: #fff; /* White background */
		    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* Subtle shadow */
		    color: #000; /* Black text for contrast */
		}
		
		/* Form Labels */
		#formContainer label {
		    display: block;
		    margin-top: 15px;
		    font-weight: bold;
		    color: #000; /* Black labels */
		}
		
		/* Input Fields, Textarea, Select, and File Upload */
		#formContainer input[type="text"],
		#formContainer textarea,
		#formContainer select,
		#formContainer input[type="file"] {
		    width: calc(100% - 20px);
		    margin-top: 5px;
		    padding: 10px;
		    border: 1px solid #EBEBEB; /* Black borders */
		    border-radius: 5px;
		    font-size: 14px;
		    box-sizing: border-box;
		    background-color: #fff; /* White input background */
		    color: #222; /* Black text */
		    outline: none;
		}
		
		#formContainer input[type="text"]:focus,
		#formContainer textarea:focus,
		#formContainer select:focus,
		#formContainer input[type="file"]:focus {
		    border-color: #222; /* Black border on focus */
		    box-shadow: 0 0 5px rgba(0, 0, 0, 0.3); /* Subtle black glow */
		}
		
		/* Textarea Specific */
		#formContainer textarea {
		    height: 80px;
		    resize: vertical; /* Allow vertical resizing */
		}
		
		/* Submit Button */
		#formContainer input[type="submit"] {
		    display: block;
		    width: 75%;
		    margin: 10px auto;
		    padding: 10px;
		    background-color: #222; /* Black button */
		    color: #fff; /* White text */
		    font-weight: bold;
		    border: none;
		    border-radius: 6px;
		    cursor: pointer;
		    font-size: 16px;
		}
		
		#formContainer input[type="submit"]:hover {
		    background-color: #333; /* Slightly lighter black on hover */
		}
		
		/* Image Preview Section */
		#formContainer #imagePreview {
		    display: flex;
		    flex-wrap: wrap;
		    gap: 10px;
		    margin-top: 15px;
		}
		
		#formContainer #imagePreview img {
		    max-width: 100px;
		    max-height: 100px;
		    object-fit: cover;
		    border: 1px solid #222; /* Black border for preview images */
		    border-radius: 5px;
		    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Subtle shadow */
		}
		
		/* Delete Button Styling */
		#formContainer #deleteButton {
		    display: block;
		    width: 75%;
		    margin: 10px auto;
		    padding: 10px;
		    background-color: #ff4d4d; /* Bright red for delete */
		    color: #fff; /* White text for contrast */
		    font-weight: bold;
		    border: none;
		    border-radius: 6px;
		    cursor: pointer;
		    font-size: 16px;
		    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Subtle shadow for depth */
		    text-align: center; /* Center align text */
		}
		
		/* Delete Button Hover Effect */
		#formContainer #deleteButton:hover {
		    background-color: #cc0000; /* Darker red on hover */
		    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Stronger shadow on hover */
		}
		
  	
		
  	</style>
</head>
<body id="body">
    <div id="formContainer">
    <form action="<%= request.getContextPath() %>/upload" method="post" enctype="multipart/form-data" onsubmit="alert('updated')">
        <input type="hidden" name="uploadType" value="styleModify" />
        <input type="hidden" name="modifyingPostId" value="${postVo.postId }" />
        <input type="hidden" name="productIds" id="product-ids">

        <!-- Title Input -->
        <label for="titleInput">제목</label>
        <input type="text" name="title" id="titleInput" required
               value="<c:out value='${postVo.title}' />">

        <!-- Caption (Content) -->
        <label for="contentInput">내용</label>
        <textarea name="content" id="contentInput"><c:out value="${postVo.content}"/></textarea>

        <!-- Image Input -->
        <label for="imageInput">이미지 선택</label>
        <input type="file" name="postImages" id="imageInput" multiple="multiple" onchange="previewImages()" required>
        
        <!-- Image Preview Section -->
        <div id="imagePreview"></div>
        <br />

        <!-- Style Category Dropdown -->
        <label for="styleCategoryInput">카테고리</label>
        <select name="styleCategory" id="styleCategoryInput">
            <option value="1" <c:if test="${postVo.styleCategory == 1}">selected</c:if>>캐주얼</option>
            <option value="2" <c:if test="${postVo.styleCategory == 2}">selected</c:if>>스트릿</option>
            <option value="3" <c:if test="${postVo.styleCategory == 3}">selected</c:if>>모던</option>
            <option value="4" <c:if test="${postVo.styleCategory == 4}">selected</c:if>>빈티지</option>
            <option value="5" <c:if test="${postVo.styleCategory == 5}">selected</c:if>>미니멀</option>
            <option value="6" <c:if test="${postVo.styleCategory == 6}">selected</c:if>>포멀</option>
        </select>
        
        <!-- 상품 태그 추가 버튼 -->
        <button type="button" id="tagBtn" class="tag-btn">상품태그 추가</button>
        <div id="tag-products" class="tag-products">
        </div>

        <!-- Submit Button -->
        <input type="submit" id="submitButton" value="수정">
    </form>
    
    
     <!-- Delete Form -->
    <form action="<%= request.getContextPath() %>/jelly?page=delete" method="post" onsubmit="return confirm('Are you sure you want to delete this post?');">
        <input type="hidden" name="postId" value="${postVo.postId }" />
        <button type="submit" id="deleteButton">삭제</button>
    </form>
	</div>

<!-- 상품 태그 모달 -->
   <div id="product-modal" class="modal-overlay">
    <div class="modal-content">
        <h3>상품 검색</h3>
        <form class="search-form" action="<%= request.getContextPath() %>/jelly?page=search" method="get" data-context-path="${pageContext.request.contextPath}">
            <button type="submit">
                <svg width="17" height="16" fill="none" xmlns="http://www.w3.org/2000/svg" role="img" aria-labelledby="search">
                    <path d="M7.667 12.667A5.333 5.333 0 107.667 2a5.333 5.333 0 000 10.667zM14.334 14l-2.9-2.9" stroke="currentColor" stroke-width="1.333" stroke-linecap="round" stroke-linejoin="round"></path>
                </svg>
            </button>
            <input class="search-input" placeholder="검색어를 입력해주세요" name="query" required="" type="text">
            <button class="search-reset" type="reset">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"></path>
                </svg>
            </button>
        </form>
        
        <div id="product-results">
            <!-- 검색 결과 여기에 표시 -->
        </div>
        
        <p>선택된 상품</p>
        <div id="selected-product">
            <!-- 선택된 상품 여기에 표시 -->
        </div>
        <button class="modal-close" id="modal-close">&times;</button>
        <button id="add-products-btn" class="add-products-btn">추가</button>
    </div>
</div>
    

    <script>
        function previewImages() {
            const files = document.getElementById('imageInput').files;
            const previewContainer = document.getElementById('imagePreview');
            previewContainer.innerHTML = '';

            if (files.length > 0) {
                for (let i = 0; i < files.length; i++) {
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        const img = document.createElement('img');
                        img.src = e.target.result;
                        previewContainer.appendChild(img);
                    };
                    reader.readAsDataURL(files[i]);
                }
            }
        }
    </script>
    <%@ include file="/views/home/footer.jsp" %>
</body>
</html>