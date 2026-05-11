package com.jelly.www.action;

import java.io.IOException;

import com.jelly.www.dao.AddressDAO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/deleteAddress")
public class DeleteAddress extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 요청객체 가져오기
		String postalCode = req.getParameter("postalCode");

		// 세션에 있는 사용자 정보 가져오기
		HttpSession session = req.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		int userId = user.getUserId();
		
		if(userId != 0) {
			
			// 주소 삭제하기
			new AddressDAO().DeleteAddressOne(userId, postalCode);
			
		}
	}

}
