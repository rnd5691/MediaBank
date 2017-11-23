package com.imagestore.payment;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.ActionDown;

public class PaymentDownService implements ActionDown {

	@Override
	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
PaymentDAO paymentDAO = new PaymentDAO();
		
		
		if(request.getMethod().equals("POST")) {
			int work_seq = 0;
			try {
				work_seq = Integer.parseInt(request.getParameter("work_seq"));
			} catch (Exception e) {
				work_seq = 0;
			}

			paymentDAO.down(request, response, work_seq);
			
		}

	}

}
