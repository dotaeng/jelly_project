package com.jelly.www.dao;

import java.sql.*;
import java.util.ArrayList;

import com.jelly.www.vo.PostSaveVO;

public class PostSaveDAO {
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
	public PostSaveDAO() {
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
	
	// 저장 중인지 확인하는 메서드
	public boolean checkSave(int postId, int userId) {
		boolean result = false;
		
		sb.setLength(0);
        sb.append("select 1 ");
        sb.append("from POST_SAVE ");
        sb.append("where post_id = ? and user_id = ?");
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId);
            pstmt.setInt(2, userId);
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
	
	 // 게시물 저장 데이터 추가
    public void insertOne(PostSaveVO vo) {
        sb.setLength(0);
        sb.append("insert into POST_SAVE (post_id, user_id, saved_at) ");
        sb.append("values (?, ?, now())");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, vo.getPostId());
            pstmt.setInt(2, vo.getUserId());
            pstmt.executeUpdate();
            System.out.println("게시물 저장 데이터 추가 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	close();
        }
    }

    // 게시물 저장 데이터 삭제
    public void deleteOne(int postId, int userId) {
        sb.setLength(0);
        sb.append("delete from POST_SAVE ");
        sb.append("where post_id = ? and user_id = ? ");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            System.out.println("게시물 저장 데이터 삭제 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	close();
        }
    }
    
    // 유저 아이디로 조회
    public ArrayList<PostSaveVO> getByUserId(int userId){
    	ArrayList<PostSaveVO> list = new ArrayList<PostSaveVO>();
    	
    	sb.setLength(0);
        sb.append("select post_id, user_id from POST_SAVE ");
        sb.append("where user_id = ? ");

        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	PostSaveVO vo = new PostSaveVO(
            			rs.getInt("post_id"),
            			rs.getInt("user_id")
                );
                list.add(vo);
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