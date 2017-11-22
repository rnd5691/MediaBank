package com.imagestore.mypage.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.file.FileDAO;
import com.imagestore.work.WorkDAO;

public class SalesRequestNowUpdateService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		WorkDAO workDAO = new WorkDAO();
		FileDAO fileDAO = new FileDAO();
		int result = 0;
		int work_seq = 0;
		String sell = "";
		String[] sellArr = null;
		
		try {
			sellArr = request.getParameter("sell").split(",");
			
			for(int i=0;i<sellArr.length; i++) {
				work_seq = Integer.parseInt(sellArr[i].split("_")[0]);
				sell = sellArr[i].split("_")[1];
				
				result = workDAO.sellUpdate(work_seq, sell);
			}
			
			if(result>0)	{
				actionFoward.setCheck(true);
			}else	{
				actionFoward.setCheck(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		actionFoward.setPath("mypageSalesRequestNow.mypage");
		
		return actionFoward;
	}

}
