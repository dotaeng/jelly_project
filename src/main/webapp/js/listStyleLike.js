$(document).ready(function () {
    const $likeBtn = $(".like-btn"); 
    

    $likeBtn.off("click").on("click", function () {
        const postId = $(this).data("post-id");
		const contextPath = $(this).data("context-path");
		
		
        console.log(`Like button pressed for post ID: ${postId}`);
        const likeBtn = $(this);
		const likeBtnId = `#like-count-${postId}`;
		const likeCountNum = $(likeBtnId);

        $.ajax({
            url: contextPath + '/likePost',
            type: "POST",
            data: {postId: postId},
            success: function(response) {
                // 좋아요 상태 반전
                likeBtn.attr("src", response.isLike ? contextPath + '/img/after_like.png' : contextPath + '/img/before_like.png');
                // 좋아요 개수 업데이트
                likeCountNum.text(response.likeCount);
            },
            error: function(xhr, status, error) {
                if (xhr.status === 401) { // 로그인 필요
                    alert("로그인이 필요합니다.");
                    window.location.href = contextPath + '/views/login/login.jsp'; // 로그인 페이지로 이동
                } else {
                    console.error("Ajax 오류:", status, error);
                    alert("서버 요청 중 오류가 발생했습니다.");
                }
            }
        })
    });
});
