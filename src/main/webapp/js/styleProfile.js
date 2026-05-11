	$(document).ready(function () {
	$("#follow-btn").on("click", function() {
		const contextPath = $(this).data("context-path");
		const followingId = $(this).data("user-id");
		const followBtn = $(this);
		const followerCount = $("#follower-count");
		const followingCount = $("#following-count");

		$.ajax({
			url: contextPath + '/follow',
			type: "POST",
			data: {followingId: followingId},
			success: function(response) {
				if (response.isFollow) {
			      // 팔로우 -> 팔로잉 상태로 변경
			      followBtn.addClass("following").text("팔로잉");
			    } else {
			      // 팔로잉 -> 팔로우 상태로 변경
			      followBtn.removeClass("following").text("팔로우");
			    }
				followerCount.text("팔로워 " + response.followerCount);
				followingCount.text("팔로잉 " + response.followingCount);
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
	})
			
    // 탭 전환 로직
    $('.tab').on('click', function () {
        $('.tab').removeClass('active');
        $(this).addClass('active');

        const target = $(this).data('target');
        $('.tab-content').removeClass('active');
        $(target).addClass('active');
    });

	// 팔로워 모달 열기
   $('#follower-count').on('click', function () {
	   const contextPath = $(this).data("context-path");
	   const userId = $(this).data("user-id");
       $.ajax({
           url: contextPath + '/jelly?page=follower&userId=' + userId,  // 서버에서 팔로워 리스트를 가져오는 URL
           type: 'GET',
		   dataType: 'html',
           success: function (html) {
               // 모달 콘텐츠에 팔로워 목록 추가
               $('#follower-modal .modal-content .modal-ajax-content').html(html);
               $('#follower-modal').css('display', 'flex');
           },
           error: function (xhr, status, error) {
               console.error("Ajax 오류:", status, error);
               alert("서버 요청 중 오류가 발생했습니다.");
           }
       });
   });

   // 팔로잉 모달 열기
   $('#following-count').on('click', function () {
	   const contextPath = $(this).data("context-path");
	   const userId = $(this).data("user-id");
       $.ajax({
           url: contextPath + '/jelly?page=following&userId=' + userId,  // 서버에서 팔로잉 리스트를 가져오는 URL
           type: 'GET',
		   dataType: 'html',
           success: function (html) {
               // 모달 콘텐츠에 팔로잉 목록 추가
               $('#following-modal .modal-content .modal-ajax-content').html(html);
               $('#following-modal').css('display', 'flex');
           },
           error: function (xhr, status, error) {
               console.error("Ajax 오류:", status, error);
               alert("서버 요청 중 오류가 발생했습니다.");
           }
       });
   });

    // 모달 닫기
    $('.modal-close').on('click', function () {
        $('.modal-overlay').css('display', 'none');
    });

    // 모달 외부 클릭 시 닫기
    $(document).on('click', function (e) {
        if ($(e.target).hasClass('modal-overlay')) {
            $('.modal-overlay').css('display', 'none');
        }
    });
});
