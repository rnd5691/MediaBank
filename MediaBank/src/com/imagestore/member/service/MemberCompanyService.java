package com.imagestore.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;

public class MemberCompanyService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) {
		ActionFoward actionFoward = new ActionFoward();
		request.setAttribute("kind", "company");
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/join/joinForm_info.jsp");
		return actionFoward;
	}

}
