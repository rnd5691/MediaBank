package com.imagestore.mypage.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.file.FileDAO;
import com.imagestore.file.FileDTO;
import com.imagestore.member.MemberDTO;
import com.imagestore.util.MakePage;
import com.imagestore.util.MakeRow;

public class SalesRequestNowAdd implements Action {

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
			String file_kind = request.getParameter("file_kind");
			
			int user_num = memberDTO.getUser_num();
			int curPage = 1;
			try{
				curPage = Integer.parseInt(request.getParameter("curPage") == null?"1":request.getParameter("curPage"));
			}catch(Exception e){
				curPage=1;
			}
			int totalCount = 0;
			
			List<FileDTO> imageAr = null;
			List<FileDTO> videoAr = null;
			
			FileDAO fileDAO = null;
			
			try {
				
				fileDAO = new FileDAO();
				totalCount = fileDAO.getTotalCount(user_num, file_kind);
				
				MakeRow makeRow = (MakeRow)session.getAttribute("makeRow");
				MakePage makePage = (MakePage)session.getAttribute("makePage");
				imageAr = fileDAO.selectNow(user_num, file_kind, makeRow);
				
				videoAr = fileDAO.selectNow(user_num, file_kind, makeRow);
				
				request.setAttribute("file", imageAr);
				request.setAttribute("video", videoAr);
				request.setAttribute("file_kind", file_kind);
				request.setAttribute("user_num", user_num);
				
				request.setAttribute("makePage", makePage);
				
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/MYPAGE/add/"+file_kind+"Add.jsp");
		}
		return actionFoward;
	}
}
