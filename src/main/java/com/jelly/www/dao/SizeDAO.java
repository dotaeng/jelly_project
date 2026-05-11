package com.jelly.www.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.jelly.www.vo.SizeVO;

public class SizeDAO {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/jelly";
    private static final String USER = "scott";
    private static final String PASSWORD = "tiger";

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private StringBuffer sb = new StringBuffer();

    // 생성자
    public SizeDAO() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // 특정 사이즈로 상품 ID 필터링
    public List<Integer> filterBySize(List<String> sizes) {
        List<Integer> productIds = new ArrayList<>();
        sb.setLength(0);

        sb.append("SELECT DISTINCT PRODUCT_ID FROM SIZE WHERE SIZE IN (");
        for (int i = 0; i < sizes.size(); i++) {
            sb.append("?");
            if (i < sizes.size() - 1) sb.append(", ");
        }
        sb.append(")");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            for (int i = 0; i < sizes.size(); i++) {
                pstmt.setString(i + 1, sizes.get(i));
            }

            rs = pstmt.executeQuery();
            while (rs.next()) {
                productIds.add(rs.getInt("PRODUCT_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return productIds;
    }

    // 상품 ID와 사이즈로 사이즈ID 찾기
    public SizeVO selectSizeIdByProductIdAndSize(int size, int productId) {
    	SizeVO vo = null;
    	sb.setLength(0);
    	sb.append("SELECT size_id FROM SIZE WHERE size = ? AND product_id = ? ");
    	
    	try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, size);
			pstmt.setInt(2, productId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new SizeVO(rs.getInt("size_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    	return vo;
    }
    
    
    // 자원 해제 메서드
    private void close() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}