package com.jelly.www.action;

import java.util.List;

import com.jelly.www.dao.AddressDAO;
import com.jelly.www.vo.AddressVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AddressBookAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		int userId = user.getUserId();
		
		if (userId != 0) {
			
			// 기본 주소 정보 가져오기 
			AddressDAO addressDAO = new AddressDAO();
			AddressVO defaultAddress = addressDAO.selectDefaultAddressOne(userId);
			
			// 전체 주소 정보 가져오기
			List<AddressVO> addressList = addressDAO.selectAddressAllExceptDefault(userId);
			
			// 요청객체에 전달
			request.setAttribute("defaultAddress", defaultAddress);
			request.setAttribute("addressList", addressList);
			request.setAttribute("user", user);
			
		}
		
		
		return "/views/mypage/addressBook.jsp";
	}

}
