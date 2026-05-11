package com.jelly.www.action;

import com.jelly.www.dao.NoticeDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class NoticeDeleteAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 세션 사용자 정보 확인
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id"); // 세션에서 사용자 ID 가져옴
        String userEmail = (String) session.getAttribute("userEmail"); // 세션에서 이메일 가져옴

        // 관리자 권한 체크
        if (userId == null || !"haribojellyam@gmail.com".equals(userEmail)) {
            request.setAttribute("errorMessage", "권한이 없습니다.");
            return "/views/error/404.jsp"; 
        }

        // 요청 파라미터에서 noticeId 가져오기
        String noticeIdStr = request.getParameter("noticeId");

        if (noticeIdStr == null || noticeIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "공지사항 ID");
            return "/views/error/404.jsp";
        }

        try {
            // 공지사항 ID를 정수로 변환
            int noticeId = Integer.parseInt(noticeIdStr); 
            
            // DAO를 이용해 공지사항 삭제
            NoticeDAO dao = new NoticeDAO();
            dao.deleteOne(noticeId); // 삭제

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "유효하지 않은 공지사항 ID입니다.");
            return "/views/error/400.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "공지사항 삭제 중 오류가 발생했습니다.");
            return "/views/error/500.jsp";
        }

        // 삭제 후 공지사항 목록 페이지로 리다이렉트
        String redirectUrl = "redirect:" + request.getContextPath() + "/jelly?page=notice";
        System.out.println("[디버깅] 리다이렉트 URL: " + redirectUrl);
        return redirectUrl;
    }
}