package com.jelly.www.action;

import com.jelly.www.dao.UserAccountDAO;
import com.jelly.www.vo.UserAccountVO;
import com.jelly.www.vo.UserVO;
import com.mysql.cj.Session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// 기존
//public class UserAccountAction implements Action {
//
//    @Override
//    public String execute(HttpServletRequest req, HttpServletResponse resp) {
//        String bankname = req.getParameter("bankname");
//        String accountnum = req.getParameter("accountnum");
//        String accountname = req.getParameter("accountname");
//
//        HttpSession session = req.getSession(true);
//        Object obj = session.getAttribute("user");
//        UserVO user = (UserVO) obj;
//        System.out.println(user);
//        int sessionuserid = user.getUserId();
//
//        UserAccountDAO dao = new UserAccountDAO();
//        
//
//        if (bankname != null) {
//            UserAccountVO user1 = new UserAccountVO();
//            user1.setBankName(bankname);
//            user1.setAccountNumber(accountnum);
//            user1.setAccountHolder(accountname);
//            user1.setUserId(sessionuserid);
//
//         
//            dao.updateAccount(user1);
//            
//            session.setAttribute("bankname", user1.getBankName());
//            session.setAttribute("accountnum", user1.getAccountNumber());
//            session.setAttribute("accountname", user1.getAccountHolder());
//    
//
//            req.setAttribute("successMessage", "계좌 정보가 수정되었습니다.");
//            System.out.println("계좌 정보 수정 완료");
//            return "views/mypage/userAccount.jsp";
//        } else {
//            String numError = null;
//
//            if (accountnum == null || accountnum.length() <= 10) {
//                numError = "계좌번호 형식이 잘못되었습니다.";
//            }
//
//            if (numError == null && sessionuserid != 0) {
//                UserAccountVO user2 = new UserAccountVO();
//                user2.setBankName(bankname);
//                user2.setAccountNumber(accountnum);
//                user2.setAccountHolder(accountname);
//                user2.setUserId(sessionuserid);
//
//                dao.insertAccount(user2);
//                
//                session.setAttribute("bankname", user2.getBankName());
//                session.setAttribute("accountnum", user2.getAccountNumber());
//                session.setAttribute("accountname", user2.getAccountHolder());
//
//                req.setAttribute("successMessage", "판매 정산 계좌가 저장되었습니다.");
//                System.out.println("계좌 정보 추가 완료");
//            } else {
//                req.setAttribute("errorMessage", "계좌번호를 정확히 입력하세요");
//            }
//
//            return "views/mypage/userAccount.jsp";
//        }
//    }
//}

public class UserAccountAction implements Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
    	
        String bankname = req.getParameter("bankname");
        String accountnum = req.getParameter("accountnum");
        String accountname = req.getParameter("accountname");
        
        HttpSession session = req.getSession(true);
        
        UserVO user = (UserVO) session.getAttribute("user");
        int sessionuserid = user.getUserId();
        UserAccountDAO dao = new UserAccountDAO();
        
        // 은행명 유효성 검사
        if (bankname == null || "default".equals(bankname)) {
            req.setAttribute("bankNameError", "은행을 선택해주세요.");
            return "views/mypage/userAccount.jsp";
        }

        // 계좌번호 유효성 검사 
        if (accountnum == null || accountnum.length() <= 10) {
            req.setAttribute("errorMessage", "계좌번호를 정확히 입력하세요");
            return "views/mypage/userAccount.jsp";
        }
        
        // 세션 이름과 동일한지 검사
        if (!accountname.equals(user.getUserName())) {
            req.setAttribute("userNameError", "회원 이름이 일치하지 않습니다.");
            return "views/mypage/userAccount.jsp";
        }
        
        UserAccountVO account = new UserAccountVO();
        account.setBankName(bankname);
        account.setAccountNumber(accountnum);
        account.setAccountHolder(accountname);
        account.setUserId(sessionuserid);

        // DB에 있으면 update, 없으면 insert
        UserAccountVO existing = dao.selectAccount(sessionuserid);
        if (existing != null) {
            dao.updateAccount(account);
            req.setAttribute("successMessage", "계좌 정보가 수정되었습니다.");
        } else {
            dao.insertAccount(account);
            req.setAttribute("successMessage", "판매 정산 계좌가 저장되었습니다.");
        }

        session.setAttribute("bankname", account.getBankName());
        session.setAttribute("accountnum", account.getAccountNumber());
        session.setAttribute("accountname", account.getAccountHolder());

        return "views/mypage/userAccount.jsp";
    }
}