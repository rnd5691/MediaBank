package com.imagestore.member.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;

public class MemberNaverLoginService implements Action {

	@Override
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		ActionFoward actionFoward = new ActionFoward();
		HttpSession session = request.getSession();
		//1.네이버 아이디로 로그인 인증요청
		String cleintId = "ktmuqeVgbwjBrjz57J54";//애플리케이션 등록시 발급 받은 ClientId
		/*String clientSecret = "6k1LYtIks5";*/
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString(32);
		//사이트 간 요청 위조 공격을 방지 하기 위해 애플리케이션에서 생성한 '상태토큰'값. URL인코딩을 적용.
		String redirectURI=null;
		String apiURL=null;
		try {
			redirectURI = URLEncoder.encode("http://localhost/MediaBank3/member/memberNaverCallback.member","UTF-8");
			//애플리케이션을 등록시 입력한 Callback URL 값. URL 인코딩을 적용.
			apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
			//response_type : 인증 과정에 대한 내부 구분값. code 로 전송해야 함.
			apiURL += "&client_id="+cleintId;
			apiURL += "&redirect_uri="+redirectURI;
			apiURL += "&state="+state;
			session.setAttribute("state", state);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actionFoward.setCheck(false);
		actionFoward.setPath(apiURL);
		
		return actionFoward;
	}

}
