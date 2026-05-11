package com.jelly.www.action;

import com.jelly.www.dao.UserDAO;
import com.jelly.www.dao.SalesHistoryDAO;
import com.jelly.www.dao.PurchaseHistoryDAO;
import com.jelly.www.vo.UserVO;
import com.jelly.www.vo.SalesHistoryVO;
import com.jelly.www.vo.PurchaseHistoryVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

public class MypageAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 로그인된 사용자 정보
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");

        // user_id가 세션에 없다면 로그인 페이지로 리다이렉트
        if (userId == null) {
            return "redirect:/login.jsp"; 
        }

        // 사용자 정보 조회
        UserDAO uDao = new UserDAO();
        UserVO uVo = uDao.selectOne(userId);

        if (uVo == null) {
            System.out.println("uVo -> null");
            return "redirect:/404.jsp";
        } else {
            // System.out.println(uVo.getProfileImage());
        }

        // JSP로 전달
        request.setAttribute("uVo", uVo);

        // 최근 3개 판매 내역 조회
        SalesHistoryDAO salesDao = new SalesHistoryDAO();
        List<SalesHistoryVO> recentSales = salesDao.getRecentSalesHistory(userId);

        // 최근 3개 판매 내역을 JSP로 전달
        request.setAttribute("recentSales", recentSales);

        // 최근 3개 구매 내역 조회
        PurchaseHistoryDAO purchaseDao = new PurchaseHistoryDAO();
        List<PurchaseHistoryVO> recentPurchases = purchaseDao.getRecentPurchaseHistory(userId);

        // 최근 3개 구매 내역을 JSP로 전달
        request.setAttribute("recentPurchases", recentPurchases);

        return "/views/mypage/mypage.jsp";
    }
}