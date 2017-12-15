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

@WebServlet("/PP03")
public class PP03 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp,"/WEB-INF/pages/enquiry/PP03.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);

		try {

			ActionPost(req, resp);
			render(req, resp,"/WEB-INF/pages/enquiry/PP03.jsp");

		} catch (Exception e) {

			PrintError(resp,e);
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, Object>> plafs = new ArrayList<HashMap<String, Object>>();
		
		String jspParams = getParam(req,"werks_low") + getParam(req,"dispo_low") + getParam(req,"gstrp_low") + getParam(req,"gltrp_low")
				+ getParam(req,"pmatnr_low") + getParam(req,"aufnr_low") + getParam(req,"dispo_textarea") + getParam(req,"cmatnr_textarea")
				+ getParam(req,"aufnr_textarea") + getParam(req,"cmatnr_low") + getParam(req,"pmatnr_textarea");

		if (jspParams.isEmpty()) {
			return;
		}

		String sql= "SELECT AFPO.PWERK, AFKO.AUFNR, AFPO.MATNR PMATNR, PMAKT.MAKTX PMAKTX, RESB.VORNR, RESB.POSNR,  " //
				+ "       RESB.MATNR, MAKT.MAKTX, RESB.MATKL, RESB.WERKS, RESB.LGORT, MARC.EKGRP, " //
				+ "       RESB.BDMNG, RESB.VMENG, RESB.ENMNG, RESB.MEINS, RESB.BDTER, AFKO.GSTRP, " //
				+ "       RESB.POTX1, RESB.BAUGR, RESB.AENNR, MARA.ZEINR, RESB.DUMPS, AFKO.GLTRP," //
				+ "       RESB.KZEAR, RESB.XLOEK, RESB.XFEHL, RESB.ALPOS, RESB.EWAHR,  " //
				+ "       RESB.AUSCH, RESB.ESMNG, AFVC.LTXA1, RESB.BWART, AFKO.DISPO,  " //
				+ "         (SELECT SUM (LABST) || ',' || SUM (INSME) || ',' || SUM(DECODE(LGORT,'RVMI',KLABS+KINSM,0)) " //
				+ "       FROM SAPSR3.MARD  " //
				+ "         WHERE MANDT = RESB.MANDT AND MATNR = RESB.MATNR AND WERKS = RESB.WERKS)   " //
				+ "         MARDD, " //
				+ "       DECODE(RESB.WERKS,'481A','482A','482A','481A','381A','382A','382A','381A','281A','281A')WERKSA,  " //
				+ "         (SELECT SUM (LABST) " //
				+ "       FROM SAPSR3.MARD  " //
				+ "         WHERE MANDT = RESB.MANDT AND MATNR = RESB.MATNR AND WERKS = DECODE(RESB.WERKS,'481A','482A','482A','481A','381A','382A','382A','381A','281A','281A'))   " //
				+ "         MARDA " //
				+ "  FROM SAPSR3.RESB,  " //
				+ "       SAPSR3.MAKT,  " //
				+ "       SAPSR3.MARA,  " //
				+ "       SAPSR3.AFKO,  " //
				+ "       SAPSR3.AFVC,  " //
				+ "       SAPSR3.AFPO,  " //
				+ "       SAPSR3.MARC, " //
				+ "       SAPSR3.MAKT PMAKT  " //
				+ " WHERE     RESB.MANDT = AFKO.MANDT  " //
				+ "       AND RESB.RSNUM = AFKO.RSNUM  " //
				+ "       AND AFVC.MANDT(+) = RESB.MANDT  " //
				+ "       AND AFVC.AUFPL(+) = RESB.AUFPL  " //
				+ "       AND AFVC.APLZL(+) = RESB.APLZL  " //
				+ "       AND MAKT.MANDT(+) = RESB.MANDT  " //
				+ "       AND MAKT.MATNR(+) = RESB.MATNR  " //
				+ "       AND MAKT.SPRAS(+) = 'M'  " //
				+ "       AND PMAKT.MANDT(+) = AFPO.MANDT  " //
				+ "       AND PMAKT.MATNR(+) = AFPO.MATNR  " //
				+ "       AND PMAKT.SPRAS(+) = 'M'  " //
				+ "       AND MARA.MANDT(+) = RESB.MANDT  " //
				+ "       AND MARA.MATNR(+) = RESB.MATNR  " //
				+ "       AND MARC.MANDT(+) = RESB.MANDT  " //
				+ "       AND MARC.MATNR(+) = RESB.MATNR  " //
				+ "       AND MARC.WERKS(+) = RESB.WERKS        " //
				+ "       AND AFPO.MANDT = AFKO.MANDT  " //
				+ "       AND AFPO.AUFNR = AFKO.AUFNR " //
				+ "       AND AFKO.MANDT = '168' " ;//
		
		if (req.getParameter("teko_checkbox") != null){
			sql += "       AND AFPO.ELIKZ = ' ' " ;//
			sql += "       AND AFPO.DNREL = ' ' "; //
		}

		if (!getParam(req,"werks_low").equals("")) {
			sql += Helper.sqlParams(req, "AFPO.PWERK", "werks_low", "werks_high");
		}
		if (!getParam(req,"dispo_low").equals("")) {
			sql += Helper.sqlParams(req, "AFKO.DISPO", "dispo_low", "dispo_high");
		}
		if (!getParam(req,"gstrp_low").equals("")) {
			sql += Helper.sqlParams(req, "AFKO.GSTRP", "gstrp_low", "gstrp_high");
		}
		if (!getParam(req,"gltrp_low").equals("")) {
			sql += Helper.sqlParams(req, "AFKO.GLTRP", "gltrp_low", "gltrp_high");
		}
		if (!getParam(req,"pmatnr_low").equals("")) {
			sql += Helper.sqlParams(req, "AFPO.MATNR", "pmatnr_low", "pmatnr_high");
		}
		if (!getParam(req,"cmatnr_low").equals("")) {
			sql += Helper.sqlParams(req, "RESB.MATNR", "pmatnr_low", "pmatnr_high");
		}
		if (!getParam(req,"aufnr_low").equals("")) {
			sql += Helper.sqlParamsFormat(req, "AFKO.AUFNR", "aufnr_low", "aufnr_high", "%012d");
		}
		if (!getParam(req,"dispo_textarea").equals("")) {
			sql += "         AND AFKO.DISPO IN (" + toSqlString(textAreaToList(req,"dispo_textarea")) + ") ";
		}
		if (!getParam(req,"pmatnr_textarea").equals("")) {
			sql += "         AND AFPO.MATNR IN (" + toSqlString(textAreaToList(req,"pmatnr_textarea")) + ") ";
		}
		if (!getParam(req,"cmatnr_textarea").equals("")) {
			sql += "         AND RESB.MATNR IN (" + toSqlString(textAreaToList(req,"cmatnr_textarea")) + ") ";
		}
		if (!getParam(req,"aufnr_textarea").equals("")) {
			sql += "         AND AFPO.AUFNR IN (" + toSqlString(textAreaToList(req,"aufnr_textarea", "%012d")) + ") ";
		}
		if (!getParam(req,"matkl_textarea").equals("")) {
			sql += "         AND RESB.MATKL IN (" + toSqlString(textAreaToList(req,"matkl_textarea")) + ") ";
		}

		if (!getParam(req,"ekgrp_textarea").equals("")) {
			sql += "         AND MARC.EKGRP IN (" + toSqlString(textAreaToList(req,"ekgrp_textarea")) + ") ";
		}

		if (req.getParameter("xfehl_checkbox") != null){
			sql += " AND RESB.XFEHL = 'X'";
		}

		if (req.getParameter("extra_checkbox") != null){
			sql += " AND (SUBSTR(RESB.VORNR,1,1) = '9' OR SUBSTR(RESB.POSNR,1,1)='Z')";
		}
		sql += "ORDER BY AFKO.GLTRP,AFPO.AUFNR, RESB.VORNR, RESB.POSNR, RESB.ALPOS DESC, RESB.EWAHR DESC ";//

		Connection conn = getSapPrdConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);

		list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();

		if (req.getParameter("aufnr_checkbox") == null && req.getParameter("extra_checkbox") == null){
			
			sql= "SELECT PLAF.PLWRK PWERK, PLAF.PLNUM AUFNR, PLAF.MATNR PMATNR, PMAKT.MAKTX PMAKTX, RESB.VORNR, RESB.POSNR,   " //
					+ "       RESB.MATNR, MAKT.MAKTX, MARA.MATKL, RESB.WERKS, RESB.LGORT, MARC.EKGRP,  " //
					+ "       RESB.BDMNG, RESB.VMENG, RESB.ENMNG, RESB.MEINS, RESB.BDTER, PLAF.PSTTR GSTRP, " //
					+ "       RESB.POTX1, RESB.BAUGR, RESB.AENNR, MARA.ZEINR, RESB.DUMPS, PLAF.PEDTR GLTRP, " //
					+ "       RESB.KZEAR, RESB.XLOEK, 'X' XFEHL, RESB.ALPOS, RESB.EWAHR,   " //
					+ "       RESB.AUSCH, RESB.ESMNG, ' ' LTXA1, 'PLAF' BWART, PMARC.FEVOR DISPO,   " //
					+ "         (SELECT SUM (LABST) || ',' || SUM (INSME)   || ',' || SUM(DECODE(LGORT,'RVMI',KLABS+KINSM,0))  " //
					+ "       FROM SAPSR3.MARD   " //
					+ "         WHERE MANDT = RESB.MANDT AND MATNR = RESB.MATNR AND WERKS = RESB.WERKS)    " //
					+ "         MARDD,  " //
					+ "       DECODE(RESB.WERKS,'481A','482A','482A','481A','381A','382A','382A','381A','281A','281A')WERKSA,   " //
					+ "         (SELECT SUM (LABST)  " //
					+ "       FROM SAPSR3.MARD   " //
					+ "         WHERE MANDT = RESB.MANDT AND MATNR = RESB.MATNR AND WERKS = DECODE(RESB.WERKS,'481A','482A','482A','481A','381A','382A','382A','381A','281A','281A'))    " //
					+ "         MARDA  " //
					+ "  FROM SAPSR3.RESB,   " //
					+ "       SAPSR3.MAKT,   " //
					+ "       SAPSR3.MARA,   " //
					+ "       SAPSR3.PLAF,   " //
					+ "       SAPSR3.MARC,  " //
					+ "       SAPSR3.MARC PMARC,  " //
					+ "       SAPSR3.MAKT PMAKT   " //
					+ " WHERE     RESB.MANDT = PLAF.MANDT   " //
					+ "       AND RESB.RSNUM = PLAF.RSNUM   " //
					+ "       AND MAKT.MANDT(+) = RESB.MANDT   " //
					+ "       AND MAKT.MATNR(+) = RESB.MATNR   " //
					+ "       AND MAKT.SPRAS(+) = 'M'   " //
					+ "       AND PMAKT.MANDT(+) = PLAF.MANDT   " //
					+ "       AND PMAKT.MATNR(+) = PLAF.MATNR   " //
					+ "       AND PMAKT.SPRAS(+) = 'M'   " //
					+ "       AND MARA.MANDT(+) = RESB.MANDT   " //
					+ "       AND MARA.MATNR(+) = RESB.MATNR   " //
					+ "       AND MARC.MANDT(+) = RESB.MANDT   " //
					+ "       AND MARC.MATNR(+) = RESB.MATNR   " //
					+ "       AND MARC.WERKS(+) = RESB.WERKS         " //
					+ "       AND PMARC.MANDT(+) = PLAF.MANDT   " //
					+ "       AND PMARC.MATNR(+) = PLAF.MATNR   " //
					+ "       AND PMARC.WERKS(+) = PLAF.PLWRK         " //
					+ "       AND PLAF.MANDT = '168' ";//

			if (!getParam(req,"werks_low").equals("")) {
				sql += Helper.sqlParams(req, "PLAF.PLWRK", "werks_low", "werks_high");
			}
			if (!getParam(req,"dispo_low").equals("")) {
				sql += Helper.sqlParams(req, "PMARC.FEVOR", "dispo_low", "dispo_high");
			}
			if (!getParam(req,"gstrp_low").equals("")) {
				sql += Helper.sqlParams(req, "PLAF.PSTTR", "gstrp_low", "gstrp_high");
			}
			if (!getParam(req,"gltrp_low").equals("")) {
				sql += Helper.sqlParams(req, "PLAF.PEDTR", "gltrp_low", "gltrp_high");
			}
			if (!getParam(req,"aufnr_low").equals("")) {
				sql += Helper.sqlParamsFormat(req, "PLAF.PLNUM", "aufnr_low", "aufnr_high", "%010d");
			}
			if (!getParam(req,"pmatnr_low").equals("")) {
				sql += Helper.sqlParams(req, "PLAF.MATNR", "pmatnr_low", "pmatnr_high");
			}
			if (!getParam(req,"cmatnr_low").equals("")) {
				sql += Helper.sqlParams(req, "RESB.MATNR", "pmatnr_low", "pmatnr_high");
			}
			if (!getParam(req,"dispo_textarea").equals("")) {
				sql += "         AND PMARC.FEVOR IN (" + toSqlString(textAreaToList(req,"dispo_textarea")) + ") ";
			}
			if (!getParam(req,"pmatnr_textarea").equals("")) {
				sql += "         AND PLAF.MATNR IN (" + toSqlString(textAreaToList(req,"pmatnr_textarea")) + ") ";
			}
			if (!getParam(req,"cmatnr_textarea").equals("")) {
				sql += "         AND RESB.MATNR IN (" + toSqlString(textAreaToList(req,"cmatnr_textarea")) + ") ";
			}
			if (!getParam(req,"aufnr_textarea").equals("")) {
				sql += "         AND PLAF.PLNUM IN (" + toSqlString(textAreaToList(req,"aufnr_textarea", "%010d")) + ") ";
			}
			if (!getParam(req,"matkl_textarea").equals("")) {
				sql += "         AND RESB.MATKL IN (" + toSqlString(textAreaToList(req,"matkl_textarea")) + ") ";
			}
			if (!getParam(req,"ekgrp_textarea").equals("")) {
				sql += "         AND MARC.EKGRP IN (" + toSqlString(textAreaToList(req,"ekgrp_textarea")) + ") ";
			}
			
			sql += "ORDER BY PLAF.PEDTR, PLAF.PLNUM, RESB.VORNR, RESB.POSNR, RESB.ALPOS DESC, RESB.EWAHR DESC ";//
	
			pstm = conn.prepareStatement(sql);
			
			plafs = Helper.resultSetToArrayList(pstm.executeQuery());
			
			pstm.close();
			
			for (HashMap<String, Object> h : plafs){
				list.add(h);
			}
		}
		conn.close();
		
		String[] buf;
		double qty;
		int rowid = 0;
		for (HashMap<String, Object> h : list) {
			if (h.get("MARDD") != null) {
				
				buf = h.get("MARDD").toString().split(",");

				if (buf.length > 1) {

					h.put("LABST", buf[0]);
					h.put("INSME", buf[1]);
					h.put("RVMI", buf[2]);
				}
			}
			
			if (h.get("XFEHL").equals("X")){
				
				h.put("COLOR", "bgcolor='#ffff99'");
				qty = Double.parseDouble(h.get("BDMNG").toString()) - Double.parseDouble(h.get("VMENG").toString()) - Double.parseDouble(h.get("ENMNG").toString()); 
				h.put("QTY", qty);
				
			}else if (h.get("DUMPS").equals("X")){
				
				h.put("COLOR", "bgcolor='#eaeaea'");
			
			}else if (h.get("VORNR").toString().startsWith("9") || h.get("POSNR").toString().startsWith("Z")){
				
				h.put("COLOR", "bgcolor='#ccff99'");
			}
			
			rowid = rowid + 1;
			h.put("ROWID", rowid);
		}

		req.setAttribute("list", list);
	}
}
