package com.jelly.www.action;

import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class JoinOkAction implements Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        String inputCode = req.getParameter("num");
        String sessionCode = (String) session.getAttribute("tempNum");


        if (inputCode != null && inputCode.equals(sessionCode)) {
            // 세션에 저장된 사용자 데이터 가져오기
            String email = (String) session.getAttribute("email");
            String name = (String) session.getAttribute("name");
            String phone = (String) session.getAttribute("phone");
            String password = (String) session.getAttribute("password");

            if (email == null || name == null || phone == null || password == null) {
                // 세션에서 필요한 데이터가 없을 경우 에러 처리
                req.setAttribute("errorMessage", "정보를 세션에서 찾을 수 없습니다. 다시 시도해주세요.");
                return "/views/join/joinNum.jsp";
            }

            System.out.println("email : " + email);
            System.out.println("name : " + name);
            System.out.println("phone : " + phone);
            System.out.println("password : " + password);

            // 데이터베이스에 저장
            UserVO user = new UserVO();
            user.setEmail(email);
            user.setUserName(name);
            user.setPhoneNumber(phone);
            user.setPassword(password);

            UserDAO dao = new UserDAO();
            dao.insertOne(user);


            System.out.println("회원가입 성공");
            return "/views/login/login.jsp";     
            
        } else {
            req.setAttribute("errorMessage", "인증 코드가 일치하지 않습니다.");
            return "/views/join/joinNum.jsp";
        }
    }
}