package com.jelly.www.action;

import com.jelly.www.dao.ProductDAO;
import com.jelly.www.vo.ProductVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SellConfirmAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 요청객체 
		String productIdParam = request.getParameter("productId");
		String priceParam = request.getParameter("price");
		
		// 형변환
		int productId = Integer.parseInt(productIdParam);
		int price = Integer.parseInt(priceParam);
		
		ProductDAO productDAO = new ProductDAO();
		ProductVO product = productDAO.selectOne(productId);
	
		request.setAttribute("price", price);
		request.setAttribute("product", product); // 상품정보 조회
		request.setAttribute("sellCharge", 8500); // 수수료
		
		return "/views/order/sellConfirm.jsp";
	}

}
