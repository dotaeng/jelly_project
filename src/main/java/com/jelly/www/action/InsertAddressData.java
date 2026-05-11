package com.jelly.www.action;

import java.io.IOException;

import com.jelly.www.dao.AddressDAO;
import com.jelly.www.vo.AddressVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/insertAddress")
public class InsertAddressData extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 요청 객체 가져오기
		String postalCode = req.getParameter("postalCode");
		String addressLine1 = req.getParameter("addressLine1");
		String addressLine2 = req.getParameter("addressLine2");
		String isDefaultParam = req.getParameter("isDefault");

		// 세션에 있는 사용자 정보 가져오기
		HttpSession session = req.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		int userId = user.getUserId();
		
		// 객체 생성
		AddressDAO addressDAO = new AddressDAO();
		AddressVO address = null;

		// 세션에 userId 가 0이 아니라면 
		if (userId != 0) {
			// 형변환
			Boolean isDefault = Boolean.parseBoolean(isDefaultParam);
			
			
			// 기본 주소로 설정했다면 -> 기존의 기본 주소는 삭제하는게 필요함
			if (isDefault == true) {
				addressDAO.unsetDefaultAddress(userId, true);
			}
			
			// 이후에 주소 추가를 위한 VO 설정
			address = new AddressVO().builder()
								.userId(userId)
								.addressLine1(addressLine1)
								.addressLine2(addressLine2)
								.postalCode(postalCode)
								.isDefault(isDefault)
								.build();
			
			// 주소 추가
			addressDAO.insertAddress(address);
		}
	}

}
