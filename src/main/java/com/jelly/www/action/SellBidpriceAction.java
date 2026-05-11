package com.jelly.www.action;

import java.text.DecimalFormat;

import com.jelly.www.dao.ProductDAO;
import com.jelly.www.dao.ProductSellerDAO;
import com.jelly.www.vo.ProductVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SellBidpriceAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		// 요청 파라미터 가져오기
		String productIdParam = request.getParameter("productId");
		String sizeParam = request.getParameter("size");

		// 세션에 있는 사용자 정보 가져오기
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		int userId = user.getUserId();

		if (userId != 0) {
			// 형변환
			int productId = Integer.parseInt(productIdParam);
			int size = Integer.parseInt(sizeParam);

			// 상품정보 가져오기
			ProductDAO productDAO = new ProductDAO();
			ProductVO product = productDAO.selectOne(productId);

			// PRODUCT_SELLER 에서 최저가 가져오기 -> 구매입찰 가격 입력 페이지에서 즉시 구매가로 띄우기 위해
			ProductSellerDAO productSellerDAO = new ProductSellerDAO();
			int lowestPrice = productSellerDAO.getLowestPrice(productId);

			DecimalFormat df = new DecimalFormat("#,###");
			String formattedPrice = df.format(lowestPrice);

			// 상품정보, 사용자정보, 주소정보, 가격, 사이즈를 요청객체에 전달
			request.setAttribute("product", product); // 상품 정보
			request.setAttribute("formattedPrice", formattedPrice); // 포맷팅 처리된 가
			request.setAttribute("price", lowestPrice); // 사이즈별 최저가
			request.setAttribute("size", size);
		}
		return "/views/order/sellBidprice.jsp";
	}

}
