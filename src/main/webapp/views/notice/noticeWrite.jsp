<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/home/subHeader.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>공지사항 작성</title>
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
            height: 700,
            branding: false, 
            content_style: 'body { font-family: Arial, sans-serif; font-size: 14px; }'
        });
    </script>
    <style>
        .notice-write-body {
            margin: 20px;
            padding: 0;
        }
        .notice-write-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            border-radius: 5px;
            min-height: 700px;
            display: flex;
            flex-direction: column;
            justify-content: space-between; 
        }
        .notice-write-header {
            font-size: 24px;
            margin-bottom: 20px;
        }
        .notice-write-label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }
        .notice-write-input, .notice-write-textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        .notice-write-button-container {
            display: flex;
            justify-content: flex-end;
        }
        .notice-write-button {
            background-color: #222222;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
    </style>
</head>
<body class="notice-write-body">
    <div class="notice-write-container">
        <h1 class="notice-write-header">공지사항 작성</h1>
        <form action="/haribo/jelly?page=noticeWrite" method="post">
            <label for="title" class="notice-write-label">제목</label>
            <input type="text" id="title" name="title" class="notice-write-input" placeholder="제목을 입력하세요" required>

            <label for="content" class="notice-write-label">내용</label>
            <textarea id="content" name="content" class="notice-write-textarea" placeholder="내용을 입력하세요"></textarea>

            <div class="notice-write-button-container">
                <button type="submit" class="notice-write-button">저장</button>
            </div>
        </form>
    </div>
    <%@ include file="/views/home/footer.jsp" %>
</body>
</html>