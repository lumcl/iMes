package imes.web.enquiry;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

import imes.core.Helper;
import imes.core.HttpController;
import imes.core.sap.SapRfcConnection;
import imes.core.sap.TableAdapterReader;
import imes.vpojo.CDPOS;

@WebServlet("/SD61")
public class SD61 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		try {
			if (getAction(req).equals("SD61GetSoLine")) {

				ActionGetSD61GetSoLine(req, resp);

			} else if (getAction(req).equals("SD61GetBomStockBalance")) {

				ActionGetSD61GetBomStockBalance(req, resp);

			} else {

				ActionGetSD61(req, resp);
			}
		} catch (Exception e) {
			PrintWriter out = resp.getWriter();
			e.printStackTrace();
			e.printStackTrace(out);
			out.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	private void ActionGetSD61(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		req.setAttribute("CNDAT", Helper.fmtDate(new Date(), "yyyyMMdd"));
		getServletContext().getRequestDispatcher("/WEB-INF/pages/enquiry/SD61.jsp").forward(req, resp);
	}

	public void ActionGetSD61GetSoLine(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String VBELN = getParam(req, "VBELN");
		String POSNR = getParam(req, "POSNR", "%06d");
		String EDATU = "";

		String sql = "SELECT VBAP.MATNR, VBAP.ARKTX, VBAP.MATKL, VBAP.WERKS, VBAK.WAERK, " //
				+ "       (VBAP.NETPR / VBAP.KPEIN) NETPR, VBAP.KBMENG, VBAK.KUNNR, VBAK.VTWEG," //
				+ "       KNA1.SORTL, " //
				+ "  (SELECT EDATU FROM SAPSR3.VBEP WHERE MANDT='168' AND VBELN=VBAP.VBELN AND POSNR=VBAP.POSNR AND ROWNUM=1) EDATU"
				+ "  FROM SAPSR3.VBAP, SAPSR3.VBAK, SAPSR3.KNA1 " //
				+ " WHERE     VBAP.MANDT = '168' " //
				+ "       AND VBAP.VBELN = ? " //
				+ "       AND VBAP.POSNR = ? " //
				+ "       AND VBAK.MANDT = VBAP.MANDT " //
				+ "       AND VBAK.VBELN = VBAP.VBELN " //
				+ "       AND KNA1.MANDT = VBAK.MANDT " //
				+ "       AND KNA1.KUNNR = VBAK.KUNNR "; //

		try {
			Connection conn = getSapPrdConnection();
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, VBELN);
			pstm.setString(2, POSNR);

			list = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();

		} catch (Exception ex) {

			ex.printStackTrace();
		}

		if (req.getParameter("WEDATU").equals("")) {

			EDATU = GetOriginalDeliveryDate(VBELN, POSNR);

		} else {

			EDATU = req.getParameter("WEDATU");
		}

		if (EDATU.equals("")) {

			EDATU = list.get(0).get("EDATU").toString();
		}

		int cnday = 0;

		try {

			Date cndat = new SimpleDateFormat("yyyyMMdd").parse(req.getParameter("CNDAT"));
			Date edatu = new SimpleDateFormat("yyyyMMdd").parse(EDATU);
			cnday = (int) ((edatu.getTime() - cndat.getTime()) / (24 * 60 * 60 * 1000));

		} catch (Exception e) {

			e.printStackTrace();
		}

		req.setAttribute("list", list);
		req.setAttribute("EDATU", EDATU);
		req.setAttribute("CNDAY", cnday);

		render(req, resp, "/WEB-INF/pages/enquiry/SD61A.jsp");
	}

	private void ActionGetSD61GetBomStockBalance(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 1400007764
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		String sql;
		sql = "WITH TBOM " //
				+ "     AS (  SELECT CWERKS, CMATKL, CMAKTX, CMATNR, MENGE DUSAGE, " //
				+ "                  DUOM, CSOBSL, ALPOS, EWAHR, ALPGR, " //
				+ "                  DPMATNR, (MATPR * 1.03) MATPR " //
				+ "             FROM VBMAT " //
				+ "            WHERE WERKS = ? AND PMATNR = ? " //
				+ "         ORDER BY CMATKL, DPMATNR, ALPGR, CMAKTX, CMATNR), " //
				+ "     GBOM " //
				+ "     AS (  SELECT CMATNR, CWERKS " //
				+ "             FROM TBOM " //
				+ "         GROUP BY CMATNR, CWERKS), " //
				+ "     TMARD " //
				+ "     AS (  SELECT MARD.MATNR, MARD.WERKS, PLIFZ, BSTRF, DISGR, " //
				+ "                  SUM (LABST) LABST, SUM (UMLME + INSME) INSME " //
				+ "             FROM SAPSR3.MARD@SAPP, SAPSR3.MARC@SAPP, GBOM " //
				+ "            WHERE     MARD.MANDT = '168' " //
				+ "                  AND MARD.MATNR = GBOM.CMATNR " //
				+ "                  AND MARD.WERKS = GBOM.CWERKS " //
				+ "                  AND MARC.MANDT = '168' " //
				+ "                  AND MARC.MATNR = GBOM.CMATNR " //
				+ "                  AND MARC.WERKS = GBOM.CWERKS " //
				+ "         GROUP BY MARD.MATNR, MARD.WERKS, PLIFZ, BSTRF, DISGR), " //
				+ "     MDBS " //
				+ "     AS (  SELECT MATNR, SUM (MNG01) MENGE " //
				+ "             FROM IT.WMD04S, GBOM " //
				+ "            WHERE MATNR = CMATNR " //
				+ "                  AND (   WERKS = CWERKS " //
				+ "                       OR (WERKS = '101A' AND LGORT = CASE WHEN CWERKS = '481A' THEN 'RM01' ELSE 'RM02' END) " //
				+ "                       OR (WERKS = CASE WHEN CWERKS = '381A' THEN '701A' ELSE CWERKS END) " //
				+ "                       OR (WERKS = CASE WHEN CWERKS = '381A' THEN '921A' ELSE CWERKS END)) " //
				+ "                  AND DELKZ = 'BE' " //
				+ "                  AND SUBSTR (LIFNR, 1, 2) NOT IN ('L1', 'L2', 'L3', 'L4', 'L5', 'L6', 'L7', 'L8', 'L9') " //
				+ "         GROUP BY MATNR) " //
				+ "  SELECT CWERKS, CMATKL, CMAKTX, CMATNR, DUSAGE, " //
				+ "         (DUSAGE * ?) REQTY, DUOM, LABST, INSME, MDBS.MENGE, " //
				+ "         NVL(COMMON,' ')COMMON, PLIFZ, BSTRF, DISGR, CSOBSL, " //
				+ "         ALPOS, EWAHR, ALPGR, DPMATNR, MATPR" //
				+ "    FROM TBOM, " //
				+ "         TMARD, " //
				+ "         MDBS, " //
				+ "         ZMARC " //
				+ "   WHERE     TMARD.MATNR = CMATNR " //
				+ "         AND TMARD.WERKS = CWERKS " //
				+ "         AND MDBS.MATNR(+) = CMATNR " //
				+ "         AND ZMARC.MATNR(+) = CMATNR " //
				+ "         AND ZMARC.WERKS(+) = CWERKS " //
				+ "ORDER BY COMMON, CMATKL, DPMATNR, ALPGR, CMAKTX, CMATNR "; //

		String WERKS = toUpperCaseParam(req, "WERKS");

		if (WERKS.equals("101A")) {
			if (toUpperCaseParam(req, "VTWEG").equals("DT")) {
				WERKS = "481A";
			} else if (toUpperCaseParam(req, "VTWEG").equals("TX")) {
				WERKS = "381A";
			}
		}

		Connection conn = getSapCoConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, WERKS);
		pstm.setString(2, toUpperCaseParam(req, "MATNR"));
		pstm.setDouble(3, toDoubleParam(req, "CNQTY"));

		list = Helper.resultSetToArrayList(pstm.executeQuery());

		pstm.close();
		conn.close();

		double reqQty, stkQty, moq;
		double idleAmt = 0;
		for (HashMap<String, Object> row : list) {
			row.put("IDEQTY", 0);

			if (row.get("COMMON").equals("X") || (row.get("ALPOS").equals("X") && toInteger(row.get("EWAHR")) != 100) ) {
				
				// No Charging

			} else {
				reqQty = toDouble(row.get("REQTY"));
				stkQty = toDouble(row.get("LABST")) + toDouble(row.get("INSME")) + toDouble(row.get("MENGE"));
				moq = toDouble(row.get("BSTRF"));

				if (toDouble(row.get("PLIFZ")) > toDoubleParam(req, "CNDAY") || row.get("DISGR").toString().equals("ZL06")) {
					// Determine MOQ

					if (moq >= reqQty) {
						reqQty = moq;
					}

					// if (moq >= reqQty) {
					//
					// row.put("IDEQTY", moq);
					//
					// } else {
					if (stkQty >= reqQty) {

						row.put("IDEQTY", reqQty);

					} else {

						row.put("IDEQTY", stkQty);
					}
					// }

				}
			}

			idleAmt += toDouble(row.get("MATPR")) * toDouble(row.get("IDEQTY"));
		}

		req.setAttribute("list", list);
		req.setAttribute("idleAmt", idleAmt);

		render(req, resp, "/WEB-INF/pages/enquiry/SD61B.jsp");

	}

	public String GetOriginalDeliveryDate(String vbeln, String posnr) {

		String OBJECTCLAS = "VERKBELEG";
		String OBJECTID = vbeln;
		String[] fieldsValue;
		String deliveryDate = "";

		CDPOS e;
		List<CDPOS> list = new ArrayList<CDPOS>();

		try {
			SapRfcConnection connect = new SapRfcConnection();

			JCoFunction function = connect.getFunction("RFC_READ_TABLE");
			function.getImportParameterList().setValue("QUERY_TABLE", "CDPOS");
			function.getImportParameterList().setValue("DELIMITER", "_");

			JCoTable fields = function.getTableParameterList().getTable("FIELDS");
			fields.appendRow();
			fields.setValue("FIELDNAME", "CHANGENR");
			fields.appendRow();
			fields.setValue("FIELDNAME", "TABKEY");
			fields.appendRow();
			fields.setValue("FIELDNAME", "FNAME");
			fields.appendRow();
			fields.setValue("FIELDNAME", "VALUE_OLD");
			// fields.appendRow();
			// fields.setValue("FIELDNAME", "VALUE_NEW");
			JCoTable options = function.getTableParameterList().getTable("OPTIONS");
			options.clear();
			options.appendRow();
			options.setValue("TEXT", "OBJECTCLAS EQ '" + OBJECTCLAS + "'");
			options.appendRow();
			options.setValue("TEXT", " AND OBJECTID EQ '" + OBJECTID + "'");
			options.appendRow();
			options.setValue("TEXT", " AND FNAME IN ('BMENG','EDATU')");

			connect.execute(function);

			JCoTable table = function.getTableParameterList().getTable("DATA");

			TableAdapterReader adapter = new TableAdapterReader(table);

			for (int i = 0; i < adapter.size(); i++) {

				fieldsValue = adapter.get("WA").split("_");

				e = new CDPOS();
				e.setCHANGENR(fieldsValue[0].trim());
				e.setTABKEY(fieldsValue[1].trim());
				e.setFNAME(fieldsValue[2].trim());
				e.setVALUE_OLD(fieldsValue[3].trim());
				e.setId(e.getCHANGENR() + e.getTABKEY() + e.getFNAME());

				list.add(e);

				adapter.next();
			}

			table.clear();
			fields.clear();
			fields.appendRow();
			fields.setValue("FIELDNAME", "CHANGENR");
			fields.appendRow();
			fields.setValue("FIELDNAME", "TABKEY");
			fields.appendRow();
			fields.setValue("FIELDNAME", "FNAME");
			fields.appendRow();
			fields.setValue("FIELDNAME", "VALUE_NEW");

			connect.execute(function);

			table = function.getTableParameterList().getTable("DATA");

			adapter = new TableAdapterReader(table);

			for (int i = 0; i < adapter.size(); i++) {

				fieldsValue = adapter.get("WA").split("_");

				for (CDPOS el : list) {
					if (el.getId().equals(fieldsValue[0].trim() + fieldsValue[1].trim() + fieldsValue[2].trim())) {
						el.setVALUE_NEW(fieldsValue[3].trim());
						// System.out.println(el.getId()+","+el.getFNAME()+","+el.getVALUE_OLD()+","+el.getVALUE_NEW());
					}
				}
				adapter.next();
			}

			String id = "";

			for (CDPOS el : list) {
				if (Integer.parseInt(el.getTABKEY().substring(13, 19)) == Integer.parseInt(posnr)) {
					if (el.getFNAME().equals("BMENG") && Double.parseDouble(el.getVALUE_OLD()) == 0) {
						id = el.getCHANGENR() + el.getTABKEY();
						break;
					}
				}
			}

			for (CDPOS el : list) {
				if (Integer.parseInt(el.getTABKEY().substring(13, 19)) == Integer.parseInt(posnr)) {
					if (el.getFNAME().equals("EDATU") && el.getId().equals(id + "EDATU")) {
						deliveryDate = el.getVALUE_OLD();
					}
				}
			}

			list.clear();
			list = null;

			function = null;
			table.clear();
			table = null;
			adapter = null;
			connect = null;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return deliveryDate;
	}
}
