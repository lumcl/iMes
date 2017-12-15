package imes.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import imes.core.HttpController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/act")
public class AutoCompleteTest extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		if (req.getParameter("term") == null) {
			getServletContext().getRequestDispatcher("/WEB-INF/pages/test/act.jsp").forward(req, resp);
		} else {
			//[{label: "#930歐洲", value:"E0100"},{label: "TXIE課", value:"J0703"},{label: "TXIE課", value:"J0703LH"},{label: "LED專案", value:"J0705"}]
			//("[{\"label\":\"博客园\",\"value\":\"cnblogs\"},{\"label\":\"囧月\",\"value\":\"囧月\"}]");
			PrintWriter out = resp.getWriter();
			//out.print(getUseridJson(req.getParameter("term")));
			out.print("[{\"label\":\"博客园\",\"value\":\"cnblogs\"},{\"label\":\"囧月\",\"value\":\"囧月\"}]");
			out.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		PrintWriter out = resp.getWriter();
		out.print(getUseridJson("l"));
		out.close();
	}

	private String getUseridJson(String terms) {
		String json = "";
		try {
			Connection conn = getConnection();
			String sql = "SELECT USERID FROM USERS WHERE userid LIKE '%" + terms + "%' ORDER BY USERID";
			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rst = pstm.executeQuery();

			while (rst.next()) {

				if (json.equals("")) {
					json = "[\"" + rst.getString("USERID") + "\"";
				} else {
					json += ",\"" + rst.getString("USERID") + "\"";
				}

			}

			if (!json.equals("")) {
				json += "]";
			}

			rst.close();
			pstm.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
