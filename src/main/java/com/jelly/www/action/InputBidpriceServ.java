package com.jelly.www.action;
import java.io.IOException;
import java.text.DecimalFormat;

import com.jelly.www.dao.ProductBuyerDAO;
import com.jelly.www.dao.TradeDAO;
import com.jelly.www.vo.ProductBuyerVO;
import com.jelly.www.vo.TradeVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/orderBidInputPrice") // 여기에서 구매입찰 데이터 추가 (PRODUCT_BUYER)
public class InputBidpriceServ extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 한글처리
		req.setCharacterEncoding("UTF-8");
		
		
		// 요청객체 가져오기(희망 가격)
		String bidMoneyParam = req.getParameter("bidMoney");
		
		// 형변환
		int bidMoney = Integer.parseInt(bidMoneyParam);
		
		// 가격 포맷팅
		DecimalFormat df = new DecimalFormat("#,###");
		String formattedPrice = df.format(bidMoney);
		
		
		// 응답을 클라이언트에게 전송
        resp.getWriter().print(formattedPrice);
		
	}

}
