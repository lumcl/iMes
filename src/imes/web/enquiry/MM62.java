package imes.web.enquiry;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import imes.vpojo.MRP_ITEMS;

@WebServlet("/MM62")
public class MM62 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		render(req, resp,"/WEB-INF/pages/enquiry/MM62.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);

		try {

			ActionPost(req, resp);
			render(req, resp,"/WEB-INF/pages/enquiry/MM62.jsp");

		} catch (Exception ex) {

			PrintError(resp,ex);
		}
	}

	private void ActionPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String jspParams = getParam(req,"werks_low") + getParam(req,"matnr_low") + getParam(req,"matkl_low") + getParam(req,"maktx_low")
				+ getParam(req,"ekgrp_low") + getParam(req,"matnr_textarea") + getParam(req,"matkl_textarea") + getParam(req,"ekgrp_textarea")
				 + getParam(req,"lifnr_low") + getParam(req,"lifnr_textarea");

		if (jspParams.equals("")) {
			return;
		}

		String sql = "  SELECT MDBS.MATNR, MDBS.WERKS " //
				+ "    FROM SAPSR3.MDBS, SAPSR3.EKKO " //
				+ "   WHERE     MDBS.MANDT = '168' " //
				+ "         AND MDBS.BSTYP = 'F' " //
				+ "         AND MDBS.MATNR <> ' ' " //
				+ "         AND (MDBS.LOEKZ = ' ' OR MDBS.LOEKZ = 'S') " //
				+ "         AND MDBS.ELIKZ = ' ' " //
				+ "         AND MDBS.MENGE <> MDBS.WEMNG " //
				+ "         AND EKKO.MANDT = MDBS.MANDT " //
				+ "         AND EKKO.EBELN = MDBS.EBELN " //
				+ "         AND EKKO.LIFNR NOT BETWEEN 'L1' AND 'L9' " //
				+ "         AND EKKO.LIFNR = 'DT2628-Z' " //
				+ "GROUP BY MDBS.MATNR, MDBS.WERKS ";//
/*
		if (!getParam(req,"werks_low").equals("")) {
			sql += Helper.sqlParams(req, "EORD.WERKS", "werks_low", "werks_high");
		}
		if (!getParam(req,"matnr_low").equals("")) {
			sql += Helper.sqlParams(req, "MDBS.MATNR", "matnr_low", "matnr_high");
		}
		if (!getParam(req,"lifnr_low").equals("")) {
			sql += Helper.sqlParams(req, "MDBS.LIFNR", "lifnr_low", "lifnr_high");
		}
		if (!getParam(req,"ekgrp_low").equals("")) {
			sql += Helper.sqlParams(req, "MARC.EKGRP", "ekgrp_low", "ekgrp_high");
		}
		if (!getParam(req,"matkl_low").equals("")) {
			sql += Helper.sqlParams(req, "MARA.MATKL", "matkl_low", "matkl_high");
		}
		if (!getParam(req,"maktx_low").equals("")) {
			sql += " AND MAKT.MAKTX LIKE '%" + getParam(req,"maktx_low") + "%'";
		}
		if (!getParam(req,"matnr_textarea").equals("")) {
			sql += "         AND MCHB.MATNR IN (" + toSqlString(textAreaToList("matnr_textarea")) + ") ";
		}
		if (!getParam(req,"matkl_textarea").equals("")) {
			sql += "         AND MARA.MATKL IN (" + toSqlString(textAreaToList("matkl_textarea")) + ") ";
		}
		if (!getParam(req,"ekgrp_textarea").equals("")) {
			sql += "         AND MARC.EKGRP IN (" + toSqlString(textAreaToList("ekgrp_textarea")) + ") ";
		}
		sql += "                 ) " //
				+ "   WHERE (WERKS = EWERK OR EWERK IS NULL) " //
				+ "ORDER BY EKGRP, MATKL, MATNR, WERKS, AUTET DESC "; //
*/
		Connection conn = getSapPrdConnection();
		PreparedStatement pstm = conn.prepareStatement(sql);
		list = Helper.resultSetToArrayList(pstm.executeQuery());
		pstm.close();
		conn.close();

		MRP_ITEMS e;

		double atp;
		
		List<MRP_ITEMS> mrpItems = new ArrayList<MRP_ITEMS>();
		
		SapRfcConnection connect = new SapRfcConnection();
		
		for (HashMap<String, Object> h : list) {
			
			JCoFunction function = connect.getFunction("BAPI_MATERIAL_STOCK_REQ_LIST");
			function.getImportParameterList().setValue("MATERIAL", h.get("MATNR"));
			function.getImportParameterList().setValue("PLANT", h.get("WERKS"));
			function.getImportParameterList().setValue("GET_ITEM_DETAILS", "X");
			function.getImportParameterList().setValue("GET_IND_LINES", "");

			System.out.println("Start Bapi:"+ new Date());
		
			connect.execute(function);

			System.out.println("End Bapi:"+ new Date());

			atp = 0;
			JCoTable table = function.getTableParameterList().getTable("MRP_ITEMS");

			TableAdapterReader reader = new TableAdapterReader(table);
			for (int i = 0; i < reader.size(); i++) {
				e = new MRP_ITEMS();
				e.setMATNR(h.get("MATNR").toString());
				e.setWERKS(h.get("WERKS").toString());
				e.setDAT00(reader.getDate("AVAIL_DATE"));
				e.setTAG00(Integer.parseInt(reader.get("SORTIND_00")));
				e.setSORT1(reader.get("SORTIND_01"));
				e.setSORT2(reader.get("SORTIND_02"));
				e.setDELKZ(reader.get("MRP_ELEMENT_IND"));
				
				e.setPLUMI(reader.get("PLUS_MINUS"));
				if (e.getPLUMI().equals("-")) {
					e.setMNG01(Double.valueOf(reader.get("REC_REQD_QTY")) * -1);
				} else {
					e.setMNG01(Double.valueOf(reader.get("REC_REQD_QTY")));
				}

				atp += e.getMNG01();

				e.setATP01(atp);
				e.setDAT01(reader.getDate("FINISH_DATE"));
				e.setUMDAT1(reader.getDate("RESCHED_DATE1"));
				e.setUMDAT2(reader.getDate("RESCHED_DATE2"));
				e.setBAART(reader.get("ORDER_TYPE"));
				e.setAUSSL(reader.get("EXCMSGKEY01"));
				e.setOLDSL(reader.get("EXCMSGKEY02"));
				e.setLGORT_D(reader.get("STORAGE_LOC"));
				e.setDELNR(reader.get("MRP_NO"));
				e.setDEL12(reader.get("MRP_NO12"));
				e.setDELPS(reader.get("MRP_POS"));
				e.setDELPS(reader.get("MRP_POS"));
				e.setBAUGR(reader.get("PEGGEDRQMT"));
				e.setKUNNR(reader.get("CUSTOMER"));
				e.setLIFNR(reader.get("VENDOR_NO"));
				e.setAUFVR(reader.get("SOURCE_NO"));
				e.setPOSVR(reader.get("SOURCE_ITEM"));
				e.setPWWRK(reader.get("PROD_PLANT"));
				
				mrpItems.add(e);
				reader.next();
			}

			function = null;
			reader = null;
			table.clear();
			table = null;

		}
		
		connect = null;

		req.setAttribute("list", mrpItems);
	}
}
