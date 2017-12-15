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

@WebServlet("/MMBE")
public class MMBE extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/enquiry/MMBE.jsp");
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

			String sql = "  SELECT MCHB.WERKS, " //
					+ "         MCHB.MATNR, " //
					+ "         MCHB.LGORT, " //
					+ "         MCHB.CHARG, " //
					+ "         MCHB.CLABS, " //
					+ "         MCHB.CUMLM, " //
					+ "         MCHB.CINSM, " //
					+ "         MCHB.CSPEM, " //
					+ "         MCHB.CEINM, " //
					+ "         MCHB.CRETM, " //
					+ "         MARA.MEINS, " //
					+ "         MARA.MATKL, " //
					+ "         MAKT.MAKTX, " //
					+ "         (SELECT SUM (VMENG) " //
					+ "            FROM SAPSR3.RESB " //
					+ "           WHERE MANDT = '168' AND WERKS = MCHB.WERKS AND MATNR = MCHB.MATNR AND XLOEK = ' ') " //
					+ "            VMENG, " //
					+ "         (SELECT MKPF.BUDAT || ',' || LFA1.SORTL || ',' || MSEG.LIFNR || ',' || MSEG.EBELN || ',' || MSEG.EBELP || ',' || MSEG.AUFNR || ',' || MSEG.WERKS " //
					+ "            FROM SAPSR3.MSEG, SAPSR3.MKPF, SAPSR3.LFA1 " //
					+ "           WHERE     MSEG.MANDT = '168' " //
					+ "                 AND MSEG.BWART = '101' " //
					+ "                 AND MSEG.MATNR = MCHB.MATNR " //
					+ "                 AND MSEG.CHARG = MCHB.CHARG " //
					+ "                 AND MSEG.INSMK = 'X' " //
					+ "                 AND MKPF.MANDT(+) = MSEG.MANDT " //
					+ "                 AND MKPF.MBLNR(+) = MSEG.MBLNR " //
					+ "                 AND MKPF.MJAHR(+) = MSEG.MJAHR " //
					+ "                 AND LFA1.MANDT(+) = MSEG.MANDT " //
					+ "                 AND LFA1.LIFNR(+) = MSEG.LIFNR " //
					+ "                 AND ROWNUM = 1) " //
					+ "            MSEGD " //
					+ "    FROM SAPSR3.MCHB, SAPSR3.MARA,SAPSR3.MAKT " //
					+ "   WHERE     MARA.MANDT = '168' " //
					+ "         AND MCHB.MANDT = MARA.MANDT " //
					+ "         AND MCHB.MATNR = MARA.MATNR " //
					+ "         AND (MCHB.CLABS + MCHB.CUMLM + MCHB.CINSM + MCHB.CSPEM + MCHB.CRETM + MCHB.CEINM) <> 0 " //
					+ "         AND MAKT.MANDT = MARA.MANDT " //
					+ "         AND MAKT.MATNR = MARA.MATNR " //
					+ "         AND MAKT.SPRAS = 'M' "; //

			if (!getParam(req, "werks_low").equals("")) {
				sql += Helper.sqlParams(req, "MCHB.WERKS", "werks_low", "werks_high");
			}
			if (!getParam(req, "matnr_low").equals("")) {
				sql += Helper.sqlParams(req, "MCHB.MATNR", "matnr_low", "matnr_high");
			}
			if (!getParam(req, "lgort_low").equals("")) {
				sql += Helper.sqlParams(req, "MCHB.LGORT", "lgort_low", "lgort_high");
			}
			if (!getParam(req, "matkl_low").equals("")) {
				sql += Helper.sqlParams(req, "MARA.MATKL", "matkl_low", "matkl_high");
			}
			if (!getParam(req, "maktx_low").equals("")) {
				sql += " AND MAKT.MAKTX LIKE '%" + getParam(req, "maktx_low") + "%'";
			}
			if (!getParam(req, "matnr_textarea").equals("")) {
				sql += "         AND MCHB.MATNR IN (" + toSqlString(textAreaToList(req, "matnr_textarea")) + ") ";
			}
			if (!getParam(req, "matkl_textarea").equals("")) {
				sql += "         AND MARA.MATKL IN (" + toSqlString(textAreaToList(req, "matkl_textarea")) + ") ";
			}

			sql += "ORDER BY MCHB.WERKS, MCHB.MATNR, MCHB.LGORT, MCHB.CHARG ";//

			Connection conn = getSapPrdConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			conn.close();
		}

		String[] buf;
		String key_werks_matnr;
		String key_werks_matnr_lgort;

		HashMap<String, HashMap<String, Double>> total = new HashMap<String, HashMap<String, Double>>();
		HashMap<String, Double> row;

		for (HashMap<String, Object> h : list) {
			if (h.get("MSEGD") != null) {
				buf = h.get("MSEGD").toString().split(",");
				if (buf.length > 1) {
					h.put("BUDAT", buf[0]);
					h.put("SORTL", buf[1]);
					h.put("LIFNR", buf[2]);
					h.put("EBELN", buf[3]);
					h.put("EBELP", buf[4]);
					h.put("AUFNR", buf[5]);
					h.put("RWERK", buf[6]);
				}
			}

			key_werks_matnr = h.get("WERKS").toString() + h.get("MATNR").toString();
			key_werks_matnr_lgort = key_werks_matnr + h.get("LGORT").toString();

			if (total.containsKey(key_werks_matnr)) {
				row = total.get(key_werks_matnr);
				row.put("CLABS", row.get("CLABS") + Double.parseDouble(h.get("CLABS").toString()));
				row.put("CINSM", row.get("CINSM") + Double.parseDouble(h.get("CINSM").toString()));
				row.put("CSPEM", row.get("CSPEM") + Double.parseDouble(h.get("CSPEM").toString()));
				row.put("CEINM", row.get("CEINM") + Double.parseDouble(h.get("CEINM").toString()));
				total.put(key_werks_matnr, row);
			} else {
				row = new HashMap<String, Double>();
				row.put("CLABS", Double.parseDouble(h.get("CLABS").toString()));
				row.put("CINSM", Double.parseDouble(h.get("CINSM").toString()));
				row.put("CSPEM", Double.parseDouble(h.get("CSPEM").toString()));
				row.put("CEINM", Double.parseDouble(h.get("CEINM").toString()));
				total.put(key_werks_matnr, row);
			}

			if (total.containsKey(key_werks_matnr_lgort)) {
				row = total.get(key_werks_matnr_lgort);
				row.put("CLABS", row.get("CLABS") + Double.parseDouble(h.get("CLABS").toString()));
				row.put("CINSM", row.get("CINSM") + Double.parseDouble(h.get("CINSM").toString()));
				row.put("CSPEM", row.get("CSPEM") + Double.parseDouble(h.get("CSPEM").toString()));
				row.put("CEINM", row.get("CEINM") + Double.parseDouble(h.get("CEINM").toString()));
				total.put(key_werks_matnr_lgort, row);
			} else {
				row = new HashMap<String, Double>();
				row.put("CLABS", Double.parseDouble(h.get("CLABS").toString()));
				row.put("CINSM", Double.parseDouble(h.get("CINSM").toString()));
				row.put("CSPEM", Double.parseDouble(h.get("CSPEM").toString()));
				row.put("CEINM", Double.parseDouble(h.get("CEINM").toString()));
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

		req.setAttribute("list", results);

		render(req, resp, "/WEB-INF/pages/enquiry/MMBE.jsp");
	}

}
