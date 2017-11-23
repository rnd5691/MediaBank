package com.imagestore.payment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.file.FileDAO;
import com.imagestore.file.FileDTO;
import com.imagestore.util.DBConnector;

public class PaymentDAO {
	//다운로드
	public void down(HttpServletRequest request, HttpServletResponse response, int work_seq) {
		FileDAO fileDAO = new FileDAO();
		Connection con = null;
		try {
			con = DBConnector.getConnect();
			FileDTO fileDTO = fileDAO.selectOne(work_seq, con);
			//파일이름 받아오기
			String fileName = fileDTO.getFile_name();
			String uploadFileName = request.getRealPath("upload") + "/" + fileName;
			
			File downFile = new File(uploadFileName);
			
			//다운
			if(downFile.exists() && downFile.isFile()) {
				try {
					// 파일 사이즈 조사하고
	                long filesize = downFile.length();

	                // content타입과 해더를 셋팅하여 파일을 출력
	                response.setContentType("application/x-msdownload");
	                response.setContentLength((int) filesize);
	                String strClient = request.getHeader("user-agent");
	 
	                if (strClient.indexOf("MSIE 5.5") != -1) {
	                    response.setHeader("Content-Disposition", "filename="
	                            + fileName + ";");
	                } else {
	                    response.setHeader("Content-Disposition",
	                            "attachment; filename=" + fileName + ";");
	                }
	                response.setHeader("Content-Length", String.valueOf(filesize));
	                response.setHeader("Content-Transfer-Encoding", "binary;");
	                response.setHeader("Pragma", "no-cache");
	                response.setHeader("Cache-Control", "private");
	 
	                byte b[] = new byte[1024];
	 
	                BufferedInputStream fin = new BufferedInputStream(
	                        new FileInputStream(downFile));
	 
	                BufferedOutputStream outs = new BufferedOutputStream(
	                        response.getOutputStream());
	 
	                int read = 0;
	 
	                while ((read = fin.read(b)) != -1) {
	                    outs.write(b, 0, read);
	                }
	                outs.flush();
	                outs.close();
	                fin.close();
				}  catch (Exception e) {
					 System.out.println("Download Exception : " + e.getMessage());
				}
			} else {
				  System.out.println("Download Error : downFile Error [" + downFile
		                    + "]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	//장바구니 찾아오기 user_num
	public List<PaymentDTO> selectAll(int user_num) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "select * from payment where user_num=?";
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, user_num);
		
		ResultSet rs = st.executeQuery();
		List<PaymentDTO> ar = new ArrayList<>();
		PaymentDTO paymentDTO = null;
		while(rs.next()){
			paymentDTO = new PaymentDTO();
			paymentDTO.setFile_num(rs.getInt("file_num"));
			paymentDTO.setFile_name(rs.getString("file_name"));
			paymentDTO.setWork(rs.getString("work"));
			paymentDTO.setPrice(rs.getInt("price"));
			ar.add(paymentDTO);
		}
		DBConnector.disConnect(rs, st, con);
		return ar;
	}
	
	//장바구니 파일넘 
		public PaymentDTO selectOne(int fileNum) throws Exception {
			Connection con = DBConnector.getConnect();
			String sql = "select * from file_table where file_num=?";
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setInt(1, fileNum);
			
			ResultSet rs = st.executeQuery();
			PaymentDTO paymentDTO = null;
			if(rs.next()){
				paymentDTO = new PaymentDTO();
				paymentDTO.setFile_num(rs.getInt("file_num"));
				paymentDTO.setFile_name(rs.getString("file_name"));
				paymentDTO.setWork_seq(rs.getInt("work_seq"));

			}
			DBConnector.disConnect(rs, st, con);
			
			return paymentDTO;
		}
	
	//장바구니 인서트
	public int insert(PaymentDTO paymentDTO) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "INSERT INTO payment VALUES(?,?,?,?,?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, paymentDTO.getUser_num());
		st.setInt(2, paymentDTO.getFile_num());
		st.setString(3, paymentDTO.getFile_name());
		st.setString(4, paymentDTO.getWork());
		st.setInt(5, paymentDTO.getPrice());
		int result = st.executeUpdate();
		DBConnector.disConnect(st, con);
		return result;
	}
}
