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

@WebServlet("/ZIEB904")
public class ZIEB904 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/enquiry/ZIEB904.jsp");
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

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String jspParams = getParam(req, "werks_low") + getParam(req, "matnr_low") + getParam(req, "matkl_low")
				+ getParam(req, "maktx_low") + getParam(req, "lgort_low") + getParam(req, "matnr_textarea")
				+ getParam(req, "matkl_textarea") + getParam(req, "charg_textarea");

		if (!jspParams.equals("")) {

			String sql = "SELECT * FROM ZIEB904 WHERE 1=1 ";

			if (!getParam(req, "werks_low").equals("")) {
				sql += Helper.sqlParams(req, "WERKS", "werks_low", "werks_high");
			}
			if (!getParam(req, "matnr_low").equals("")) {
				sql += Helper.sqlParams(req, "MATNR", "matnr_low", "matnr_high");
			}
			if (!getParam(req, "lgort_low").equals("")) {
				sql += Helper.sqlParams(req, "LGORT", "lgort_low", "lgort_high");
			}
			if (!getParam(req, "matkl_low").equals("")) {
				sql += Helper.sqlParams(req, "MATKL", "matkl_low", "matkl_high");
			}
			if (!getParam(req, "maktx_low").equals("")) {
				sql += " AND MAKTX LIKE '%" + getParam(req, "maktx_low") + "%'";
			}
			if (!getParam(req, "matnr_textarea").equals("")) {
				sql += "         AND MATNR IN (" + toSqlString(textAreaToList(req, "matnr_textarea")) + ") ";
			}
			if (!getParam(req, "matkl_textarea").equals("")) {
				sql += "         AND MATKL IN (" + toSqlString(textAreaToList(req, "matkl_textarea")) + ") ";
			}

			sql += "ORDER BY WERKS, MATNR, LGORT, CHARG ";//
			System.out.println(sql);
			Connection conn = getConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			conn.close();
		}
/*
		String key_werks_matnr;
		String key_werks_matnr_lgort;

		HashMap<String, HashMap<String, Double>> total = new HashMap<String, HashMap<String, Double>>();
		HashMap<String, Double> row;

		for (HashMap<String, Object> h : list) {

			key_werks_matnr = h.get("WERKS").toString() + h.get("MATNR").toString();
			key_werks_matnr_lgort = key_werks_matnr + h.get("LGORT").toString();

			if (total.containsKey(key_werks_matnr)) {
				row = total.get(key_werks_matnr);
				row.put("CLABS", row.get("CLABS") + toDouble(h.get("CLABS").toString()));
				row.put("CINSM", row.get("CINSM") + toDouble(h.get("CINSM").toString()));
				row.put("CSPEM", row.get("CSPEM") + toDouble(h.get("CSPEM").toString()));
				row.put("CEINM", row.get("CEINM") + toDouble(h.get("CEINM").toString()));
				total.put(key_werks_matnr, row);
			} else {
				row = new HashMap<String, Double>();
				row.put("CLABS", toDouble(h.get("CLABS").toString()));
				row.put("CINSM", toDouble(h.get("CINSM").toString()));
				row.put("CSPEM", toDouble(h.get("CSPEM").toString()));
				row.put("CEINM", toDouble(h.get("CEINM").toString()));
				total.put(key_werks_matnr, row);
			}

			if (total.containsKey(key_werks_matnr_lgort)) {
				row = total.get(key_werks_matnr_lgort);
				row.put("CLABS", row.get("CLABS") + toDouble(h.get("CLABS").toString()));
				row.put("CINSM", row.get("CINSM") + toDouble(h.get("CINSM").toString()));
				row.put("CSPEM", row.get("CSPEM") + toDouble(h.get("CSPEM").toString()));
				row.put("CEINM", row.get("CEINM") + toDouble(h.get("CEINM").toString()));
				total.put(key_werks_matnr_lgort, row);
			} else {
				row = new HashMap<String, Double>();
				row.put("CLABS", toDouble(h.get("CLABS").toString()));
				row.put("CINSM", toDouble(h.get("CINSM").toString()));
				row.put("CSPEM", toDouble(h.get("CSPEM").toString()));
				row.put("CEINM", toDouble(h.get("CEINM").toString()));
				total.put(key_werks_matnr_lgort, row);
			}
		}

		List<HashMap<String, Object>> results = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> e;
		for (HashMap<String, Object> h : list) {

			if (req.getParameter("dsptotal_checkbox") != null) {

				key_werks_matnr = h.get("WERKS").toString() + h.get("MATNR").toString();
				key_werks_matnr_lgort = key_werks_matnr + h.get("LGORT").toString();

				if (total.containsKey(key_werks_matnr)) {
					row = total.get(key_werks_matnr);
					e = new HashMap<String, Object>();
					e.put("WERKS", h.get("WERKS"));
					e.put("MATNR", h.get("MATNR"));
					e.put("MEINS", h.get("MEINS"));
					e.put("MATKL", h.get("MATKL"));
					e.put("MAKTX", h.get("MAKTX"));
					e.put("VMENG", h.get("VMENG"));
					e.put("CLABS", row.get("CLABS"));
					e.put("CINSM", row.get("CINSM"));
					e.put("CSPEM", row.get("CSPEM"));
					e.put("CEINM", row.get("CEINM"));
					e.put("CHARG", "**工廠合計");
					results.add(e);
					total.remove(key_werks_matnr);
				}

				if (total.containsKey(key_werks_matnr_lgort)) {
					row = total.get(key_werks_matnr_lgort);
					e = new HashMap<String, Object>();
					e.put("WERKS", h.get("WERKS"));
					e.put("MATNR", h.get("MATNR"));
					e.put("MEINS", h.get("MEINS"));
					e.put("MATKL", h.get("MATKL"));
					e.put("MAKTX", h.get("MAKTX"));
					e.put("VMENG", h.get("VMENG"));
					e.put("LGORT", h.get("LGORT"));
					e.put("CLABS", row.get("CLABS"));
					e.put("CINSM", row.get("CINSM"));
					e.put("CSPEM", row.get("CSPEM"));
					e.put("CEINM", row.get("CEINM"));
					e.put("CHARG", "****儲位合計");
					results.add(e);
					total.remove(key_werks_matnr_lgort);
				}
			}
			results.add(h);

		}
*/
		req.setAttribute("list", list);

		render(req, resp, "/WEB-INF/pages/enquiry/ZIEB904.jsp");
	}

}
