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
 * Servlet implementation class SearchController
 */
@WebServlet("/SearchController")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private HashMap<String, Object> map;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	map = new HashMap<>();
    	//1.파일명 구하기
    	String fileName = config.getInitParameter("properties");
    	//2.파일 실제 경로
    	String path = config.getServletContext().getRealPath("WEB-INF/properties");
    	//3파싱
    	File file = new File(path, fileName);
    	FileInputStream fi = null;
    	Properties properties = new Properties();
    	
    	//4.파싱
    	try {
    		fi = new FileInputStream(file);
    		properties.load(fi);
    	//5.key value
    		Iterator<Object> it = properties.keySet().iterator();
    		while(it.hasNext()) {
    			String key = (String)it.next();
    			String value = (String)properties.getProperty(key);
    	//6.vlaue 객체
    			Class obj = Class.forName(value);
    			Object instance = obj.newInstance();
    			
    			map.put(key, instance);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
		} finally {
			try {
				fi.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ActionFoward actionFoward =null;
		Action action = null;
		
		String uri = request.getRequestURI();//uri 주소
		int sIndex = request.getContextPath().length();
		int lIndex = uri.lastIndexOf(".");
		uri = uri.substring(sIndex, lIndex);
		System.out.println("URI : "+uri);
		//=============================
			action = (Action)map.get(uri);
			try {
				actionFoward = action.doProcess(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//=============================

			if(actionFoward.isCheck()==true) {
				RequestDispatcher view = request.getRequestDispatcher(actionFoward.getPath());
				view.forward(request, response);
			} else {
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
