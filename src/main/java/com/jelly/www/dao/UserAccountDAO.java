package com.jelly.www.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.jelly.www.vo.UserAccountVO;
import com.jelly.www.vo.UserVO;

public class UserAccountDAO {
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/jelly";
    private String user = "scott";
    private String password = "tiger";

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private StringBuilder sb = new StringBuilder();

    public UserAccountDAO() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("MySQL DB 연결 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL 드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("MySQL DB 연결 실패");
            e.printStackTrace();
        }
    }
    
    // 1. 유저 계좌 추가
    public int insertAccount(UserAccountVO user) {
        sb.setLength(0);
        sb.append("INSERT INTO USER_ACCOUNT (account_id, user_id, bank_name, account_number, account_holder, is_default, created_at, updated_at) ");
        sb.append("VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())");

        int result = 0;
        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, user.getAccountId());
            pstmt.setInt(2, user.getUserId());
            pstmt.setString(3, user.getBankName());
            pstmt.setString(4, user.getAccountNumber());
            pstmt.setString(5, user.getAccountHolder());
            pstmt.setBoolean(6, user.isDefault());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // 2. 유저 계좌 변경
    public void updateAccount(UserAccountVO user) {
    	sb.setLength(0);
        sb.append("UPDATE user_account SET bank_name = ?, account_number = ?, account_holder = ?, updated_at = now() WHERE user_id = ?" );

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, user.getBankName());
            pstmt.setString(2, user.getAccountNumber());
            pstmt.setString(3, user.getAccountHolder());
            pstmt.setInt(4, user.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
    // 3. 유저 계좌 정보 조회
    public UserAccountVO selectAccount(int userId) {
    	UserAccountVO user = null;
    	sb.setLength(0);
        sb.append("SELECT * FROM USER_ACCOUNT WHERE user_id = ?");
        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserAccountVO(
                   rs.getInt("account_id"),
                   rs.getInt("user_id"),
                   rs.getString("bank_name"),
                   rs.getString("account_number"),
                   rs.getString("account_holder"),
                   rs.getBoolean("is_default"),
                   rs.getString("created_at"),
                   rs.getString("updated_at")
                   
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	return user;
    }
 // userId로 사용자 기본 account 정보 조회
    public UserAccountVO selectIsDefaultAccountByUserId(int userId) {
        UserAccountVO vo = null;
        sb.setLength(0);
        sb.append("SELECT account_id, user_id, bank_name, account_number, account_holder, is_default, created_at, updated_at FROM USER_ACCOUNT WHERE user_id = ? and is_default = 1");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
            vo = new UserAccountVO(
                    rs.getInt("account_id"),
                    rs.getInt("user_id"),
                    rs.getString("bank_name"),
                    rs.getString("account_number"),
                    rs.getString("account_holder"),
                    rs.getBoolean("is_default"),
                    rs.getString("created_at"),
                    rs.getString("updated_at")
                    );

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return vo;
    }
    
    // 자원 해제
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