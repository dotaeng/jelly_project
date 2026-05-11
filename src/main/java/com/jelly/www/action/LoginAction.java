package com.jelly.www.action;

import java.util.List;

import com.jelly.www.dao.UserDAO;
import com.jelly.www.dao.WishlistDAO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();

        // 세션에서 리다이렉트 URL 가져옴
        String redirectUrl = (String) session.getAttribute("redirectUrl");
        String returnPage = (String) session.getAttribute("returnPage");
        
        
        System.out.println("email : " + email);
        System.out.println("password : " + password);
        System.out.println("redirectUrl : " + redirectUrl);
        System.out.println("Received parameter: " + returnPage);

        // DAO 객체 생성
        UserDAO userDAO = new UserDAO();
        UserVO user = userDAO.findOneByEmailAndPw(email, password);
        userDAO.close();

        // 에러 메시지 초기화
        String emailError = null;
        String passwordError = null;

        // 이메일과 비밀번호 유효성 검사
        if (email == null || !email.contains("@")) {
            emailError = "정확한 이메일 주소를 입력하세요.";
        }
        if (password == null || password.length() < 8 || password.length() > 16) {
            passwordError = "비밀번호는 8자 이상, 16자 이하로 입력하세요.";
        }

        // 유효성 검사 실패 시 에러 메시지와 함께 로그인 페이지로 이동
        if (emailError != null || passwordError != null) {
            req.setAttribute("emailError", emailError);
            req.setAttribute("passwordError", passwordError);
            return "/views/login/login.jsp";
        }

        // 로그인 처리
        if (user != null) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            session.setAttribute("user", user);
            session.setAttribute("user_id", user.getUserId());
            session.setAttribute("userEmail", email);
            
            session.setMaxInactiveInterval(1800); // 30분 동안 미사용 시 자동 로그아웃

            // 관리자 계정 여부 확인
            boolean isAdmin = "haribojellyam@gmail.com".equals(email) && "12341234".equals(password);
            session.setAttribute("isAdmin", isAdmin);

            // 관심목록 다시 세션에 저장
            WishlistDAO wishlistDAO = new WishlistDAO();
            List<Integer> wishlist = wishlistDAO.getWishlistByUserId(user.getUserId());
            session.setAttribute("wishlist", wishlist);

            // 리다이렉트 URL 설정
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
//                return "redirect:" + redirectUrl;
            	 return "redirect:" + req.getContextPath() + redirectUrl;
            } else {
//                return "redirect:/haribo/jelly";
            	return "redirect:" + req.getContextPath() + "/jelly";
            }
        } else {
            // 로그인 실패 시 메시지를 요청 속성에 저장
            req.setAttribute("errorMessage", "이메일 또는 비밀번호가 잘못되었습니다.");
            return "/views/login/login.jsp";
        }
    }
}