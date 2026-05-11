package com.jelly.www.dao;

import java.sql.*;
import java.util.ArrayList;

import com.jelly.www.vo.PostImageVO;

public class PostImageDAO {
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
	public PostImageDAO() {
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
	
	public ArrayList<PostImageVO> getByPostId(int postId) {
		ArrayList<PostImageVO> list = new ArrayList<PostImageVO>();
		sb.setLength(0);
        sb.append("select post_image_id, post_image_url, post_id, post_image_order ");
        sb.append("from POST_IMAGE ");
        sb.append("where post_id = ? ");
        sb.append("order by post_image_order asc");

        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PostImageVO vo = new PostImageVO(
                    rs.getInt("post_image_id"),
                    rs.getString("post_image_url"),
                    rs.getInt("post_id"),
                    rs.getInt("post_image_order")
                    
//                    // need to add to database
//                    rs.getString("post_image_path")
                 
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
	
	
	public PostImageVO getFirstImageByPostId(int postId) {
		PostImageVO vo = null;
		sb.setLength(0);
        sb.append("select post_image_id, post_image_url, post_id, post_image_order ");
        sb.append("from POST_IMAGE ");
        sb.append("where post_id = ? ");
        sb.append("order by post_image_order asc");

        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                vo = new PostImageVO(
                    rs.getInt("post_image_id"),
                    rs.getString("post_image_url"),
                    rs.getInt("post_id"),
                    rs.getInt("post_image_order")
                    
                    
//                 // need to add to database
//                    rs.getString("post_image_path")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
		
		return vo;
	}
	
	public void deleteImageOfPost(int postId) {
		sb.setLength(0);
		sb.append("delete from POST_IMAGE where post_id = ?");
		try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId); 
            pstmt.executeUpdate();
            System.out.println("deleting all images of postId " + postId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	

	
	
    public void insertOne(PostImageVO vo) {
        sb.setLength(0);
//        sb.append("insert into POST_IMAGE (post_image_url, post_id, post_image_order, post_image_path) ");
//        sb.append("values (?, ?, ?);");
        
        sb.append("insert into POST_IMAGE (post_image_url, post_id, post_image_order) ");
        sb.append("values (?, ?, ?);");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, vo.getPostImageUrl()); 
            pstmt.setInt(2, vo.getPostId());
            pstmt.setInt(3, vo.getPostImageOrder());
            
            
//            // need to add to database
//            pstmt.setString(4, vo.getPostImagePath()); 
            
            pstmt.executeUpdate();
            System.out.println("상품 추가 완료: " + vo);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	close();
        }
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