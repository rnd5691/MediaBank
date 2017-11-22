package com.imagestore.mypage.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.member.MemberDAO;
import com.imagestore.member.MemberDTO;
import com.imagestore.util.DBConnector;
import com.imagestore.work.WorkDAO;

public class DropOutService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		Connection con = null;
		int result = 0;
		try{
			con = DBConnector.getConnect();
			con.setAutoCommit(false);
			MemberDAO memberDAO = new MemberDAO();
			//회원 탈퇴정보 추가
			result=memberDAO.dropOut(memberDTO.getUser_num(), con);
		
			//탈퇴 회원 작품이 있는지 확인
			WorkDAO workDAO = new WorkDAO();
			
			int work=workDAO.workTotalCount(memberDTO.getUser_num());
			//작품이 있을 경우 판매유무를 n으로 변경
			if(work>0){
				result=workDAO.dropOut(memberDTO.getUser_num(), con);
			}
			con.commit();
		}catch(Exception e){
			e.printStackTrace();
			con.rollback();
		}finally {
			con.setAutoCommit(true);
			con.close();
		}
				
		if(result>0){
			request.setAttribute("message", "탈퇴 되셨습니다. 그동안 Media Bank를 이용해주셔서 감사합니다.");
			request.setAttribute("path", "../member/memberLogout.member");
		}else{
			request.setAttribute("message", "탈퇴 실패하였습니다. 반복될 시에 관리자에게 문의해주시기 바랍니다.");
			request.setAttribute("path", "../index.jsp");
		}
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/common/result.jsp");
		
		
		
		return actionFoward;
	}

}
