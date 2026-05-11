package com.jelly.www.action;

import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginInfoSAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		int userId = user.getUserId();
		
		UserDAO dao = new UserDAO();
		UserVO user2 = dao.selectOne(userId);
		
		if (user2 != null) {
			session.setAttribute("email", user2.getEmail());
			session.setAttribute("password", user2.getPassword());
			session.setAttribute("phonenumber", user2.getPhoneNumber());
		}
		return "/views/mypage/loginInfo.jsp";
	}

}
