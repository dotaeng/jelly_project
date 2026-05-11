package com.jelly.www.action;

import com.jelly.www.dao.NoticeDAO;
import com.jelly.www.vo.NoticeVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NoticeEditOkAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String noticeIdStr = request.getParameter("noticeId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        if (noticeIdStr == null || noticeIdStr.trim().isEmpty() || title == null || content == null || title.trim().isEmpty() || content.trim().isEmpty()) {
            request.setAttribute("errorMessage", "필드안채워짐");
            return "/views/notice/noticeEdit.jsp";
        }

        try {
            int noticeId = Integer.parseInt(noticeIdStr);

            NoticeDAO dao = new NoticeDAO();
            NoticeVO notice = new NoticeVO();
            notice.setNoticeId(noticeId);
            notice.setTitle(title);
            notice.setContent(content);

            dao.updateOne(notice);  // 공지사항 수정

            return "redirect:" + request.getContextPath() + "/jelly?page=notice";  // 수정 후 공지사항 목록으로 리다이렉트
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "공지사항 수정 중 오류가 발생했습니다.");
            return "/views/error/500.jsp";
        }
    }
}