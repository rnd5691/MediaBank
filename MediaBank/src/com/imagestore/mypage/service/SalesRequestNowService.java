package com.imagestore.mypage.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.file.FileDAO;
import com.imagestore.file.FileDTO;
import com.imagestore.util.PageMaker;
import com.imagestore.work.WorkDAO;

public class SalesRequestNowService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		
		int user_num = 0;
		int curPage = 1;
		String file_kind = "";
		try {
			user_num = Integer.parseInt(request.getParameter("user_num"));
			curPage = Integer.parseInt(request.getParameter("curPage") == null?"1":request.getParameter("curPage"));
			file_kind = request.getParameter("file_kind") == null?"image":request.getParameter("file_kind");
		} catch (Exception e) {
			e.printStackTrace();
			curPage = 1;
		}
		
		int totalCount = 0;
		
		List<FileDTO> imageAr = null;
		List<FileDTO> videoAr = null;
		FileDTO fileDTO = null;
		FileDAO fileDAO = null;
		WorkDAO workDAO = null;
		
		try {
			fileDTO = new FileDTO();
			fileDAO = new FileDAO();
			totalCount = fileDAO.getTotalCount(user_num, file_kind);
			PageMaker pageMaker = new PageMaker(curPage, 12, totalCount);
			imageAr = fileDAO.selectNow(user_num, file_kind, pageMaker.getMakeRow());
			videoAr = fileDAO.selectNow2(user_num, file_kind, pageMaker.getMakeRow());
			
			request.setAttribute("file", imageAr);
			request.setAttribute("video", videoAr);
			request.setAttribute("file_kind", file_kind);
			request.setAttribute("user_num", user_num);
			
			request.setAttribute("makePage", pageMaker.getMakePage());
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/view/MYPAGE/salesRequestNow.jsp");
		return actionFoward;
	}

}
