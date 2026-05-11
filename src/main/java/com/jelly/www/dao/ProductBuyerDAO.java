package com.jelly.www.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jelly.www.vo.ProductBuyerVO;
import com.jelly.www.vo.ProductVO;

public class ProductBuyerDAO {
	 // DB 연결 정보
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/jelly";
    String user = "scott";
    String password = "tiger";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StringBuffer sb = new StringBuffer();
    
    public ProductBuyerDAO() {
    	try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("ProductBuyer DAO: MySQL DB 연결 성공");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL 드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("MySQL DB 연결 실패");
            e.printStackTrace();
        }
    }
    
    // 시세 반영을 위한 구매정보 추가
    public int insertBuyerData(ProductBuyerVO vo) {
    	int result = 0;
    	sb.setLength(0);
    	sb.append("INSERT INTO PRODUCT_BUYER (product_id, buyer_id, size, bid_money) VALUES(?, ?, ?, ?)");
    	
    	try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getProductId());
			pstmt.setInt(2, vo.getBuyerId());
			pstmt.setString(3, vo.getSize());
			pstmt.setInt(4, vo.getBidMoney());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println(result);
    	return result; // insert 안됐으면 0
    }
    
 // 구매자 조회 (사이즈, 가격, 상품id가 맞는 사람)
 	public ProductBuyerVO selectBuyerIdOne(int productId, String size, int bidMoney) {
 		ProductBuyerVO vo = null;
 		sb.setLength(0);
 		sb.append("SELECT product_buyer_id FROM PRODUCT_BUYER ");
 		sb.append("WHERE product_id = ? AND size = ? AND bidMoney = ? ");
 		sb.append("ORDER BY created_at ASC limit 1");
 		try {
 			pstmt = conn.prepareStatement(sb.toString());
 			pstmt.setInt(1, productId);
 			pstmt.setString(2, size);
 			pstmt.setInt(3, bidMoney);
 			rs = pstmt.executeQuery();

 			if (rs.next()) {
 				vo = new ProductBuyerVO(rs.getInt("product_buyer_id"));
 			}
 		} catch (SQLException e) {
 			e.printStackTrace();
 		}
 		return vo;
 	}


    
    
}
