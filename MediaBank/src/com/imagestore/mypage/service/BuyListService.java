package com.imagestore.mypage.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.buy.BuyDAO;
import com.imagestore.buy.BuyDTO;
import com.imagestore.member.MemberDTO;
import com.imagestore.util.PageMaker;

public class BuyListService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		BuyDAO buyDAO = new BuyDAO();
		int curPage = 1;
		try{
			curPage = Integer.parseInt(request.getParameter("curPage"));
		}catch(Exception e){
			curPage=1;
		}
		
		int totalCount = 0;
		
		int user_num = Integer.parseInt(request.getParameter("user_num"));
		
		
		try{
			totalCount = buyDAO.getTotalCount(memberDTO.getUser_num());
			PageMaker pageMaker = new PageMaker(curPage, totalCount);
			List<BuyDTO> ar = buyDAO.selectList(memberDTO.getKind(), user_num, pageMaker.getMakeRow());
			
			request.setAttribute("list", ar);
			request.setAttribute("makePage", pageMaker.getMakePage());
		}catch(Exception e){
			e.printStackTrace();
		}

		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/MYPAGE/buyList.jsp");
		return actionFoward;
	}

}
