<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/views/home/subHeader.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Jelly</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/mypageCommon.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/rank.css" />
</head>
<body>
	<div class="mypage-container">

		<%@ include file="/views/mypage/mypageNavi.jsp"%>

		<div class="mypage-content">
			<div class="mypageSubtitle">판매자 등급</div>
			<div class="myRank">
				<div class="myRankContent">
					<div class="items">
						<span>현재 등급</span> <span class="myRankTitle">${grade }</span>
					</div>
					<div class="divideline"></div>
					<div class="items">
						<span>판매 수수료</span> <span class="mySellFee">${sellFee }%</span>
					</div>
				</div>
				<div class="myRanknotice">
					<p>
						적용 기간 2024.12.02 - 2025.01.01<br /> &nbsp&nbsp&nbsp 다음 등급 업데이트
						2025.01.02
					</p>
				</div>
			</div>
			<div class="benefitPerRank">
				<h3>판매자 등급별 혜택</h3>
				<div class="rankGraph">
					<div class="graph" id="level1">
						<div>Lv.1</div>
						<div>4.0%</div>
					</div>
					<div class="graph" id="level2">
						<div>Lv.2</div>
						<div>3.85%</div>
					</div>
					<div class="graph" id="level3">
						<div>Lv.3</div>
						<div>3.7%</div>
					</div>
					<div class="graph" id="level4">
						<div>Lv.4</div>
						<div>3.6%</div>
					</div>
					<div class="graph" id="level5">
						<div>Lv.5</div>
						<div>3.5%</div>
					</div>
				</div>
				<div class="wrap">
					<dl class="rankDetail">
						<dt class="list">Lv.1</dt>
						<dd class="detail">
							<div>
								<span class="detailTitle">판매 수수료</span>
								<div>4.0%</div>
							</div>
							<div>
								<span class="detailTitle">총 정산 금액</span>
								<div>200만원 미만</div>
							</div>
						</dd>
					</dl>
					<div class=line></div>
					<dl class="rankDetail">
						<dt class="list">Lv.2</dt>
						<dd class="detail">
							<div>
								<span class="detailTitle">판매 수수료</span>
								<div>3.85%</div>
							</div>
							<div>
								<span class="detailTitle">총 정산 금액</span>
								<div>200만원 이상 1000만원 미만</div>
							</div>
						</dd>
					</dl>
					<div class=line></div>
					<dl class="rankDetail">
						<dt class="list">Lv.3</dt>
						<dd class="detail">
							<div>
								<span class="detailTitle">판매 수수료</span>
								<div>3.7%</div>
							</div>
							<div>
								<span class="detailTitle">총 정산 금액</span>
								<div>1000만원 이상 2000만원 미만</div>
							</div>
						</dd>
					</dl>
					<div class=line></div>
					<dl class="rankDetail">
						<dt class="list">Lv.4</dt>
						<dd class="detail">
							<div>
								<span class="detailTitle">판매 수수료</span>
								<div>3.6%</div>
							</div>
							<div>
								<span class="detailTitle">총 정산 금액</span>
								<div>2000만원 이상 6000만원 미만</div>
							</div>
						</dd>
					</dl>
					<div class=line></div>
					<dl class="rankDetail">
						<dt class="list">Lv.5</dt>
						<dd class="detail">
							<div>
								<span class="detailTitle">판매 수수료</span>
								<div>3.5%</div>
							</div>
							<div>
								<span class="detailTitle">총 정산 금액</span>
								<div>6000만원 이상</div>
							</div>
						</dd>
					</dl>
				</div>
			</div>
		</div>

	</div>
	<%@ include file="/views/home/footer.jsp"%>
</body>
</body>
</html>
