package com.jelly.www.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.jelly.www.vo.CouponVO;
import com.jelly.www.vo.UserCouponVO;

public class UserCouponDAO {
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/jelly";
	private String user = "scott";
	private String password = "tiger";

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private StringBuilder sb = new StringBuilder();

	public UserCouponDAO() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("TadeDAO: MySQL DB 연결 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL 드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("MySQL DB 연결 실패");
			e.printStackTrace();
		}
	}

	
	// 사용자가 가진 모든 쿠폰 정보 조회(쿠폰 코드, 상세 설명, 최소금액, 유효기간)	
	public List<CouponVO> selectUserCoupons(int userId){
		List<CouponVO> userCouponList = new ArrayList<CouponVO>();
		
		sb.setLength(0);
		sb.append("SELECT UC.coupon_id, C.coupon_code, C.description, C.discount_amount, C.discount_percentage, C.minimum_order_amount, C.expiry_date ");
		sb.append("FROM COUPON C JOIN USER_COUPON UC ON C.coupon_id = UC.coupon_id " );
		sb.append("WHERE user_id = ? AND is_used = 0" );
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CouponVO vo = new CouponVO(
						rs.getInt("coupon_id"),
						rs.getString("coupon_code"),
						rs.getString("description"),
						rs.getInt("discount_amount"),
						rs.getInt("discount_percentage"),
						rs.getInt("minimum_order_amount"),
						rs.getString("expiry_date")
						);
				userCouponList.add(vo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userCouponList;
	}
	
	// 사용자가 선택한 쿠폰 조회(쿠폰 코드, 상세 설명, 최소금액, 유효기간)	
		public CouponVO selectCoupon(int userId, int couponId){
			CouponVO vo = new CouponVO();
			sb.setLength(0);
			sb.append("SELECT C.discount_amount, C.discount_percentage ");
			sb.append("FROM COUPON C JOIN USER_COUPON UC ON C.coupon_id = UC.coupon_id " );
			sb.append("WHERE UC.user_id = ? AND UC.coupon_id = ?" );
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, userId);
				pstmt.setInt(2, couponId);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					vo = new CouponVO(
							rs.getInt("discount_amount"),
							rs.getInt("discount_percentage")
							);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return vo;
		}
		
		// 사용자가 결제시 선택한 쿠폰 삭제
		public void deleteCoupon(int userId, int couponId) {
			sb.setLength(0);
			sb.append("DELETE FROM USER_COUPON WHERE user_id = ? AND coupon_id = ?");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, userId);
				pstmt.setInt(2, couponId);
				pstmt.executeUpdate();
				System.out.println("쿠폰 삭제 완료");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		// 사용완료한 쿠폰 is_used, used_at 업데이트
		public void updateUsedCoupon(UserCouponVO vo) {
			sb.setLength(0);
			sb.append("UPDATE USER_COUPON SET is_used = 1, used_at = NOW() ");
			sb.append("WHERE user_id = ? AND coupon_id = ?");
			
			try {
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.setInt(1, vo.getUserId());
				pstmt.setInt(2, vo.getCouponId());
				
				pstmt.executeUpdate();
				System.out.println("쿠폰 사용 완료" + vo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
}
