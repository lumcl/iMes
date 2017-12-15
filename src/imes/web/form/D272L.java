package imes.web.form;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;

@WebServlet("/D272L")
public class D272L extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/form/D272L.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		try {
			ActionPost(req, resp);
		} catch (Exception e) {

			PrintError(resp, e);
		}
		render(req, resp, "/WEB-INF/pages/form/D272L.jsp");
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String jspParams = getParam(req, "bdbh_textarea") + getParam(req, "aufnr_textarea");
		
		if (jspParams.equals("")) {
			return;
		}

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String sql = "SELECT * " //
				+ "  FROM D272H, D272L " //
				+ " WHERE D272L.GSDM = D272H.GSDM AND D272L.BDDM = D272H.BDDM AND D272L.BDBH = D272H.BDBH "; //

		if (!getParam(req, "bdbh_textarea").equals("")) {
			sql += "         AND D272H.BDBH IN (" + toSqlString(textAreaToList(req, "bdbh_textarea")) + ") ";
		}

		if (!getParam(req, "aufnr_textarea").equals("")) {
			sql += "         AND D272H.AUFNR IN (" + toSqlString(textAreaToList(req, "aufnr_textarea")) + ") ";
		}

		sql += " ORDER BY D272L.GSDM,D272L.BDDM,D272L.BDBH,D272L.SQNR";
		
		Connection conn = getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);

		list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();
		conn.close();

		req.setAttribute("list", list);

	}

}
