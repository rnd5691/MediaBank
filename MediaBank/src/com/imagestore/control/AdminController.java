package com.imagestore.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.imagestore.action.Action;
import com.imagestore.action.ActionFoward;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/AdminController")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HashMap<String, Object> map;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminController() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void init(ServletConfig config) throws SecurityException{
    	map = new HashMap<>();
    	//1.파일명 구하기
    	String fileName = config.getInitParameter("properties");
    	//2.파일 실제 경로 구하기
    	String realPath = config.getServletContext().getRealPath("WEB-INF/properties");
    	File f = new File(realPath, fileName);
    	//3.파싱 준비
    	FileInputStream fi = null;
    	Properties properties = new Properties();
    	try{
    		//4.파일을 파싱
    		fi = new FileInputStream(f);
    		properties.load(fi);
    		//5.key를 이용해서 value 구하기
    		Iterator<Object> it = properties.keySet().iterator();
    		while(it.hasNext()){
    			String key = (String)it.next();
    			String value = (String)properties.get(key);
    			//6.value를 이용해서 객체 생성하기
    			Class obj = Class.forName(value);
    			Object instance = obj.newInstance();
    			//7.key와 instance를 맵에 저장
    			System.out.println("key:"+key);
    			System.out.println("value:"+value);
    			map.put(key, instance);
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try{
    			fi.close();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		int startIndex = request.getContextPath().length();
		int lastIndex = uri.lastIndexOf(".");
		uri = uri.substring(startIndex, lastIndex);
		System.out.println(uri);
		//-------------------
		Action action = (Action)map.get(uri);
		ActionFoward actionFoward = null;
		try {
			actionFoward = action.doProcess(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//-------------------
		if(actionFoward.isCheck()){
			RequestDispatcher view = request.getRequestDispatcher(actionFoward.getPath());
			view.forward(request, response);
		}else{
			response.sendRedirect(actionFoward.getPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
