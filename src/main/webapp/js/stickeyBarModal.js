document.addEventListener("DOMContentLoaded", function() {
    const wishButton = document.querySelector(".btn_wish2");

    wishButton.addEventListener("click", function(event) {
        event.preventDefault(); // 기본 동작 방지

        // 상태 토글 (활성화 ↔ 비활성화)
        if (wishButton.classList.contains("disabled")) {
            // 비활성화 상태 -> 활성화 상태로 변경
            wishButton.classList.remove("disabled");
            wishButton.textContent = "관심등록"; // 텍스트 변경
        } else {
            // 활성화 상태 -> 비활성화 상태로 변경
            wishButton.classList.add("disabled");
            wishButton.textContent = "관심등록됨"; // 텍스트 변경
        }
    });
});

// 스티키 바 구매/판매 버튼 클릭시 모달 열기
document.getElementById("sticky-buy-btn").addEventListener("click", function() {
  document.getElementById("sticky-buy-modal").style.display = "flex";
});

document.getElementById("sticky-sell-btn").addEventListener("click", function() {
  document.getElementById("sticky-sell-modal").style.display = "flex";
});

// 스티키 바 모달 닫기
document.getElementById("sticky-buy-close").addEventListener("click", function() {
  document.getElementById("sticky-buy-modal").style.display = "none";
});

document.getElementById("sticky-sell-close").addEventListener("click", function() {
  document.getElementById("sticky-sell-modal").style.display = "none";
});