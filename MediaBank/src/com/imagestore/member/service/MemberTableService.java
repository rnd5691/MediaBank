package com.imagestore.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;

public class MemberTableService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) {
		ActionFoward actionFoward = new ActionFoward();
		String kind = request.getParameter("kind");
		
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/join/add/"+kind+"Add.jsp");
		return actionFoward;
	}

}
