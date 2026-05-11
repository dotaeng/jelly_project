<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="icon" href="<%= request.getContextPath() %>/img/jelly.ico" type="image/x-icon">
  <title>Jelly</title>
  <!-- upbutton.css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/upbutton.css" />
</head>
<body>
  <jsp:include page="header.jsp" />
  <jsp:include page="mainBanner.jsp" />
   <br />
   <br />
  <jsp:include page="mainShop.jsp" />
  <br />
  <br />
  <jsp:include page="mainStylePopular.jsp" />
  <br />
  <br />
  <br />
  <br />
  <jsp:include page="mainBrand.jsp" />
  <br />
  <br />
  <jsp:include page="mainCoupon.jsp" />
  <jsp:include page="footer.jsp" />
  
  <!-- 맨 위로 이동 버튼 -->
  <button class="btn_top" id="btnTop" title="맨 위로 가기"></button>
  <script src="<%= request.getContextPath() %>/js/upbutton.js"></script>
</body>
</html>