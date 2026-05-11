package com.jelly.www.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebFilter("/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        boolean loggedIn = req.getSession().getAttribute("user") != null;

        // 로그인이 필요한 페이지들 (마이페이지, 관심, 로그인)

        if ((uri.contains("/mypage") || uri.contains("/wish")) && !loggedIn) {
            resp.sendRedirect(req.getContextPath() + "/home?page=login");
            return;
        }
        chain.doFilter(req, resp);
    }
}