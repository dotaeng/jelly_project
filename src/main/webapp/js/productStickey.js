// Sticky Bar JavaScript입니다
document.addEventListener("DOMContentLoaded", () => {
  const stickyBar = document.getElementById("stickyBar");

  window.addEventListener("scroll", () => {
    const scrollY = window.scrollY || window.pageYOffset;

    // 스크롤이 100px 이상 내려가면 Sticky Bar 표시
    if (scrollY > 100) {
      stickyBar.style.display = "flex";
    } else {
      stickyBar.style.display = "none";
    }
  });
});