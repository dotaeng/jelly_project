package com.jelly.www.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.jelly.www.vo.SalesHistoryVO;

public class SalesHistoryDAO {
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/jelly";
    private String user = "scott";
    private String password = "tiger";

    public SalesHistoryDAO() {
        try {
            Class.forName(driver);
            System.out.println("MySQL 드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL 드라이버 로드 실패");
            e.printStackTrace();
        }
    }

    // 판매내역 조회 (페이지네이션)
    public List<SalesHistoryVO> getSalesHistory(int sellerId, int page) {
        List<SalesHistoryVO> list = new ArrayList<>();
        int pageSize = 5; 
        int offset = (page - 1) * pageSize; 

        String sql = "SELECT T.trade_id, P.image_url, P.product_name, PS.price AS sale_price " +
                     "FROM TRADE T " +
                     "JOIN PRODUCT_SELLER PS ON T.product_seller_id = PS.product_seller_id " +
                     "JOIN PRODUCT P ON PS.product_id = P.product_id " +
                     "WHERE PS.seller_id = ? " +
                     "ORDER BY T.trade_date DESC " + 
                     "LIMIT ?, ?";

        System.out.println("실행할 SQL: " + sql);
        System.out.println("seller_id: " + sellerId + ", page: " + page);

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sellerId);
            pstmt.setInt(2, offset);
            pstmt.setInt(3, pageSize);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tradeId = rs.getInt("trade_id");
                    String imageUrl = rs.getString("image_url");
                    String productName = rs.getString("product_name");
                    int salePrice = rs.getInt("sale_price");

                    SalesHistoryVO vo = new SalesHistoryVO(tradeId, imageUrl, productName, salePrice);
                    list.add(vo);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 총 판매내역 수 조회
    public int getSalesHistoryCount(int sellerId) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total " +
                     "FROM TRADE T " +
                     "JOIN PRODUCT_SELLER PS ON T.product_seller_id = PS.product_seller_id " +
                     "WHERE PS.seller_id = ?";

        System.out.println("SQL: " + sql);

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sellerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                    System.out.println("총 판매내역 수: " + count);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    // 최근 3개 판매 내역 조회
    public List<SalesHistoryVO> getRecentSalesHistory(int sellerId) {
        List<SalesHistoryVO> list = new ArrayList<>();

        String sql = "SELECT T.trade_id, P.image_url, P.product_name, PS.price AS sale_price " +
                     "FROM TRADE T " +
                     "JOIN PRODUCT_SELLER PS ON T.product_seller_id = PS.product_seller_id " +
                     "JOIN PRODUCT P ON PS.product_id = P.product_id " +
                     "WHERE PS.seller_id = ? " +
                     "ORDER BY T.created_at DESC " +
                     "LIMIT 3";

        System.out.println("SQL: " + sql);
        System.out.println("seller_id: " + sellerId);

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sellerId); // seller_id 값 설정

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tradeId = rs.getInt("trade_id");
                    String imageUrl = rs.getString("image_url");
                    String productName = rs.getString("product_name");
                    int salePrice = rs.getInt("sale_price");

                    SalesHistoryVO vo = new SalesHistoryVO(tradeId, imageUrl, productName, salePrice);
                    list.add(vo);

                    System.out.println("거래 ID: " + tradeId + ", 이미지 URL: " + imageUrl + ", 상품명: " + productName + ", 판매가격: " + salePrice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}