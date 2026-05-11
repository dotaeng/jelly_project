package com.jelly.www.dao;

import java.sql.*;

import com.jelly.www.vo.SalesHistoryDetailVO;

public class SalesHistoryDetailDAO {
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/jelly";
    private String user = "scott";
    private String password = "tiger";

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private StringBuffer sb = new StringBuffer();

    public SalesHistoryDetailDAO() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("MySQL DB 연결 성공");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL 드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("MySQL DB 연결 실패");
            e.printStackTrace();
        }
    }

    public SalesHistoryDetailVO getDetail(int tradeId, int sellerId) {
        SalesHistoryDetailVO vo = null;
        sb.setLength(0);

        sb.append("SELECT T.trade_id, P.product_id, P.product_name, P.image_url, PS.price AS sale_price, ");
        sb.append("UA.bank_name, UA.account_number, UA.account_holder, ");
        sb.append("T.trade_date ");
        sb.append("FROM TRADE T ");
        sb.append("JOIN PRODUCT_SELLER PS ON T.product_seller_id = PS.product_seller_id ");
        sb.append("JOIN PRODUCT P ON PS.product_id = P.product_id ");
        sb.append("LEFT JOIN USER_ACCOUNT UA ON UA.user_id = ? AND UA.is_default = TRUE ");
        sb.append("WHERE T.trade_id = ? AND PS.seller_id = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, sellerId); // 로그인된 사용자 ID
            pstmt.setInt(2, tradeId); // 조회할 거래 ID
            pstmt.setInt(3, sellerId); // 판매자인지 확인

            rs = pstmt.executeQuery();

            if (rs.next()) {
                vo = new SalesHistoryDetailVO();
                vo.setTradeId(rs.getInt("trade_id"));
                vo.setProductId(rs.getInt("product_id")); // productId 추가
                vo.setProductName(rs.getString("product_name"));
                vo.setImageUrl(rs.getString("image_url"));
                vo.setSalePrice(rs.getInt("sale_price"));
                vo.setBankName(rs.getString("bank_name")); // NULL 가능
                vo.setAccountNumber(rs.getString("account_number")); // NULL 가능
                vo.setAccountHolder(rs.getString("account_holder")); // NULL 가능
                vo.setTradeDate(rs.getTimestamp("trade_date"));

                System.out.println("조회된 값: " + vo);
            } else {
                System.out.println("해당 거래 정보가 없습니다.");
            }
        } catch (SQLException e) {
            System.err.println("SQL 에러");
            e.printStackTrace();
        } finally {
            close();
        }
        return vo;
    }

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