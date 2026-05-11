package com.jelly.www.action;

import com.jelly.www.dao.SalesHistoryDetailDAO;
import com.jelly.www.vo.SalesHistoryDetailVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SalesHistoryDetailAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Action: SalesHistoryDetailAction 시작");

        // 로그인 여부 확인
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");
        if (userId == null) {
            System.out.println("로그인되지 않은 사용자 접근");
            return "/views/login/login.jsp";
        }

        // trade_id 파라미터 처리
        String tradeIdParam = request.getParameter("trade_id");
        int tradeId = 0;
        try {
            if (tradeIdParam == null || tradeIdParam.isEmpty()) {
                throw new IllegalArgumentException("trade_id가 제공되지 않음");
            }
            tradeId = Integer.parseInt(tradeIdParam);
        } catch (NumberFormatException e) {
            System.err.println("trade_id가 유효한 숫자가 아님: " + tradeIdParam);
            return "/views/error/400.jsp";
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return "/views/error/400.jsp";
        }

        // DAO 호출 및 데이터 조회
        SalesHistoryDetailDAO dao = new SalesHistoryDetailDAO();
        SalesHistoryDetailVO detailVO = dao.getDetail(tradeId, userId);

        // 조회 결과 확인
        if (detailVO == null) {
            System.out.println("trade_id에 해당하는 데이터 없음: " + tradeId);
            return "/views/error/404.jsp";
        }

        // 조회된 데이터 출력
        System.out.println("조회된 데이터: " + detailVO);

        // request에 데이터 저장
        request.setAttribute("detailVO", detailVO);

        System.out.println("Action: SalesHistoryDetailAction 종료");
        // 판매내역 상세 페이지로 이동
        return "/views/mypage/salesHistoryDetail.jsp";
    }
}