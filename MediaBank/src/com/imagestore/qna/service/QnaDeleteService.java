package com.imagestore.qna.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.qna.QnaDAO;

public class QnaDeleteService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		int qna_seq = Integer.parseInt(request.getParameter("qna_seq"));
		QnaDAO qnaDAO = new QnaDAO();
		System.out.println("qna_seq : "+qna_seq);
		int result = 0;
		try{
			result=qnaDAO.delete(qna_seq);			
		}catch(Exception e){
			e.printStackTrace();
		}
		if(result>0){
			request.setAttribute("message", "해당 게시판을 삭제하였습니다.");
		}else{
			request.setAttribute("message", "게시판 삭제를 실패하였습니다.");
		}
		request.setAttribute("path", "./qnaList.qna");
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/common/result.jsp");
		return actionFoward;
	}

}
