package imes.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/toExcel")
public class toExcelServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("application/ms-excel");
		resp.setHeader("Content-disposition", "inline; filename=sap.xls");
		resp.setCharacterEncoding("UTF-8");
		String basePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/";
		PrintWriter out = resp.getWriter();
		out.print("<html>");
		out.print("<head>");
		out.print("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
		out.print("<link type=\"text/css\" rel=\"stylesheet\" href=\"" + basePath + "stylesheet/style.css\">");
		out.print("</head>");
		out.print("<body>");
		out.print("<table>");
		out.print(req.getParameter("htmlStr"));
		out.print("</table>");
		out.print("</body>");
		out.print("</html>");
		out.flush();
		out.close();
	}

}
