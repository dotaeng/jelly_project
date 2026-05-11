package com.jelly.www.action;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JoinFormAction implements Action {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) {

			return "views/join/joinForm.jsp";
		
	}
}
