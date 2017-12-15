package imes.web.enquiry;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

import imes.core.Helper;
import imes.core.HttpController;
import imes.core.sap.SapRfcConnection;
import imes.core.sap.TableAdapterReader;
import imes.vpojo.MRP_ITEMS;

@WebServlet("/MD04")
public class MD04 extends HttpController {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
		try {

			if (getAction(req).equals("B")) {
				AjaxGetB(req, resp);
				return;
			} else {
				render(req, resp,"/WEB-INF/pages/enquiry/MD04A.jsp");
				return;
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

	private void AjaxGetB(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("VJ", "Delvry");
		map.put("SB", "DepReq");
		map.put("SH", "SafeSt");
		map.put("VP", "----->");
		map.put("U1", "Ord.DS");
		map.put("VT", "RetDly");
		map.put("BE", "PO項目");
		map.put("RP", "Return");
		map.put("MR", "料保留");
		map.put("LB", "SLocSt");
		map.put("QM", "QM-lot");
		map.put("U2", "PRqRel");
		map.put("VI", "FOCDly");
		map.put("VC", "客訂單");
		map.put("PP", "IndReq");
		map.put("WB", "庫存");
		map.put("AR", "OrdRes");
		map.put("BA", "PurRqs");
		map.put("PA", "計劃單");
		map.put("U3", "PlORel");
		map.put("FE", "製程單");

		MRP_ITEMS e;

		List<MRP_ITEMS> list = new ArrayList<MRP_ITEMS>();

		List<String> aufnrs = new ArrayList<String>();
		List<String> lifnrs = new ArrayList<String>();
		List<String> kunnrs = new ArrayList<String>();
		List<String> plnums = new ArrayList<String>();

		double atp = 0;

		SapRfcConnection connect = new SapRfcConnection();

		JCoFunction function = connect.getFunction("BAPI_MATERIAL_STOCK_REQ_LIST");
		function.getImportParameterList().setValue("MATERIAL", toUpperCaseParam(req,"matnr"));
		function.getImportParameterList().setValue("PLANT", toUpperCaseParam(req,"werks"));
		function.getImportParameterList().setValue("GET_ITEM_DETAILS", "X");
		function.getImportParameterList().setValue("GET_IND_LINES", "");

		connect.execute(function);

		JCoStructure mrpList = function.getExportParameterList().getStructure("MRP_LIST");
		if (mrpList.getString("MRP_DATE") != null) {
			req.setAttribute("mrpDate", mrpList.getString("MRP_DATE"));
			req.setAttribute("mrpTime", mrpList.getString("MRP_TIME"));
		}
		mrpList = null;

		JCoStructure mrpStockDetail = function.getExportParameterList().getStructure("MRP_STOCK_DETAIL");
		if (mrpStockDetail.getString("UNRESTRICTED_STCK") != null){
			req.setAttribute("UNRESTRICTED_STCK", mrpStockDetail.getString("UNRESTRICTED_STCK"));
			req.setAttribute("QUAL_INSPECTION", mrpStockDetail.getString("QUAL_INSPECTION"));
			req.setAttribute("BLKD_STKC", mrpStockDetail.getString("BLKD_STKC"));
			req.setAttribute("RESTR_USE", mrpStockDetail.getString("RESTR_USE"));
			req.setAttribute("BLKD_RETURNS", mrpStockDetail.getString("BLKD_RETURNS"));
			req.setAttribute("BLKD_GR", mrpStockDetail.getString("BLKD_GR"));
			req.setAttribute("RESERVATIONS", mrpStockDetail.getString("RESERVATIONS"));
			req.setAttribute("SALES_REQS", mrpStockDetail.getString("SALES_REQS"));
			req.setAttribute("PUR_ORDERS", mrpStockDetail.getString("PUR_ORDERS"));
			req.setAttribute("PUR_REQ", mrpStockDetail.getString("PUR_REQ"));
			req.setAttribute("PLND_ORDER", mrpStockDetail.getString("PLND_ORDER"));
			req.setAttribute("PROD_ORDER", mrpStockDetail.getString("PROD_ORDER"));
			req.setAttribute("FORECAST_REQ", mrpStockDetail.getString("FORECAST_REQ"));
		}
		mrpStockDetail = null;
		
		JCoTable table = function.getTableParameterList().getTable("MRP_ITEMS");

		TableAdapterReader reader = new TableAdapterReader(table);
		for (int i = 0; i < reader.size(); i++) {
			e = new MRP_ITEMS();
			e.setMATNR(toUpperCaseParam(req,"matnr"));
			e.setWERKS(toUpperCaseParam(req,"werks"));
			e.setDAT00(reader.getDate("AVAIL_DATE"));
			e.setTAG00(Integer.parseInt(reader.get("SORTIND_00")));
			e.setSORT1(reader.get("SORTIND_01"));
			e.setSORT2(reader.get("SORTIND_02"));
			e.setDELKZ(reader.get("MRP_ELEMENT_IND"));

			if (map.containsKey(e.getDELKZ().trim())) {
				e.setDELB0(map.get(e.getDELKZ().trim()));
			} else {
				e.setDELB0(e.getDELKZ());
			}

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
			e.setBEDAT(reader.getDate("PO_DATE"));
			
			if (e.getDELKZ().equals("AR")) {

				if (!aufnrs.contains(e.getDEL12())) {
					aufnrs.add(e.getDEL12());
				}
			} else if (e.getDELKZ().equals("BE") || e.getDELKZ().equals("PA") || e.getDELKZ().equals("BA")) {

				if (!lifnrs.contains(e.getLIFNR())) {
					lifnrs.add(e.getLIFNR());
				}

				if (e.getDELKZ().equals("PA")) {
					if (!plnums.contains(e.getDELNR())) {
						plnums.add(e.getDELNR());
					}
				}

			} else if (e.getDELKZ().equals("SB")) {

				if (!plnums.contains(e.getAUFVR())) {
					plnums.add(e.getAUFVR());
				}
			}

			if (!e.getKUNNR().equals("")) {
				if (!kunnrs.contains(e.getKUNNR())) {
					kunnrs.add(e.getKUNNR());
				}
			}

			list.add(e);
			reader.next();
		}

		map.clear();
		map = null;
		function = null;
		reader = null;
		connect = null;
		table.clear();
		table = null;

		HashMap<String, String> afpoTable = new HashMap<String, String>();
		HashMap<String, String> lfa1Table = new HashMap<String, String>();
		HashMap<String, String> plafTable = new HashMap<String, String>();
		HashMap<String, String> kna1Table = new HashMap<String, String>();

		Connection conn = getSapPrdConnection();
		;
		PreparedStatement pstm;
		ResultSet rst;

		String sql;
		if (!aufnrs.isEmpty()) {
			sql = "SELECT AUFNR,MATNR FROM SAPSR3.AFPO WHERE MANDT='168'"//
					+ " AND AUFNR IN (" + toSqlString(aufnrs) + ") ";
			pstm = conn.prepareStatement(sql);
			rst = pstm.executeQuery();
			while (rst.next()) {
				afpoTable.put(rst.getString("AUFNR"), rst.getString("MATNR"));
			}
			rst.close();
			pstm.close();
		}

		if (!lifnrs.isEmpty()) {
			sql = "SELECT LIFNR,SORTL FROM SAPSR3.LFA1 WHERE MANDT='168'"//
					+ " AND LIFNR IN (" + toSqlString(lifnrs) + ") ";
			pstm = conn.prepareStatement(sql);
			rst = pstm.executeQuery();
			while (rst.next()) {
				lfa1Table.put(rst.getString("LIFNR"), rst.getString("SORTL"));
			}
			rst.close();
			pstm.close();
		}

		if (!kunnrs.isEmpty()) {
			sql = "SELECT KUNNR,SORTL FROM SAPSR3.KNA1 WHERE MANDT='168'"//
					+ " AND KUNNR IN (" + toSqlString(kunnrs) + ") ";
			pstm = conn.prepareStatement(sql);
			rst = pstm.executeQuery();
			while (rst.next()) {
				kna1Table.put(rst.getString("KUNNR"), rst.getString("SORTL"));
			}
			rst.close();
			pstm.close();
		}

		if (!plnums.isEmpty()) {
			sql = "SELECT PLNUM,MATNR FROM SAPSR3.PLAF WHERE MANDT='168'" //
					+ " AND PLNUM IN (" + toSqlString(plnums) + ") ";
			pstm = conn.prepareStatement(sql);
			rst = pstm.executeQuery();
			while (rst.next()) {
				plafTable.put(rst.getString("PLNUM"), rst.getString("MATNR"));
			}
			rst.close();
			pstm.close();
		}

		conn.close();

		aufnrs.clear();
		aufnrs = null;
		lifnrs.clear();
		lifnrs = null;
		plnums.clear();
		plnums = null;

		String str;

		for (MRP_ITEMS m : list) {
			if (m.getDELKZ().equals("AR") || m.getDELKZ().equals("FE")) {
				m.setBAUGR(afpoTable.get(m.getDEL12()));
				str = m.getDELNR();
				m.setDELNR(m.getDEL12().substring(2));
				m.setDEL12(str);
			} else if (m.getDELKZ().equals("BE") || m.getDELKZ().equals("PA") || m.getDELKZ().equals("BA")) {
				m.setSORTL(lfa1Table.get(m.getLIFNR()));
				if (m.getDELKZ().equals("PA")) {
					m.setBAUGR(plafTable.get(m.getDELNR()));
				}
				if (m.getDELKZ().equals("BE")){
					m.setBAUGR("PO Date:"+Helper.fmtDate(m.getBEDAT(), "yyyyMMdd"));
				}
			} else if (m.getDELKZ().equals("SB")) {
				str = m.getDELNR();
				m.setBAUGR(plafTable.get(m.getAUFVR()));
				m.setDELNR(m.getAUFVR());
				m.setDEL12(str);
			}

			if (!m.getKUNNR().equals("")) {
				m.setSORTL(kna1Table.get(m.getKUNNR()));
			}
		}

		afpoTable.clear();
		lfa1Table.clear();
		kna1Table.clear();
		plafTable.clear();
		afpoTable = null;
		lfa1Table = null;
		kna1Table = null;
		plafTable = null;

		req.setAttribute("list", list);

		render(req,resp,"/WEB-INF/pages/enquiry/MD04B.jsp");
	}
}
