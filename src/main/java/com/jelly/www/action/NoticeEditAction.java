package com.jelly.www.action;

import com.jelly.www.dao.NoticeDAO;
import com.jelly.www.vo.NoticeVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NoticeEditAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String noticeIdStr = request.getParameter("noticeId");

        if (noticeIdStr == null || noticeIdStr.trim().isEmpty()) {
            request.setAttribute("errorMessage", "공지사항 ID가 필요합니다.");
            return "/views/error/404.jsp";
        }

        try {
            int noticeId = Integer.parseInt(noticeIdStr);
            NoticeDAO dao = new NoticeDAO();
            NoticeVO notice = dao.selectOne(noticeId);  // 공지사항 조회

            if (notice == null) {
                request.setAttribute("errorMessage", "존재하지 않는 공지사항입니다.");
                return "/views/error/404.jsp";  // 공지사항이 없으면 404 페이지로 이동
            }

            request.setAttribute("notice", notice);  // 조회된 공지사항을 request에 저장
            return "/views/notice/noticeEdit.jsp";

        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "유효하지 않은 공지사항 ID입니다.");
            return "/views/error/404.jsp";
        }
    }
}