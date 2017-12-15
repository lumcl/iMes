package imes.web.enquiry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import imes.core.Helper;
import imes.core.HttpController;

@WebServlet("/SD01")
public class SD01 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req,resp,"/WEB-INF/pages/enquiry/SD01.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
		try {

			ActionPost(req, resp);
			render(req, resp,"/WEB-INF/pages/enquiry/SD01.jsp");

		} catch (Exception e) {

			PrintError(resp,e);
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String jspParams = getParam(req,"vtweg_low") + getParam(req,"mbdat_low") + getParam(req,"edatu_low") + getParam(req,"vkgrp_textarea")
				+ getParam(req,"kunnr_textarea") + getParam(req,"matnr_textarea");

		if (jspParams.isEmpty()) {
			return;
		}

		String sql = "WITH T001 " //
				+ "     AS ( (SELECT VBBE.MBDAT, " //
				+ "                  CASE WHEN LIPS.BWART IN ('982', '992') THEN EKPO.BEDNR ELSE LIPS.VGBEL END VBELN, " //
				+ "                  CASE WHEN LIPS.BWART IN ('982', '992') THEN LPAD (EKPO.AFNAM, 6, '0') ELSE LIPS.VGPOS END POSNR, " //
				+ "                  VBBE.ETENR, " //
				+ "                  VBBE.MATNR, " //
				+ "                  VBBE.WERKS, " //
				+ "                  VBBE.LGORT, " //
				+ "                  VBBE.VBTYP, " //
				+ "                  VBBE.OMENG, " //
				+ "                  VBBE.VMENG, " //
				+ "                  VBBE.MEINS, " //
				+ "                  VBBE.VBELN DLONR, " //
				+ "                  VBBE.POSNR DLPOS, " //
				+ "                  LIPS.CHARG, " //
				+ "                  LIPS.BWART, " //
				+ "                  LIKP.LFDAT EDATU " //
				+ "             FROM SAPSR3.VBBE, " //
				+ "                  SAPSR3.LIPS, " //
				+ "                  SAPSR3.EKPO, " //
				+ "                  SAPSR3.LIKP " //
				+ "            WHERE     VBBE.MANDT = '168' " //
				+ "                  AND VBBE.OMENG <> 0 ";//
		
		if (req.getParameter("sto_checkbox") == null) {
			sql += "       AND LIPS.BWART <> '982' ";//
		}

		sql += "                  AND LIPS.MANDT = VBBE.MANDT " //
				+ "                  AND LIPS.VBELN = VBBE.VBELN " //
				+ "                  AND LIPS.POSNR = VBBE.POSNR " //
				+ "                  AND LIKP.MANDT = VBBE.MANDT " //
				+ "                  AND LIKP.VBELN = VBBE.VBELN " //
				+ "                  AND EKPO.MANDT(+) = LIPS.MANDT " //
				+ "                  AND EKPO.EBELN(+) = LIPS.VGBEL " //
				+ "                  AND EKPO.EBELP(+) = SUBSTR (LIPS.VGPOS, -5) " //
				+ "                  AND VBBE.VBTYP IN ('T', 'J')) " //
				+ "         UNION ALL " //
				+ "         (SELECT VBBE.MBDAT, " //
				+ "                 VBELN, " //
				+ "                 POSNR, " //
				+ "                 VBBE.ETENR, " //
				+ "                 VBBE.MATNR, " //
				+ "                 VBBE.WERKS, " //
				+ "                 VBBE.LGORT, " //
				+ "                 VBBE.VBTYP, " //
				+ "                 VBBE.OMENG, " //
				+ "                 VBBE.VMENG, " //
				+ "                 VBBE.MEINS, " //
				+ "                 ' ' DLONR, " //
				+ "                 ' ' DLPOS, " //
				+ "                 ' ' CHARG, " //
				+ "                 ' ' BWART, " //
				+ "                 (SELECT EDATU " //
				+ "                    FROM SAPSR3.VBEP " //
				+ "                   WHERE     VBEP.MANDT = VBBE.MANDT " //
				+ "                         AND VBEP.VBELN = VBBE.VBELN " //
				+ "                         AND VBEP.POSNR = VBBE.POSNR " //
				+ "                         AND VBEP.ETENR = VBBE.ETENR) " //
				+ "                    EDATU " //
				+ "            FROM SAPSR3.VBBE " //
				+ "           WHERE VBBE.MANDT = '168' AND VBBE.VBTYP NOT IN ('T', 'J') AND VBBE.OMENG <> 0)) " //
				+ "SELECT T001.MBDAT, " //
				+ "       T001.VBELN, " //
				+ "       T001.POSNR, " //
				+ "       T001.ETENR, " //
				+ "       T001.MATNR, " //
				+ "       MARA.MATKL, " //
				+ "       MAKT.MAKTX, " //
				+ "       T001.WERKS, " //
				+ "       T001.LGORT, " //
				+ "       T001.VBTYP, " //
				+ "       T001.OMENG, " //
				+ "       T001.VMENG, " //
				+ "       T001.MEINS, " //
				+ "       T001.DLONR, " //
				+ "       T001.DLPOS, " //
				+ "       T001.CHARG, " //
				+ "       T001.BWART, " //
				+ "       T001.EDATU, " //
				+ "       VBAK.KUNNR, " //
				+ "       VBAK.VTWEG, " //
				+ "       VBAK.VKGRP, " //
				+ "       KNA1.NAME1, " //
				+ "       KNA1.SORTL, " //
				+ "       VBAK.WAERK, " //
				+ "       (VBAP.NETPR / VBAP.KPEIN) NETPR, " //
				+ "       (SELECT SUM (DECODE (MARD.WERKS, '481A', LABST + INSME,0)) || ',' || SUM (DECODE (MARD.WERKS, '482A', LABST + INSME,0)) || ',' || SUM (DECODE (MARD.WERKS, '101A', LABST + INSME,0)) || ',' || SUM (DECODE (MARD.WERKS, '381A', LABST + INSME,0)) || ',' || SUM (DECODE (MARD.WERKS, '281A', LABST + INSME,0)) || ',' || SUM (DECODE (MARD.WERKS, '701A', LABST + INSME,0)) || ',' || SUM (DECODE (MARD.WERKS, '382A', LABST + INSME,0)) || ',' || SUM (DECODE (MARD.WERKS, '921A', LABST + INSME,0)) " //
				+ "          FROM SAPSR3.MARD " //
				+ "         WHERE MARD.MANDT = '168' AND MATNR = T001.MATNR) " //
				+ "          MARDD, " //
				+ "              NVL (  " //
				+ "       (SELECT CASE WHEN VBAK.WAERK IN ('TWD', 'JPY') THEN UKURS * 100 ELSE UKURS END UKURS  " //
				+ "          FROM SAPSR3.TCURR  " //
				+ "         WHERE     MANDT = '168'  " //
				+ "               AND KURST = 'C'  " //
				+ "               AND TCURR = 'USD'  " //
				+ "               AND FCURR = VBAK.WAERK  " //
				+ "               AND (99999999 - GDATU) <= T001.EDATU " //
				+ "               AND ROWNUM = 1),  " //
				+ "       1)  " //
				+ "       USDCR, " //
				+ "       (  SELECT  " //
				+ "         MAX (DECODE (KNVP.PARVW, 'Y1', CONCAT (NCHMC, VNAMC), ' '))|| ',' || " //
				+ "         MAX (DECODE (KNVP.PARVW, 'Y2', CONCAT (NCHMC, VNAMC), ' '))|| ',' || " //
				+ "         MAX (DECODE (KNVP.PARVW, 'Y3', CONCAT (NCHMC, VNAMC), ' '))|| ',' || " //
				+ "         MAX (DECODE (KNVP.PARVW, 'YB', CONCAT (NCHMC, VNAMC), ' '))|| ',' || " //
				+ "         MAX (DECODE (KNVP.PARVW, 'YC', CONCAT (NCHMC, VNAMC), ' ')) " //
				+ "    FROM SAPSR3.PA0002, SAPSR3.KNVP " //
				+ "   WHERE     SUBSTR (KNVP.PARVW, 1, 1) = 'Y' " //
				+ "         AND TO_CHAR (SYSDATE, 'YYYYMMDD') BETWEEN PA0002.BEGDA AND PA0002.ENDDA " //
				+ "         AND PA0002.MANDT = KNVP.MANDT " //
				+ "         AND PA0002.PERNR = KNVP.PERNR " //
				+ "         AND KNVP.MANDT = '168' " //
				+ "         AND KNVP.KUNNR = VBAK.KUNNR " //
				+ "       )KNVPD " //
				+ "  FROM T001, " //
				+ "       SAPSR3.VBAK, " //
				+ "       SAPSR3.VBAP, " //
				+ "       SAPSR3.MARA, " //
				+ "       SAPSR3.MAKT, " //
				+ "       SAPSR3.KNA1 " //
				+ " WHERE     MARA.MANDT = '168' " //
				+ "       AND MARA.MATNR = T001.MATNR " //
				+ "       AND MAKT.MANDT = '168' " //
				+ "       AND MAKT.MATNR = T001.MATNR " //
				+ "       AND MAKT.SPRAS = 'M' " //
				+ "       AND VBAK.MANDT(+) = '168' " //
				+ "       AND VBAK.VBELN(+) = T001.VBELN " //
				+ "       AND VBAP.MANDT(+) = '168' " //
				+ "       AND VBAP.VBELN(+) = T001.VBELN " //
				+ "       AND VBAP.POSNR(+) = T001.POSNR " //
				+ "       AND KNA1.MANDT(+) = '168' " //
				+ "       AND KNA1.KUNNR(+) = VBAK.KUNNR "; //

		if (!getParam(req,"vtweg_low").equals("")) {
			sql += Helper.sqlParams(req, "VBAK.VTWEG", "vtweg_low", "vtweg_high");
		}
		if (!getParam(req,"mbdat_low").equals("")) {
			sql += Helper.sqlParams(req, "T001.MBDAT", "mbdat_low", "mbdat_high");
		}
		if (!getParam(req,"edatu_low").equals("")) {
			sql += Helper.sqlParams(req, "T001.EDATU", "edatu_low", "edatu_high");
		}
		if (!getParam(req,"matnr_textarea").equals("")) {
			sql += "         AND T001.MATNR IN (" + toSqlString(textAreaToList(req,"matnr_textarea")) + ") ";
		}
		if (!getParam(req,"vkgrp_textarea").equals("")) {
			sql += "         AND VBAK.VKGRP IN (" + toSqlString(textAreaToList(req,"vkgrp_textarea")) + ") ";
		}
		if (!getParam(req,"kunnr_textarea").equals("")) {
			sql += "         AND VBAK.KUNNR IN (" + toSqlString(textAreaToList(req,"kunnr_textarea")) + ") ";
		}

		sql += " ORDER BY VTWEG,EDATU,KUNNR,T001.VBELN,T001.POSNR "; //

		try {
			Connection conn = getSapPrdConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			list = Helper.resultSetToArrayList(pstm.executeQuery());
			pstm.close();
			conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		String buf[];
		for (HashMap<String, Object> e : list) {

			if (e.get("MARDD") != null) {
				buf = e.get("MARDD").toString().split(",");
				if (buf.length > 1) {
					e.put("W481A", buf[0]);
					e.put("W482A", buf[1]);
					e.put("W101A", buf[2]);
					e.put("W381A", buf[3]);
					e.put("W281A", buf[4]);
					e.put("W701A", buf[5]);
					e.put("W382A", buf[6]);
					e.put("W921A", buf[7]);

				}
			}

			if (e.get("KNVPD") != null) {
				buf = e.get("KNVPD").toString().split(",");
				if (buf.length > 1) {
					e.put("Y1", buf[0]);
					e.put("Y2", buf[1]);
					e.put("Y3", buf[2]);
					e.put("YB", buf[3]);
					e.put("YC", buf[4]);
				}
			}
		}

		req.setAttribute("list", list);
	}
}
