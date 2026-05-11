<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>joinNum.jsp</title>
<%-- <link rel="stylesheet" href="<%= request.getContextPath() %>/css/joinok.css"/> --%>
<style type="text/css">

@font-face {
  font-family: "Pretendard Variable";
  font-weight: 100 900;
  font-display: swap;
  src: url("https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/woff2/PretendardVariable.woff2")
    format("woff2-variations");
}

body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
  background-color: #fff;
  color: #000;
}

.container {
  text-align: center;
  max-width: 400px;
  width: 100%;
  border: #ffffff solid 1px;
  margin: 100px auto 0;
  padding-top: 1rem;
}

.header {
  width: 100%;
  background-color: hsl(0, 0%, 100%);
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
  position: fixed;
  top: 0;
  left: 0;
  z-index: 10;
}

/* �ㅻ뜑 TOP */
.header-top {
  width: 1280px;
  height: 30px;
  margin: 10px auto 0;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 20px;
  font-size: 13px;
  color: #222222cc;
  box-sizing: border-box;
}

.header-menu {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
  gap: 10px;
  margin-right: 20px;
}

.header-menu a {
  text-decoration: none;
  color: #222222cc;
  font-size: 13px;
}

.header-menu a:hover {
  color: #000;
}

/* �ㅻ뜑 MAIN */
.header-main {
  width: 1280px;
  height: 50px;
  margin: 0 auto;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding: 10px 20px;
  box-sizing: border-box;
}

.logo {
  margin-left: -13px;
}
.logo img {
  height: 50px;
  width: auto;
}

/* �ㅻ뜑 BOTTOM */
.header-bottom {
  width: 1280px;
  margin: 5px auto 0;
  padding: 10px 20px;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.bottom-nav {
  display: flex;
  gap: 15px;
}

.bottom-nav a {
  text-decoration: none;
  color: #222222;
  font-weight: 500;
  font-size: 20px;
  position: relative;
  padding-bottom: 5px;
}

.bottom-nav a:hover {
  color: #000;
}

.bottom-nav a.active::after {
  content: "";
  position: absolute;
  left: 0;
  bottom: 0;
  width: 100%;
  height: 3px;
  background-color: #000;
}

/* 寃��� �� */
.form {
  --timing: 0.3s;
  --width-of-input: 200px;
  --height-of-input: 40px;
  --border-height: 2px;
  --input-bg: #fff;
  --border-color: #cdcacacc;
  --border-radius: 30px;
  position: relative;
  width: var(--width-of-input);
  height: var(--height-of-input);
  display: flex;
  align-items: center;
  padding-inline: 0.8em;
  border: 1px solid var(--border-color);
  border-radius: var(--border-radius);
  transition: border-radius 0.5s ease, border-color 0.3s ease;
  background: var(--input-bg, #fff);
  margin-top: 2px;
  text-align: right;
  margin-left: 55rem;
}

.form button {
  border: none;
  background: none;
  color: #8b8ba7;
}

.form svg {
  width: 17px;
  margin-top: 3px;
}

.input {
  font-size: 0.9rem;
  background-color: transparent;
  width: 100%;
  height: 100%;
  padding-inline: 0.5em;
  padding-block: 0.7em;
  border: none;
}

input:focus {
  outline: none;
}

.reset {
  border: none;
  background: none;
  opacity: 0;
  visibility: hidden;
}

input:not(:placeholder-shown) ~ .reset {
  opacity: 1;
  visibility: visible;
}

.logo {
  font-size: 2rem;
  font-weight: bold;
}

.tagline {
  font-size: 0.9rem;
  color: #666;
  margin-bottom: 2rem;
}

form {
  width: 100%;
}

.form-group {
  margin-bottom: 1.5rem;
  width: 100%;
  font-weight: bolder;
}

.form-group input {
  width: calc(100% - 20px); /* 양쪽 10px씩 여백 추가 */
  padding: 0.8rem;
  font-size: 1rem;
  border: none;
  border-radius: 0;
  box-sizing: border-box;
  margin-top: 0.5rem;
  background-color: #fff;
  box-shadow: inset 0 -2px 0 0 #ccc;
  transition: box-shadow 0.3s ease;
  max-width: 400px; /* 필요시 최대 크기 제한 */
}

.form-group input:focus {
  outline: none;
  box-shadow: inset 0 -2.5px 0 0 #000;
}

/* .form-group label {
  display: block;
  text-align: center;
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 0.3rem;
} */

.email-btn {
  width: 100%;
  padding: 1rem;
  background-color: #ffffff;
  color: #000000;
  border: black solid 1px;
  border-radius: 4px;
  font-size: 1.2rem;
  cursor: pointer;
  margin-bottom: 1rem;
  font-weight: bolder;
}

.email-btn:hover {
  background-color: #f3f3f3;
}

.login-btn {
  width: 100%;
  padding: 1rem;
  background-color: #000;
  color: #fff;
  border: none;
  border-radius: 4px;
  font-size: 1.2rem;
  cursor: pointer;
  margin-bottom: 1rem;
  font-weight: bolder;
}

.login-btn:hover {
  background-color: #333;
}

.links {
  margin-top: 1rem;
  font-size: 0.9rem;
  color: #666;
}

.links a {
  color: #696969;
  text-decoration: none;
  margin: 0 0.5rem;
  font-size: 12px;
}

.links a:hover {
  text-decoration: underline;
}

#logo2 {
  width: 250px;
}

.email {
  font-size: 2rem;
  font-weight: 700;
}
.p-content {
  font-size: 1rem;
  font-weight: 300;
}

.submit-btn {
  width: 25%;
  padding: 1rem;
  background-color: white;
  color: black;
  border:black solid 1px;
  border-radius: 15px;
  font-size: 1rem;
  cursor: pointer;
  text-align: center;
  font-weight: bolder;
}

.submit-btn:hover {
  background-color: #333;
  color: white;
}


</style>
</head>
<body>
    <div class="header">
      <!-- 헤더 TOP -->
      <div class="header-top">
        <ul class="header-menu">
          <li><a href="/notice">고객센터</a></li>
          <li><a href="/mypage">마이페이지</a></li>
          <li><a href="/saved">관심</a></li>
          <li><a href="/login">로그인</a></li>
        </ul>
      </div>

      <!-- 헤더 MAIN -->
      <div class="header-main">
        <a href="/" class="logo">
          <img src="<%= request.getContextPath() %>/img/jellyLogo.png" alt="jellyLogo" />
        </a>
        <!-- 헤더 BOTTOM -->
        <!-- <div class="header-bottom"> -->
        <form class="form">
          <button>
            <svg
              width="17"
              height="16"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
              role="img"
              aria-labelledby="search"
            >
              <path
                d="M7.667 12.667A5.333 5.333 0 107.667 2a5.333 5.333 0 000 10.667zM14.334 14l-2.9-2.9"
                stroke="currentColor"
                stroke-width="1.333"
                stroke-linecap="round"
                stroke-linejoin="round"
              ></path>
            </svg>
          </button>
          <input
            class="input"
            placeholder="검색어를 입력해주세요"
            required=""
            type="text"
          />
          <button class="reset" type="reset">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-6 w-6"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              stroke-width="2"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M6 18L18 6M6 6l12 12"
              ></path>
            </svg>
          </button>
        </form>
        <!-- </div> -->
      </div>
    </div>
    <div class="container">
      <form action="<%= request.getContextPath() %>/jelly?page=joinOk" method="post" onsubmit="alert('회원가입이 완료되었습니다.')">
      <br /><br /><br />
        <div class="form-group">
          <label for="email" class="email">이메일 인증 요청</label>
          <br /><br />
          <p class="p-content">메일을 전송하였습니다.</p>
          <p class="p-content">가입한 메일로 전송된 인증번호를</p>
          <p class="p-content">입력하면 회원가입이 완료됩니다.</p>
          <br /> <br>
          <label for="email">인증번호</label>
            <input type="text" id="num" name="num" placeholder="인증 번호를 입력하세요" required />
            <br /> <br/>
			<c:if test="${not empty Error }">
			${error }
			</c:if>
        </div>
        <br />
      <label for="email">이메일을 받지 못하셨나요?</label>
      <br /><br /><br />
        <button type="submit" class="login-btn">가입 완료</button>
        </form>
      <form action="">
        <button type="button" class="email-btn">이메일 재전송</button>
      </form>
    </div>
</body>
</html>