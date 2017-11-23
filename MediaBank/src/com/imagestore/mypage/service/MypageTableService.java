package com.imagestore.mypage.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.company.CompanyDAO;
import com.imagestore.company.CompanyDTO;
import com.imagestore.member.MemberDTO;
import com.imagestore.person.PersonDAO;
import com.imagestore.person.PersonDTO;

public class MypageTableService implements Action {

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
			String kind = request.getParameter("kind");
			int user_num = Integer.parseInt(request.getParameter("user_num"));
			if(kind.equals("company")){
				CompanyDAO companyDAO = new CompanyDAO();
				CompanyDTO companyDTO = null;
				try{
					companyDTO = companyDAO.selectOne(user_num);
				}catch(Exception e){
					e.printStackTrace();
				}
				request.setAttribute("company_name", companyDTO.getCompany_name());
				request.setAttribute("company_num", companyDTO.getCompany_num());
				request.setAttribute("company_phone", companyDTO.getCompany_phone());
			}else{
				PersonDAO personDAO = new PersonDAO();
				PersonDTO personDTO = null;
				try{
					personDTO = personDAO.selectOne(user_num);
				}catch(Exception e){
					e.printStackTrace();
				}
				request.setAttribute("nickname", personDTO.getNickName());
				request.setAttribute("name", personDTO.getName());
				request.setAttribute("birth", personDTO.getBirth());
				request.setAttribute("artist", personDTO.getArtist());
			}
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/MYPAGE/add/"+kind+"Add.jsp");
		}
		return actionFoward;
	}

}
