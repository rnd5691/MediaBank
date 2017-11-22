package com.imagestore.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.member.MemberDAO;

public class MemberIdCheckService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) {
		ActionFoward actionFoward = new ActionFoward();
		String id = request.getParameter("id");
		MemberDAO memberDAO = new MemberDAO();
		
		try{
			boolean check = memberDAO.checkId(id);
			if(check){
				request.setAttribute("message", "사용 가능한 ID 입니다.") ;
			}else{
				request.setAttribute("message", "이미 사용 중인 ID 입니다.") ;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/join/check/Check.jsp");
		return actionFoward;
	}

}
