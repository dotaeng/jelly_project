package com.jelly.www.action;

import java.util.List;

import com.jelly.www.dao.ProductDAO;
import com.jelly.www.vo.ProductVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SearchAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // 검색어 가져오기 및 트림 처리
        String query = request.getParameter("query");
        if (query != null) {
            query = query.trim();
        }

        // 검색어 유효성 검증
        if (query == null || query.isEmpty()) {
            // 검색어가 없을 경우 처리
            request.setAttribute("error", "검색어를 입력해주세요.");
            return "/views/error/invalidSearch.jsp"; // 검색어 없으면 검색어가 없습니다 페이지로 이동
        }

        try {
            // DAO 호출 및 검색 결과 가져오기
            ProductDAO productDAO = new ProductDAO();
            List<ProductVO> searchResults = productDAO.searchProducts(query);

            // 검색 결과 Request에 저장
            request.setAttribute("searchResults", searchResults);
            request.setAttribute("query", query); // 검색어도 뷰에 전달
            productDAO.close();
            
            if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            	return "/views/style/searchResult.jsp";
            }
            // 검색 결과 JSP 경로 반환
            return "/views/product/searchResults.jsp";

        } catch (Exception e) {
            // 예외 발생 시 에러 페이지로 이동
            e.printStackTrace();
            request.setAttribute("error", "검색 처리 중 오류가 발생했습니다.");
            return "/views/error/error.jsp"; // 에러 JSP 경로
        }
    }
}