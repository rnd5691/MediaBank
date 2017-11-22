package com.imagestore.qna.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.qna.QnaDAO;
import com.imagestore.qna.QnaDTO;

public class QnaViewService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		int qna_seq = Integer.parseInt(request.getParameter("qna_seq"));
		QnaDAO qnaDAO = new QnaDAO();
		QnaDTO qnaDTO = null;
		
		try{
			qnaDTO = qnaDAO.selectOne(qna_seq);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		request.setAttribute("qna", qnaDTO);
		
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/qna/qnaView.jsp");
		return actionFoward;
	}

}
