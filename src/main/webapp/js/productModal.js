document.addEventListener("DOMContentLoaded", function () {
	  // 공통 모달 열기 함수
	  function openModal(triggerId, modalId, loginRequired = false) {
	    const trigger = document.getElementById(triggerId);
	    const modal = document.getElementById(modalId);

	    if (trigger && modal) {
	      trigger.addEventListener("click", function () {
	        if (loginRequired && !loggedIn) {
	          location.href = contextPath + "/jelly?page=login";
	        } else {
	          modal.style.display = "flex";
	        }
	      });
	    }
	  }

	  // 공통 모달 닫기 함수
	  function closeModal(closeBtnId, modalId) {
	    const closeBtn = document.getElementById(closeBtnId);
	    const modal = document.getElementById(modalId);

	    if (closeBtn && modal) {
	      closeBtn.addEventListener("click", function () {
	        modal.style.display = "none";
	      });
	    }
	  }

	  // 모달 열기/닫기 설정
	  openModal("size-dropdown", "size-modal", true); // 모든 사이즈 모달 (로그인 필요)
	  closeModal("modal-close", "size-modal");

	  openModal("buy-btn", "buy-modal", true); // 구매 모달 (로그인 필요)
	  closeModal("buy-modal-close", "buy-modal");

	  openModal("sell-btn", "sell-modal", true); // 판매 모달 (로그인 필요)
	  closeModal("sell-modal-close", "sell-modal");

	  // 관심상품 버튼 이벤트
	  const wishlistBtnLogin = document.getElementById("wishlist-button");
	  if (wishlistBtnLogin) {
	    wishlistBtnLogin.addEventListener("click", function () {
	    });
	  }

	  const wishlistBtnNoLogin = document.getElementById("wishlist-button-nologin");
	  if (wishlistBtnNoLogin) {
	    wishlistBtnNoLogin.addEventListener("click", function () {
	      location.href = contextPath + "/jelly?page=login";
	    });
	  }
	});
	