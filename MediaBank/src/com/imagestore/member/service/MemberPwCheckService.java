package com.imagestore.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;

public class MemberPwCheckService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) {
		ActionFoward actionFoward = new ActionFoward();
		boolean check = Boolean.parseBoolean(request.getParameter("check"));
		
		if(check){
			request.setAttribute("message", "사용 가능한 PW 입니다.");
		}else{
			request.setAttribute("message", "비밀번호는 8자리 이상이어야 하며 공백 없이 영문과 숫자, 특수문자를 반드시 포함해야 합니다.");
		}
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/join/check/Check.jsp");
		return actionFoward;
	}

}
