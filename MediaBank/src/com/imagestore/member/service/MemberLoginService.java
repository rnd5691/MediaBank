package com.imagestore.member.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.company.CompanyDAO;
import com.imagestore.member.MemberDAO;
import com.imagestore.member.MemberDTO;
import com.imagestore.person.PersonDAO;

public class MemberLoginService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		MemberDAO memberDAO = new MemberDAO();
		MemberDTO memberDTO = new MemberDTO();
		
		memberDTO.setId(request.getParameter("id"));
		memberDTO.setPw(request.getParameter("pw"));
		memberDTO.setKind(request.getParameter("kind"));
		
		String message = "로그인 실패 하셨습니다.";
		HttpSession session = request.getSession();
		try {
			memberDTO = memberDAO.selectOne(memberDTO);
			
			if(memberDTO != null) {
				session.setAttribute("member", memberDTO);
				String writer = null;
				if(memberDTO.getKind().equals("company")){
					CompanyDAO companyDAO = new CompanyDAO();
					writer = companyDAO.selectWriter(memberDTO.getUser_num());
				}else{
					PersonDAO personDAO = new PersonDAO();
					writer = personDAO.selectWriter(memberDTO.getUser_num());
				}
				session.setAttribute("writer", writer);
				message = "로그인 성공";
				if(memberDTO.getKind().equals("admin")){
					request.setAttribute("path", "../qna/qnaList.qna");
				}else{
					request.setAttribute("path", "../index.jsp");			
				}
			}else{
				request.setAttribute("path", "../index.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("message", message);
		
		
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/common/result.jsp");
		
	return actionFoward;
	}

}
