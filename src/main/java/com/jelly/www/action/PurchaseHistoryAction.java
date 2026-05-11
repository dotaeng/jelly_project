package com.jelly.www.action;

import java.util.List;

import com.jelly.www.dao.PurchaseHistoryDAO;
import com.jelly.www.vo.PurchaseHistoryVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PurchaseHistoryAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        // 세션에서 user_id 가져오기
        Integer buyerId = (Integer) session.getAttribute("user_id");

        if (buyerId == null) {
            System.out.println("[ERROR] 로그인되지 않은 사용자입니다.");
            return "/views/login/login.jsp"; // 로그인 페이지로 이동
        }

        // 현재 페이지 가져오기 (기본값 1)
        int currentPage = 1;
        if (request.getParameter("currentPage") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] currentPage 파라미터가 유효하지 않음. 기본값 1 사용.");
            }
        }

        int pageSize = 5; // 한 페이지에 보여줄 데이터 수
        PurchaseHistoryDAO dao = new PurchaseHistoryDAO();

        // 구매내역 총 개수 가져오기
        int totalRecords = dao.getPurchaseHistoryCount(buyerId);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // 유효한 페이지 번호인지 확인
        if (currentPage < 1) currentPage = 1;
        if (currentPage > totalPages) currentPage = totalPages;

        // 구매내역 조회
        List<PurchaseHistoryVO> purchaseList = dao.getPurchaseHistory(buyerId, currentPage);

        if (purchaseList == null || purchaseList.isEmpty()) {
            System.out.println("구매내역이 없습니다.");
        } else {
            System.out.println("구매내역 조회 성공. 내역 수: " + purchaseList.size());
        }

        // 조회 결과와 페이지네이션 정보를 request에 담기
        request.setAttribute("purchaseList", purchaseList);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);

        // 이동할 JSP 경로 반환
        return "/views/mypage/purchaseHistory.jsp";
    }
}