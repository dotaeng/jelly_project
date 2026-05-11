//package com.jelly.www.action;
//
//import java.io.IOException;
//
//import com.jelly.www.dao.AddressDAO;
//import com.jelly.www.vo.AddressVO;
//import com.jelly.www.vo.UserVO;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//@WebServlet("/updateAddress")
//public class UpdateAddress extends HttpServlet {
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		// 요청객체 가져오기
//		String postalCode = req.getParameter("postalCode"); // 기존 주소의 우편번호
//		String modifyPostalCode = req.getParameter("modifyPostalCode"); // 수정된 주소의 우편번호
//		String modifyAddressLine1 = req.getParameter("modifyAddressLine1");
//		String modifyAddressLine2 = req.getParameter("modifyAddressLine2");
//		String isDefaultParam = req.getParameter("isDefault");
//
//		// 세션에 있는 사용자 정보 가져오기
//		HttpSession session = req.getSession();
//		UserVO user = (UserVO) session.getAttribute("user");
//		int userId = user.getUserId();
//		
//		
//		if(userId != 0) {
//			// 형변환
//			Boolean isDefault = Boolean.parseBoolean(isDefaultParam);
//			
//			// 기본 주소로 설정했다면 기존의 기본배송지는 일반으로 변경
//			if (isDefault == true) {
//			new AddressDAO().unsetDefaultAddress(userId);
//			}
//			
//			// 주소 변경
//			new AddressDAO().updateAddress(new AddressVO().builder()
//					.postalCode(modifyPostalCode)
//					.addressLine1(modifyAddressLine1)
//					.addressLine2(modifyAddressLine2)
//					.isDefault(isDefault)
//					.userId(userId)
//					.build(), postalCode);			
//		}
//
//
//	}
//
//}
