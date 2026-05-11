package com.jelly.www.action;

import com.jelly.www.dao.NoticeDAO;
import com.jelly.www.vo.NoticeVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class NoticeWriteAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id"); // 사용자 ID
        String userEmail = (String) session.getAttribute("userEmail"); // 이메일 


        // 관리자 권한
        if (userId == null || !"haribojellyam@gmail.com".equals(userEmail)) {
            request.setAttribute("errorMessage", "권한이 없습니다.");
            return "/views/error/403.jsp"; 
        }

        // 제목, 내용
        String title = request.getParameter("title");
        String content = request.getParameter("content");


        // 제목과 내용이 비어 있는 경우
        if (title == null || content == null || title.trim().isEmpty() || content.trim().isEmpty()) {
            request.setAttribute("errorMessage", "제목과 내용을 입력해주세요.");
            return "/views/notice/noticeWrite.jsp";
        }

        // 공지사항 객체 생성 및 값 설정
        NoticeDAO dao = new NoticeDAO();
        NoticeVO notice = new NoticeVO();
        notice.setUserId(userId);
        notice.setTitle(title.trim());
        notice.setContent(content.trim());

        try {
            // 데이터베이스에 공지사항 저장
            dao.insertOne(notice);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "공지사항 저장 중 오류가 발생.");
            return "/views/notice/noticeWrite.jsp";
        }

        // 저장 후 공지사항 목록 페이지로 리다이렉트
        String redirectUrl = "redirect:" + request.getContextPath() + "/jelly?page=notice";
        return redirectUrl;
    }
}