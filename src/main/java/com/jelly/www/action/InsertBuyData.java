package com.jelly.www.action;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

import com.jelly.www.dao.AddressDAO;
import com.jelly.www.dao.ProductBuyerDAO;
import com.jelly.www.dao.ProductSellerDAO;
import com.jelly.www.dao.TradeDAO;
import com.jelly.www.dao.UserCouponDAO;
import com.jelly.www.vo.AddressVO;
import com.jelly.www.vo.ProductBuyerVO;
import com.jelly.www.vo.ProductSellerVO;
import com.jelly.www.vo.TradeVO;
import com.jelly.www.vo.UserCouponVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/buyData") // 여기에서 구매 데이터 추가 TRADE, PRODUCT_BUYER, PAYMENT 테이블에 데이터 삽입
public class InsertBuyData extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 요청객체 가져오기(희망 가격)
		String orderNoParam = req.getParameter("orderNo").replaceAll("[A-Za-z]", ""); // 주문 번호 (에서 숫자만 추출)
		String totalPriceParam = req.getParameter("totalPrice"); // 최종 판매가격
		String priceParam = req.getParameter("price"); // 판매 입찰가
		String productIdParam = req.getParameter("productId"); // 상품 아이디
		String paymentMethod = req.getParameter("payMethod"); // 결제 수단
		String size = req.getParameter("size"); // 사이즈
		String couponIdParam = req.getParameter("couponId"); // 쿠폰 아이디

		// 세션에 있는 사용자 정보 가져오기
		HttpSession session = req.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		int userId = user.getUserId();

		// 전역변수 선언
		int orderNo = 0;
		int totalPrice = 0;
		int price = 0;
		int productId = 0;
		int couponId = 0;
		AddressDAO addressDAO = null;
		AddressVO address = null;
		int defaultAddressId = 0;
		TradeDAO tradeDAO = null;
		TradeVO trade = null;
		TradeVO tradeIdVO = null; // trade_id 가져올 vo
		ProductSellerDAO productSellerDAO = null;
		ProductSellerVO productSeller = null;
		int productSellerId = 0;
		int tradeId = 0;

		// 세션에 userId 가 0이 아니라면
		if (userId != 0) {

			// 형변환
			orderNo = Integer.parseInt(orderNoParam);
			totalPrice = Integer.parseInt(totalPriceParam);
			price = Integer.parseInt(priceParam);
			productId = Integer.parseInt(productIdParam);

			// 사용자의 주소 정보 조회
			addressDAO = new AddressDAO();
			address = addressDAO.selectDefaultAddressOne(userId);
			defaultAddressId = address.getAddressId();

			if (couponIdParam == null) { // 쿠폰을 사용하지 않았다면
				
				// TRADE 테이블에 데이터 추가를 위한 객체 생성 (구매자 존재할 수 있을 수도 있고 없을 수도 있음)
				tradeDAO = new TradeDAO();
				trade = null;

				// 상품 아이디, 사이즈, 판매자가 입력한 금액 / 즉시 판매가와 가격이 동일한 구매자 찾기
				productSellerDAO = new ProductSellerDAO();
				productSeller = productSellerDAO.selectSellerIdOne(productId, size, price);

				// 그런 판매자가 있다면
				if (productSeller != null) {
					productSellerId = productSeller.getSellerId();
					trade = TradeVO.builder()
							.orderNo(orderNo)
							.productSellerId(productSellerId)
							.buyerId(userId)
							.addressId(defaultAddressId)
							.totalPrice(totalPrice)
							.tradeStatus(2) // 체결완료 상태
							.build();
				} else { // 그런 구매자가 없다면
					trade = TradeVO.builder()
							.orderNo(orderNo)
							.buyerId(userId)
							.addressId(defaultAddressId)
							.totalPrice(totalPrice)
							.tradeStatus(1) // 체결 대기중인 상태
							.build();
				}
				// TRADE 테이블에 데이터 추가
				tradeDAO.insertBuyOne(trade);
				
				// TRADE 테이블에서 trade_id 가져오기
				tradeIdVO = tradeDAO.selectTradeId(orderNo);
				tradeId = tradeIdVO.getTradeId();

				// PAYMENT 테이블에 데이터 삽입
				tradeDAO.insertPayment(tradeId, paymentMethod, totalPrice);

				// PRODUCT_BUYER 테이블에 데이터 삽입
				new ProductBuyerDAO().insertBuyerData(new ProductBuyerVO(productId, userId, size, price));
			}

			else if (couponIdParam != null) { // 쿠폰을 사용했다면
				couponId = Integer.parseInt(couponIdParam);

				// TRADE 테이블에 데이터 추가를 위한 객체 생성 (구매자 존재할 수 있을 수도 있고 없을 수도 있음)
				tradeDAO = new TradeDAO();
				trade = null;

				// 상품 아이디, 사이즈, 판매자가 입력한 금액 / 즉시 판매가와 가격이 동일한 구매자 찾기
				productSellerDAO = new ProductSellerDAO();
				productSeller = productSellerDAO.selectSellerIdOne(productId, size, price);

				// 그런 판매자가 있다면
				if (productSeller != null) {
					productSellerId = productSeller.getSellerId();
					trade = TradeVO.builder().
							orderNo(orderNo).
							productSellerId(productSellerId)
							.buyerId(userId)
							.addressId(defaultAddressId)
							.couponId(couponId) // 쿠폰도 우선 1 로 함 (제약조건은 없앴으나 )
							.totalPrice(totalPrice)
							.tradeStatus(2) // 체결완료 상태
							.build();
				} else { // 그런 구매자가 없다면
					trade = TradeVO.builder()
							.orderNo(orderNo)
							.buyerId(userId)
							.addressId(defaultAddressId)
							.couponId(couponId) // 쿠폰도 우선 1 로 함 (제약조건은 없앴으나 )
							.totalPrice(totalPrice)
							.tradeStatus(1) // 체결 대기중인 상태
							.build();
				}
				// TRADE 테이블에 데이터 추가
				tradeDAO.insertBuyOne(trade);
				
				// TRADE 테이블에서 trade_id 가져오기
				tradeIdVO = tradeDAO.selectTradeId(orderNo);
				tradeId = tradeIdVO.getTradeId();

				// PAYMENT 테이블에 데이터 삽입
				tradeDAO.insertPayment(tradeId, paymentMethod, totalPrice);

				// PRODUCT_BUYER 테이블에 데이터 삽입
//				try {
				new ProductBuyerDAO().insertBuyerData(new ProductBuyerVO(productId, userId, size, price));
//				}catch(SQLIntegrityConstraintViolationException e) { // 사용자가 같은상품을 여러번 결제할 수 없음 ... 
//					
//				} // 데이터 무결성 조건 위반시 나타낼 경고 메세지를 어떻게 ..
				
				// 사용한 쿠폰으로 처리
				new UserCouponDAO().updateUsedCoupon(new UserCouponVO().builder().userId(userId).couponId(couponId).build());
			}
		}

	}

}
