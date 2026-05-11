$(document).ready(function () {
  const $commentMenu = $("#comment-menu");
  const $openCommentBtn = $("#open-comment-btn");
  const $closeBtn = $("#close-btn");
  const $applyBtn = $("#postComment");
  const $comments = $("#comments");
  let rep = 1;
  
  
  document.addEventListener("click", function (event) {
      if (!$commentMenu[0].contains(event.target) && event.target !== $closeBtn[0]) {
          $commentMenu.removeClass("open"); 
      }
  });

  // 필터 메뉴 열기
  $(document).on("click", "#open-comment-btn", function () {
    const contextPath = $(this).data("context-path");
    const postId = $(this).data("post-id");

    console.log(`[DEBUG] Opening comment menu for Post ID: ${postId}, Context Path: ${contextPath}`);

    $commentMenu.addClass("open");

    $.ajax({
      url: contextPath + '/comment',
      type: "POST",
      data: { postId: postId },
      dataType: 'html',
      beforeSend: function () {
        console.log("[DEBUG] Sending AJAX request to load comments...");
      },
      success: function (html) {
        console.log("[DEBUG] Successfully loaded comments.");
        $comments.html(html);
      },
      error: function (xhr, status, error) {
        console.error("[ERROR] Failed to load comments:", {
          status: status,
          error: error,
          responseText: xhr.responseText,
        });
        if (xhr.status === 401) {
          alert("로그인이 필요합니다. 로그인 페이지로 이동합니다.");
          window.location.href = contextPath + '/views/login/login.jsp';
        } else {
          alert("댓글을 불러오는 데 문제가 발생했습니다.");
        }
      },
    });
  });

  // 필터 메뉴 닫기
  $closeBtn.on("click", function () {
    console.log("[DEBUG] Closing comment menu.");
    $commentMenu.removeClass("open");
  });
  

  // 댓글 추가 버튼 클릭 이벤트
  $applyBtn.on("click", function () {
    const comment = $("#myComment").val();
    const contextPath = $(this).data("context-path");
    const postId = $(this).data("post-id");
    let commentCount = parseInt($(this).data("comment-count"), 10) || 0;

    if (!comment || comment.trim() === "") {
      alert("댓글 내용을 입력해주세요.");
      console.log("[DEBUG] Comment is empty.");
      return;
    }

    console.log(`[DEBUG] Adding comment. Post ID: ${postId}, Comment: ${comment}`);

    $.ajax({
      url: contextPath + '/comment',
      type: "POST",
      data: { postId: postId, comment: comment.trim() },
      dataType: 'html',
      beforeSend: function () {
        console.log("[DEBUG] Sending AJAX request to add comment...");
      },
      success: function (html) {
        console.log("[DEBUG] Comment successfully added.");
        $comments.html(html);

        if (comment) {
          commentCount += rep;
          rep++;
          $("#comment-count").text(commentCount);
        }

        $("#myComment").val('');
      },
      error: function (xhr, status, error) {
        console.error("[ERROR] Failed to add comment:", {
          status: status,
          error: error,
          responseText: xhr.responseText,
        });
        if (xhr.status === 401) {
          alert("로그인이 필요합니다. 로그인 페이지로 이동합니다.");
          window.location.href = contextPath + '/views/login/login.jsp';
        } else {
          alert("댓글 작성 중 문제가 발생했습니다.");
        }
      },
    });
  });

  // 댓글 삭제 이벤트
  $comments.on("click", ".delete-comment-btn", function () {
    const deleteCommentId = $(this).data("comment-id");
    const contextPath = $(this).data("context-path");
    const postId = $(this).data("post-id");

    console.log(`[DEBUG] Deleting comment. Comment ID: ${deleteCommentId}, Post ID: ${postId}`);

    $.ajax({
      url: contextPath + '/comment',
      type: "POST",
      data: { deleteCommentId: deleteCommentId, postId: postId },
      beforeSend: function () {
        console.log("[DEBUG] Sending AJAX request to delete comment...");
      },
      success: function (html) {
        console.log("[DEBUG] Comment successfully deleted.");
        $comments.html(html);

        const commentCount = parseInt($("#comment-count").text(), 10) - 1;
        $("#comment-count").text(commentCount > 0 ? commentCount : 0);
      },
      error: function (xhr, status, error) {
        console.error("[ERROR] Failed to delete comment:", {
          status: status,
          error: error,
          responseText: xhr.responseText,
        });
        if (xhr.status === 401) {
          alert("로그인이 필요합니다. 로그인 페이지로 이동합니다.");
          window.location.href = contextPath + '/views/login/login.jsp';
        } else {
          alert("댓글 삭제 중 문제가 발생했습니다.");
        }
      },
    });
  });
});