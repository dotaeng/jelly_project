package com.jelly.www.action;

import com.jelly.www.dao.NoticeDAO;
import com.jelly.www.vo.NoticeVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class NoticeAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 세션에서 관리자 여부 확인
        HttpSession session = request.getSession();
        boolean isAdmin = Boolean.TRUE.equals(session.getAttribute("isAdmin"));
        request.setAttribute("isAdmin", isAdmin);

        // 현재 페이지 번호
        String pageParam = request.getParameter("currentPage");
        int currentPage = 1;
        try {
            if (pageParam != null) {
                currentPage = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException e) {
            currentPage = 1;
        }

        int pageSize = 10;

        NoticeDAO noticeDAO = new NoticeDAO();
        List<NoticeVO> noticeList = null;
        int totalNotices = 0;
        int totalPages = 0;

        try {
            noticeList = noticeDAO.selectByPage(currentPage, pageSize);
            totalNotices = noticeDAO.countAll();
            totalPages = (int) Math.ceil((double) totalNotices / pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "공지사항 정보 가져오는데 오류 발생");
            return "/views/error/500.jsp";
        }

        // 공지사항 리스트가 비어있는 경우 메시지 설정
        if (noticeList == null || noticeList.isEmpty()) {
            request.setAttribute("noticeMessage", "등록된 공지사항이 없음");
        } else {
            request.setAttribute("noticeList", noticeList);
        }

        // 페이지네이션 관련 데이터 설정
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalNotices", totalNotices); 


        // Notice JSP 경로로 이동
        return "/views/notice/notice.jsp";
    }
}