package com.jelly.www.action;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Properties;

import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.UserVO;

public class ConfirmPwAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		String email = request.getParameter("email");

		UserDAO dao = new UserDAO();
		UserVO vo = dao.findEmailByUserEmail(email);

		if (vo != null) {
			String userEmail = vo.getEmail();

			// 임시 비밀번호 생성
			String tempPassword = generateTempPw();

	        // 사용자 비밀번호 업데이트
	       vo = dao.updateUserPassword(userEmail, tempPassword);

			// 설정 정보
			Properties props = new Properties();

			// SMTP 서버 설정
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", "true"); 
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");

			// 보내는 사람 이메일 및 비밀번호
			String username = "haribojellyam@gmail.com";
			String password = "xcbdisbkelojfdoq";

			// 받는 사람 이메일
			String receiver = userEmail;

			// 세션 생성
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			// 보낼 메세지
			StringBuffer sb = new StringBuffer();
			sb.append("<h3> 임시 비밀번호가 발급되었습니다. 비밀번호는 " + tempPassword + "입니다. </h3>");
			String title = "임시 비밀번호 발급";

			try {
				// 보낼 메일 객체
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username, "JELLY", "UTF-8"));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
				message.setSubject(title);
				message.setContent(sb.toString(), "text/html; charset=UTF-8");

				// 메일 전송
				Transport.send(message);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return "views/find/confirmPw.jsp";
		} else {
			System.out.println("해당 이메일로 등록된 회원이 없습니다.");
			return "views/find/findPw.jsp";
		}
	}

	// 임시 비밀번호 생성 메서드
	private String generateTempPw() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder(8);
		for (int i = 0; i < 8; i++) {
			int index = (int) (Math.random() * characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}
}