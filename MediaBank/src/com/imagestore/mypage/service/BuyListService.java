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
import com.imagestore.file.FileDAO;
import com.imagestore.file.FileDTO;
import com.imagestore.member.MemberDTO;
import com.imagestore.util.PageMaker;

public class BuyListService implements Action {

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
			BuyDAO buyDAO = new BuyDAO();
			int curPage = 1;
			try{
				curPage = Integer.parseInt(request.getParameter("curPage"));
			}catch(Exception e){
				curPage=1;
			}
			int totalCount =0;
			
			try {
				
				totalCount = buyDAO.getTotalCount(memberDTO.getUser_num());
				PageMaker pageMaker = new PageMaker(curPage, totalCount);
				List<BuyDTO> ar = buyDAO.selectList(memberDTO.getUser_num(), pageMaker.getMakeRow());
				FileDAO fileDAO = new FileDAO();
				List<FileDTO> file = null;
				for(int i=0; i<ar.size(); i++) {
					file = fileDAO.selectWorkSeq(ar.get(i).getFile_num());
				}
				for(int i=0; i<ar.size(); i++) {
					System.out.println("작가명 : "+ar.get(i).getNickname());
				}
				request.setAttribute("list", ar);
				request.setAttribute("makePage", pageMaker.getMakePage());
				request.setAttribute("file", file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/MYPAGE/buyList.jsp");
		}
		return actionFoward;
	}

}
