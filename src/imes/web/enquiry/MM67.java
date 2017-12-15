package imes.web.enquiry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import imes.core.Helper;
import imes.core.HttpController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MM67")
public class MM67 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp, "/WEB-INF/pages/enquiry/MM67.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		try {
			ActionPost(req, resp);
			render(req, resp, "/WEB-INF/pages/enquiry/MM67.jsp");
		} catch (Exception ex) {
			PrintError(resp, ex);
		}

	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String jspParams = getParam(req, "werks_low") + getParam(req, "matnr_low") + getParam(req, "matkl_low")
				+ getParam(req, "lifnr_low") + getParam(req, "maktx_low") + getParam(req, "ekgrp_low")
				+ getParam(req, "matnr_textarea") + getParam(req, "matkl_textarea") + getParam(req, "charg_textarea")
				+ getParam(req, "ekgrp_textarea") + getParam(req, "budat_low");

		if (!jspParams.equals("")) {

			String sql = "SELECT EKBE.BWART, " //
					+ "       EKBE.EBELN, " //
					+ "       EKBE.EBELP, " //
					+ "       EKBE.BELNR, " //
					+ "       EKBE.BUZEI, " //
					+ "       EKBE.BUDAT, " //
					+ "       (EKPO.NETPR / EKPO.PEINH) NETPR, " //
					+ "       EKBE.MENGE, " //
					+ "       EKBE.WRBTR, " //
					+ "       EKBE.WAERS, " //
					+ "       EKBE.SHKZG, " //
					+ "       EKBE.MATNR, " //
					+ "       ZMARA.MATKL, " //
					+ "       ZMARA.MAKTX, " //
					+ "       ZMARA.MEINS, " //
					+ "       ZMARA.WGBEZ, " //
					+ "       ZMARA.HRKTX, " //
					+ "       EKBE.WERKS, " //
					+ "       EKKO.LIFNR, " //
					+ "       EKKO.BSART, " //
					+ "       NVL ( " //
					+ "          (SELECT CASE WHEN EKBE.WAERS IN ('JPY', 'TWD') THEN 100 * UKURS ELSE UKURS END " //
					+ "             FROM SAPSR3.TCURR@SAPP " //
					+ "            WHERE     MANDT = '168' " //
					+ "                  AND KURST = 'M' " //
					+ "                  AND TCURR = 'USD' " //
					+ "                  AND FCURR = EKBE.WAERS " //
					+ "                  AND (99999999 - GDATU) <= EKBE.BUDAT " //
					+ "                  AND ROWNUM = 1), " //
					+ "          1) " //
					+ "          MRATE, " //
					+ "       CASE WHEN EKBE.WERKS IN ('101A', '481A', '482A') THEN 'DT' ELSE 'TX' END VTWEG, " //
					+ "       CASE WHEN EKBE.WAERS = 'RMB' THEN 'RMB' ELSE 'CUR' END CTYPE, " //
					+ "       LFA1.NAME1, " //
					+ "       LFA1.SORTL " //
					+ "  FROM SAPSR3.EKBE@SAPP, " //
					+ "       SAPSR3.EKKO@SAPP, " //
					+ "       SAPSR3.EKPO@SAPP, " //
					+ "       SAPSR3.LFA1@SAPP, " //
					+ "       SAPCO.ZMARA " //
					+ " WHERE     EKBE.MANDT = '168' " //
					+ "       AND EKBE.BWART IN ('101', '102') " //
					+ "       AND EKKO.MANDT = EKBE.MANDT " //
					+ "       AND EKKO.EBELN = EKBE.EBELN " //
					+ "       AND EKKO.BSART <> 'Z006' " //
					+ "       AND EKPO.MANDT = EKBE.MANDT " //
					+ "       AND EKPO.EBELN = EKBE.EBELN " //
					+ "       AND EKPO.EBELP = EKBE.EBELP " //
					+ "       AND LFA1.MANDT = '168' " //
					+ "       AND LFA1.LIFNR = EKKO.LIFNR " //
					+ "       AND ZMARA.MANDT = '168' " //
					+ "       AND ZMARA.MATNR = EKBE.MATNR ";//

			if (!getParam(req, "werks_low").equals("")) {
				sql += Helper.sqlParams(req, "EKBE.WERKS", "werks_low", "werks_high");
			}
			if (!getParam(req, "budat_low").equals("")) {
				if (getParam(req, "budat_low").length() > 4) {
					sql += " AND GJAHR >= '" + getParam(req, "budat_low").substring(0, 4) + "'";
				}
				sql += Helper.sqlParams(req, "EKBE.BUDAT", "budat_low", "budat_high");
			}
			if (!getParam(req, "matnr_low").equals("")) {
				sql += Helper.sqlParams(req, "EKBE.MATNR", "matnr_low", "matnr_high");
			}
			if (!getParam(req, "matkl_low").equals("")) {
				sql += Helper.sqlParams(req, "EKPO.MATKL", "matkl_low", "matkl_high");
			}
			if (!getParam(req, "maktx_low").equals("")) {
				sql += " AND ZMARA.MAKTX LIKE '%" + getParam(req, "maktx_low") + "%'";
			}
			if (!getParam(req, "matnr_textarea").equals("")) {
				sql += " AND EKBE.MATNR IN (" + toSqlString(textAreaToList(req, "matnr_textarea")) + ") ";
			}
			if (!getParam(req, "matkl_textarea").equals("")) {
				sql += " AND EKPO.MATKL IN (" + toSqlString(textAreaToList(req, "matkl_textarea")) + ") ";
			}
			if (!getParam(req, "lifnr_low").equals("")) {
				sql += Helper.sqlParams(req, "EKKO.LIFNR", "lifnr_low", "lifnr_high");
			}
			sql += " ORDER BY EKBE.WERKS,EKBE.MATNR,EKBE.BUDAT ";

			Connection conn = getSapCoConnection();

			PreparedStatement pstm = conn.prepareStatement(sql);

			list = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();
		}

		req.setAttribute("list", list);

	}

}
