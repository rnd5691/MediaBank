package com.imagestore.qna.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.company.CompanyDAO;
import com.imagestore.member.MemberDTO;
import com.imagestore.person.PersonDAO;
import com.imagestore.qna.QnaDAO;
import com.imagestore.qna.QnaDTO;
import com.imagestore.util.PageMaker;

public class QnaListService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		
		QnaDAO qnaDAO = new QnaDAO();
		int curPage=1;
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}catch(Exception e) {
			curPage = 1;
		}
		String kind = request.getParameter("kind");
		String search = request.getParameter("search");
		if(search==null){
			search="";
		}
		
		if(kind==null){
			kind="title";
		}else if(kind.equals("writer")){
			CompanyDAO companyDAO = new CompanyDAO();
			PersonDAO personDAO = new PersonDAO();
			
			try {
				int company = companyDAO.kindCheck(search);
				int person = personDAO.kindCheck(search);
				if(company==1){
					kind="company_name";
				}else if(person==1){
					kind="nickname";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try{
			int totalCount = qnaDAO.getTotalCount(kind, search);
			PageMaker pageMaker = new PageMaker(curPage, totalCount);
			HttpSession session = request.getSession();
			MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
			
			if(memberDTO == null){
				memberDTO = new MemberDTO();
				memberDTO.setKind("");
			}
			
			List<QnaDTO> ar = null;
			if(memberDTO.getKind().equals("admin")){
				ar = qnaDAO.adminSelectList(pageMaker.getMakeRow(), kind, search);
			}else{
				ar = qnaDAO.selectList(pageMaker.getMakeRow(), kind, search);
			}
			request.setAttribute("list", ar);
			request.setAttribute("kind", kind);
			request.setAttribute("search", search);
			request.setAttribute("makePage", pageMaker.getMakePage());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/qna/qnaList.jsp");
		
		return actionFoward;
	}

}
