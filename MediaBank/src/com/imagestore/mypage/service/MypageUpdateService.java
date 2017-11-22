package com.imagestore.mypage.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.company.CompanyDAO;
import com.imagestore.company.CompanyDTO;
import com.imagestore.member.MemberDAO;
import com.imagestore.member.MemberDTO;
import com.imagestore.person.PersonDAO;
import com.imagestore.person.PersonDTO;
import com.imagestore.util.DBConnector;

public class MypageUpdateService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		HttpSession session = request.getSession();
		MemberDAO memberDAO = new MemberDAO();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		Connection con = null;
		int result = 0;
		if(memberDTO.getToken() == null){
			memberDTO.setPw(request.getParameter("pw"));			
		}else{
			memberDTO.setPw("NULL");
		}
		memberDTO.setPhone(request.getParameter("phone"));
		memberDTO.setEmail(request.getParameter("email"));
		
		try{
			con = DBConnector.getConnect();
			con.setAutoCommit(false);
			result = memberDAO.update(memberDTO, con);
			
			if(memberDTO.getKind().equals("company")){
				CompanyDAO companyDAO = new CompanyDAO();
				CompanyDTO companyDTO = new CompanyDTO();
				
				companyDTO.setUser_num(memberDTO.getUser_num());
				companyDTO.setCompany_name(request.getParameter("company_name"));
				companyDTO.setCompany_num(request.getParameter("company_num"));
				companyDTO.setCompany_phone(request.getParameter("company_phone"));
				
				result = companyDAO.update(companyDTO, con);
				
				con.commit();
			}else{
				PersonDAO personDAO = new PersonDAO();
				PersonDTO personDTO = new PersonDTO();
				if(!request.getParameter("birth").equals("")){
					personDTO.setBirth(Date.valueOf(request.getParameter("birth")));
				}
				personDTO.setUser_num(memberDTO.getUser_num());
				personDTO.setName(request.getParameter("name"));
				personDTO.setArtist(request.getParameter("artist"));

				result = personDAO.upload(personDTO, con);
				
				con.commit();
			}
		}catch(Exception e){
			e.printStackTrace();
			con.rollback();
		}finally {
			con.setAutoCommit(true);
		}
		
		String message = "회원 정보 수정에 실패 하셨습니다.";
		if(result>0){
			message = "회원 정보 수정을 성공 하셨습니다.";
		}
		
		request.setAttribute("message", message);
		request.setAttribute("path", "mypageMyInfo.mypage");
		
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/common/result.jsp");
		
		return actionFoward;
	}

}
