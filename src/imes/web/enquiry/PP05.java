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

@WebServlet("/PP05")
public class PP05 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/enquiry/PP05.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		try {

			ActionPost(req, resp);
			render(req, resp, "/WEB-INF/pages/enquiry/PP05.jsp");

		} catch (Exception e) {

			PrintError(resp, e);
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String jspParams = getParam(req, "werks_low") + getParam(req, "budat_low") + getParam(req, "matnr_low")
				+ getParam(req, "aufnr_low") + getParam(req, "matnr_textarea") + getParam(req, "aufnr_textarea");

		if (jspParams.isEmpty()) {
			return;
		}

		String sql = "  SELECT AFKO.AUFNR, " //
				+ "         AFPO.MATNR, " //
				+ "         AFRU.WERKS, " //
				+ "         AFRU.VORNR, " //
				+ "         AFRU.BUDAT, " //
				+ "         CRHD.ARBPL, " //
				+ "         T001.ARBPL CARBPL, " //
				+ "         CASE WHEN STZHL = '00000000' THEN AFRU.LMNGA ELSE AFRU.LMNGA * -1 END LMNGA, " //
				+ "         AFRU.MEINH, " //
				+ "         CASE WHEN STZHL = '00000000' THEN AFRU.ISM01 ELSE AFRU.ISM01 * -1 END ISM01, " //
				+ "         CASE WHEN STZHL = '00000000' THEN AFRU.ISM02 ELSE AFRU.ISM02 * -1 END ISM02, " //
				+ "         CASE WHEN STZHL = '00000000' THEN AFRU.ISM03 ELSE AFRU.ISM03 * -1 END ISM03, " //
				+ "         CASE WHEN STZHL = '00000000' THEN AFRU.ISM04 ELSE AFRU.ISM04 * -1 END ISM04, " //
				+ "         CASE WHEN STZHL = '00000000' THEN AFRU.ISM05 ELSE AFRU.ISM05 * -1 END ISM05, " //
				+ "         CASE WHEN STZHL = '00000000' THEN AFRU.ISM06 ELSE AFRU.ISM06 * -1 END ISM06, " //
				+ "         AFVC.LTXA1, " //
				+ "         MAKT.MAKTX, " //
				+ "         AFRU.ERNAM, " //
				+ "         AFVV.VGW01, " //
				+ "         AFVV.VGW05, " //
				+ "         AFVV.BMSCH, " //
				+ "         AFRU.LTXA1 AFRUT " //
				+ "    FROM SAPSR3.AFKO, " //
				+ "         SAPSR3.AFVC, " //
				+ "         SAPSR3.AFRU, " //
				+ "         SAPSR3.AFVV, " //
				+ "         SAPSR3.AFPO, " //
				+ "         SAPSR3.CRHD, " //
				+ "         SAPSR3.CRHD T001, " //
				+ "         SAPSR3.MAKT " //
				+ "   WHERE AFVC.MANDT = AFKO.MANDT " //
				+ "         AND AFVC.AUFPL = AFKO.AUFPL " //
				+ "         AND AFRU.MANDT = AFVC.MANDT " //
				+ "         AND AFRU.RUECK = AFVC.RUECK " //
				+ "         AND AFVV.MANDT = AFVC.MANDT " //
				+ "         AND AFVV.AUFPL = AFVC.AUFPL " //
				+ "         AND AFVV.APLZL = AFVC.APLZL " //
				+ "         AND AFPO.MANDT(+) = AFKO.MANDT " //
				+ "         AND AFPO.AUFNR(+) = AFKO.AUFNR " //
				+ "         AND CRHD.MANDT(+) = AFVC.MANDT " //
				+ "         AND CRHD.OBJID(+) = AFVC.ARBID " //
				+ "         AND CRHD.OBJTY(+) = 'A' " //
				+ "         AND T001.MANDT(+) = AFRU.MANDT " //
				+ "         AND T001.OBJID(+) = AFRU.ARBID " //
				+ "         AND T001.OBJTY(+) = 'A' " //
				+ "         AND MAKT.MANDT(+) = AFPO.MANDT " //
				+ "         AND MAKT.MATNR(+) = AFPO.MATNR " //
				+ "         AND MAKT.SPRAS(+) = 'M' " //
				+ "         AND AFKO.MANDT = '168' ";//

		if (!getParam(req, "werks_low").equals("")) {
			sql += Helper.sqlParams(req, "AFRU.WERKS", "werks_low", "werks_high");
		}
		if (!getParam(req, "budat_low").equals("")) {
			sql += Helper.sqlParams(req, "AFRU.BUDAT", "budat_low", "budat_high");
		}
		if (!getParam(req, "matnr_low").equals("")) {
			sql += Helper.sqlParams(req, "AFPO.MATNR", "matnr_low", "matnr_high");
		}
		if (!getParam(req, "aufnr_low").equals("")) {
			sql += Helper.sqlParamsFormat(req, "AFKO.AUFNR", "aufnr_low", "aufnr_high", "%012d");
		}
		if (!getParam(req, "matnr_textarea").equals("")) {
			sql += "         AND AFPO.MATNR IN (" + toSqlString(textAreaToList(req, "matnr_textarea")) + ") ";
		}
		if (!getParam(req, "aufnr_textarea").equals("")) {
			sql += "         AND AFKO.AUFNR IN (" + toSqlString(textAreaToList(req, "aufnr_textarea", "%012d")) + ") ";
		}
		sql += "ORDER BY AFKO.AUFNR, AFRU.VORNR, AFRU.RMZHL "; //

		Connection conn = getSapPrdConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);

		list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();
		conn.close();

		double stdlab;
		double perfm;
		double labhrs;
		for (HashMap<String, Object> h : list) {

			perfm = 0;
			labhrs = Double.parseDouble(h.get("ISM06").toString());
			stdlab = (Double.parseDouble(h.get("VGW01").toString()) * Double.parseDouble(h.get("LMNGA").toString()))
					/ Double.parseDouble(h.get("BMSCH").toString());

			if (labhrs != 0) {
				perfm = (stdlab * 100) / labhrs;
			}

			h.put("STDLAB", stdlab);
			h.put("PERFM", perfm);
			h.put("DIFHRS", labhrs - stdlab);

			if (perfm != 0 && perfm < 100) {
				h.put("COLOR", "bgcolor='#ffff99'");
			}
		}

		req.setAttribute("list", list);

	}
}
