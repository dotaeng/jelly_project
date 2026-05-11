package com.jelly.www.controller;

import java.io.IOException;

import org.json.simple.JSONObject;

import com.jelly.www.dao.FollowDAO;
import com.jelly.www.dao.PostDAO;
import com.jelly.www.dao.PostSaveDAO;
import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.FollowVO;
import com.jelly.www.vo.PostSaveVO;
import com.jelly.www.vo.PostVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/follow")
public class FollowController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	JSONObject jsonResponse = new JSONObject();
    	
    	HttpSession session = request.getSession();
        UserVO user = (UserVO) session.getAttribute("user");
		
        if (user == null) { // 로그인이 되어 있지 않을 경우
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // HTTP 401 상태
            jsonResponse.put("error", "로그인이 필요합니다.");
        }
        else {
			int followerId = user.getUserId();
	    	
	        boolean isFollow = false;
	    	
	    	// 요청 데이터 읽기
	    	int followingId = Integer.parseInt(request.getParameter("followingId"));
	
	        // DAO 호출
	    	FollowDAO followDao = new FollowDAO();
	    	UserDAO userDao = new UserDAO();
	    	
	    	if(followDao.checkFollow(followerId, followingId)) {
	    		// 팔로우 중이라면
	    		followDao.deleteOne(followerId, followingId);
	    		userDao.minusFollow(followerId, followingId);
	    	}
	    	else {
	    		// 팔로우 중이 아니라면
	    		followDao.insertOne(new FollowVO(followerId, followingId));
	    		userDao.plusFollow(followerId, followingId);
	    		isFollow = true;
	    	}
	    	
	    	UserDAO userDao1 = new UserDAO();
	    	UserVO userVo = userDao1.selectOne(followingId);
	    			
	    	int followerCount = userVo.getFollowerCount();
	    	int followingCount = userVo.getFollowingCount();
	
	        // JSON 응답 생성
	    	jsonResponse.put("isFollow", isFollow);
	    	jsonResponse.put("followerCount", followerCount);
	    	jsonResponse.put("followingCount", followingCount);
	
	    	followDao.close();
	    	userDao.close();
	    	userDao1.close();
	        // 응답
	        response.setContentType("application/json;charset=UTF-8");
	        response.getWriter().write(jsonResponse.toJSONString());
        }
    }
}