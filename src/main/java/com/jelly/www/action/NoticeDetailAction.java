package com.jelly.www.action;

import com.jelly.www.dao.NoticeDAO;
import com.jelly.www.vo.NoticeVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NoticeDetailAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 공지사항 ID 파라미터
        String noticeIdStr = request.getParameter("noticeId");

        if (noticeIdStr == null || noticeIdStr.trim().isEmpty()) {
            // 공지사항 ID가 없는 경우 에러 메시지 설정 및 에러 페이지로 이동
            request.setAttribute("errorMessage", "공지사항 ID가 필요합니다.");
            return "/views/error/400.jsp"; // 400 에러 페이지
        }

        try {
            // 공지사항 ID를 정수로 변환
            int noticeId = Integer.parseInt(noticeIdStr);

            // DAO를 이용하여 공지사항 상세 정보 조회
            NoticeDAO dao = new NoticeDAO();
            NoticeVO notice = dao.selectOne(noticeId);

            if (notice == null) {
                // 공지사항이 존재하지 않을 경우 에러 메시지 설정 및 에러 페이지로 이동
                request.setAttribute("errorMessage", "존재하지 않는 공지사항");
                return "/views/error/404.jsp";
            }

            // 조회된 공지사항 정보를 request에 저장
            request.setAttribute("notice", notice);

            return "/views/notice/noticeDetail.jsp";

        } catch (NumberFormatException e) {
            // 공지사항 ID가 유효하지 않을 경우 에러 메시지 설정 및 에러 페이지로 이동
            e.printStackTrace();
            request.setAttribute("errorMessage", "유효하지 않은 공지사항 ID");
            return "/views/error/404.jsp"; 
        } catch (Exception e) {
            // 예외 발생 시 에러 처리
            e.printStackTrace();
            request.setAttribute("errorMessage", "공지사항 조회 오류 발생");
            return "/views/error/500.jsp";
        }
    }
}