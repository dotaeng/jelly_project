package com.jelly.www.action;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogoutAction implements Action {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate(); // 세션 무효화
        return "/views/home/home.jsp"; // 로그아웃 후 홈 화면으로 이동
    }
}