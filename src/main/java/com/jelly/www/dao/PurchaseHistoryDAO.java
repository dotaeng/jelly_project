package com.jelly.www.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.jelly.www.vo.PurchaseHistoryVO;

public class PurchaseHistoryDAO {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/jelly";
    String user = "scott";
    String password = "tiger";

    public PurchaseHistoryDAO() {
        try {
            Class.forName(driver);
            System.out.println("MySQL 드라이버 로드 성공");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL 드라이버 로드 실패");
            e.printStackTrace();
        }
    }

    // 구매내역 조회
    public List<PurchaseHistoryVO> getPurchaseHistory(int buyerId) {
        List<PurchaseHistoryVO> list = new ArrayList<>();
        String sql = "SELECT T.trade_id, P.image_url, P.product_name, T.total_price " +
                     "FROM TRADE T " +
                     "JOIN PRODUCT_SELLER PS ON T.product_seller_id = PS.product_seller_id " +
                     "JOIN PRODUCT P ON PS.product_id = P.product_id " +
                     "WHERE T.buyer_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, buyerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tradeId = rs.getInt("trade_id");
                    String imageUrl = rs.getString("image_url");
                    String productName = rs.getString("product_name");
                    int purchasePrice = rs.getInt("total_price");
                    PurchaseHistoryVO vo = new PurchaseHistoryVO(tradeId, imageUrl, productName, purchasePrice);
                    list.add(vo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 구매내역 조회 (페이지네이션, 최신순 정렬)
    public List<PurchaseHistoryVO> getPurchaseHistory(int buyerId, int page) {
        List<PurchaseHistoryVO> list = new ArrayList<>();
        int pageSize = 5; // 한 페이지에 출력할 데이터 수
        int offset = (page - 1) * pageSize; // 시작 위치 계산

        String sql = "SELECT T.trade_id, P.image_url, P.product_name, T.total_price " +
                     "FROM TRADE T " +
                     "JOIN PRODUCT_SELLER PS ON T.product_seller_id = PS.product_seller_id " +
                     "JOIN PRODUCT P ON PS.product_id = P.product_id " +
                     "WHERE T.buyer_id = ? " +
                     "ORDER BY T.trade_date DESC " + 
                     "LIMIT ?, ?";

        System.out.println("실행할 SQL: " + sql);
        System.out.println("buyer_id: " + buyerId + ", page: " + page);

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, buyerId);
            pstmt.setInt(2, offset);
            pstmt.setInt(3, pageSize);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tradeId = rs.getInt("trade_id");
                    String imageUrl = rs.getString("image_url");
                    String productName = rs.getString("product_name");
                    int purchasePrice = rs.getInt("total_price");

                    PurchaseHistoryVO vo = new PurchaseHistoryVO(tradeId, imageUrl, productName, purchasePrice);
                    list.add(vo);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 총 구매내역 수 조회
    public int getPurchaseHistoryCount(int buyerId) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS total " +
                     "FROM TRADE T " +
                     "WHERE T.buyer_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, buyerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    // 최근 3개 구매내역 조회
    public List<PurchaseHistoryVO> getRecentPurchaseHistory(int buyerId) {
        List<PurchaseHistoryVO> list = new ArrayList<>();
        String sql = "SELECT T.trade_id, P.image_url, P.product_name, T.total_price " +
                     "FROM TRADE T " +
                     "JOIN PRODUCT_SELLER PS ON T.product_seller_id = PS.product_seller_id " +
                     "JOIN PRODUCT P ON PS.product_id = P.product_id " +
                     "WHERE T.buyer_id = ? " +
                     "ORDER BY T.trade_id DESC " + // 최신 순으로 정렬
                     "LIMIT 3"; // 최근 3개만 조회

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, buyerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tradeId = rs.getInt("trade_id");
                    String imageUrl = rs.getString("image_url");
                    String productName = rs.getString("product_name");
                    int purchasePrice = rs.getInt("total_price");
                    PurchaseHistoryVO vo = new PurchaseHistoryVO(tradeId, imageUrl, productName, purchasePrice);
                    list.add(vo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}