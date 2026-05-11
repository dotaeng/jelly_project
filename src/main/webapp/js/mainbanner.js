document.addEventListener('DOMContentLoaded', () => {
  // 배너 슬라이드와 관련된 DOM 요소들 가져오기
  let slide = document.querySelector('.banner-slide'); // 슬라이드를 포함하는 요소
  let images = document.querySelectorAll('.banner-slide img'); // 배너 이미지들
  let indicators = document.querySelectorAll('.indicator div'); // 인디케이터(슬라이드 하단의 점들)
  let prev = document.querySelector('.arrow.left'); // 이전 버튼
  let next = document.querySelector('.arrow.right'); // 다음 버튼
  let currentIndex = 0; // 현재 슬라이드의 인덱스, 처음은 0번째

  // 슬라이드를 보여주는 함수
  function showSlide(index) {
    let translateXValue = -(index * 100); // 현재 인덱스에 맞춰 translateX 계산
    slide.style.transform = `translateX(${translateXValue}%)`; // 슬라이드를 이동시킴
    indicators.forEach((dot, i) => {
      // 각 인디케이터의 활성화 여부를 설정 (현재 슬라이드와 일치하는 인디케이터에 'active' 클래스 추가)
      dot.classList.toggle('active', i === index);
    });
  }

  // 다음 슬라이드로 넘어가는 함수
  function nextSlide() {
    currentIndex = (currentIndex + 1) % images.length; // 슬라이드 인덱스를 1씩 증가시키되, 마지막 슬라이드에서 다시 첫 슬라이드로 돌아감
    showSlide(currentIndex); // 변경된 인덱스를 반영해 슬라이드를 보여줌
  }

  // 이전 슬라이드로 넘어가는 함수
  function prevSlide() {
    currentIndex = (currentIndex - 1 + images.length) % images.length; // 슬라이드 인덱스를 1씩 감소시키되, 첫 슬라이드에서 다시 마지막 슬라이드로 돌아감
    showSlide(currentIndex); // 변경된 인덱스를 반영해 슬라이드를 보여줌
  }

  // 다음 버튼 클릭 시 다음 슬라이드를 보여줌
  next.addEventListener('click', nextSlide);
  // 이전 버튼 클릭 시 이전 슬라이드를 보여줌
  prev.addEventListener('click', prevSlide);

  // 인디케이터(점) 클릭 시 해당하는 슬라이드로 이동
  indicators.forEach((dot, i) => {
    dot.addEventListener('click', () => {
      currentIndex = i; // 클릭한 인디케이터의 인덱스를 currentIndex로 설정
      showSlide(currentIndex); // 해당 인덱스를 반영해 슬라이드를 보여줌
    });
  });

  // 자동 슬라이드 (5초마다 자동으로 다음 슬라이드로 이동)
  setInterval(nextSlide, 5000);
});