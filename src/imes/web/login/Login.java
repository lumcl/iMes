package imes.web.login;

import imes.core.Helper;
import imes.core.HttpController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class Login extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);

		for (Enumeration<String> e = req.getSession().getAttributeNames(); e.hasMoreElements();) {
			req.getSession().removeAttribute(e.nextElement());
		}
		getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		
		if (isAuthorisedUser(req)) {
			if (req.getSession().getAttribute("jurl") != null){
				String url = req.getSession().getAttribute("jurl").toString();
				req.getSession().removeAttribute("jurl");
				resp.sendRedirect(url);
			}else{
				getServletContext().getRequestDispatcher("/WEB-INF/pages/login/home.jsp").forward(req, resp);
			}
		} else {
			req.setAttribute("message", "用戶帳戶或登入密碼錯誤, 請再次輸入帳戶及密碼");
			getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
		}
	}

	private boolean isAuthorisedUser(HttpServletRequest req) {
		boolean authorised = false;
		try {
			String sql = "select name,admin,email,manager from users where userid=? and password=? and status='A'";
			String str;
			Connection conn;
			conn = getDataSource().getConnection();
			// TODO Auto-generated catch block
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Helper.toUpperCase(req.getParameter("user")));
			pstm.setString(2, Helper.getMD5(req.getParameter("password").getBytes()));
			ResultSet rst = pstm.executeQuery();
			if (rst.next()) {
				authorised = true;
				req.getSession().setAttribute("uid", Helper.toUpperCase(req.getParameter("user")));
				req.getSession().setAttribute("uname", rst.getString("name"));
				req.getSession().setAttribute("uadmin", rst.getString("admin"));
				req.getSession().setAttribute("uemail", rst.getString("email"));
				req.getSession().setAttribute("umgr", rst.getString("manager"));
			} else {
				for (Enumeration<String> e = req.getSession().getAttributeNames(); e.hasMoreElements();) {
					str = e.nextElement();
					if (!str.equals("jurl")){
						req.getSession().removeAttribute(str);
					}
				}
			}
			rst.close();
			pstm.close();
			conn.close();
			sql = null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authorised;
	}

}
