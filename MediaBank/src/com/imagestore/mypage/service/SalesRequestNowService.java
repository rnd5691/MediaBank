package com.imagestore.mypage.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.member.MemberDTO;

public class SalesRequestNowService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		if(memberDTO == null) {
			request.setAttribute("message", "잘못된 접근 방식 입니다.");
			request.setAttribute("path", "../index.jsp");
			actionFoward.setPath("../WEB-INF/view/common/result.jsp");
			actionFoward.setCheck(true);
		}else {
			String file_kind = "";
			try {
				file_kind = request.getParameter("file_kind") == null?"image":request.getParameter("file_kind");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			request.setAttribute("file_kind", file_kind);
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/MYPAGE/salesRequestNow.jsp");
		}
		return actionFoward;
	}

}
