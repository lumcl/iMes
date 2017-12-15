package imes.web.enquiry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;

@WebServlet("/ZIEB901")
public class ZIEB901  extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/enquiry/ZIEB901.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		try {

			ActionPost(req, resp);

		} catch (Exception ex) {

			PrintError(resp, ex);
		}

	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String sql = "SELECT * FROM ZIEB901 WHERE 1=1 ";
		sql += Helper.sqlParams(req, "BUKRS", "STRBUKRS", "ENDBUKRS");
		sql += Helper.sqlParams(req, "IMDAT", "STRIMDAT", "ENDIMDAT");
		sql += Helper.sqlParams(req, "LIFNR", "STRLIFNR", "ENDLIFNR");
		sql += Helper.sqlParams(req, "IMPNR", "STRIMPNR", "ENDIMPNR");
		sql += Helper.sqlParams(req, "MATNR", "STRMATNR", "ENDMATNR");
		
		Connection conn = getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		List<HashMap<String, Object>> list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();
		conn.close();

		req.setAttribute("list", list);

		getServletContext().getRequestDispatcher("/WEB-INF/pages/enquiry/ZIEB901.jsp").forward(req, resp);
	}
}
