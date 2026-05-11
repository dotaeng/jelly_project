package com.jelly.www.action;

import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginInfoPwAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String userpassword = req.getParameter("userpassword");
		HttpSession session = req.getSession(true);
        Object obj = session.getAttribute("user");
        UserVO user = (UserVO) obj;
        int sessionuserid = user.getUserId();
        
        UserDAO dao = new UserDAO();
        if(userpassword != null) {
        	UserVO user1 = new UserVO();
        	user1.setPassword(userpassword);
        	
        	// 이메일 변경 메서드
        	dao.updateInfoUserPw(userpassword, sessionuserid);
        	session.setAttribute("userpassword", user1.getPassword());
        	
        	req.setAttribute("successMessage", "비밀번호가 수정되었습니다");
            System.out.println("비밀번호 수정 완료");
            return "views/mypage/loginInfo.jsp";
        }
        req.setAttribute("errorMessage", "비밀번호를 정확히 입력하세요");
		return "/views/mypage/loginInfo.jsp";
	}

}
