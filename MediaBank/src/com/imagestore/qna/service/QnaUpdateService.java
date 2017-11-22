package com.imagestore.qna.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.qna.QnaDAO;
import com.imagestore.qna.QnaDTO;

public class QnaUpdateService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		String method = request.getMethod();
		QnaDTO qnaDTO = new QnaDTO();
		qnaDTO.setQna_seq(Integer.parseInt(request.getParameter("qna_seq")));
		qnaDTO.setTitle(request.getParameter("title"));
		qnaDTO.setContents(request.getParameter("contents"));
		qnaDTO.setWriter(request.getParameter("writer"));
		
		if(method.equals("GET")){
			request.setAttribute("qna", qnaDTO);
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/qna/qnaUpdate.jsp");
		}else{
			System.out.println("일단 넘어왔음.");
			QnaDAO qnaDAO = new QnaDAO();
			int result = 0;
			try{
				result = qnaDAO.update(qnaDTO);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(result>0){
				actionFoward.setCheck(true);
				actionFoward.setPath("qnaList.qna");
			}else{
				request.setAttribute("message", "해당 내용 수정에 실패하였습니다.");
				request.setAttribute("path", "qnaList.qna");
				actionFoward.setCheck(true);
				actionFoward.setPath("../WEB-INF/view/common/result.jsp");
			}
		}
		return actionFoward;
	}

}
