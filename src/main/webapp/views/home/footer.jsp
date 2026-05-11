<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/common.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/footer.css">
  <title>Footer</title>
</head>
<body>
  <div class="footer-section-container">
    <hr class="footer-section-divider">
    <div class="footer-section-content">
      <div class="footer-service-area">
        <strong class="footer-service-title">고객센터
          <a href="tel:1588-0000" class="footer-service-tel">1588-0000</a>
        </strong>
        <div class="footer-service-time">
          <p>운영시간 평일 10:00 - 18:00 (토∙일, 공휴일 휴무)</p>
          <p>점심시간 평일 13:00 - 14:00</p>
        </div>
        <p class="footer-service-noti">1:1 문의하기는 앱에서만 가능합니다.</p>
        <button class="footer-btn-faq" onclick="location.href='<%= request.getContextPath() %>/jelly?page=faq'">자주 묻는 질문</button>
      </div>

      <div class="footer-menu">
        <div class="footer-menu-box">
          <strong>이용안내</strong>
          <ul>
            <li><a href="#">검수기준</a></li>
            <li><a href="#">이용정책</a></li>
            <li><a href="#">페널티 정책</a></li>
            <li><a href="#">커뮤니티 가이드라인</a></li>
          </ul>
        </div>
        <div class="footer-menu-box">
          <strong>고객지원</strong>
          <ul>
            <li><a href="<%= request.getContextPath() %>/jelly?page=notice">공지사항</a></li>
            <li><a href="<%= request.getContextPath() %>/about" target="_blank">서비스 소개</a></li>
            <li><a href="<%= request.getContextPath() %>/showroom" target="_blank">스토어 안내</a></li>
            <li><a href="<%= request.getContextPath() %>/about/seller_reception" target="_blank">판매자 방문접수</a></li>
          </ul>
        </div>
      </div>
    </div>

    <div class="footer-business-info">
      <p>젤리 주식회사 · 대표 홍길동 | 사업자등록번호 : 000-00-00000 | 통신판매업 : 제 2024-서울종로-0000호 | 사업장소재지 : 서울시 종로구 율곡로10길 105 디아망 4층</p>
    </div>

    <div class="footer-bottom">
      <p class="footer-notice">젤리(주)는 통신판매 중개자로서 통신판매의 당사자가 아닙니다. 본 상품은 개별판매자가 등록한 상품으로 상품, 상품정보, 거래에 관한 의무와 책임은 각 판매자에게 있습니다.</p>
      <p class="footer-notice"> 실제 사이트가 아닌 개인의 연습용 사이트입니다.</p>
      <p class="footer-copyright">© Jelly Corp.</p>
    </div>
  </div>
</body>
</html>