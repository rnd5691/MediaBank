package com.imagestore.mypage.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.file.FileDAO;
import com.imagestore.member.MemberDTO;
import com.imagestore.util.DBConnector;
import com.imagestore.work.WorkDAO;

public class SalesRequestViewDeleteService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		if(memberDTO == null) {
			request.setAttribute("message", "잘못된 접근 방식 입니다.");
			request.setAttribute("path", "../index.jsp");
			actionFoward.setPath("../WEB-INF/view/common/result.jsp");
			actionFoward.setCheck(true);
		}else {
			int work_seq = Integer.parseInt(request.getParameter("work_seq"));
			FileDAO fileDAO = new FileDAO();
			WorkDAO workDAO = new WorkDAO();
			Connection con = null;
			try {
				con = DBConnector.getConnect();
				con.setAutoCommit(false);
				//file table delete
				fileDAO.salesRequestViewDelete(work_seq, con);
				//work table delete
				workDAO.salesRequestViewDelete(work_seq, con);
				con.commit();
			} catch (Exception e) {
				con.rollback();
				e.printStackTrace();
			} finally {
				con.setAutoCommit(true);
				con.close();
			}
			actionFoward.setCheck(true);
			actionFoward.setPath("mypageSalesRequestList.mypage");
		}
		return actionFoward;
	}

}
