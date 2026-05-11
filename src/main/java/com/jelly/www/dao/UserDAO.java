package com.jelly.www.dao;

import java.sql.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jelly.www.vo.AddressVO;
import com.jelly.www.vo.UserAccountVO;
import com.jelly.www.vo.UserVO;


public class UserDAO {
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/jelly";
    private String user = "scott";
    private String password = "tiger";

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private StringBuilder sb = new StringBuilder();
    private StringBuilder sb1 = new StringBuilder();

    public UserDAO() {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL 드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("MySQL DB 연결 실패");
            e.printStackTrace();
        }
    }

    // 1. 사용자 전체 조회
    public List<UserVO> selectAll() {
        List<UserVO> userList = new ArrayList<>();
        sb.setLength(0);
        sb.append("SELECT * FROM USER");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	String profileImage = rs.getString("profile_image");
            	// 프사가 null이거나 값이 없으면 기본 프로필 출력
            	if (profileImage == null || profileImage.isEmpty()) {
            	    profileImage = "/img/profile.png";
            	}
                UserVO user = new UserVO(
                    rs.getInt("user_id"),
                    rs.getString("user_name"),
                    rs.getString("nickname"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone_number"),
                    rs.getString("birth_date"),
                    rs.getString("kakao_id"),
                    rs.getString("naver_id"),
                    rs.getString("profile_image"),
                    rs.getInt("follower_count"),                   
                    rs.getInt("following_count"),                   
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return userList;
    }

    // 2. 특정 사용자 조회 (ID로)
    public UserVO selectOne(int userId) {
        UserVO user = null;
        sb.setLength(0);
        sb.append("SELECT * FROM USER WHERE user_id = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
            	String profileImage = rs.getString("profile_image");
            	// 프사가 null이거나 값이 없으면 기본 프로필 출력
            	if (profileImage == null || profileImage.isEmpty()) {
            	    profileImage = "/img/profile.png";

            	}
                user = new UserVO(
                    rs.getInt("user_id"),
                    rs.getString("user_name"),
                    rs.getString("nickname"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone_number"),
                    rs.getString("birth_date"),
                    rs.getString("kakao_id"),
                    rs.getString("naver_id"),
                    rs.getString("profile_image"),
                    rs.getInt("follower_count"),                   
                    rs.getInt("following_count"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

 // 3. 사용자 추가
    public int insertOne(UserVO user) {
        sb.setLength(0);
        sb.append("INSERT INTO USER (user_name, nickname, email, password, phone_number, birth_date, kakao_id, naver_id, profile_image, created_at, updated_at) ");
        sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())");

        int result = 0;

        try {
            pstmt = conn.prepareStatement(sb.toString());
            
            // 닉네임이 null이거나 비어 있으면 10자리 랜덤 닉네임 생성
            String nickname = user.getNickname();
            if (nickname == null || nickname.isEmpty()) {
                nickname = generateRandomNickname(10); // 10자리 랜덤값 생성
            }
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, nickname); // 랜덤 닉네임
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getPhoneNumber());
            pstmt.setString(6, user.getBirthDate());
            pstmt.setString(7, user.getKakaoId());
            pstmt.setString(8, user.getNaverId());

            // 기본 프로필 이미지 설정
            String profileImage = user.getProfileImage();
            if (profileImage == null || profileImage.isEmpty()) {
                profileImage = "https://www.pngarts.com/files/10/Default-Profile-Picture-Download-PNG-Image.png";
            }
            pstmt.setString(9, profileImage);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return result;
    }
    
    // 랜덤 닉네임 생성
    private String generateRandomNickname(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder nickname = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            nickname.append(chars.charAt(random.nextInt(chars.length())));
        }

        return nickname.toString();
    }

    // 4. 사용자 삭제
    public int deleteOne(int userId) {
        sb.setLength(0);
        sb.append("DELETE FROM USER WHERE user_id = ?");

        int result = 0;
        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, userId);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return result;
    }

    // 5. 사용자 정보 업데이트
    public int updateOne(UserVO user) {
        sb.setLength(0);
        sb.append("UPDATE USER SET user_name = ?, nickname = ?, email = ?, password = ?, phone_number = ?, ");
        sb.append("birth_date = ?, kakao_id = ?, naver_id = ?, profile_image = ?, updated_at = NOW() ");
        sb.append("WHERE user_id = ?");

        int result = 0;
        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getNickname());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());
            pstmt.setString(5, user.getPhoneNumber());
            pstmt.setString(6, user.getBirthDate());
            pstmt.setString(7, user.getKakaoId());
            pstmt.setString(8, user.getNaverId());
            pstmt.setString(9, user.getProfileImage());
            pstmt.setInt(10, user.getUserId());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return result;
    }

    // 6. 이메일 및 비밀번호로 사용자 조회
    public UserVO findOneByEmailAndPw(String email, String password) {
        UserVO user = null;
        sb.setLength(0);
        sb.append("SELECT * FROM USER WHERE email = ? AND password = ?");

       
        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserVO(
                    rs.getInt("user_id"),
                    rs.getString("user_name"),
                    rs.getString("nickname"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone_number"),
                    rs.getString("birth_date"),
                    rs.getString("kakao_id"),
                    rs.getString("naver_id"),
                    rs.getString("profile_image"),
                    rs.getInt("follower_count"),                   
                    rs.getInt("following_count"),    
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return user;
    }
    
    // 7. 회원가입할 때 중복 회원 체크하는 메서드
    public boolean isDuplicate(String email, String phoneNumber) {
        sb.setLength(0);
        sb.append("SELECT COUNT(*) FROM USER WHERE email = ? OR phone_number = ?");
        
        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, email);
            pstmt.setString(2, phoneNumber);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0; // 중복이 있으면 true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false; // 중복이 없으면 false 
    }


	// 팔로우 증가 메서드
	public void plusFollow(int followerId, int followingId) {
		sb.setLength(0);
        sb.append("update USER ");
        sb.append("set follower_count = follower_count + 1 ");
        sb.append("where user_id = ?");
        
        sb1.setLength(0);
        sb1.append("update USER ");
        sb1.append("set following_count = following_count + 1 ");
        sb1.append("where user_id = ?");
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, followingId);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement(sb1.toString());
            pstmt.setInt(1, followerId);
            pstmt.executeUpdate();
            
            System.out.println("팔로우 증가 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
	}
	
	// 팔로우 하락 메서드
	public void minusFollow(int followerId, int followingId) {
		sb.setLength(0);
        sb.append("update USER ");
        sb.append("set follower_count = follower_count - 1 ");
        sb.append("where user_id = ?");
        
        sb1.setLength(0);
        sb1.append("update USER ");
        sb1.append("set following_count = following_count - 1 ");
        sb1.append("where user_id = ?");
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, followingId);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement(sb1.toString());
            pstmt.setInt(1, followerId);
            pstmt.executeUpdate();
            
            System.out.println("팔로우 하락 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
	}
    
    //8. 핸드폰 번호로 이메일 찾기
    public UserVO findEmail(String phonenumber) {
        UserVO user = null;
        sb.setLength(0);
        sb.append("SELECT * FROM USER WHERE PHONE_NUMBER = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, phonenumber);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserVO(
                		rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("nickname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("birth_date"),
                        rs.getString("kakao_id"),
                        rs.getString("naver_id"),
                        rs.getString("profile_image"),
                        rs.getInt("follower_count"),                   
                        rs.getInt("following_count"),    
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        return user;
    }
    
    // 9. 이메일로 정보찾기
    public UserVO findEmailByUserEmail(String userEmail) {
        UserVO user = null;
        sb.setLength(0);
        sb.append("SELECT * FROM USER WHERE EMAIL = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, userEmail);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserVO(
                		rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("nickname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("birth_date"),
                        rs.getString("kakao_id"),
                        rs.getString("naver_id"),
                        rs.getString("profile_image"),
                        rs.getInt("follower_count"),                   
                        rs.getInt("following_count"),    
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
 // 10. 비밀번호 재설정
    public UserVO updateUserPassword(String userEmail, String newPassword) {
        UserVO user = null;
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE USER SET password = ? WHERE EMAIL = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, newPassword);
            pstmt.setString(2, userEmail);
            int result = pstmt.executeUpdate();

            if (result > 0) {
                user = findEmailByUserEmail(userEmail); // 비밀번호 업데이트 후 사용자 정보를 다시 조회
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
 // 11. 이메일 변경
    public UserVO updateInfoUserEmail(String userEmail, int userId) {
        UserVO user = null;
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE USER SET email = ? WHERE user_id = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, userEmail);
            pstmt.setInt(2, userId);
            int result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
  
 // 12. 비밀번호 변경
    public UserVO updateInfoUserPw(String userPw, int userId) {
        UserVO user = null;
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE USER SET password = ? WHERE user_id = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, userPw);
            pstmt.setInt(2, userId);
            int result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
 // 13. 휴대폰 번호 변경
    public UserVO updateInfoUserPhoneNumber(String userPhoneNum, int userId) {
        UserVO user = null;
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE USER SET phone_number = ? WHERE user_id = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, userPhoneNum);
            pstmt.setInt(2, userId);
            int result = pstmt.executeUpdate();

      
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // 자원 해제
    public void close() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}