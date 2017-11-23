package com.imagestore.search;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.buy.BuyDAO;
import com.imagestore.buy.BuyDTO;
import com.imagestore.file.FileDAO;
import com.imagestore.file.FileDTO;
import com.imagestore.member.MemberDTO;
import com.imagestore.util.DBConnector;
import com.imagestore.work.WorkDAO;
import com.imagestore.work.WorkDTO;

public class ViewService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		int work_seq = Integer.parseInt(request.getParameter("work_seq"));
		
		Connection con = null;
		WorkDTO workDTO = null;
		FileDTO fileDTO = null;
		BuyDTO buyDTO = null;
		try{
			con = DBConnector.getConnect();
			con.setAutoCommit(false);
			WorkDAO workDAO = new WorkDAO();
			workDTO = workDAO.selectOne(work_seq, con);
			
			FileDAO fileDAO = new FileDAO();
			fileDTO = fileDAO.selectOne(work_seq, con);
			
			if(memberDTO != null) {
				BuyDAO buyDAO = new BuyDAO();
				buyDTO = buyDAO.buyCheck(memberDTO.getUser_num(), fileDTO, workDTO, con);
				request.setAttribute("buyCheck", buyDTO);
			}
			
			con.commit();
		}catch(Exception e){
			e.printStackTrace();
			con.rollback();
		}finally{
			con.setAutoCommit(true);
			con.close();
		}
		
		request.setAttribute("file", fileDTO);
		request.setAttribute("work", workDTO);
		
		
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/search/searchView.jsp");
		return actionFoward;
	}

}
