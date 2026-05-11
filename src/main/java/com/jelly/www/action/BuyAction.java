package com.jelly.www.action;

import java.text.DecimalFormat;
import java.util.List;

import com.jelly.www.dao.AddressDAO;
import com.jelly.www.dao.CouponDAO;
import com.jelly.www.dao.ProductDAO;
import com.jelly.www.dao.UserCouponDAO;
import com.jelly.www.vo.AddressVO;
import com.jelly.www.vo.CouponVO;
import com.jelly.www.vo.ProductVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BuyAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		// 요청 파라미터 가져오기
		String productIdParam = request.getParameter("productId");
		String sizeParam = request.getParameter("size");
		String priceParam = request.getParameter("price");
		String couponIdParam = request.getParameter("couponId"); // 사용자가 쿠폰을 선택했을 경우

		// 세션에 있는 사용자 정보 가져오기
		HttpSession session = request.getSession();
		UserVO user = (UserVO) session.getAttribute("user");
		int userId = user.getUserId();

		// 전역변수 선언
		int productId = 0;
		int size = 0;
		int price = 0; // jellyController 에서 넘어오는 price
		int totalPrice = 0;
		ProductDAO productDAO = null;
		ProductVO product = null;
		AddressDAO addressDAO = null;
		AddressVO defaultAddress = null;
		List<AddressVO> addressList = null;
		List<CouponVO> userCouponList = null;
		int discountPrice = 0; // 할인금액
		int selectCouponId = 0; // 쿠폰 아이디

		if (userId != 0) {

			if (couponIdParam == null) { // 기본 배송화면(쿠폰 선택하지 않았다면)
				// 형변환
				productId = Integer.parseInt(productIdParam);
				size = Integer.parseInt(sizeParam);
				price = Integer.parseInt(priceParam);
				totalPrice = price;

				// 상품정보 가져오기
				productDAO = new ProductDAO();
				product = productDAO.selectOne(productId);

				// userId로 주소 정보 조회
				addressDAO = new AddressDAO();
				defaultAddress = addressDAO.selectDefaultAddressOne(userId); // 사용자의 기본주소
				addressList = addressDAO.selectAddressAllExceptDefault(userId); // 사용자의 전체 주소

				// 사용자가 소지한 쿠폰리스트 조회
				userCouponList = new UserCouponDAO().selectUserCoupons(userId);

			}
			else if (couponIdParam != null) { // 사용자가 쿠폰을 선택했다면
				// 형변환
				productId = Integer.parseInt(productIdParam);
				size = Integer.parseInt(sizeParam);
				price = Integer.parseInt(priceParam);
				selectCouponId = Integer.parseInt(couponIdParam);

				// 상품정보 가져오기
				productDAO = new ProductDAO();
				product = productDAO.selectOne(productId);

				// userId로 주소 정보 조회
				addressDAO = new AddressDAO();
				defaultAddress = addressDAO.selectDefaultAddressOne(userId); // 사용자의 기본주소
				addressList = addressDAO.selectAddressAllExceptDefault(userId); // 사용자의 전체 주소

				// 사용자가 선택한 쿠폰 조회
				UserCouponDAO couponDAO = new UserCouponDAO();
				CouponVO selectCoupon = couponDAO.selectCoupon(userId, selectCouponId);
				
				// 사용자가 소지한 쿠폰리스트 조회
				userCouponList = new UserCouponDAO().selectUserCoupons(userId);

				if (selectCoupon.getDiscountPercentage() == 0) { // 쿠폰이 할인금액인 경우
					int discountAmount = selectCoupon.getDiscountAmount();
					discountPrice = discountAmount;
					totalPrice = price - discountPrice;
					

				} else if (selectCoupon.getDiscountAmount() == 0) { // 쿠폰이 할인율인 경우
					int discountPercentage = selectCoupon.getDiscountPercentage();
					discountPrice = price * discountPercentage / 100;
					totalPrice = price - discountPrice;
				}

			}
		}

		// 가격 포맷팅
		DecimalFormat df = new DecimalFormat("#,###");
		String formattedTotalPrice = df.format(totalPrice); // 포맷팅된 가격(할인 적용 후)
		System.out.println(userCouponList);
		
		
		// 상품정보, 사용자정보, 주소정보, 가격, 사이즈를 요청객체에 전달
		request.setAttribute("product", product); // 상품 정보
		request.setAttribute("user", user); // 사용자 정보
		request.setAttribute("defaultAddress", defaultAddress); // 사용자의 기본주소 정보
		request.setAttribute("addressList", addressList); // 사용자의 전체 주소 정보
		request.setAttribute("price", price); // 상품의 원래 가격
		request.setAttribute("totalPrice", totalPrice); // 할인 적용된	가격
		request.setAttribute("discountPrice", discountPrice); // 할인 금액
		request.setAttribute("formattedTotalPrice", formattedTotalPrice); // 포맷팅 처리된 가격
		request.setAttribute("size", size); // 사이즈
		request.setAttribute("userCouponList", userCouponList); // 사용자가 가진 쿠폰 조회
		request.setAttribute("deliveryFee", 3000); // 배송비
		request.setAttribute("selectCouponId", selectCouponId); // 쿠폰 아이
		

		return "/views/order/buy.jsp";
	}

}