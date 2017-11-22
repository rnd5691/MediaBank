package com.imagestore.mypage.service;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.file.FileDAO;
import com.imagestore.file.FileDTO;
import com.imagestore.util.DBConnector;
import com.imagestore.work.WorkDAO;
import com.imagestore.work.WorkDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class SalesRequestViewUpdateService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		WorkDAO workDAO = new WorkDAO();
		FileDAO fileDAO = new FileDAO();
		WorkDTO workDTO = null;
		FileDTO fileDTO = null;
		Connection con = null;
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(request.getMethod().equals("POST")) {
			System.out.println("post로 옴");
			String savePath = request.getServletContext().getRealPath("upload");
			int sizeLimit = 1024*1024*10;//10MB 파일크기제한
			System.out.println("savePath :"+savePath);
			String fileName=null;
			int result = 0;
			try {
				MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "UTF-8", new DefaultFileRenamePolicy());
				Enumeration files = multi.getFileNames();
				while(files.hasMoreElements()) {
					fileName = (String)files.nextElement();
					fileName = multi.getFilesystemName(fileName);
				}
				con = DBConnector.getConnect();
				con.setAutoCommit(false);
				//work UPDATE
				workDTO = new WorkDTO();
				workDTO.setWork(multi.getParameter("work"));
				workDTO.setTag(multi.getParameter("tag"));
				workDTO.setPrice(Integer.parseInt(multi.getParameter("price")));
				workDTO.setContents(multi.getParameter("contents"));
				workDTO.setWork_seq(Integer.parseInt(multi.getParameter("work_seq")));
				result = workDAO.salesViewUpdate(workDTO, con);
				
				//file UPDATE
				fileDTO = new FileDTO();
				if(fileName==null) {
					int work_seq = Integer.parseInt(multi.getParameter("work_seq"));
					fileDTO = fileDAO.selectOne(work_seq, con);
				} else {
					fileDTO.setWork_seq(Integer.parseInt(multi.getParameter("work_seq")));
					fileDTO.setFile_name(fileName);
					fileDTO.setFile_route(savePath);
					fileDTO.setWidth(multi.getParameter("width"));
					fileDTO.setHeight(multi.getParameter("height"));
					fileDTO.setFile_kind(multi.getParameter("file_kind"));
				}
				result = fileDAO.salesRequestViewUpdate(fileDTO, con);
				
					actionFoward.setCheck(true);
					actionFoward.setPath("mypageSalesRequestList.mypage");
			} catch (Exception e) {
				e.printStackTrace();
				con.rollback();
			} finally {
				con.setAutoCommit(true);
				con.close();
			}
		} else {
			System.out.println("get으로 옴");
			int work_seq = Integer.parseInt(request.getParameter("work_seq"));
			try {
				con = DBConnector.getConnect();
				workDTO = new WorkDTO();
				fileDTO = new FileDTO();
				workDTO = workDAO.selectOne(work_seq, con);
				fileDTO = fileDAO.selectOne(work_seq, con);
				
				request.setAttribute("work", workDTO);
				request.setAttribute("file", fileDTO);
			} catch (Exception e) {
				e.printStackTrace();
				con.rollback();
			} finally {
				con.close();
			}
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/MYPAGE/salesRequestViewUpdate.jsp");
		}
		return actionFoward;
	}
}
