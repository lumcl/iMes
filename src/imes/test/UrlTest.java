package imes.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UrlTest
 */
@WebServlet({ "/UrlTest", "/UrlTest/*" })
public class UrlTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UrlTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String buf[] = {};
		if (request.getPathInfo() != null) {
			buf = request.getPathInfo().split("/");
		}

		PrintWriter out = response.getWriter();
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String key = headers.nextElement();
			out.write(key + ":" + request.getHeader(key) + "<br/>");
		}
		out.write("getLocalAddr:" + request.getLocalAddr() + "<br/>");
		out.write("getRequestURI:" + request.getRequestURI() + "<br/>");
		out.write("getRequestURL:" + request.getRequestURL() + "<br/>");
		out.write("getQueryString:" + request.getQueryString() + "<br/>");
		out.write("getContextPath:" + request.getContextPath() + "<br/>");
		out.write("getPathInfo:" + request.getPathInfo() + "<br/>");
		out.write("getPathTranslated:" + request.getPathTranslated() + "<br/>");
		out.write("getRemoteAddr:" + request.getRemoteAddr() + "<br/>");
		out.write("getServerName:" + request.getServerName() + "<br/>");
		out.write("getAuthType:" + request.getAuthType() + "<br/>");
		out.write("getCharacterEncoding:" + request.getCharacterEncoding() + "<br/>");
		out.write("getScheme:" + request.getScheme() + "<br/>");

		for (String str : buf) {
			out.write("pathinfo:" + str + "<br/>");
		}

		String str = "  我們一起abc D後面  ";
		
		out.write("中文字:"+str.trim().toUpperCase()+":");
		
		
		
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
