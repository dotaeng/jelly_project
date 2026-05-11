package com.jelly.www.action;

import java.util.List;

import com.jelly.www.dao.SalesHistoryDAO;
import com.jelly.www.vo.SalesHistoryVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SalesHistoryAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        // 세션에서 seller_id 가져오기
        Integer sellerId = (Integer) session.getAttribute("user_id");
        System.out.println("seller_id: " + sellerId);

        if (sellerId == null) {
            System.out.println("로그인되지 않은 사용자");
            return "/views/login/login.jsp";
        }

        // 현재 페이지 가져오기 (기본값: 1)
        int currentPage = 1;
        if (request.getParameter("currentPage") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] currentPage 파라미터가 유효하지 않음. 기본값 1 사용.");
            }
        }

        // DAO 호출
        SalesHistoryDAO dao = new SalesHistoryDAO();

        // 총 판매내역 수 가져오기
        int totalRecords = dao.getSalesHistoryCount(sellerId);
        int pageSize = 5; // 한 페이지에 보여줄 데이터 수
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // 유효한 페이지 번호 확인
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPages) currentPage = totalPages;

        // 판매내역 조회
        List<SalesHistoryVO> salesList = dao.getSalesHistory(sellerId, currentPage);

        if (salesList == null || salesList.isEmpty()) {
            System.out.println("판매내역 없음");
        } else {
            System.out.println("판매내역 조회 성공, 내역 수: " + salesList.size());
            for (SalesHistoryVO vo : salesList) {
                System.out.println("조회된 데이터: " + vo);
            }
        }

        // 조회 결과와 페이지네이션 정보를 request에 담음
        request.setAttribute("salesList", salesList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        return "/views/mypage/salesHistory.jsp";
    }
}