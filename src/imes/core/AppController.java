package imes.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	public HttpServletRequest req;
	public HttpServletResponse resp;

	public void render(String jspFilename) throws ServletException, IOException {
		getServletContext().getRequestDispatcher(jspFilename).forward(req, resp);
	}
	
	public String getAction() {
		String buf[] = {};
		if (req.getPathInfo() != null) {
			buf = req.getPathInfo().split("/");
		}
		return buf.length == 2 ? buf[1] : "";
	}
	
	public String getParams(String name) {
		return req.getParameter(name) == null ? "" : req.getParameter(name).trim().toUpperCase();
	}
	
	public int getInteger(String name) {
		return req.getParameter(name) == null  ? 0 : Integer.parseInt(getParams(name));
	}
}
