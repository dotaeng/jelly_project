package com.jelly.www.dao;

import java.sql.*;
import java.util.ArrayList;

import com.jelly.www.vo.PostVO;

public class PostDAO {
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
	public PostDAO() {
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
	
	public PostVO selectOne(int postId) {
		PostVO postVO = null;
		sb.setLength(0);
		sb.append("select * from POST where post_id = ?");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				postVO = new PostVO(
					rs.getInt("post_id"), 
					rs.getInt("user_id"), 
					rs.getInt("style_category"),
					rs.getString("title"), 
					rs.getString("content"), 
					rs.getString("thumbnail_image_url"), 
					rs.getInt("like_count"),
					rs.getInt("comment_count"), 
					rs.getInt("view_count"), 
					rs.getInt("save_count"),
					rs.getTimestamp("created_at"),
					rs.getTimestamp("updated_at")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return postVO;
	}
	
	public ArrayList<PostVO> selectAll(){
		ArrayList<PostVO> list = new ArrayList<PostVO>();
		sb.setLength(0);
        sb.append("select * from POST");

        try {
        	pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();

            while(rs.next()) {
            	PostVO vo = new PostVO(
            			rs.getInt("post_id"), 
    					rs.getInt("user_id"), 
    					rs.getInt("style_category"),
    					rs.getString("title"), 
    					rs.getString("content"), 
    					rs.getString("thumbnail_image_url"), 
    					rs.getInt("like_count"),
    					rs.getInt("comment_count"), 
    					rs.getInt("view_count"), 
    					rs.getInt("save_count"),
    					rs.getTimestamp("created_at"),
    					rs.getTimestamp("updated_at")
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
	
	public ArrayList<PostVO> selectByStyleCategory(int category, int currentPage, int orderBy){
		ArrayList<PostVO> list = new ArrayList<PostVO>();
		sb.setLength(0);
		if(category == 0) {
			if(orderBy == 0) {
				sb.append("select * from POST order by created_at desc limit ?, 12");
			} else if(orderBy == 1) {
				sb.append("select * from POST order by like_count desc limit ?, 12");
			}
		} else {
			if(orderBy == 0) {
				sb.append("select * from POST where style_category = ? order by created_at desc limit ?, 12");
			} else if (orderBy == 1) {
				sb.append("select * from POST where style_category = ? order by like_count desc limit ?, 12");
			}
			
		}
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());
        	if(category == 0) {
        		pstmt.setInt(1, (currentPage-1)*12);
        	} else {
        		pstmt.setInt(1, category);
        		pstmt.setInt(2, (currentPage-1)*12);
        	}
        	rs = pstmt.executeQuery();

            while(rs.next()) {
            	PostVO vo = new PostVO(
            			rs.getInt("post_id"), 
    					rs.getInt("user_id"), 
    					rs.getInt("style_category"),
    					rs.getString("title"), 
    					rs.getString("content"), 
    					rs.getString("thumbnail_image_url"), 
    					rs.getInt("like_count"),
    					rs.getInt("comment_count"), 
    					rs.getInt("view_count"), 
    					rs.getInt("save_count"),
    					rs.getTimestamp("created_at"),
    					rs.getTimestamp("updated_at")
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
	
	public ArrayList<PostVO> getByUserId(int userId){
		ArrayList<PostVO> list = new ArrayList<PostVO>();
		sb.setLength(0);
        sb.append("select * from POST ");
        sb.append("where user_id = ? ");
        sb.append("order by created_at desc");

        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	PostVO vo = new PostVO(
            			rs.getInt("post_id"), 
    					rs.getInt("user_id"), 
    					rs.getInt("style_category"),
    					rs.getString("title"), 
    					rs.getString("content"), 
    					rs.getString("thumbnail_image_url"), 
    					rs.getInt("like_count"),
    					rs.getInt("comment_count"), 
    					rs.getInt("view_count"), 
    					rs.getInt("save_count"),
    					rs.getTimestamp("created_at"),
    					rs.getTimestamp("updated_at")
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
	
	
	public ArrayList<PostVO> getFollowersPosts(int userId, int orderBy){
		ArrayList<PostVO> list = new ArrayList<PostVO>();
		sb.setLength(0);

		if(orderBy == 0) {
			System.out.println("order by recent");
			sb.append("SELECT POST.* FROM POST INNER JOIN FOLLOW ");
	        sb.append("ON POST.user_id = FOLLOW.following_id ");
	        sb.append("WHERE FOLLOW.follower_id = ? ");
	        sb.append("order by created_at desc");
		} else if (orderBy == 1) {
			System.out.println("order by like count");
			sb.append("SELECT POST.* FROM POST INNER JOIN FOLLOW ");
	        sb.append("ON POST.user_id = FOLLOW.following_id ");
	        sb.append("WHERE FOLLOW.follower_id = ? ");
	        sb.append("order by like_count desc");
		}

        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
            	PostVO vo = new PostVO(
            			rs.getInt("post_id"), 
    					rs.getInt("user_id"), 
    					rs.getInt("style_category"),
    					rs.getString("title"), 
    					rs.getString("content"), 
    					rs.getString("thumbnail_image_url"), 
    					rs.getInt("like_count"),
    					rs.getInt("comment_count"), 
    					rs.getInt("view_count"), 
    					rs.getInt("save_count"),
    					rs.getTimestamp("created_at"),
    					rs.getTimestamp("updated_at")
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
	
	
	
	// 조회수 증가 메서드
	public void plusView(int postId) {
		sb.setLength(0);
        sb.append("update POST ");
        sb.append("set view_count = view_count + 1 ");
        sb.append("where post_id = ?");
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
            System.out.println("조회수 증가 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
	}
	
	// 조회수 증가 메서드
	public void plusComment(int postId) {
		sb.setLength(0);
        sb.append("update POST ");
        sb.append("set comment_count = comment_count + 1 ");
        sb.append("where post_id = ?");
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
            System.out.println("comment수 증가 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
	}
	
	// 조회수 증가 메서드
	public void minusComment(int postId) {
		sb.setLength(0);
        sb.append("update POST ");
        sb.append("set comment_count = comment_count - 1 ");
        sb.append("where post_id = ?");
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
            System.out.println("comment수 minus 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
	}
	
	// 좋아요 증가 메서드
	public void plusLike(int postId) {
		sb.setLength(0);
        sb.append("update POST ");
        sb.append("set like_count = like_count + 1 ");
        sb.append("where post_id = ?");
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
            System.out.println("좋아요 증가 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
	}
	
	// 좋아요 하락 메서드
	public void minusLike(int postId) {
		sb.setLength(0);
        sb.append("update POST ");
        sb.append("set like_count = like_count - 1 ");
        sb.append("where post_id = ?");
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
            System.out.println("좋아요 하락 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
	}
	
	public void createNewPost(PostVO vo) {
		sb.setLength(0);
		sb.append("INSERT INTO POST (user_id, title, content, style_category, created_at, updated_at) ");
		sb.append("values (?, ?, ?, ?, NOW(), NOW())");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, vo.getUserId());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setInt(4, vo.getStyleCategory());
	        pstmt.executeUpdate();
	        System.out.println("created new post in dao");
	        
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            close();
        }
	}
	
	public void setThumbNailImageURL(int postId, String thumbnailURL) {
		sb.setLength(0);
		sb.append("update POST set thumbnail_image_url = ? where post_id = ? ");
		
		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, thumbnailURL);
			pstmt.setInt(2, postId);
	        pstmt.executeUpdate();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
            close();
        }
	}
	
	
	
	public PostVO getUsersNewPost(int userId) {
		PostVO postVO = null;
		sb.setLength(0);
		sb.append("SELECT * FROM POST WHERE user_id = ? ORDER BY created_at DESC LIMIT 1");

		try {
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				postVO = new PostVO(
					rs.getInt("post_id"), 
					rs.getInt("user_id"), 
					rs.getInt("style_category"),
					rs.getString("title"), 
					rs.getString("content"), 
					rs.getString("thumbnail_image_url"), 
					rs.getInt("like_count"),
					rs.getInt("comment_count"), 
					rs.getInt("view_count"), 
					rs.getInt("save_count"),
					rs.getTimestamp("created_at"),
					rs.getTimestamp("updated_at")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return postVO;
	}

	// 저장 증가 메서드
	public void plusSave(int postId) {
		sb.setLength(0);
        sb.append("update POST ");
        sb.append("set save_count = save_count + 1 ");
        sb.append("where post_id = ?");
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
            System.out.println("저장 증가 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
	}
	
	// 저장 하락 메서드
	public void minusSave(int postId) {
		sb.setLength(0);
        sb.append("update POST ");
        sb.append("set save_count = save_count - 1 ");
        sb.append("where post_id = ?");
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
            System.out.println("저장 하락 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
	}
	
	public void updatePost(int postId, String title, String content, int styleCategory) {
		sb.setLength(0);
        sb.append("update POST ");
        sb.append("set title = ?,  content = ?, style_category = ?, updated_at = NOW() ");
        sb.append("where post_id = ?");
        
        try {
        	pstmt = conn.prepareStatement(sb.toString());pstmt.setString(1, title);
        	pstmt.setString(2, content);
        	pstmt.setInt(3, styleCategory);
            pstmt.setInt(4, postId);
            pstmt.executeUpdate();
            System.out.println("updated post");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
	}
	
    // 4. 데이터 삭제
    public void deleteOne(int postId) {
        sb.setLength(0);
        sb.append("DELETE FROM POST WHERE post_id = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, postId);
            pstmt.executeUpdate();
            System.out.println("post 삭제 완료: ID = " + postId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	close();
        }
    }

    // 좋아요 순 상위 4개 가져오기
    public ArrayList<PostVO> getTop4ByLikes(){
    	ArrayList<PostVO> list = new ArrayList<PostVO>();
    	sb.setLength(0);
    	sb.append("select * from POST order by like_count desc limit 4");
    	
    	 try {
         	pstmt = conn.prepareStatement(sb.toString());
             rs = pstmt.executeQuery();

             while(rs.next()) {
             	PostVO vo = new PostVO(
             			rs.getInt("post_id"), 
     					rs.getInt("user_id"), 
     					rs.getInt("style_category"),
     					rs.getString("title"), 
     					rs.getString("content"), 
     					rs.getString("thumbnail_image_url"), 
     					rs.getInt("like_count"),
     					rs.getInt("comment_count"), 
     					rs.getInt("view_count"), 
     					rs.getInt("save_count"),
     					rs.getTimestamp("created_at"),
     					rs.getTimestamp("updated_at")
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