package com.jelly.www.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;

import com.jelly.www.dao.FollowDAO;
import com.jelly.www.dao.PostDAO;
import com.jelly.www.dao.PostLikeDAO;
import com.jelly.www.dao.PostSaveDAO;
import com.jelly.www.dao.PostTagDAO;
import com.jelly.www.dao.ProductDAO;
import com.jelly.www.dao.UserDAO;
import com.jelly.www.vo.PostSaveVO;
import com.jelly.www.vo.PostTagVO;
import com.jelly.www.vo.PostVO;
import com.jelly.www.vo.ProductVO;
import com.jelly.www.vo.StyleProfileListVO;
import com.jelly.www.vo.UserVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class StyleProfileAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String uId = request.getParameter("userId");

		if (uId != null) {
			int userId = Integer.parseInt(uId);
			
			// 유저 가져오기
			UserDAO userDao = new UserDAO();
			UserVO userVo = userDao.selectOne(userId);
		
			// 팔로우 중인지 조회
			boolean isFollow = false;
			HttpSession session = request.getSession();
	        UserVO user = (UserVO) session.getAttribute("user");
			
			if(user != null) {
				FollowDAO followDao = new FollowDAO();
				isFollow = followDao.checkFollow(user.getUserId(), userVo.getUserId());
			}
		
			// 유저의 스타일 리스트 가져오기
			PostDAO postDao = new PostDAO();
			PostLikeDAO postLikeDAO =  new PostLikeDAO();
			ArrayList<PostVO> postList = postDao.getByUserId(userId);
			ArrayList<StyleProfileListVO> newPostList = new ArrayList<StyleProfileListVO>();
			
			for(PostVO vo: postList) {
				boolean isLike = false;
				if(user != null) {
					isLike = postLikeDAO.checkLike(vo.getPostId(), user.getUserId());
				}
				StyleProfileListVO obj = new StyleProfileListVO(
						vo.getPostId(),
						vo.getUserId(),
						vo.getStyleCategory(),
						vo.getTitle(),
						vo.getContent(),
						vo.getThumbnailImageUrl(),
						vo.getLikeCount(),
						vo.getCommentCount(),
						vo.getViewCount(),
						vo.getSaveCount(),
						vo.getCreatedAt(),
						vo.getUpdatedAt(),
						isLike
						);
				newPostList.add(obj);
			}
			
			// 태그 상품 리스트 가져오기
			HashSet<ProductVO> productSet = new HashSet<ProductVO>();
			PostTagDAO postTagDao = new PostTagDAO();
			for(PostVO post : postList) {
				ArrayList<PostTagVO> postTagList = postTagDao.getByPostId(post.getPostId());
				for(PostTagVO postTag : postTagList) {
					ProductDAO productDao = new ProductDAO();
					ProductVO productVo = productDao.selectOne(postTag.getProductId());
					// 가격 포맷팅 처리
			        DecimalFormat df = new DecimalFormat("#,###");
			        productVo.setFormattedPrice(df.format(productVo.getInitialPrice()));
					productSet.add(productVo);
				}
			}
			
			// 저장된 스타일 가져오기
			PostSaveDAO postSaveDao = new PostSaveDAO();
			ArrayList<PostSaveVO> postSaveList = postSaveDao.getByUserId(userId);
			ArrayList<StyleProfileListVO> savedList = new ArrayList<StyleProfileListVO>();
			for(PostSaveVO post1 : postSaveList) {
				PostDAO postDao1 = new PostDAO();
				PostVO vo = postDao1.selectOne(post1.getPostId());
				
				boolean isLike = false;
				if(user != null) {
					isLike = postLikeDAO.checkLike(vo.getPostId(), user.getUserId());
				}
				StyleProfileListVO obj = new StyleProfileListVO(
						vo.getPostId(),
						vo.getUserId(),
						vo.getStyleCategory(),
						vo.getTitle(),
						vo.getContent(),
						vo.getThumbnailImageUrl(),
						vo.getLikeCount(),
						vo.getCommentCount(),
						vo.getViewCount(),
						vo.getSaveCount(),
						vo.getCreatedAt(),
						vo.getUpdatedAt(),
						isLike
						);
				savedList.add(obj);
				
			}
			
			System.out.println("Saved List Size: " + savedList.size());
			for (StyleProfileListVO item : savedList) {
			    System.out.println(item);
			}
			
			userDao.close();
			postDao.close();
			postLikeDAO.close();
			postTagDao.close();
			postSaveDao.close();
			// 요청 파라미터 설정
			request.setAttribute("userVo", userVo);
			request.setAttribute("isFollow", isFollow);
			request.setAttribute("postList", newPostList);
			request.setAttribute("productSet", productSet);
			request.setAttribute("savedList", savedList);
			
		}

		return "/views/style/styleProfile.jsp";
	}

}
