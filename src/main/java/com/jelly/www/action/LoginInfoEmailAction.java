package com.jelly.www.action;

import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginInfoEmailAction implements Action{

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {
		String useremail = req.getParameter("useremail");
		HttpSession session = req.getSession(true);
        Object obj = session.getAttribute("user");
        UserVO user = (UserVO) obj;
        int sessionuserid = user.getUserId();
        
        UserDAO dao = new UserDAO();
        if(useremail != null) {
        	UserVO user1 = new UserVO();
        	user1.setEmail(useremail);
        	
        	// 이메일 변경 메서드
        	dao.updateInfoUserEmail(useremail, sessionuserid);
        	session.setAttribute("useremail", user1.getEmail());
        	
        	req.setAttribute("successMessage", "이메일이 수정되었습니다");
            System.out.println("이메일 수정 완료");
            return "views/mypage/loginInfo.jsp";
        }
        req.setAttribute("errorMessage", "이메일을 정확히 입력하세요");
		return "/views/mypage/loginInfo.jsp";
	}

}
