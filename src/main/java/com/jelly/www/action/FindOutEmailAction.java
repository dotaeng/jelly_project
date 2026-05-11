package com.jelly.www.action;

import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FindOutEmailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		String phoneNumber = request.getParameter("phone_number");

		UserDAO dao = new UserDAO();
		UserVO vo = dao.findEmail(phoneNumber);

		if (vo != null) {
			String email = vo.getEmail();

			String maskedemail = "";

			if (email != null) {
			    String prefix = email.substring(0, email.indexOf("@")); // @ 앞부분 
			    String domain = email.substring(email.indexOf("@")); // @ 뒷부분 

			        maskedemail = prefix.charAt(0) 
			                    + "*".repeat(prefix.length() - 2) 
			                    + prefix.charAt(prefix.length() - 1)
			                    + domain;
			}

			request.setAttribute("maskedemail", maskedemail);
			request.setAttribute("phone_number", phoneNumber);

			System.out.println("이메일 찾기 성공");
			return "views/find/findoutEmail.jsp";
		} else {
			// 사용자 정보가 없는 경우 에러 메시지 설정
			request.setAttribute("errorMessage", "입력한 번호로 등록된 이메일이 없습니다.");

			return "views/find/findEmail.jsp";
		}
	}
}