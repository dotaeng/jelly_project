package com.jelly.www.controller;

import java.io.IOException;

import org.json.simple.JSONObject;

import com.jelly.www.dao.PostDAO;
import com.jelly.www.dao.PostSaveDAO;
import com.jelly.www.vo.PostSaveVO;
import com.jelly.www.vo.PostVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/savePost")
public class SavePostController extends HttpServlet {
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
			int userId = user.getUserId();
	    	
	        boolean isSave = false;
	    	
	    	// 요청 데이터 읽기
	    	int postId = Integer.parseInt(request.getParameter("postId"));
	
	        // DAO 호출
	    	PostDAO postDao = new PostDAO();
	    	PostSaveDAO postSaveDao = new PostSaveDAO();
	    	
	    	if(postSaveDao.checkSave(postId, userId)) {
	    		// 좋아요 중이라면
	    		postSaveDao.deleteOne(postId, userId);
	    		postDao.minusSave(postId);
	    	}
	    	else {
	    		// 좋아요 중이 아니라면
	    		PostSaveVO postSaveVo = new PostSaveVO(postId, userId);
	    		postSaveDao.insertOne(postSaveVo);
	    		postDao.plusSave(postId);
	    		isSave = true;
	    	}
	    	
	    	PostVO postVo = postDao.selectOne(postId);
	
	        // JSON 응답 생성
	    	jsonResponse.put("saveCount", postVo.getSaveCount());
	    	jsonResponse.put("isSave", isSave);
	    	
	    	postDao.close();
	    	postSaveDao.close();
	
	        // 응답
	        response.setContentType("application/json;charset=UTF-8");
	        response.getWriter().write(jsonResponse.toJSONString());
        }
    }
}