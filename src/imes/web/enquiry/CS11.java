package imes.web.enquiry;

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

@WebServlet("/CS11")
public class CS11 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req,resp,"/WEB-INF/pages/enquiry/CS11.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);

		try {
			ActionPost(req, resp);
		} catch (Exception e) {

			PrintError(resp,e);
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String jspParams = getParam(req,"werks_low") + getParam(req,"pmatnr_low") + getParam(req,"pmatnr_low") + getParam(req,"cmatnr_low")
				+ getParam(req,"matkl_low") + getParam(req,"pmatnr_textarea") + getParam(req,"cmatnr_textarea") + getParam(req,"matkl_textarea");

		if (!jspParams.isEmpty()) {
			String sql = "SELECT WERKS, PMATNR, PMAKTX, BLEVEL, " //
					+ "       DPMATNR, CMATNR, CMAKTX, " //
					+ "       CMATKL, CWERKS, DUSAGE, DUOM, POTX1, " //
					+ "       ALPOS, EWAHR, ALPGR, AENNR " //
					+ "  FROM IT.SBOMXTB " //
					+ " WHERE 1 = 1 "; //

			if (!getParam(req,"werks_low").equals("")) {
				sql += Helper.sqlParams(req, "SBOMXTB.WERKS", "werks_low", "werks_high");
			}
			if (!getParam(req,"pmatnr_low").equals("")) {
				sql += Helper.sqlParams(req, "SBOMXTB.PMATNR", "pmatnr_low", "pmatnr_high");
			}
			if (!getParam(req,"cmatnr_low").equals("")) {
				sql += Helper.sqlParams(req, "SBOMXTB.CMATNR", "cmatnr_low", "cmatnr_high");
			}
			if (!getParam(req,"matkl_low").equals("")) {
				sql += Helper.sqlParams(req, "SBOMXTB.CMATKL", "matkl_low", "matkl_high");
			}
			if (!getParam(req,"pmatnr_textarea").trim().equals("")) {
				sql += "         AND SBOMXTB.PMATNR IN (" + toSqlString(textAreaToList(req,"pmatnr_textarea")) + ") ";
			}
			if (!getParam(req,"cmatnr_textarea").trim().equals("")) {
				sql += "         AND SBOMXTB.CMATNR IN (" + toSqlString(textAreaToList(req,"cmatnr_textarea")) + ") ";
			}
			if (!getParam(req,"matkl_textarea").trim().equals("")) {
				sql += "         AND SBOMXTB.CMATKL IN (" + toSqlString(textAreaToList(req,"matkl_textarea")) + ") ";
			}
			if (!req.getParameter("maktx_low").trim().equals("")) {
				sql += "         AND UPPER (CMAKTX) LIKE '%" + req.getParameter("maktx_low").toUpperCase() + "%' "; //
			}

			sql += " ORDER BY WERKS,PMATNR,CMATKL,DPMATNR,ALPOS,ALPGR,EWAHR DESC,CMAKTX,CMATNR "; //

			Connection conn = getSapCoConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			conn.close();

		}

		req.setAttribute("list", list);

		render(req,resp,"/WEB-INF/pages/enquiry/CS11.jsp");
	}

}
