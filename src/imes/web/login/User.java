package imes.web.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;

@WebServlet("/user")
public class User  extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		String action = Helper.getAction(req);
		if (action.equals("chgpassword")){
			getServletContext().getRequestDispatcher("//WEB-INF/pages/login/chgpassword.jsp").forward(req, resp);
		}else{
			getServletContext().getRequestDispatcher("/WEB-INF/pages/login/home.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
		String action = Helper.getAction(req);
		if (action.equals("chgpassword")){
			if (chgpassword(req) == 0){
				req.setAttribute("message", "密碼更新失敗!!");
			}else{
				req.setAttribute("message", "密碼更新成功!!");
			}
		}
		for (Enumeration<String> e = req.getSession().getAttributeNames(); e.hasMoreElements();) {
			req.getSession().removeAttribute(e.nextElement());
		}
		getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);	}
	
	private int chgpassword(HttpServletRequest req){
		int i=0;
		String sql = "update users set password = ? where userid=? and password=? and status='A'";
		try {
			Connection conn = getDataSource().getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Helper.getMD5(req.getParameter("cfmpassword").getBytes()));
			pstm.setString(2, req.getSession().getAttribute("uid").toString());
			pstm.setString(3, Helper.getMD5(req.getParameter("password").getBytes()));
			i = pstm.executeUpdate();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

}
