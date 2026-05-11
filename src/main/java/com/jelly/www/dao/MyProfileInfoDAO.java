package com.jelly.www.dao;

import com.jelly.www.vo.UserProfileInfoVO;

import java.sql.*;

public class MyProfileInfoDAO {

    // DB 연결 정보
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/jelly";
    private static final String USER = "scott";
    private static final String PASSWORD = "tiger";

    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    private StringBuffer sb = new StringBuffer();

    // 생성자
    public MyProfileInfoDAO() {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("MySQL DB 연결 성공");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL 드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("MySQL DB 연결 실패");
            e.printStackTrace();
        }
    }

    // 1. 사용자 정보 조회
    public UserProfileInfoVO getUserProfile(int userId) {
        UserProfileInfoVO user = null;
        sb.setLength(0);
        sb.append("SELECT user_id, user_name, nickname, email, phone_number, profile_image ");
        sb.append("FROM USER WHERE user_id = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String profileImage = rs.getString("profile_image");
                if (profileImage == null || profileImage.isEmpty()) {
                	profileImage = "/img/profile.png";
                }

                user = new UserProfileInfoVO(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("nickname"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        profileImage
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return user;
    }

    // 2. 닉네임 수정
    public boolean updateNickname(int userId, String nickname) {
        boolean isUpdated = false;
        sb.setLength(0);
        sb.append("UPDATE USER SET nickname = ? WHERE user_id = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, nickname);
            pstmt.setInt(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            isUpdated = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return isUpdated;
    }

    // 3. 프로필 이미지 수정
    public boolean updateProfileImage(int userId, String profileImage) {
        boolean isUpdated = false;
        sb.setLength(0);
        sb.append("UPDATE USER SET profile_image = ? WHERE user_id = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, profileImage);
            pstmt.setInt(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            isUpdated = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return isUpdated;
    }

    // 자원 해제 메서드
    private void close() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}