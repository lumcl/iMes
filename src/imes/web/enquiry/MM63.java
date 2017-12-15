package imes.web.enquiry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;
import imes.vpojo.MM63VP;

@WebServlet("/MM63")
public class MM63 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/enquiry/MM63.jsp");
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
				+ getParam(req, "maktx_low") + getParam(req, "ekgrp_low") + getParam(req, "matnr_textarea")
				+ getParam(req, "matkl_textarea") + getParam(req, "charg_textarea") + getParam(req, "ekgrp_textarea");

		int agdaylow = toIntegerParam(req, "agday_low");
		int agdayhigh = toIntegerParam(req, "agday_high");

		if (agdayhigh == 0 && agdaylow > 0) {

			agdayhigh = agdaylow;

		} else if (agdayhigh == 0 && agdaylow == 0) {

			agdayhigh = 999999;
		}

		if (!jspParams.equals("")) {

			String sql = "SELECT MCHB.WERKS, " //
					+ "       MCHB.MATNR, " //
					+ "       MCHB.CHARG, " //
					+ "       MARA.MATKL, " //
					+ "       MAKT.MAKTX, " //
					+ "       MARA.MEINS, " //
					+ "       MARA.MTART, " //
					+ "       CASE WHEN VPRSV = 'V' THEN (VERPR / PEINH) ELSE (STPRS / PEINH) END MATCS, " //
					+ "       (MCHB.CLABS + MCHB.CUMLM + MCHB.CINSM + MCHB.CSPEM + MCHB.CRETM + MCHB.CEINM) MENGE, " //
					+ "       ROUND ( " //
					+ "          SYSDATE " //
					+ "          - TO_DATE ( " //
					+ "               NVL ( " //
					+ "                  (SELECT MIN (MKPF.BUDAT) " //
					+ "                     FROM SAPSR3.MSEG, SAPSR3.MKPF " //
					+ "                    WHERE     MSEG.MANDT = '168' " //
					+ "                          AND MSEG.BWART = '101' " //
					+ "                          AND MSEG.MATNR = MCHB.MATNR " //
					+ "                          AND MSEG.CHARG = MCHB.CHARG " //
					+ "                          AND MKPF.MANDT(+) = MSEG.MANDT " //
					+ "                          AND MKPF.MBLNR(+) = MSEG.MBLNR " //
					+ "                          AND MKPF.MJAHR(+) = MSEG.MJAHR), " //
					+ "                  MCHB.ERSDA), " //
					+ "               'YYYYMMDD'), " //
					+ "          0) " //
					+ "          AGDAY, " //
					+ "       MARC.EKGRP " //
					+ "  FROM SAPSR3.MCHB, " //
					+ "       SAPSR3.MARA, " //
					+ "       SAPSR3.MAKT, " //
					+ "       SAPSR3.MBEW, " //
					+ "       SAPSR3.MARC " //
					+ " WHERE     MCHB.MANDT = '168' " //
					+ "       AND (MCHB.CLABS + MCHB.CUMLM + MCHB.CINSM + MCHB.CSPEM + MCHB.CRETM + MCHB.CEINM) <> 0 " //
					+ "       AND MARA.MANDT(+) = MCHB.MANDT " //
					+ "       AND MARA.MATNR(+) = MCHB.MATNR " //
					+ "       AND MAKT.MANDT(+) = MCHB.MANDT " //
					+ "       AND MAKT.MATNR(+) = MCHB.MATNR " //
					+ "       AND MAKT.SPRAS(+) = 'M' " //
					+ "       AND MBEW.MANDT(+) = MCHB.MANDT " //
					+ "       AND MBEW.MATNR(+) = MCHB.MATNR " //
					+ "       AND MBEW.BWKEY(+) = MCHB.WERKS " //
					+ "       AND MBEW.BWTAR(+) = ' ' " //
					+ "       AND MARC.MANDT(+) = MCHB.MANDT " //
					+ "       AND MARC.MATNR(+) = MCHB.MATNR " //
					+ "       AND MARC.WERKS(+) = MCHB.WERKS " //
			;

			if (!getParam(req, "werks_low").equals("")) {
				sql += Helper.sqlParams(req, "MCHB.WERKS", "werks_low", "werks_high");
			}
			if (!getParam(req, "matnr_low").equals("")) {
				sql += Helper.sqlParams(req, "MCHB.MATNR", "matnr_low", "matnr_high");
			}
			if (!getParam(req, "ekgrp_low").equals("")) {
				sql += Helper.sqlParams(req, "MARC.EKGRP", "ekgrp_low", "ekgrp_high");
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
			if (!getParam(req, "ekgrp_textarea").equals("")) {
				sql += "         AND MARC.EKGRP IN (" + toSqlString(textAreaToList(req, "ekgrp_textarea")) + ") ";
			}
			if (!getParam(req, "charg_textarea").equals("")) {
				sql += "         AND MCHB.CHARG IN (" + toSqlString(textAreaToList(req, "charg_textarea")) + ") ";
			}

			// sql += "ORDER BY MCHB.WERKS, MCHB.MATNR";//

			Connection conn = getSapPrdConnection();

			PreparedStatement pstm = conn.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			conn.close();
		}

		String key;
		MM63VP e;
		double qty, amt;
		int agday;
		HashMap<String, MM63VP> map = new HashMap<String, MM63VP>();

		for (HashMap<String, Object> row : list) {
			agday = toInteger(row.get("AGDAY"));

			if (agday >= agdaylow && agday <= agdayhigh) {

				key = (String) row.get("WERKS") + (String) row.get("MATNR");
				if (map.containsKey(key)) {

					e = map.get(key);

				} else {

					e = new MM63VP();
					e.setWerks((String) row.get("WERKS"));
					e.setMatnr((String) row.get("MATNR"));
					e.setMatkl((String) row.get("MATKL"));
					e.setMaktx((String) row.get("MAKTX"));
					e.setMeins((String) row.get("MEINS"));
					e.setMtart((String) row.get("MTART"));
					e.setEkgrp((String) row.get("EKGRP"));
					e.setMatcs(toDouble(row.get("MATCS")));
				}

				qty = toDouble(row.get("MENGE"));
				amt = qty * e.getMatcs();

				e.setMenge(e.getMenge() + qty);
				e.setMtamt(e.getMtamt() + amt);

				if (agday <= 7) {
					e.setQty007(e.getQty007() + qty);
					e.setAmt007(e.getAmt007() + amt);
				} else if (agday <= 15) {
					e.setQty015(e.getQty015() + qty);
					e.setAmt015(e.getAmt015() + amt);
				} else if (agday <= 30) {
					e.setQty030(e.getQty030() + qty);
					e.setAmt030(e.getAmt030() + amt);
				} else if (agday <= 60) {
					e.setQty060(e.getQty060() + qty);
					e.setAmt060(e.getAmt060() + amt);
				} else if (agday <= 90) {
					e.setQty090(e.getQty090() + qty);
					e.setAmt090(e.getAmt090() + amt);
				} else if (agday <= 90) {
					e.setQty090(e.getQty090() + qty);
					e.setAmt090(e.getAmt090() + amt);
				} else if (agday <= 180) {
					e.setQty180(e.getQty180() + qty);
					e.setAmt180(e.getAmt180() + amt);
				} else if (agday <= 360) {
					e.setQty360(e.getQty360() + qty);
					e.setAmt360(e.getAmt360() + amt);
				} else if (agday <= 999) {
					e.setQty999(e.getQty999() + qty);
					e.setAmt999(e.getAmt999() + amt);
				} else {
					e.setQtymax(e.getQtymax() + qty);
					e.setAmtmax(e.getAmtmax() + amt);
				}

				map.put(key, e);
			}
		}

		List<MM63VP> results = new ArrayList<MM63VP>();

		for (String str : map.keySet()) {
			e = map.get(str);
			results.add(e);
		}

		Collections.sort(results, new Comparator<MM63VP>() {
			public int compare(MM63VP e0, MM63VP e1) {
				return (e0.getWerks() + e0.getMatnr()).compareTo(e1.getWerks() + e1.getMatnr());
			}
		});

		req.setAttribute("list", results);

		render(req, resp, "/WEB-INF/pages/enquiry/MM63.jsp");
	}

}
