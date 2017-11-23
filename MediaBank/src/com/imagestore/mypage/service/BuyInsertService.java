package com.imagestore.mypage.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.buy.BuyDAO;
import com.imagestore.buy.BuyDTO;
import com.imagestore.member.MemberDAO;
import com.imagestore.member.MemberDTO;
import com.imagestore.util.DBConnector;
import com.imagestore.work.WorkDAO;
import com.imagestore.work.WorkDTO;

public class BuyInsertService implements Action {

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
			int work_seq = 1;
			try {
				work_seq = Integer.parseInt(request.getParameter("work_seq"));
			} catch (Exception e) {
				work_seq = 1;
			}
			
			WorkDAO workDAO = new WorkDAO();
			
			BuyDAO buyDAO = new BuyDAO();
			Connection con = null;
			int result = 0;
			String name = null;
			try {
				con = DBConnector.getConnect();
				con.setAutoCommit(false);
				WorkDTO workDTO = workDAO.selectOne(work_seq, con);
				BuyDTO buyDTO = buyDAO.selectBuyOne(work_seq, con);
				MemberDAO memberDAO = new MemberDAO();
				try{
					name = memberDAO.searchNickName(memberDTO.getUser_num(), memberDTO.getKind());
				}catch(Exception e){
					e.printStackTrace();
				}
				
				buyDTO.setUser_num(memberDTO.getUser_num());
				buyDTO.setNickname(name);
				buyDTO.setWork_seq(work_seq);
				buyDTO.setPrice(workDTO.getPrice());
				
				result = buyDAO.insert(buyDTO, con);
				con.commit();
				if(result>0) {
					actionFoward.setCheck(true);
					request.setAttribute("message", "결제가 완료되었습니다");
					request.setAttribute("path", "../search/searchView.search?work_seq="+work_seq+"&file_num="+buyDTO.getFile_num());
					actionFoward.setPath("../WEB-INF/view/common/result.jsp");
				} else {
					actionFoward.setCheck(true);
					request.setAttribute("message", "SYSYEM : 결제에 실패하였습니다");
					request.setAttribute("path", "../search/searchView.search?work_seq="+work_seq);
					actionFoward.setPath("../WEB-INF/view/common/result.jsp");
				}
			} catch (Exception e) {
				e.printStackTrace();
				con.rollback();
			} finally {
				con.setAutoCommit(true);
				con.close();
			}
		}
		
		return actionFoward;
	}

}
