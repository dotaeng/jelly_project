package com.jelly.www.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.jelly.www.vo.CommentVO;

public class CommentDAO {
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
		public CommentDAO() {
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
		
	    // 게시물 좋아요 데이터 삭제
	    public void deleteOne(int commentId) {
	        sb.setLength(0);
	        sb.append("delete from COMMENT ");
	        sb.append("where comment_id = ?");

	        try {
	            pstmt = conn.prepareStatement(sb.toString());
	            pstmt.setInt(1, commentId);
	            pstmt.executeUpdate();
	            System.out.println("comment 데이터 삭제 완료");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } 
	    }
		
		
		 // 팔로우 데이터 추가
	    public void insertOne(CommentVO vo) {
	        sb.setLength(0);
	        sb.append("insert into COMMENT (post_id, user_id, content) ");
	        sb.append("values (?, ?, ?)");

	        try {
	            pstmt = conn.prepareStatement(sb.toString());
	            pstmt.setInt(1, vo.getPostId());
	            pstmt.setInt(2, vo.getUserId());
	            pstmt.setString(3, vo.getContent());
	            pstmt.executeUpdate();
	            System.out.println("comment 데이터 추가 완료");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } 
	    }
	    // 팔로워 리스트 가져오기
	    public ArrayList<CommentVO> getCommentOfPost(int postId) {
	    	ArrayList<CommentVO> list = new ArrayList<CommentVO>();
	    	sb.setLength(0);
	    	sb.append("select * from COMMENT ");
	    	sb.append("where post_id = ? order by created_at asc");
	    	
	    	try {
	        	pstmt = conn.prepareStatement(sb.toString());
	        	pstmt.setInt(1, postId);
	            rs = pstmt.executeQuery();

	            while(rs.next()) {
	            	CommentVO vo = new CommentVO(
	            			rs.getInt("comment_id"),
	            			rs.getInt("post_id"), 
	    					rs.getInt("user_id"), 
	    					rs.getString("content"),
	    					rs.getInt("likes_count"),
	    					rs.getTimestamp("created_at"),
	    					rs.getTimestamp("updated_at")
	            			);
	            	
	                list.add(vo);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
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
