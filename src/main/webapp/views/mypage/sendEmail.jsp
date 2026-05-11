
<%@page import="jakarta.mail.Transport"%>
<%@page import="jakarta.mail.Message"%>
<%@page import="jakarta.mail.internet.InternetAddress"%>
<%@page import="jakarta.mail.internet.MimeMessage"%>
<%@page import="jakarta.mail.PasswordAuthentication"%>
<%@page import="jakarta.mail.Authenticator"%>
<%@page import="jakarta.mail.Session"%>
<%@page import="java.util.Properties"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>sendEmail.jsp</title>
</head>
<body>
<%
	// 설정 정보
	Properties p = new Properties();
	
	// 네이버 설정
	// hashMap 임 -> key, value 
	//p.put("mail.smtp.auth", "true");
	//p.put("mail.smtp.starttls.enable", "true"); // TLS 활성화
	//p.put("mail.smtp.host", "smtp.naver.com"); // SMTP 호스트
	//p.put("mail.smtp.port", "587"); // TLS 포트

	// 구글 설정
	  p.put("mail.transport.protocol", "smtp");
      p.put("mail.smtp.host", "smtp.gmail.com"); 
      p.put("mail.smtp.port", "465"); 
	  p.put("mail.smtp.auth", "true"); 

	  p.put("mail.smtp.quitwait", "false"); 
	  p.put("mail.smtp.socketFactory.port", "465"); 
	  p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); 
	  p.put("mail.smtp.socketFactory.fallback", "false"); 
	  
	  
	
	// 보내는 사람 이메일
	String username = "doyeon0099@gmail.com";
	String password = "nvzvqgvvkwguocms";
	
	// 받는 사람 이메일
	String receiver = "kimdo09@naver.com";
	
	// 세션 생성
	Session sessions = Session.getInstance(p, new Authenticator(){
		protected jakarta.mail.PasswordAuthentication getPasswordAuthentication(){
/* 		내가 준 메일주소와 비밀번호를 넣어서 메일을 발송할 거임 */
		return new PasswordAuthentication(username, password);
		}
	});
	
	// 보낼 메세지
	StringBuffer sb = new StringBuffer();
	sb.append("<h3> 안녕하세요 </h3>");
	sb.append("<h3> 가입 테스트입니다. </h3>");
	String code = "" + (int)(Math.random()*100000);
	sb.append("<h3> 인증번호는 " + code + " </h3>");
	
	System.out.println("sessions : " + sessions);
	
	session.setAttribute("num", code);
	
	
	
	String title = "test 인증번호 입니다.";
	
	// 보낼 메일 객체
	Message message = new MimeMessage(sessions);
	message.setFrom(new InternetAddress(username, "test", "UTF-8"));
	message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
	message.setSubject(title);
	message.setContent(sb.toString(), "text/html; charset=UTF-8");
	Transport.send(message);
%>
	<form action="authOk.jsp" method="post">
	인증번호 : <input type="text" name="num" id="" />
	<br />
	<input type="submit" value="본인인증 확인" /></form>
</body>
</html>