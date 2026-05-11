package com.jelly.www.action;

import java.util.List;
import com.jelly.www.dao.WishPageDAO;
import com.jelly.www.vo.WishPageVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class WishPageAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 세션에서 로그인 사용자 ID 가져오기
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        // 로그인 여부 확인
        if (userId == null) {
            return "redirect:/jelly?page=login";
        }

        // DAO 객체 생성
        WishPageDAO dao = new WishPageDAO();

        // 삭제 요청 처리
        String deleteProductId = request.getParameter("deleteProductId");
        if (deleteProductId != null) {
            try {
                // 숫자인지 확인
                if (deleteProductId.matches("\\d+")) {
                    int productId = Integer.parseInt(deleteProductId);

                    // 관심상품 삭제
                    dao.removeWishlistItem(userId, productId);

                } else {

                }
            } catch (NumberFormatException e) {
                System.err.println("Error parsing productId: " + deleteProductId);
                e.printStackTrace();
            }
        }

        // 관심상품 목록 조회 및 페이지네이션 처리
        String pageParam = request.getParameter("currentPage");
        int currentPage = 1; // 기본 페이지
        if (pageParam != null && pageParam.matches("\\d+")) {
            currentPage = Integer.parseInt(pageParam);
        }

        int itemsPerPage = 5;
        int offset = (currentPage - 1) * itemsPerPage;

        List<WishPageVO> wishlist = dao.getWishlist(userId, offset, itemsPerPage);

        // 총 상품 수와 페이지 수 계산
        int totalItems = dao.getWishlistCount(userId);
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        // JSP로 데이터 전달
        request.setAttribute("wishlist", wishlist);
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);

        // System.out.println("Wishlist : " + wishlist);
        // System.out.println("Total Items : " + totalItems + ", Total Pages: " + totalPages);

        // JSP 페이지로 포워딩
        return "/views/wish/wish.jsp";
    }
}