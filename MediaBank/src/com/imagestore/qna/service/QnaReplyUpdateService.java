package com.imagestore.qna.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.qna.QnaDAO;
import com.imagestore.qna.QnaDTO;

public class QnaReplyUpdateService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		QnaDTO qnaDTO = new QnaDTO();
		qnaDTO.setQna_seq(Integer.parseInt(request.getParameter("qna_seq")));
		qnaDTO.setReply(request.getParameter("reply"));
		
		int result = 0;
		
		try{
			QnaDAO qnaDAO = new QnaDAO();
			result = qnaDAO.replyUpdate(qnaDTO);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(result>0){
			actionFoward.setPath("qnaList.qna");
		}else{
			request.setAttribute("message", "답글 업로드에 실패하셨습니다.");
			request.setAttribute("path", "qnaView.qna?qna_seq="+qnaDTO.getQna_seq());
			actionFoward.setPath("../WEB-INF/view/common/result.jsp");
		}
		actionFoward.setCheck(true);
		return actionFoward;
	}

}
