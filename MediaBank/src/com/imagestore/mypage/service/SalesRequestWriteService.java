package com.imagestore.mypage.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.file.FileDAO;
import com.imagestore.file.FileDTO;
import com.imagestore.member.MemberDTO;
import com.imagestore.person.PersonDAO;
import com.imagestore.person.PersonDTO;
import com.imagestore.util.DBConnector;
import com.imagestore.work.WorkDAO;
import com.imagestore.work.WorkDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class SalesRequestWriteService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberDTO memberDTO = (MemberDTO)session.getAttribute("member");
		
		if(method.equals("GET")){
			PersonDAO personDAO = new PersonDAO();
			try {
				PersonDTO personDTO = personDAO.selectOne(memberDTO.getUser_num());
				request.setAttribute("nickname", personDTO.getNickName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/view/MYPAGE/salesRequestWrite.jsp");
		} else {
			//파일업로드 영역
			String savePath = request.getServletContext().getRealPath("upload");
			int sizeLimit = 1024*1024*10;//10MB 파일크기제한
			System.out.println("savePath :"+savePath);
			FileDTO fileDTO = new FileDTO();
			FileDAO fileDAO = new FileDAO();
			WorkDTO workDTO = new WorkDTO();
			WorkDAO workDAO = new WorkDAO();
			PersonDAO personDAO = new PersonDAO();
			
			
			
			int result = 0;
			String fileName = null;
			Connection con = null;
			String extension = null;
			try {
				MultipartRequest multi = new MultipartRequest(request, savePath, sizeLimit, "UTF-8", new DefaultFileRenamePolicy());
				Enumeration files = multi.getFileNames();
				while(files.hasMoreElements()) {
					fileName = (String)files.nextElement();
					
					fileName = multi.getFilesystemName(fileName);
				}
				//파일 확장자구분
				 int index = fileName.lastIndexOf(".");
				 extension = fileName.substring(index);
				//찾아온다!
				String writer = personDAO.selectWriter(memberDTO.getUser_num());
				//커넥션
				con = DBConnector.getConnect();
				con.setAutoCommit(false);
				//유저인포
				int seq = workDAO.fileNumSelect(con);
				workDTO.setWork(multi.getParameter("work"));
				workDTO.setUser_num(memberDTO.getUser_num());
				workDTO.setWork_seq(seq);
				workDTO.setNickname(writer);
				workDTO.setUpload_check("대기중");
				workDTO.setTag(multi.getParameter("tag"));
				workDTO.setPrice(Integer.parseInt(multi.getParameter("price")));
				workDTO.setContents(multi.getParameter("contents"));
				workDTO.setSell("N");
				
				result = workDAO.insert(workDTO, con);
				System.out.println("Work-seq : "+workDTO.getWork_seq());
				//파일확장자 구분
				if(extension.equals(".mp4") || extension.equals(".avi") || extension.equals(".flv")) {
					extension = "video";
				} else {
					extension = "image";
				}
				//파일 테이블
				fileDTO.setFile_name(fileName);
				fileDTO.setWork_seq(seq);
				System.out.println("file work-seq : "+fileDTO.getWork_seq());
				fileDTO.setWidth(multi.getParameter("width"));
				fileDTO.setHeight(multi.getParameter("height"));
				fileDTO.setFile_route(savePath);
				fileDTO.setFile_kind(extension);
				result = fileDAO.fileUpload(request, response, fileDTO, con);
				
				con.commit();
				
			} catch (Exception e) {
				e.printStackTrace();
				con.rollback();
			} finally {
				con.setAutoCommit(true);
				con.close();
			}
			
			if(result>0){
				actionFoward.setCheck(true);
				actionFoward.setPath("mypageSalesRequestList.mypage");
			}else{
				actionFoward.setCheck(true);
				request.setAttribute("message", "업로드에 실패 하셨습니다.");
				request.setAttribute("path", "mypageSalesRequestList.mypage");
				actionFoward.setPath("../WEB-INF/view/common/result.jsp");
			}
		}
		return actionFoward;
	}

}
