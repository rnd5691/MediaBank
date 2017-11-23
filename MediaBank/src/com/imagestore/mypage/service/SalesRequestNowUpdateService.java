package com.imagestore.mypage.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.file.FileDAO;
import com.imagestore.member.MemberDTO;
import com.imagestore.util.DBConnector;
import com.imagestore.work.WorkDAO;

public class SalesRequestNowUpdateService implements Action {

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
			
			String[]  ar = null;
			List<Integer> checkWork_seq = new ArrayList<>();//체크된 작품들
			List<Integer> ncheckWork_seq = new ArrayList<>();//체크가 안된 작품들
			List<Integer> totalWork_seq = new ArrayList<>();//전체 작품
			WorkDAO workDAO = new WorkDAO();
			FileDAO fileDAO = new FileDAO();
			Connection con = null;
			int result = 0;
			try{
				ar = request.getParameterValues("view");
				//문자열로 되어있는 체크된 작품 번호를 숫자로 변환
				for(int i=0; i<ar.length; i++) {
					int work_seq = Integer.parseInt(ar[i]);
					checkWork_seq.add(work_seq);
				}
				totalWork_seq = fileDAO.work_seq(memberDTO.getUser_num(), file_kind);//전체 작품 수 가져오기
				System.out.println("[전체 work_seq]");
				for(int i=0; i<totalWork_seq.size(); i++){
					System.out.print(totalWork_seq.get(i)+", ");
				}
				System.out.println();
				
				for(int i=0; i<totalWork_seq.size(); i++){
					boolean check = false;
					for(int j=0; j<checkWork_seq.size(); j++){
						if(totalWork_seq.get(i)==checkWork_seq.get(j)){
							check = false;
							break;
						}else{
							check = true;
						}
					}
					if(check){
						ncheckWork_seq.add(totalWork_seq.get(i));
					}
				}
			}catch(Exception e){
				//체크된 값이 없을 경우 진행
				try{
					ncheckWork_seq = fileDAO.work_seq(memberDTO.getUser_num(), file_kind);
				}catch(Exception e1){
					e.printStackTrace();
				}
			}
			try{
				con = DBConnector.getConnect();
				con.setAutoCommit(false);
				//체크된 작품들 판매 상태 바꾸기
				if(checkWork_seq != null){
					for(int work : checkWork_seq){
						result = workDAO.sellUpdate(con, work, "Y");
					}				
				}
				//미체크된 작품들 판매 상태 바꾸기
				if(ncheckWork_seq != null){
					for(int work : ncheckWork_seq){
						result = workDAO.sellUpdate(con, work, "N");
					}				
				}
				
				con.commit();
			}catch(Exception e){
				con.rollback();
				e.printStackTrace();
			}finally {
				con.setAutoCommit(true);
				con.close();
			}
			if(result==0){
				request.setAttribute("message", "변경 사항이 저장되지 않았습니다.");
				request.setAttribute("path", "mypageSalesRequestNow.mypage");
				actionFoward.setCheck(true);
				actionFoward.setPath("../WEB-INF/view/common/result.jsp");
			}else{
				actionFoward.setCheck(true);
				actionFoward.setPath("mypageSalesRequestNow.mypage");			
			}
		}
		
		return actionFoward;
	}

}
