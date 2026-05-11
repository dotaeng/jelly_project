<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/views/home/subHeader.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.tier-details {
  margin-block-start: 1em;
  margin-block-end: 1em;
  margin-inline-start: 0px;
  margin-inline-end: 0px;
  unicode-bidi: isolate;
}

.tier-level {
  font-feature-settings: "tnum";
  font-size: 24px;
  font-variant-numeric: tabular-nums;
  font-weight: 700;
  letter-spacing: -.36px;
  padding-right: 20px;
  width: 45%;
}

.tier-benefits {
  font-size: 1rem;
  line-height: 1.6;
}

.benefit-item {
  margin-bottom: 15px;
}

.benefit-label {
  color: rgba(34, 34, 34, .4);
  display: flex;
  flex-direction: column;
  font-size: 13px;
  letter-spacing: -.07px;
}

.benefit-value {
  color: #222;
  font-size: 15px;
  font-weight: 600;
  letter-spacing: -.15px;
  margin-top: 4px;
}

.benefit-value br {
  margin-bottom: 10px;
}

.tier-divider {
  width: 100%;
  height: 1px;
  background-color: #eee;
  margin: 20px 0;
}

.tier-guidelines {
  margin: 20px 0;
  font-family: Arial, sans-serif;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.guidelines-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 15px;
  color: #333;
}

.guidelines-list {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.guidelines-item {
  margin-bottom: 10px;
}

.guidelines-description {
  margin: 0;
  font-size: 1rem;
  line-height: 1.5;
  color: #555;
}


</style>
</head>
<body>
	<div class="tier-details">
	  <div class="tier-level">LV.5</div>
	  <div class="tier-benefits">
	    <div class="benefit-item">
	      <span class="benefit-label">판매 수수료</span>
	      <strong class="benefit-value">최대 3.5%</strong>
	    </div>
	    <div class="benefit-item">
	      <span class="benefit-label">전월 총 정산 금액</span>
	      <strong class="benefit-value">6000만원 이상</strong>
	    </div>
	    <div class="benefit-item">
	      <span class="benefit-label">판매자 센터 이용 시</span>
	      <strong class="benefit-value">
	        창고보관 이용권 10회<br>
	        페널티 100건 면제 (가품, 중고품 페널티 제외)
	      </strong>
	    </div>
	  </div>
	</div>
	
	<div class="tier-details">
	  <div class="tier-level">LV.4</div>
	  <div class="tier-benefits">
	    <div class="benefit-item">
	      <span class="benefit-label">판매 수수료</span>
	      <strong class="benefit-value">최대 3.6%</strong>
	    </div>
	    <div class="benefit-item">
	      <span class="benefit-label">전월 총 정산 금액</span>
	      <strong class="benefit-value">2000만원 이상 6000만원 미만</strong>
	    </div>
	    <div class="benefit-item">
	      <span class="benefit-label">판매자 센터 이용 시</span>
	      <strong class="benefit-value">페널티 30건 면제 (가품, 중고품 페널티 제외)</strong>
	    </div>
	  </div>
	</div>
	
	<div class="tier-details">
	  <div class="tier-level">LV.3</div>
	  <div class="tier-benefits">
	    <div class="benefit-item">
	      <span class="benefit-label">판매 수수료</span>
	      <strong class="benefit-value">최대 3.7%</strong>
	    </div>
	    <div class="benefit-item">
	      <span class="benefit-label">전월 총 정산 금액</span>
	      <strong class="benefit-value">1000만원 이상 2000만원 미만</strong>
	    </div>
	    <div class="benefit-item">
	      <span class="benefit-label">판매자 센터 이용 시</span>
	      <strong class="benefit-value">페널티 15건 면제 (가품, 중고품 페널티 제외)</strong>
	    </div>
	  </div>
	</div>
	
	<div class="tier-details">
	  <div class="tier-level">LV.2</div>
	  <div class="tier-benefits">
	    <div class="benefit-item">
	      <span class="benefit-label">판매 수수료</span>
	      <strong class="benefit-value">최대 3.85%</strong>
	    </div>
	    <div class="benefit-item">
	      <span class="benefit-label">전월 총 정산 금액 or 전월 총 정산 건수</span>
	      <strong class="benefit-value">
	        200만원 이상 1000만원 미만 or 10건 이상<br>
	        (2개 조건 중 한 가지만 충족해도 LV.2로 산정)
	      </strong>
	    </div>
	    <div class="benefit-item">
	      <span class="benefit-label">판매자 센터 이용 시</span>
	      <strong class="benefit-value">페널티 10건 면제 (가품, 중고품 페널티 제외)</strong>
	    </div>
	  </div>
	</div>
	
	<div class="tier-details">
	  <div class="tier-level">LV.1</div>
	  <div class="tier-benefits">
	    <div class="benefit-item">
	      <span class="benefit-label">판매 수수료</span>
	      <strong class="benefit-value">최대 4.0%</strong>
	    </div>
	    <div class="benefit-item">
	      <span class="benefit-label">전월 총 정산 금액</span>
	      <strong class="benefit-value">200만원 미만</strong>
	    </div>
	    <div class="benefit-item">
	      <span class="benefit-label">판매자 센터 이용 시</span>
	      <strong class="benefit-value">페널티 5건 면제 (가품, 중고품 페널티 제외)</strong>
	    </div>
	  </div>
	</div>
	
	<div class="tier-guidelines">
	  <h2 class="guidelines-title">등급 산정 안내</h2>
	  <ul class="guidelines-list">
	    <li class="guidelines-item">
	      <p class="guidelines-description">매월 1일 전월의 '총 정산 금액' or 총 정산 건수를 반영하여, 매월 2일부터 적용됩니다.</p>
	    </li>
	    <li class="guidelines-item">
	      <p class="guidelines-description">창고보관으로 구매 후 재판매 주문건은 제외됩니다.</p>
	    </li>
	    <li class="guidelines-item">
	      <p class="guidelines-description">별도 혜택이 적용되는 프로모션은 해당 프로모션의 수수료가 적용됩니다.</p>
	    </li>
	    <li class="guidelines-item">
	      <p class="guidelines-description">향후 판매자의 등급 선정 기준 및 혜택은 사전 통지 후 변경될 수 있습니다.</p>
	    </li>
	    <li class="guidelines-item">
	      <p class="guidelines-description">등급 수수료는 기본수수료 (일반 5,000원/프리미엄 20,000원)와 별개이며, 모든 수수료는 VAT 별도입니다.</p>
	    </li>
	  </ul>
	</div>
	
	

</body>
<%@ include file="/views/home/footer.jsp" %>
</html>