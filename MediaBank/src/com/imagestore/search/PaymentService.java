package com.imagestore.search;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.company.CompanyDAO;
import com.imagestore.member.MemberDTO;
import com.imagestore.person.PersonDAO;
import com.imagestore.person.PersonDTO;
import com.imagestore.util.DBConnector;
import com.imagestore.work.WorkDAO;
import com.imagestore.work.WorkDTO;

public class PaymentService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		int work_seq = Integer.parseInt(request.getParameter("work_seq"));
		WorkDTO workDTO = new WorkDTO();
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		String name = null;
		try{
			Connection con = DBConnector.getConnect();
			WorkDAO workDAO = new WorkDAO();
			workDTO = new WorkDTO();
			workDTO = workDAO.selectOne(work_seq, con);
			con.close();
			
			if(memberDTO.getKind().equals("company")){
				CompanyDAO companyDAO = new CompanyDAO();
				name = companyDAO.selectWriter(memberDTO.getUser_num());
				
			}else{
				PersonDAO personDAO = new PersonDAO();
				PersonDTO personDTO = personDAO.selectOne(memberDTO.getUser_num());
				if(personDTO.getName()==null){
					name = personDTO.getNickName();
				}else{
					name = personDTO.getName();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("work", workDTO);
		request.setAttribute("name", name);
		
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/search/payment.jsp");
		
		return actionFoward;
	}

}
