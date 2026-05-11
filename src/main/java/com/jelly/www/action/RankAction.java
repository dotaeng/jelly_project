package com.jelly.www.action;

import com.jelly.www.dao.ProductSellerDAO;
import com.jelly.www.vo.ProductSellerVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class RankAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute("user");
		int userId = userVO.getUserId();
		
		
		// 판매자 등급
		ProductSellerVO productSellerRank =  new ProductSellerDAO().getSellerRank(userId);
		int productSellerId = productSellerRank.getProductSellerId(); // 판매자 아이디 조회
		int priceSum = productSellerRank.getPrice(); // 판매자 판매금액 총 합 조회
		System.out.println(priceSum);
		// 변수 선언
		String grade = null;
		double sellFee = 0;
		
		if(priceSum >= 60000000) {
			grade  = "Lv.5";
			sellFee = 3.5;
		} else if(priceSum >= 20000000 ) {
			grade  = "Lv.4";
			sellFee = 3.6;
		} else if(priceSum >= 10000000 ) {
			grade  = "Lv.3";
			sellFee = 3.7;
		} else if(priceSum >= 2000000 ) {
			grade  = "Lv.2";
			sellFee = 3.85;
		} else if(priceSum < 2000000 ) {
			grade  = "Lv.1";
			sellFee = 4.0;
		}
			
		
		request.setAttribute("grade", grade);
		request.setAttribute("sellFee", sellFee);
		
		return "/views/mypage/rank.jsp";
	}

}
