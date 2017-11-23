package com.imagestore.payment;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.file.FileDAO;
import com.imagestore.file.FileDTO;
import com.imagestore.member.MemberDTO;

public class PaymentDownService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		PaymentDAO paymentDAO = new PaymentDAO();
		
		
		if(request.getMethod().equals("POST")) {
			int work_seq = 0;
			try {
				work_seq = Integer.parseInt(request.getParameter("work_seq"));
			} catch (Exception e) {
				work_seq = 0;
			}
			
			paymentDAO.down(request, response, work_seq);
			
			/*actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/search/searchView.jsp?work_seq="+work_seq);*/
		}
		return actionFoward;
	}
}