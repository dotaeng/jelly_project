package com.jelly.www.action;

import java.io.IOException;
import java.text.DecimalFormat;

import com.jelly.www.dao.AddressDAO;
import com.jelly.www.dao.ProductBuyerDAO;
import com.jelly.www.dao.ProductDAO;
import com.jelly.www.dao.ProductSellerDAO;
import com.jelly.www.dao.TradeDAO;
import com.jelly.www.vo.AddressVO;
import com.jelly.www.vo.ProductBuyerVO;
import com.jelly.www.vo.ProductSellerVO;
import com.jelly.www.vo.ProductVO;
import com.jelly.www.vo.TradeVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sellData") // 여기에서 판매 데이터 추가 TRADE, PRODUCT_SELLER 테이블에 데이터 삽입
public class InsertSellData extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청객체 가져오기(희망 가격)
		String tradeIdParam = req.getParameter("tradeId").replaceAll("[A-Za-z]", ""); // 주문 번호 (에서 숫자만 추출)
		String totalPriceParam = req.getParameter("totalPrice"); // 최종 판매가격
		String priceParam = req.getParameter("price"); // 판매 입찰가
		String productIdParam = req.getParameter("productId"); // 상품 아이디
		String size = req.getParameter("size"); // 사이즈

		// 세션에 있는 사용자 정보 가져오기
		HttpSession session = req.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		int userId = user.getUserId();

		if (userId != 0) {

			// 형변환
			int tradeId = Integer.parseInt(tradeIdParam);
			int totalPrice = Integer.parseInt(totalPriceParam);
			int price = Integer.parseInt(priceParam);
			int productId = Integer.parseInt(productIdParam);

			// 사용자의 주소 정보 조회
			AddressDAO addressDAO = new AddressDAO();
			AddressVO address = addressDAO.selectDefaultAddressOne(userId);
			int defaultAddressId = address.getAddressId();

			// TRADE 테이블에 데이터 추가를 우한 객체 생성 (구매자 존재할 수 있을 수도 있고 없을 수도 있음)
			TradeDAO tradeDAO = new TradeDAO();
			TradeVO trade =null;
		
			// 상품 아이디, 사이즈, 판매자가 입력한 금액 / 즉시 판매가와 가격이 동일한 구매자 찾기 
			ProductBuyerDAO productBuyerDAO = new ProductBuyerDAO();
			ProductBuyerVO productBuyer = productBuyerDAO.selectBuyerIdOne(productId, size, price);
			
			// 그런 구매자가 있다면
			if (productBuyer != null) {
				int productBuyerId = productBuyer.getBuyerId();
				
				trade = TradeVO.builder()
					.orderNo(tradeId)
					.productSellerId(userId) 
					.buyerId(productBuyerId)
					.addressId(defaultAddressId)
					.couponId(1) //  쿠폰도 우선 1 로 함 (제약조건은 없앴으나 )
					.totalPrice(totalPrice)
					.tradeStatus(2) // 체결완료 상태
					.build();
			} else { // 그런 구매자가 없다면
				trade = TradeVO.builder()
						.orderNo(tradeId)
						.productSellerId(userId) 
						.addressId(defaultAddressId)
						.couponId(1) //  쿠폰도 우선 1 로 함 (제약조건은 없앴으나 )
						.totalPrice(totalPrice)
						.tradeStatus(1) // 체결 대기중인 상태
						.build();
			}
					// TRADE 테이블에 데이터 추가
					tradeDAO.insertBuyOne(trade);
					
			// PRODUCT_SELLER 테이블에 데이터 삽입
			new ProductSellerDAO().insertSellerData(new ProductSellerVO(productId, userId, size, price));
		}
	}

}
