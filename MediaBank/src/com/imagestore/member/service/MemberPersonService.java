package com.imagestore.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;

public class MemberPersonService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) {
		ActionFoward actionFoward = new ActionFoward();
		HttpSession session = request.getSession();
		
		if(session.getAttribute("naver")==null){
			actionFoward.setPath("../WEB-INF/view/join/joinForm_info.jsp");			
		}else{
			actionFoward.setPath("../WEB-INF/view/join/joinForm_info_naver.jsp");
		}
		request.setAttribute("kind", "person");
		actionFoward.setCheck(true);
		return actionFoward;
	}

}
