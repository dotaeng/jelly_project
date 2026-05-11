package com.jelly.www.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.jelly.www.vo.NoticeVO;

public class NoticeDAO {
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
    public NoticeDAO() {
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

    // 1. 공지사항 전체 조회
    public List<NoticeVO> selectAll() {
        List<NoticeVO> list = new ArrayList<>();
        sb.setLength(0);
        sb.append("SELECT notice_id, user_id, title, content, created_at, updated_at ");
        sb.append("FROM NOTICE ORDER BY created_at DESC");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                NoticeVO vo = new NoticeVO(
                    rs.getInt("notice_id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
                list.add(vo);
                System.out.println("조회된 공지사항: " + vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return list;
    }

    // 2. 공지사항 상세 조회
    public NoticeVO selectOne(int noticeId) {
        NoticeVO vo = null;
        sb.setLength(0);
        sb.append("SELECT notice_id, user_id, title, content, created_at, updated_at ");
        sb.append("FROM NOTICE WHERE notice_id = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, noticeId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                vo = new NoticeVO(
                    rs.getInt("notice_id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return vo;
    }

    // 3. 공지사항 추가
    public void insertOne(NoticeVO vo) {
        sb.setLength(0);
        sb.append("INSERT INTO NOTICE (user_id, title, content, created_at, updated_at) ");
        sb.append("VALUES (?, ?, ?, NOW(), NOW())");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, vo.getUserId());
            pstmt.setString(2, vo.getTitle());
            pstmt.setString(3, vo.getContent());
            pstmt.executeUpdate();
            System.out.println("공지사항 추가 완료: " + vo);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    // 4. 공지사항 수정
    public void updateOne(NoticeVO vo) {
        sb.setLength(0);
        sb.append("UPDATE NOTICE SET title = ?, content = ?, updated_at = NOW() ");
        sb.append("WHERE notice_id = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setString(1, vo.getTitle());
            pstmt.setString(2, vo.getContent());
            pstmt.setInt(3, vo.getNoticeId());
            pstmt.executeUpdate();
            System.out.println("공지사항 수정 완료: " + vo);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    // 5. 공지사항 삭제
    public void deleteOne(int noticeId) {
        sb.setLength(0);
        sb.append("DELETE FROM NOTICE WHERE notice_id = ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            pstmt.setInt(1, noticeId);
            pstmt.executeUpdate();
            System.out.println("공지사항 삭제 완료: ID = " + noticeId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
    
    // 페이지네이션
    public List<NoticeVO> selectByPage(int page, int pageSize) {
        List<NoticeVO> list = new ArrayList<>();
        sb.setLength(0);
        sb.append("SELECT notice_id, user_id, title, content, created_at, updated_at ");
        sb.append("FROM NOTICE ");
        sb.append("ORDER BY created_at DESC ");
        sb.append("LIMIT ?, ?");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            int offset = (page - 1) * pageSize;
            pstmt.setInt(1, offset);
            pstmt.setInt(2, pageSize);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                NoticeVO vo = new NoticeVO(
                    rs.getInt("notice_id"),
                    rs.getInt("user_id"),
                    rs.getString("title"),
                    rs.getString("content"),
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
    
    // 전체 공지사항 개수
    public int countAll() {
        int count = 0;
        sb.setLength(0);
        sb.append("SELECT COUNT(*) AS count FROM NOTICE");

        try {
            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return count;
    }


    // 자원 해제 메서드
    public void close() {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}