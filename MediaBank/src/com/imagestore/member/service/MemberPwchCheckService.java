package com.imagestore.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;

public class MemberPwchCheckService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) {
		ActionFoward actionFoward = new ActionFoward();
		boolean check = Boolean.parseBoolean(request.getParameter("check"));
		
		if(check){
			request.setAttribute("message", "비밀 번호가 일치 합니다.");
		}else{
			request.setAttribute("message", "비밀 번호가 일치 하지 않습니다.");
		}
		
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/join/check/Check.jsp");
		return actionFoward;
	}

}
