package com.jelly.www.action;

import java.util.Properties;

import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.UserVO;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class JoinNumAction implements Action {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		
		// 사용자 입력값 가져오기
		String username = req.getParameter("user_name");
		String email = req.getParameter("email");
		String phonenumber = req.getParameter("phone_number");
		String password = req.getParameter("password");

		String emailError = null;
		String passwordError = null;
		String duplicateError = null;

		// UserVO 객체 생성
		UserVO user = new UserVO();

		// DAO 생성
		UserDAO dao = new UserDAO();
		
		boolean isEmailValidation = email == null || !email.contains("@") || email.length() < 10;
		boolean isPasswordValidation = password == null || password.length() < 8 || password.length() > 16;
		boolean isDuplicateUser = dao.isDuplicate(email, phonenumber);

		// 이메일 유효성 검사
		if (isEmailValidation) {
			System.out.println("이메일 양식 에러");
			emailError = "정확한 이메일 주소를 입력하세요.";
		}

		// 비밀번호 유효성 검사
		if (isPasswordValidation) {
			System.out.println("비밀번호 글자 수 에러");
			passwordError = "비밀번호는 8자 이상, 16자 이하로 입력하세요.";
		}
		
		// 중복 회원 체크
		if (isDuplicateUser) {
			System.out.println("중복된 회원 존재");
			duplicateError = "이미 가입된 이메일 또는 전화번호입니다.";
		}

		if (!isEmailValidation && !isPasswordValidation && !isDuplicateUser) {

			// 입력값 세션에 저장
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
			session.setAttribute("name", username);
			session.setAttribute("email", email);
			session.setAttribute("phone", phonenumber);
			session.setAttribute("password", password);

			if (email != null) {

				// 인증코드
				String tempNum = generateTemporaryNum();
				
				session.setAttribute("tempNum", tempNum);

				// 설정 정보
				Properties p = new Properties();

				// SMTP 서버 설정
				// 구글 설정
				p.put("mail.transport.protocol", "smtp");
				p.put("mail.smtp.host", "smtp.gmail.com");
				p.put("mail.smtp.port", "465");
				p.put("mail.smtp.auth", "true");

				p.put("mail.smtp.quitwait", "false");
				p.put("mail.smtp.socketFactory.port", "465");
				p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				p.put("mail.smtp.socketFactory.fallback", "false");

				// 보내는 사람 이메일 및 비밀번호
				String jellyemail = "haribojellyam@gmail.com";
				String jellypassword = "xcbdisbkelojfdoq";

				// 받는 사람 이메일
				String receiver = email;

				System.out.println(email);

				// 세션 생성
				Session session1 = Session.getInstance(p, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(jellyemail, jellypassword);
					}
				});

				// 보낼 메세지
				StringBuffer sb = new StringBuffer();
				sb.append("<h3> 해당 인증코드를 입력창에 입력하세요. 인증코드는 " + tempNum + "입니다. </h3>");
				String title = "인증코드 발급 완료";

				try {
					// 보낼 메일 객체
					Message message = new MimeMessage(session1);
					message.setFrom(new InternetAddress(jellyemail, "JELLY", "UTF-8"));
					message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
					message.setSubject(title);
					message.setContent(sb.toString(), "text/html; charset=UTF-8");

					// 메일 전송
					Transport.send(message);

					System.out.println("인증코드 발송 완료");
				} catch (Exception e) {
					e.printStackTrace();

					System.out.println("인증코드 발송 실패");
				}

			}

			req.setAttribute("success", true);
			return "/views/join/joinNum.jsp"; // 성공 -> 결과 페이지로
			
		} else {
			//에러 메시지를 요청 속성에 추가
			if (isEmailValidation) {
				req.setAttribute("emailError", emailError);
			}
			if (isPasswordValidation) {
				req.setAttribute("passwordError", passwordError);
			}
			if (isDuplicateUser) {
				req.setAttribute("duplicateError", duplicateError);
			} 
			// 로그인 페이지로 이동
			return "/views/join/joinForm.jsp";
		}

	}

	// 인증코드 생성 메서드
	private String generateTemporaryNum() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++) {
			int index = (int) (Math.random() * characters.length());
			sb.append(characters.charAt(index));
		}
		return sb.toString();
	}
}