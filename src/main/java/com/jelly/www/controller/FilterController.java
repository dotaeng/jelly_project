package com.jelly.www.controller;

import com.google.gson.Gson;
import com.jelly.www.dao.ProductDAO;
import com.jelly.www.vo.ProductVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FilterController {

    // (이거 그 필터아님 카테고리 필터임) 필터링 요청 처리 메서드
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
        try {
//            System.out.println("[FilterController] handleRequest 호출됨");

            // JSON 데이터
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = req.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String requestData = sb.toString();
            
//            System.out.println("[FilterController] 요청 데이터: " + requestData);

            // JSON 파싱
            Gson gson = new Gson();
            FilterRequest filterRequest = gson.fromJson(requestData, FilterRequest.class);
            
//            System.out.println("[FilterController] 파싱된 데이터 - 브랜드: " + filterRequest.getBrands());
//            System.out.println("[FilterController] 파싱된 데이터 - 카테고리: " + filterRequest.getCategories());
//            System.out.println("[FilterController] 파싱된 데이터 - 가격: " + filterRequest.getPrice());

            // 필터 조건 변환
            List<String> brands = filterRequest.getBrands() != null ? filterRequest.getBrands() : new ArrayList<>();
            List<Integer> categories = filterRequest.getCategories() != null ? filterRequest.getCategories() : new ArrayList<>();
            String priceRange = filterRequest.getPrice() != null ? filterRequest.getPrice() : "";

//            System.out.println("[FilterController] 변환된 브랜드 필터: " + brands);
//            System.out.println("[FilterController] 변환된 카테고리 필터: " + categories);

            // DAO 호출
            ProductDAO dao = new ProductDAO();
            List<ProductVO> filteredProducts = dao.filterByBrandsCategoriesAndPrice(brands, categories, priceRange);
            // DAO 호출 후 결과 확인
//            System.out.println("[FilterController] 필터링된 상품 개수: " + filteredProducts.size());
            dao.close();
            // JSON 응답 반환
            String jsonResponse = gson.toJson(filteredProducts);
            resp.setContentType("application/json;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.print(jsonResponse);
            out.flush();
//            System.out.println("[FilterController] JSON 응답: " + jsonResponse);

        } catch (Exception e) {
//            System.err.println("[FilterController] 필터 처리 중 오류 발생");
            e.printStackTrace();
            try {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "필터 처리 중 오류 발생");
            } catch (Exception ignored) {
                System.err.println("[FilterController] 응답 오류 처리 중 추가 예외 발생");
            }
        }
    }

    // 요청에서 필터 조건을 담을 객체
    static class FilterRequest {
        private List<String> brands;
        private List<Integer> categories;
        private String price;

        // getters, setters
        public List<String> getBrands() {
            return brands;
        }

        public void setBrands(List<String> brands) {
            this.brands = brands;
        }

        public List<Integer> getCategories() {
            return categories;
        }

        public void setCategories(List<Integer> categories) {
            this.categories = categories;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}