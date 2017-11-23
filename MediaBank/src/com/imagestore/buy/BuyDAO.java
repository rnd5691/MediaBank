package com.imagestore.buy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.imagestore.file.FileDTO;
import com.imagestore.member.MemberDAO;
import com.imagestore.util.DBConnector;
import com.imagestore.util.MakeRow;
import com.imagestore.work.WorkDTO;

public class BuyDAO {
	
	//구매목록 work 가져오기
	public List<BuyDTO> buyWork(int user_num) throws Exception {
		Connection con = DBConnector.getConnect();
		String sql = "SELECT w.work FROM work_info w, buy_info b WHERE w.user_num=? and w.USER_NUM=b.USER_NUM and w.WORK_SEQ=b.WORK_SEQ ORDER BY b.work_seq desc";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, user_num);
		ResultSet rs = st.executeQuery();
		List<BuyDTO> ar = new ArrayList<>();
		while(rs.next()) {
			BuyDTO buyDTO = new BuyDTO();
			buyDTO.setWork(rs.getString("work"));
			ar.add(buyDTO);
		}
		DBConnector.disConnect(rs, st, con);
		return ar;
	}
	
	
	//viewPage file_num 비교
			public BuyDTO buyCheck(int user_num, FileDTO fileDTO, WorkDTO workDTO, Connection con) throws Exception {
				
				String sql = "SELECT * FROM buy_info WHERE user_num=? and file_num=? and work_seq=?";
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, user_num);
				st.setInt(2, fileDTO.getFile_num());
				st.setInt(3, workDTO.getWork_seq());
				ResultSet rs = st.executeQuery();
				BuyDTO buyDTO = null;
				if(rs.next()) {
					buyDTO = new BuyDTO();
					buyDTO.setBuy_seq(rs.getInt("buy_seq"));
				} else {
					buyDTO = null;
				}
				return buyDTO;
			}
	
	//insert
		public int insert(BuyDTO buyDTO, Connection con) throws Exception {
			String sql = "INSERT INTO buy_info VALUES (buy_seq.nextval,?,?,?,sysdate,?)";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, buyDTO.getUser_num());
			st.setInt(2, buyDTO.getFile_num());
			st.setInt(3, buyDTO.getWork_seq());
			st.setInt(4, buyDTO.getPrice());
			int result = st.executeUpdate();
			st.close();
			return result;
		}
	
	//buy_info에 넣기위해 work_info와 file_table의 정보를 하나씩만
			public BuyDTO selectBuyOne(int work_seq, Connection con) throws Exception {
				String sql = "SELECT DISTINCT f.file_num, w.work FROM file_table f, work_info w WHERE f.work_seq =? and w.WORK_SEQ = f.WORK_SEQ";
				PreparedStatement st = con.prepareStatement(sql);
				st.setInt(1, work_seq);
				ResultSet rs = st.executeQuery();
				BuyDTO buyDTO = null;
				if(rs.next()){
					buyDTO = new BuyDTO();
					buyDTO.setFile_num(rs.getInt("file_num"));
					buyDTO.setWork(rs.getString("work"));
				}
				rs.close();
				st.close();
				return buyDTO;
			}
	
	
	public int getTotalCount(int user_num) throws Exception{
		Connection con = DBConnector.getConnect();
		String sql = "select nvl(count(buy_seq), 0) from buy_info where user_num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, user_num);
		ResultSet rs = st.executeQuery();
		rs.next();
		int result = rs.getInt(1);
		
		DBConnector.disConnect(rs, st, con);
		
		return result;
	}
	
	//구매목록 리스트 쑤정
	public List<BuyDTO> selectList(int user_num, MakeRow makeRow) throws Exception{
		Connection con = DBConnector.getConnect();
		String sql = "select * from (select rownum R, Q.* from (select DISTINCT b.*, w.work from buy_info b, work_info w where b.user_num=? and b.WORK_SEQ=w.WORK_SEQ order by buy_seq desc) Q) where R between ? and ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, user_num);
		st.setInt(2, makeRow.getStartRow());
		st.setInt(3, makeRow.getLastRow());
		ResultSet rs = st.executeQuery();
		List<BuyDTO> ar = new ArrayList<>();
		MemberDAO memberDAO = new MemberDAO();
		while(rs.next()){
			BuyDTO buyDTO = new BuyDTO();
			buyDTO.setBuy_seq(rs.getInt("buy_seq"));
			buyDTO.setUser_num(rs.getInt("user_num"));
			buyDTO.setNickname(memberDAO.searchNickName(rs.getInt("user_num"), memberDAO.searchKind(rs.getInt("user_num"))));
			buyDTO.setWork(rs.getString("work"));
			buyDTO.setFile_num(rs.getInt("file_num"));
			buyDTO.setBuy_date(rs.getDate("buy_date"));
			buyDTO.setWork_seq(rs.getInt("work_seq"));
			buyDTO.setPrice(rs.getInt("price"));
			ar.add(buyDTO);
		}
		
		DBConnector.disConnect(rs, st, con);
		return ar;
	}
}

