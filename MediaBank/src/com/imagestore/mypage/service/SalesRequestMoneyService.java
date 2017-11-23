package com.imagestore.mypage.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.member.MemberDTO;
import com.imagestore.util.PageMaker;
import com.imagestore.work.WorkDAO;
import com.imagestore.work.WorkDTO;

public class SalesRequestMoneyService implements Action {

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
			WorkDAO workDAO = new WorkDAO();
			
			int curPage=1;
			
			try {
				curPage = Integer.parseInt(request.getParameter("curPage"));
				
				
			} catch (Exception e) {
				
				curPage=1;
			}
			
			int totalCount = 0;
			int user_num;
			int workTotalCount = 0;
			int totalMoney = 0;
			try {
				user_num = memberDTO.getUser_num();
				
				totalCount = workDAO.getTotalCount(user_num, "승인");
				
				workTotalCount = workDAO.workTotalCount(user_num);
				
				PageMaker pageMaker = new PageMaker(curPage, totalCount);
				
				List<WorkDTO> ar = workDAO.MoneySelectList(memberDTO.getUser_num(), pageMaker.getMakeRow());
				totalMoney = workDAO.totalMoney(user_num);
				request.setAttribute("totalMoney", totalMoney);
				request.setAttribute("workTotal", workTotalCount);
				request.setAttribute("list", ar);
				request.setAttribute("makePage", pageMaker.getMakePage());
			} catch (Exception e) {
				e.printStackTrace();
			}
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/MYPAGE/salesRequestMoney.jsp");
		}
				
		return actionFoward;
	}

}
