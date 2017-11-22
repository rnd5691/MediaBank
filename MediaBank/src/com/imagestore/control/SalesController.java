package com.imagestore.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
 * Servlet implementation class SalesController
 */
@WebServlet("/SalesController")
public class SalesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HashMap<String, Object> map;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SalesController() {
        super();
        
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	map = new HashMap<>();
    	
    	String fileName = config.getInitParameter("properties");
    	
    	String realPath = config.getServletContext().getRealPath("WEB-INF/properties");
    	
    	File f = new File(realPath, fileName);
    	
    	FileInputStream fi = null;
    	Properties properties = new Properties();
    	
    	try {
			fi = new FileInputStream(f);
			properties.load(fi);
			
			Iterator<Object> it = properties.keySet().iterator();
			
			while(it.hasNext())	{
				String key = (String)it.next();
				String value = (String)properties.get(key);
				
				Class obj = Class.forName(value);
				Object instance = obj.newInstance();
				System.out.println(key);
				System.out.println(instance);
				map.put(key, instance);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally	{
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
			System.out.println(view);
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
