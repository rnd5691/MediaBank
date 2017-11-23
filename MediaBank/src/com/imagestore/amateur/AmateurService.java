package com.imagestore.amateur;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.file.FileDAO;
import com.imagestore.file.FileDTO;
import com.imagestore.util.DBConnector;
import com.imagestore.util.MakePage;
import com.imagestore.util.MakeRow;
import com.imagestore.util.PageMaker;
import com.imagestore.work.WorkDAO;
import com.imagestore.work.WorkDTO;

public class AmateurService implements Action {
	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionfoward = new ActionFoward();
		FileDAO fileDAO = new FileDAO();
		WorkDAO workDAO = new WorkDAO();
		List<FileDTO> ar = null;
		
		String select = null;
		String search = null;
		String kind = null;
		int curPage = 1;
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		} catch (Exception e) {
			curPage= 1;
		}
		//HashMap<String, List<FileDTO>> map = null;
		try {
			
			kind = request.getParameter("kind");
			select = request.getParameter("select");
			search = request.getParameter("search");
			if(select==null) {
				select="work";
			}
			if(search==null) {
				search="";
			}
			if(kind==null) {
				kind="image";
			}
			int totalCount = 0;
			if(kind.equals("image")) {
				totalCount = workDAO.artistGetTotalCountImgString(select, search);	
			} else if (kind.equals("video")) {
				totalCount = workDAO.artistGetTotalCountVideo(select, search);
			}
			 PageMaker pageMaker = new PageMaker(curPage,12,totalCount,6);
				
			//file_table or work_table의 작품명, 닉네임, 파일이름, 파일종류를 가져와
			 ar = fileDAO.fnWInfo();
			 
			 if(select.equals("work")) {
				 ar = workDAO.artistSearch(kind, select, search,pageMaker.getMakeRow());
			 } else if(select.equals("tag")) {
				 ar = workDAO.artistSearch(kind, select, search, pageMaker.getMakeRow());
				 for(int i=0; i<ar.size(); i++) {
					 String[] tag = ar.get(i).getTag().split(",");
					 for(int j=0; j<tag.length; j++) {
						 if(tag[j].equals("serach")) {
							 ar = workDAO.seachWorkSEQ(tag[j], kind, pageMaker.getMakeRow());
						 }
					 }
				 }
			 } else if(select.equals("nickname")) {
				 ar = workDAO.artistSearch(kind, select, search,pageMaker.getMakeRow());
			 } else {
				 System.out.println("아무것도 안들어왔대");
			 }
			 
		 
			 
			 MakePage makePage = pageMaker.getMakePage();
			 request.setAttribute("page", makePage);
		} catch (Exception e) {
			select = "work";
			search = "";		
			e.printStackTrace();
		}
		
		System.out.println("search : "+search);
		System.out.println("select : "+select);
		System.out.println("kind   : "+kind);
		
		
		request.setAttribute("select", select);
		request.setAttribute("search", search);
		request.setAttribute("author", ar);
		actionfoward.setCheck(true);
		actionfoward.setPath("../WEB-INF/view/amateur/amateur.jsp");
		
		return actionfoward;
	}

}
