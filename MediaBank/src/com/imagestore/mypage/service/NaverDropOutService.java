package com.imagestore.mypage.service;

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
import com.imagestore.member.NaverDAO;
import com.imagestore.member.service.MemberNaverCallbackService;

public class NaverDropOutService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		HttpSession session = request.getSession();
		//유효성 검사
		
		String clientId = "ktmuqeVgbwjBrjz57J54";
		String clientSecret = "6k1LYtIks5";
		
		String access_token = (String)session.getAttribute("access_token");
		String refresh_token = (String)session.getAttribute("refresh_token");
		String apiURL=null;
		
		try {
			//접근토큰 유효성 검사
			NaverDAO naverDAO = new NaverDAO();
			String message = naverDAO.checkToken(access_token);
    		if(!message.equals("success")){
    			//유효성이 없을 경우 진행
    			access_token=naverDAO.renewToken(clientId, clientSecret, refresh_token);
    		}
			apiURL = "https://nid.naver.com/oauth2.0/token?";
			apiURL += "grant_type=delete";
			apiURL += "&client_id="+clientId;
			apiURL += "&client_secret="+clientSecret;
			apiURL += "&access_token="+URLEncoder.encode(access_token, "UTF-8");
			apiURL += "&service_provider=NAVER";
			
			System.out.println("apiURL : "+apiURL);
			URL url = new URL(apiURL);
			
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
	    	BufferedReader br;
	    	
	    	if(responseCode==200) { // 정상 호출
	    		br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	    	} else {  // 에러 발생
	    		br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	    	}
			JSONParser jsonParser = new JSONParser();
			JSONObject obj = (JSONObject)jsonParser.parse(br);
			
			br.close();
			System.out.println("obj : "+obj.toJSONString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		actionFoward.setCheck(true);
		actionFoward.setPath("DropOut.mypage");
		
		return actionFoward;
	}

}
