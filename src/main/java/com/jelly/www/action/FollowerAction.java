package com.jelly.www.action;

import java.util.ArrayList;

import com.jelly.www.dao.FollowDAO;
import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FollowerAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String uId = request.getParameter("userId");

		if (uId != null) {
			int userId = Integer.parseInt(uId);
			
			// 팔로워 리스트 가져오기
			FollowDAO followDao = new FollowDAO();
			ArrayList<Integer> followerList = followDao.getFollowerIdList(userId);
			ArrayList<UserVO> followerUserList = new ArrayList<UserVO>();
			for(Integer followerId : followerList) {
				UserDAO userDao = new UserDAO();
				followerUserList.add(userDao.selectOne(followerId));
			}
			followDao.close();
			
			request.setAttribute("followerUserList", followerUserList);
			
		}

		return "/views/style/follower.jsp";
	}

}
