// 버튼 클릭 시 맨 위로 이동
document.getElementById('btnTop').addEventListener('click', function() {
    window.scrollTo({ top: 0, behavior: 'smooth' }); // 부드럽게 스크롤
});

// 스크롤 이벤트로 버튼 표시/숨김 제어
window.addEventListener('scroll', function() {
    const btnTop = document.getElementById('btnTop');
    if (window.scrollY > 200) { // 200px 이상 스크롤 시 버튼 표시
        btnTop.style.display = 'block';
    } else {
        btnTop.style.display = 'none';
    }
});