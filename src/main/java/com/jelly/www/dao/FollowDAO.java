package com.jelly.www.dao;

import java.sql.*;
import java.util.ArrayList;

import com.jelly.www.vo.FollowVO;

public class FollowDAO {
	// DB 연결 정보
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/jelly";
	String user = "scott";
	String password = "tiger";

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sb = new StringBuffer();

	// 생성자
	public FollowDAO() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			System.err.println("MySQL 드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("MySQL DB 연결 실패");
			e.printStackTrace();
		}
	}
	
	// 팔로우 중인지 확인하는 메서드
	public boolean checkFollow(int followerId, int followingId) {
		boolean result = false;
		
		sb.setLength(0);
        sb.append("select 1 ");
        sb.append("from FOLLOW ");
        sb.append("where follower_id = ? and following_id = ?");
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, followerId);
            pstmt.setInt(2, followingId);
            rs = pstmt.executeQuery();

            if (rs.next()) { // 데이터가 존재하면 true를 반환
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
		
		return result;
	}
	
	 // 팔로우 데이터 추가
    public void insertOne(FollowVO vo) {
        sb.setLength(0);
        sb.append("insert into FOLLOW (follower_id, following_id, created_at) ");
        sb.append("values (?, ?, now())");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, vo.getFollowerId());
            pstmt.setInt(2, vo.getFollowingId());
            pstmt.executeUpdate();
            System.out.println("팔로우 데이터 추가 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	close();
        }
    }

    // 팔로우 데이터 삭제
    public void deleteOne(int followerId, int followingId) {
        sb.setLength(0);
        sb.append("delete from FOLLOW ");
        sb.append("where follower_id = ? and following_id = ? ");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, followerId);
            pstmt.setInt(2, followingId);
            pstmt.executeUpdate();
            System.out.println("팔로우 데이터 삭제 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	close();
        }
    }
    
    // 팔로워 리스트 가져오기
    public ArrayList<Integer> getFollowerIdList(int followingId) {
    	ArrayList<Integer> list = new ArrayList<Integer>();
    	sb.setLength(0);
    	sb.append("select follower_id from FOLLOW ");
    	sb.append("where following_id = ?");
    	
    	try {
        	pstmt = conn.prepareStatement(sb.toString());
        	pstmt.setInt(1, followingId);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                list.add(rs.getInt("follower_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    	
    	return list;
    }
    
    // 팔로잉 리스트 가져오기
    public ArrayList<Integer> getFollowingIdList(int followerId) {
    	ArrayList<Integer> list = new ArrayList<Integer>();
    	sb.setLength(0);
    	sb.append("select following_id from FOLLOW ");
    	sb.append("where follower_id = ?");
    	
    	try {
        	pstmt = conn.prepareStatement(sb.toString());
        	pstmt.setInt(1, followerId);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                list.add(rs.getInt("following_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    	
    	return list;
    }

	// 자원 해제 메서드
	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}