package com.jelly.www.dao;

import com.jelly.www.vo.WishPageVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishPageDAO {
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

    // 관심상품 조회 (페이지네이션 추가)
    public List<WishPageVO> getWishlist(int userId, int offset, int limit) {
        List<WishPageVO> wishlist = new ArrayList<>();
        String sql = "SELECT " +
                     "    P.product_id AS productId, " +
                     "    P.image_url AS productImage, " +
                     "    P.product_name AS productName, " +
                     "    P.description AS productDescription, " +
                     "    MIN(PS.price) AS lowestPrice, " +
                     "    P.initial_price AS releasePrice " +
                     "FROM WISHLIST W " +
                     "JOIN PRODUCT P ON W.product_id = P.product_id " +
                     "LEFT JOIN PRODUCT_SELLER PS ON P.product_id = PS.product_id " +
                     "WHERE W.user_id = ? " +
                     "GROUP BY P.product_id, P.image_url, P.product_name, P.description, P.initial_price " +
                     "LIMIT ? OFFSET ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, limit);
            pstmt.setInt(3, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("productId");
                    String productImage = rs.getString("productImage");
                    String productName = rs.getString("productName");
                    String productDescription = rs.getString("productDescription");
                    Integer lowestPrice = rs.getObject("lowestPrice", Integer.class);
                    Integer releasePrice = rs.getObject("releasePrice", Integer.class);

                    WishPageVO vo = new WishPageVO(productId, productImage, productName, productDescription, lowestPrice, releasePrice);
                    wishlist.add(vo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishlist;
    }

    // 관심상품 전체 개수 조회
    public int getWishlistCount(int userId) {
        String sql = "SELECT COUNT(*) FROM WISHLIST WHERE user_id = ?";
        int count = 0;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    // 관심상품 삭제
    public void removeWishlistItem(int userId, int productId) {
        String sql = "DELETE FROM WISHLIST WHERE user_id = ? AND product_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, productId);

            int rowsAffected = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}