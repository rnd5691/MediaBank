package com.imagestore.mypage.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.member.MemberDTO;
import com.imagestore.work.WorkDAO;
import com.imagestore.work.WorkDTO;

public class AdminUpdateService implements Action {

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
			WorkDTO workDTO = new WorkDTO();
			workDTO.setWork_seq(Integer.parseInt(request.getParameter("work_seq")));
			
			String method = request.getMethod();
			WorkDAO workDAO = new WorkDAO();
			int result = 0;
			if(method.equals("GET")){
				try{
					result = workDAO.approvalUpdate(workDTO.getWork_seq());
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				workDTO.setReply(request.getParameter("reply"));
				try{
					
					result = workDAO.replyUpdate(workDTO);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				
			}
			actionFoward.setCheck(true);
			if(result>0){
				actionFoward.setPath("mypageSalesRequestList.mypage");
			}else{
				request.setAttribute("message", "업데이트를 실패하였습니다.");
				request.setAttribute("path", "mypageSalesRequestList.mypage");
				actionFoward.setPath("../WEB-INF/view/common/result.jsp");
			}
		}
		
		return actionFoward;
	}

}
