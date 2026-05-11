<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/home/subHeader.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>공지사항 수정</title>
    <!-- TinyMCE Script -->
    <script src="https://cdn.tiny.cloud/1/4p8ix71mi8nsbipixchff4jnrh0hw33f2gov6r1axfanvlgy/tinymce/7/tinymce.min.js" referrerpolicy="origin"></script>
    <script>
        tinymce.init({
            selector: '#content',
            plugins: [
                'anchor', 'autolink', 'charmap', 'codesample', 'emoticons', 'image', 'link', 
                'lists', 'media', 'searchreplace', 'table', 'visualblocks', 'wordcount'
            ],
            toolbar: 'undo redo | bold italic underline strikethrough | alignleft aligncenter alignright | numlist bullist | link image media table | removeformat',
            menubar: true, 
            height: 500,
            branding: false, 
            content_style: 'body { font-family: Arial, sans-serif; font-size: 14px; }'
        });
    </script>
    <style>
        .edit-container {
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
        }
        .edit-container h2 {
            text-align: left;
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
        }
        .edit-form {
            display: flex;
            flex-direction: column;
        }
        .edit-form label {
            margin-bottom: 10px;
            font-weight: bold;
            font-size: 16px;
            color: #555;
            text-align: left;
        }
        .edit-form input[type="text"], .edit-form textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        .edit-form .button-container {
            text-align: right;
        }
        .edit-form button {
            background-color: #222;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .edit-form button:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
    <div class="edit-container">
        <h2>공지사항 수정</h2>
        <form class="edit-form" action="${pageContext.request.contextPath}/jelly?page=noticeEditOk" method="post">
            <input type="hidden" name="noticeId" value="${notice.noticeId}">
            <label for="title">제목:</label>
            <input type="text" id="title" name="title" value="${notice.title}" required>
            <label for="content">내용:</label>
            <textarea id="content" name="content" required>${notice.content}</textarea>
            <div class="button-container">
                <button type="submit">수정</button>
            </div>
        </form>
    </div>
    <%@ include file="/views/home/footer.jsp" %>
</body>
</html>