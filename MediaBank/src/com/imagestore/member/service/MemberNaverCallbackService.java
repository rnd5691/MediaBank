package com.imagestore.member.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;
import com.imagestore.member.MemberDAO;
import com.imagestore.member.MemberDTO;
import com.imagestore.member.NaverDAO;
import com.imagestore.person.PersonDAO;
import com.imagestore.person.PersonDTO;

public class MemberNaverCallbackService implements Action {

	
	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		System.out.println("[CallBack 함수로 왔습니다.]");
		ActionFoward actionFoward = new ActionFoward();
		String clientId = "ktmuqeVgbwjBrjz57J54";//애플리케이션 클라이언트 아이디값";
	    String clientSecret = "6k1LYtIks5";//애플리케이션 클라이언트 시크릿값";
	    String code = request.getParameter("code");
	    String state = request.getParameter("state");
	    String redirectURI; 
	    String apiURL;
	    String access_token = null;
	    String refresh_token = null;
	    try {
	    	redirectURI = URLEncoder.encode("http://localhost/MediaBank/member/memberPerson.member", "UTF-8");
	    	apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
	    	apiURL += "client_id=" + clientId;
	    	apiURL += "&client_secret=" + clientSecret;
	    	apiURL += "&redirect_uri=" + redirectURI;
	    	apiURL += "&code=" + code;
	    	apiURL += "&state=" + state;
	    	System.out.println("apiURL="+apiURL);
	    	URL url = new URL(apiURL);
	    	HttpURLConnection con = (HttpURLConnection)url.openConnection();
	    	con.setRequestMethod("GET");
	    	int responseCode = con.getResponseCode();
	    	BufferedReader br;
	    	System.out.println("responseCode="+responseCode);
	    	if(responseCode==200) { // 정상 호출
	    		br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    	} else {  // 에러 발생
	    		br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	    	}
	    	
	    	JSONParser jsonParser = new JSONParser();
	    	JSONObject obj = (JSONObject)jsonParser.parse(br);
	    	access_token = (String)obj.get("access_token");
	    	refresh_token = (String)obj.get("refresh_token");
	    	
	    	br.close();
	    	System.out.println(obj.get("access_token"));
	    	if(responseCode==200) {
	    		System.out.println("obj : "+obj.toString());
	    	}
	    	/////////////////////////////////////
	    	NaverDAO naverDAO = new NaverDAO();
	    	MemberDTO memberDTO = null;
	    	PersonDTO personDTO = naverDAO.naver_info(access_token, refresh_token);
	    	
	    	PersonDAO personDAO = new PersonDAO();
	    	boolean check = naverDAO.searchToken(personDTO.getToken());
	    	MemberDAO memberDAO = new MemberDAO();
	    	
	    	if(check==true){
	    		System.out.println("[회원 정보가 없으므로 신규 가입을 진행하겠습니다.]");
	    		//회원정보에 값이 없을 때
	    		HttpSession session = request.getSession();
	    		session.setAttribute("naver", personDTO);
	    		
	    		actionFoward.setCheck(false);
	    		actionFoward.setPath("../member/memberAgreement.member");
	    	}else{
	    		System.out.println("[회원 정보가 있으므로 로그인을 진행하겠습니다.]");
	    		
	    		HttpSession session = request.getSession();
	    		
	    		String message = naverDAO.checkToken(access_token);
	    		if(!message.equals("success")){
	    			//유효성이 없을 경우 진행
	    			access_token=naverDAO.renewToken(clientId, clientSecret, refresh_token);
	    		}
	    		
	    		memberDTO = memberDAO.searchUser(personDTO);
	    		
	    		String writer = personDAO.selectWriter(memberDTO.getUser_num());
	    		
	    		session.setAttribute("access_token", access_token);
	    		session.setAttribute("refresh_token", refresh_token);
	    		session.setAttribute("member", memberDTO);
	    		session.setAttribute("writer", writer);
	    		
	    		actionFoward.setCheck(false);
	    		actionFoward.setPath("../index.jsp");
	    	}
	    } catch (Exception e) {
	    	System.out.println(e);
	    }
		
		
		return actionFoward;
	}
	
	
	
}
