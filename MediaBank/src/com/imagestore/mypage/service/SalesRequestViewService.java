package com.imagestore.mypage.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.file.FileDAO;
import com.imagestore.file.FileDTO;
import com.imagestore.member.MemberDTO;
import com.imagestore.util.DBConnector;
import com.imagestore.work.WorkDAO;
import com.imagestore.work.WorkDTO;

public class SalesRequestViewService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		if(memberDTO == null) {
			request.setAttribute("message", "잘못된 접근 방식 입니다.");
			request.setAttribute("path", "../index.jsp");
			actionFoward.setPath("../WEB-INF/view/common/result.jsp");
		}else {
			WorkDAO workDAO = new WorkDAO();
			int work_seq = Integer.parseInt(request.getParameter("work_seq"));
			boolean check=true;
			try{
				check = workDAO.adminCheck(work_seq);				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(check){
				//대기중일때
				WorkDTO workDTO = null;
				FileDTO fileDTO = null;
				Connection con = null;
				try{
					con = DBConnector.getConnect();
					con.setAutoCommit(false);
					workDTO = workDAO.selectOne(work_seq, con);
					FileDAO fileDAO = new FileDAO();
					fileDTO = fileDAO.selectOne(work_seq, con);
					con.commit();
				}catch(Exception e){
					e.printStackTrace();
					con.rollback();
				} finally {
					con.setAutoCommit(true);
					con.close();
				}
				request.setAttribute("file", fileDTO);
				request.setAttribute("work", workDTO);
				
				if(memberDTO.getKind().equals("admin")){
					actionFoward.setPath("../WEB-INF/view/admin/salesRequestView.jsp");
				}else{			
					actionFoward.setPath("../WEB-INF/view/MYPAGE/salesRequestView.jsp");
				}		
			}else{
				request.setAttribute("message", "이미 관리가 완료 된 작품 입니다.");
				request.setAttribute("path", "mypageSalesRequestList.mypage");
				actionFoward.setPath("../WEB-INF/view/common/result.jsp");
			}
			
		}
		actionFoward.setCheck(true);
		
		return actionFoward;
	}
}
