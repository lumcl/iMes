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

@WebServlet("/ME03")
public class ME03 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/enquiry/ME03.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);

		try {

			ActionPost(req, resp);
			render(req, resp, "/WEB-INF/pages/enquiry/ME03.jsp");

		} catch (Exception ex) {

			PrintError(resp, ex);
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String jspParams = getParam(req, "werks_low") + getParam(req, "matnr_low") + getParam(req, "matkl_low")
				+ getParam(req, "maktx_low") + getParam(req, "ekgrp_low") + getParam(req, "matnr_textarea")
				+ getParam(req, "matkl_textarea") + getParam(req, "ekgrp_textarea") + getParam(req, "lifnr_low")
				+ getParam(req, "lifnr_textarea");

		if (jspParams.equals("")) {
			return;
		}

		String sql = "  SELECT EKGRP, MATKL, MATNR, MAKTX, WERKS, " //
				+ "         LIFNR, SORTL, SPERQ, EKORG, VDATU, " //
				+ "         BDATU, AUTET, INFNR, EWERK, WAERS, " //
				+ "         NETPR, PEINH, PRDAT, RMBCR, USDCR, " //
				+ "         ROUND ( ( (RMBCR * NETPR) / PEINH), 6) RMBPR, ROUND ( ( (USDCR * NETPR) / PEINH), 6) USDPR, ZEINR, PLIFZ, BSTRF, " //
				+ "         DISGR, FREI_DAT, FREI_MNG, BEST_MG, SPERRGRUND, " //
				+ "         SPERRFKT, KURZTEXT, NAME1 " //
				+ "    FROM (SELECT MARC.EKGRP, " //
				+ "                 MARA.MATKL, " //
				+ "                 EORD.MATNR, " //
				+ "                 MAKT.MAKTX, " //
				+ "                 EORD.WERKS, " //
				+ "                 EORD.FLIFN, " //
				+ "                 EORD.NOTKZ, " //
				+ "                 EORD.LIFNR, " //
				+ "                 LFA1.SORTL, " //
				+ "                 LFA1.SPERQ, " //
				+ "                 EORD.EKORG, " //
				+ "                 EORD.VDATU, " //
				+ "                 EORD.BDATU, " //
				+ "                 EORD.AUTET, " //
				+ "                 EINA.INFNR, " //
				+ "                 EINE.WERKS EWERK, " //
				+ "                 EINE.WAERS, " //
				+ "                 EINE.NETPR, " //
				+ "                 EINE.PEINH, " //
				+ "                 EINE.PRDAT, " //
				+ "                 NVL ( " //
				+ "                    (SELECT CASE WHEN EINE.WAERS IN ('TWD', 'JPY') THEN UKURS * 100 ELSE UKURS END UKURS " //
				+ "                       FROM SAPSR3.TCURR " //
				+ "                      WHERE     MANDT = '168' " //
				+ "                            AND KURST = 'C' " //
				+ "                            AND TCURR = 'RMB' " //
				+ "                            AND FCURR = EINE.WAERS " //
				+ "                            AND (99999999 - GDATU) <= TO_CHAR (SYSDATE, 'YYYYMMDD') " //
				+ "                            AND ROWNUM = 1), " //
				+ "                    1) " //
				+ "                    RMBCR, " //
				+ "                 NVL ( " //
				+ "                    (SELECT CASE WHEN EINE.WAERS IN ('TWD', 'JPY') THEN UKURS * 100 ELSE UKURS END UKURS " //
				+ "                       FROM SAPSR3.TCURR " //
				+ "                      WHERE     MANDT = '168' " //
				+ "                            AND KURST = 'C' " //
				+ "                            AND TCURR = 'USD' " //
				+ "                            AND FCURR = EINE.WAERS " //
				+ "                            AND (99999999 - GDATU) <= TO_CHAR (SYSDATE, 'YYYYMMDD') " //
				+ "                            AND ROWNUM = 1), " //
				+ "                    1) " //
				+ "                    USDCR, " //
				+ "                 MARA.ZEINR, " //
				+ "                 MARC.PLIFZ, " //
				+ "                 MARC.BSTRF, " //
				+ "                 MARC.DISGR, " //
				+ "                 QINF.FREI_DAT, " //
				+ "                 QINF.FREI_MNG, " //
				+ "                 QINF.BEST_MG, " //
				+ "                 QINF.SPERRGRUND, " //
				+ "                 QINF.SPERRFKT, " //
				+ "                 TQ04S.KURZTEXT, " //
				+ "                 LFA1.NAME1 " //
				+ "            FROM SAPSR3.EORD, " //
				+ "                 SAPSR3.MARA, " //
				+ "                 SAPSR3.MARC, " //
				+ "                 SAPSR3.MAKT, " //
				+ "                 SAPSR3.EINA, " //
				+ "                 SAPSR3.EINE, " //
				+ "                 SAPSR3.LFA1, " //
				+ "                 SAPSR3.QINF, " //
				+ "                 SAPSR3.TQ04S " //
				+ "           WHERE     EORD.MANDT = '168' " //
				+ "                 AND SUBSTR (EORD.LIFNR, 1, 2) NOT BETWEEN 'L1' AND 'L9' " //
				+ "                 AND TO_CHAR (SYSDATE, 'YYYYMMDD') BETWEEN EORD.VDATU AND EORD.BDATU " //
				+ "                 AND EORD.NOTKZ = ' ' " //
				+ "                 AND MARA.MANDT = EORD.MANDT " //
				+ "                 AND MARA.MATNR = EORD.MATNR " //
				+ "                 AND MAKT.MANDT = EORD.MANDT " //
				+ "                 AND MAKT.MATNR = EORD.MATNR " //
				+ "                 AND MAKT.SPRAS = 'M' " //
				+ "                 AND LFA1.MANDT = EORD.MANDT " //
				+ "                 AND LFA1.LIFNR = EORD.LIFNR " //
				+ "                 AND MARC.MANDT(+) = EORD.MANDT " //
				+ "                 AND MARC.WERKS(+) = EORD.WERKS " //
				+ "                 AND MARC.MATNR(+) = EORD.MATNR " //
				+ "                 AND EINA.MANDT(+) = EORD.MANDT " //
				+ "                 AND EINA.LIFNR(+) = EORD.LIFNR " //
				+ "                 AND EINA.MATNR(+) = EORD.MATNR " //
				+ "                 AND EINA.LOEKZ(+) = ' ' " //
				+ "                 AND EINE.MANDT(+) = EINA.MANDT " //
				+ "                 AND EINE.INFNR(+) = EINA.INFNR " //
				+ "                 AND EINE.LOEKZ(+) = ' ' " //
				+ "                 AND EINE.PRDAT(+) >= TO_CHAR (SYSDATE, 'YYYYMMDD') " //
				+ "                 AND QINF.MANDT(+) = EORD.MANDT " //
				+ "                 AND QINF.MATNR(+) = EORD.MATNR " //
				+ "                 AND QINF.LIEFERANT(+) = EORD.LIFNR " //
				+ "                 AND QINF.WERK(+) = EORD.WERKS " //
				+ "                 AND QINF.LOEKZ(+) = ' ' " //
				+ "                 AND QINF.REVLV(+) = ' ' " //
				+ "                 AND TQ04S.MANDT(+) = QINF.MANDT " //
				+ "                 AND TQ04S.SPERRFKT(+) = QINF.SPERRFKT " //
				+ "                 AND TQ04S.SPRACHE(+) = 'M' ";//

		if (!getParam(req, "werks_low").equals("")) {
			sql += Helper.sqlParams(req, "EORD.WERKS", "werks_low", "werks_high");
		}
		if (!getParam(req, "matnr_low").equals("")) {
			sql += Helper.sqlParams(req, "EORD.MATNR", "matnr_low", "matnr_high");
		}
		if (!getParam(req, "lifnr_low").equals("")) {
			sql += Helper.sqlParams(req, "EORD.LIFNR", "lifnr_low", "lifnr_high");
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
		sql += "                 ) " //
				+ "   WHERE (WERKS = EWERK OR EWERK IS NULL) " //
				+ "ORDER BY EKGRP, MATKL, MATNR, WERKS, AUTET DESC "; //

		Connection conn = getSapPrdConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		list = Helper.resultSetToArrayList(pstm.executeQuery());
		pstm.close();
		conn.close();

		String key;
		HashMap<String, Integer> counter = new HashMap<String, Integer>();

		for (HashMap<String, Object> h : list) {
			key = h.get("WERKS").toString() + "_" + h.get("MATNR").toString();
			if (counter.containsKey(key)) {
				counter.put(key, counter.get(key) + 1);
			} else {
				counter.put(key, 1);
			}
		}

		for (HashMap<String, Object> h : list) {
			key = h.get("WERKS").toString() + "_" + h.get("MATNR").toString();
			if (counter.containsKey(key)) {
				h.put("COUNT", counter.get(key));
			}
		}

		counter.clear();
		counter = null;

		req.setAttribute("list", list);
	}
}
