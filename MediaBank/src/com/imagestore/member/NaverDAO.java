package com.imagestore.member;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.imagestore.person.PersonDTO;
import com.imagestore.util.DBConnector;

public class NaverDAO {
	//access_token 유효성 검사
		public String checkToken(String access_token) throws Exception{
			System.out.println("유효성 검사");
			String header = "Bearer "+access_token;
			String apiURL = null;
			apiURL = "https://openapi.naver.com/v1/nid/verify";
			
			URL url = new URL(apiURL);
			
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", header);
			int responseCode = con.getResponseCode();
			
			BufferedReader br;
			
			if(responseCode==200){//정상호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}else{//에러발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			JSONParser jsonParser = new JSONParser();
			JSONObject obj = (JSONObject)jsonParser.parse(br);
			
			br.close();
			System.out.println("obj : "+obj.toJSONString());
			String message = (String)obj.get("message");
			
			return message;
		}
		//토큰 새로 갱신 받기
		public String renewToken(String clientId, String clientSecret, String refresh_token) throws Exception{
			System.out.println("새로 갱신 받기");
			String apiURL = "https://nid.naver.com/oauth2.0/token?";
			apiURL += "grant_type=refresh_token";
			apiURL += "&client_id="+clientId;
			apiURL += "&client_secret="+clientSecret;
			apiURL += "&refresh_token="+refresh_token;
			

			URL url = new URL(apiURL);
			
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			
			BufferedReader br;
			if(responseCode==200){//정상호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}else{//에러발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			
			JSONParser jsonParser = new JSONParser();
			JSONObject obj = (JSONObject)jsonParser.parse(br);
			
			br.close();
			System.out.println("obj : "+obj.toJSONString());
			
			String access_token = (String)obj.get("access_token");
			System.out.println("새로 갱신 받은 access_token : "+access_token);
			return access_token;
		}
	//네이버 토큰 확인
	public boolean searchToken(String token) throws Exception{
		Connection con = DBConnector.getConnect();
		String sql = "select * from user_info where token=? and session_check='N'";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, token);
		ResultSet rs = st.executeQuery();
		boolean check = true;
		if(rs.next()){
			check = false;
		}
		
		DBConnector.disConnect(rs, st, con);
		
		return check;
	}
	//네이버 회원 정보
		public PersonDTO naver_info(String access_token, String refresh_token) throws Exception{
			PersonDTO personDTO = new PersonDTO();
			String header = "Bearer "+access_token;
			String apiUPL = "https://openapi.naver.com/v1/nid/me";
			URL url = new URL(apiUPL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", header);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if(responseCode==200){//정상호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			}else{//에러발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			JSONParser jsonParser = new JSONParser();
			JSONObject response = (JSONObject)jsonParser.parse(br);
			JSONObject obj = (JSONObject)response.get("response");
			
			System.out.println("[NaverDAO의 obj] : "+obj.toString());
			br.close();
			
			//email값을 이용하여 id 값 구하기
			String email = (String)obj.get("email");
			System.out.println("obj의 email : "+email);
			StringTokenizer st_id = new StringTokenizer(email, "@");
			String result_id = null;
			if(st_id.hasMoreTokens()){
				result_id = st_id.nextToken();
			}
			personDTO.setId(result_id);
			personDTO.setPw("(null)");
			personDTO.setEmail(email);
			personDTO.setKind("person");
			personDTO.setToken((String)obj.get("id"));
			
			///////////////////////////
			personDTO.setNickName((String)obj.get("nickname"));
			return personDTO;
		}
}
