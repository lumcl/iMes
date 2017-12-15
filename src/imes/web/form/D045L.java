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

@WebServlet("/D045L")
public class D045L extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/form/D045L.jsp");
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
		render(req, resp, "/WEB-INF/pages/form/D045L.jsp");
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		if (getParam(req, "bdbh_textarea").equals("")) {
			return;
		}

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String sql = "SELECT * " //
				+ "  FROM D045H, D045L " //
				+ " WHERE D045L.GSDM = D045H.GSDM AND D045L.BDDM = D045H.BDDM AND D045L.BDBH = D045H.BDBH "; //

		if (!getParam(req, "bdbh_textarea").equals("")) {
			sql += "         AND D045L.BDBH IN (" + toSqlString(textAreaToList(req, "bdbh_textarea")) + ") ";
		}

		sql += " ORDER BY D045L.GSDM,D045L.BDDM,D045L.BDBH,D045L.SQNR";
		Connection conn = getConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);

		list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();
		conn.close();

		req.setAttribute("list", list);

	}

}
