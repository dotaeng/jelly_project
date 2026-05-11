$(document).ready(function () {
          const contextPath = $("#wishlist-button").data("context-path");
          const productId = $("#wishlist-button").data("product-id");
          const userId = $("#wishlist-button").data("user-id");
          const wishlistButton = $("#wishlist-button");
          const wishlistText = $("#wishlist-text");

          // 페이지 로드 시 관심상품 상태 확인
          if (userId) {
              console.log("로그인 된 사용자. 관심상품 상태 확인 중");
              $.ajax({
                  url: contextPath + '/jelly', 
                  type: "POST",
                  data: {
                      page: "checkWishlist",
                      productId: productId,
                      userId: userId
                  },
                  success: function (response) {
                      if (response.isWishlist) {
                          wishlistButton.addClass("active");
                          wishlistText.text("관심상품에 추가됨");
                      } else {
                          wishlistButton.removeClass("active");
                          wishlistText.text("관심상품");
                      }
                  },
                  error: function (xhr, status, error) {
                      console.error("응답 내용:", xhr.responseText);
                  }
              });
          }

          // 관심상품 토글 이벤트
          wishlistButton.on("click", function () {
              if (!userId) {
                  const currentUrl = window.location.href;
                  window.location.href = contextPath + "/jelly?page=login&redirect=" + encodeURIComponent(currentUrl);
                  return;
              }

              $.ajax({
                  url: contextPath + '/jelly',
                  type: "POST",
                  data: {
                      page: "wishlistToggle",
                      productId: productId,
                      userId: userId
                  },
                  success: function (response) {
                      if (response.status === "added") {
                          wishlistButton.addClass("active");
                          wishlistText.text("관심상품에 추가됨");
                      } else if (response.status === "removed") {
                          wishlistButton.removeClass("active");
                          wishlistText.text("관심상품");
                      }
                  },
                  error: function (xhr, status, error) {
                      alert("서버 요청 중 오류 발생. 다시 시도해주세요.");
                  }
              });
          });
      });