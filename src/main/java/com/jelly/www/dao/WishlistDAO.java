package com.jelly.www.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/jelly";
    private String user = "scott";
    private String password = "tiger";

    // DB 연결 메서드
    private Connection getConnection() throws SQLException {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("MySQL 연결 실패");
        }
    }

    // 관심상품 상태 확인
    public boolean isWishlistExists(int userId, int productId) {
        String sql = "SELECT 1 FROM WISHLIST WHERE user_id = ? AND product_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 관심상품 추가
    public void addWishlist(int userId, int productId) {
        String sql = "INSERT INTO WISHLIST (user_id, product_id) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 관심상품 삭제
    public void removeWishlist(int userId, int productId) {
        String sql = "DELETE FROM WISHLIST WHERE user_id = ? AND product_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 사용자의 관심상품 목록 조회
    public List<Integer> getWishlistByUserId(int userId) {
        List<Integer> productIds = new ArrayList<>();
        String sql = "SELECT product_id FROM WISHLIST WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    productIds.add(rs.getInt("product_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productIds;
    }
}