package com.jelly.www.action;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.jelly.www.dao.FollowDAO;
import com.jelly.www.dao.PostDAO;
import com.jelly.www.dao.PostImageDAO;
import com.jelly.www.dao.PostLikeDAO;
import com.jelly.www.dao.PostSaveDAO;
import com.jelly.www.dao.PostTagDAO;
import com.jelly.www.dao.ProductDAO;
import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.PostImageVO;
import com.jelly.www.vo.PostTagVO;
import com.jelly.www.vo.PostVO;
import com.jelly.www.vo.ProductVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class StyleDetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		String pId = request.getParameter("postId");

		if (pId != null) {
			int postId = Integer.parseInt(pId);
			
			// 조회수 증가
			PostDAO postDao = new PostDAO();
			postDao.plusView(postId);

			// 게시물 정보 조회
			PostVO postVo = postDao.selectOne(postId); 
			
			// 게시물 이미지 조회
			PostImageDAO postImageDao = new PostImageDAO();
			ArrayList<PostImageVO> postImageList = postImageDao.getByPostId(postId);
			
			// 유저 정보 조회
			UserDAO userDao = new UserDAO();
			UserVO userVo = userDao.selectOne(postVo.getUserId()); 
			
			// 태그 상품 조회
			PostTagDAO postTagDao = new PostTagDAO();
			ArrayList<PostTagVO> postTagList = postTagDao.getByPostId(postId);
			ArrayList<ProductVO> productList = new ArrayList<ProductVO>();
			ProductDAO productDao = new ProductDAO();
			for(PostTagVO postTagVo : postTagList) {
				ProductVO productVo = productDao.selectOne(postTagVo.getProductId());
				// 가격 포맷팅 처리
		        DecimalFormat df = new DecimalFormat("#,###");
		        productVo.setFormattedPrice(df.format(productVo.getInitialPrice()));
				productList.add(productVo);
			}
			
			// 작성자의 다른 게시물 조회
			ArrayList<PostVO> postList = postDao.getByUserId(postVo.getUserId());
			
			// 좋아요 / 저장 / 팔로우 중인지 조회
			boolean isLike = false;
			boolean isSave = false;
			boolean isFollow = false;
			HttpSession session = request.getSession();
	        UserVO user = (UserVO) session.getAttribute("user");
			
			if(user != null) {
				PostLikeDAO postLikeDao = new PostLikeDAO();
				isLike = postLikeDao.checkLike(postId, user.getUserId());
				PostSaveDAO postSaveDao = new PostSaveDAO();
				isSave = postSaveDao.checkSave(postId, user.getUserId());
				FollowDAO followDao = new FollowDAO();
				isFollow = followDao.checkFollow(user.getUserId(), postVo.getUserId());
			}
			
			postDao.close();
			postImageDao.close();
			userDao.close();
			request.setAttribute("user", user);
			request.setAttribute("postVo", postVo);
			request.setAttribute("postImageList", postImageList);
			request.setAttribute("userVo", userVo);
			request.setAttribute("productList", productList);
			request.setAttribute("postList", postList);
			request.setAttribute("isLike", isLike);
			request.setAttribute("isSave", isSave);
			request.setAttribute("isFollow", isFollow);
		}

		return "/views/style/styleDetail.jsp";
	}

}
