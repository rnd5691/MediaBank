package com.imagestore.qna.service;

import java.sql.SQLException;

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

public class QnaWriteService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		String method = request.getMethod();
		
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		String writer = null;
		
		if(method.equals("GET")){
			if(memberDTO.getKind().equals("company")){
				CompanyDAO companyDAO = new CompanyDAO();
				try{
					writer = companyDAO.selectWriter(memberDTO.getUser_num());
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				PersonDAO personDAO = new PersonDAO();
				try{
					writer = personDAO.selectWriter(memberDTO.getUser_num());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			request.setAttribute("writer", writer);
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/qna/qnaWrite.jsp");
		}else{
			QnaDAO qnaDAO = new QnaDAO();
			QnaDTO qnaDTO = new QnaDTO();
			
			qnaDTO.setWriter(request.getParameter("writer"));
			qnaDTO.setContents(request.getParameter("contents"));
			qnaDTO.setTitle(request.getParameter("title"));
			
			int result=0;
			
			try{
				result=qnaDAO.insert(qnaDTO, memberDTO.getUser_num());
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(result>0){
				actionFoward.setCheck(false);
				actionFoward.setPath("./qnaList.qna");
			}else{
				actionFoward.setCheck(true);
				request.setAttribute("message", "Q&A 업로드에 실패 하셨습니다.");
				request.setAttribute("path", "./qnaList.qna");
				actionFoward.setPath("../WEB-INF/view/common/result.jsp");
			}
		}
		return actionFoward;
	}

}
