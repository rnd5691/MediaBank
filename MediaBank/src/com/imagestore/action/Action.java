package com.imagestore.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	public ActionFoward doProcess(HttpServletRequest request, HttpServletResponse response) throws SQLException;
}
