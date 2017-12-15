package imes.web.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;
import imes.entity.User;

@WebServlet("/register")
public class Register extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		PrintWriter out = resp.getWriter();
		if (req.getParameter("action").equals("create")) {
			if (insertUser(req) == 1) {
				out.print("用戶註冊成功");
			} else {
				out.println("用戶註冊失敗");
			}
		}else if (req.getParameter("action").equals("resetPassword")){
			if (resetPassword(req) == 1) {
				out.print("用戶密碼重置成功");
			} else {
				out.println("用戶密碼重置失敗");
			}
		}else{
			out.println("沒有作業");
		}
		out.flush();
		out.close();
	}

	private int insertUser(HttpServletRequest req) {
		int i = 0;
		String sql = "insert into users (userid, password, email, name, manager, status, crtdt, chgdt, admin)" //
				+ " values ( ?,?,?,?,' ','A',to_char(sysdate,'YYYYMMDD'),to_char(sysdate,'YYYYMMDD'),' ')";

		try {
			Connection conn = getDataSource().getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, toUpperCaseParam(req,"uid"));
			pstm.setString(2, Helper.getMD5("1234".getBytes()));
			pstm.setString(3, toUpperCaseParam(req,"uid") + "@l-e-i.com");
			pstm.setString(4, req.getParameter("name"));
			i = pstm.executeUpdate();
			pstm.close();

			req.getServletContext().setAttribute("userIds", User.getAllUserIds(conn));
			
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	private int resetPassword(HttpServletRequest req){
		int i = 0;
		String sql = "update users set password = ?, chgdt = to_char(sysdate,'YYYYMMDD') where userid = ?";
		try {
			Connection conn = getDataSource().getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Helper.getMD5("1234".getBytes()));
			pstm.setString(2, toUpperCaseParam(req,"uid"));
			i = pstm.executeUpdate();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
