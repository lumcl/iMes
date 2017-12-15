package imes.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import imes.core.sap.SapRfcConnection;
import imes.core.sap.TableAdapterReader;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class RfcBapiPoChageNetPrice implements Runnable {
	private ServletContext context;

	private Logger logger = Logger.getLogger(this.getClass());

	public RfcBapiPoChageNetPrice(ServletContext context) {
		super();
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		logger.info("RfcBapiPoChageNetPrice Start Up");
		try {
			// ReadOpenPoWithoutConfirmationControl();
			// ReadPoSchedule();
			ReadWMD04POS();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("RfcBapiPoChageNetPrice Shutdown");
	}

	public void ReadWMD04POS() throws Exception {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String,String> map;
		
		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
/*		
		String sql = "SELECT A.EKORG, A.EKGRP, A.MATKL, A.BEDAT, A.LIFNR, A.WERKS, A.EBELN, " //
				+ "       A.EBELP, A.MATNR, A.TXZ01, A.NETPR, A.PEINH, A.WAERS, B.MINPR NEWPR, A.WEMNG " //
				+ "  FROM IPS.WMD04POS A, " //
				+ "       (  SELECT LIFNR, MATNR, WERKS, WAERS, PEINH, MIN (NETPR) MINPR, " //
				+ "                 MAX (NETPR) MAXPR " //
				+ "            FROM IPS.WMD04POS " //
				+ "        GROUP BY LIFNR, MATNR, WERKS, WAERS, PEINH " //
				+ "          HAVING MIN (NETPR) <> MAX (NETPR)) B " //
				+ " WHERE     A.LIFNR = B.LIFNR " //
				+ "       AND A.MATNR = B.MATNR " //
				+ "       AND A.WERKS = B.WERKS " //
				+ "       AND A.WAERS = B.WAERS " //
				+ "       AND A.PEINH = B.PEINH " //
				+ "       AND A.NETPR > B.MINPR " //
				+ "       AND A.WEMNG = 0 " //
*/
		
		String sql = "SELECT A.WERKS, A.LIFNR, A.SORTL, A.EBELN, A.EBELP, A.MATNR, A.TXZ01, " //
				+ "       A.EKGRP, A.MATKL, A.MENGE, A.WEMNG, A.WAERS, A.NETPR, B.NETPR NEWPR, " //
				+ "       B.CHGPO, B.BDBH, B.QHSJ, B.QHYH " //
				+ "  FROM SAPWEB.WD_MD04_POS A, SAPWEB.WD_D089S B " //
				+ " WHERE     A.LIFNR = B.LIFNR " //
				+ "       AND A.MATNR = B.MATNR " //
				+ "       AND A.WERKS = B.WERKS " //
				+ "       AND A.WAERS = B.WAERS " //
				+ "       AND A.PEINH = B.PEINH " //
				+ "       AND A.NETPR <> B.NETPR " //
		//		+ " AND A.WERKS IN ('481A','482A')"
				+ "       AND B.CHGPO <> '3' " //
		;
		String ebeln,ebelp,waers;
		double netpr;
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			ebeln = rst.getString("EBELN");
			ebelp = rst.getString("EBELP");
			waers = rst.getString("WAERS");
			netpr = rst.getDouble("NEWPR");
			System.out.println(ebeln + "-" + ebelp);
			map = new HashMap<String, String>();
			map.put("EBELN", ebeln);
			map.put("EBELP", ebelp);
			map.put("WAERS", waers);
			map.put("NETPR", Double.toString(netpr));
			list.add(map);
		}
		rst.close();
		pstm.close();
		conn.close();
		
		int i = 0;
		
		for (HashMap<String,String> m : list) {
			ebeln = m.get("EBELN");
			ebelp = m.get("EBELP");
			waers = m.get("WAERS");
			netpr = Double.parseDouble(m.get("NETPR"));
			i++;
			
			System.out.println(i+"/"+list.size()+"-"+ebeln + "-" + ebelp);

			ChangePoNetPrice(ebeln,ebelp,waers,netpr);

		}
	}
	
	public void ChangePoNetPrice(String ebeln, String ebelp, String waers, double netpr) throws Exception {
		SapRfcConnection rfc;
		JCoFunction function, commit;
		JCoTable poItem, poItemx, poCond, poCondx;
		// JCoRepository repos;
		JCoDestination dest;

		rfc = new SapRfcConnection();

		// repos = rfc.getRepos();
		dest = rfc.getDest();

		JCoContext.begin(dest);

		commit = rfc.getFunction("BAPI_TRANSACTION_COMMIT");
		commit.getImportParameterList().setValue("WAIT", "X");

		function = rfc.getFunction("BAPI_PO_CHANGE");
		function.getImportParameterList().setValue("PURCHASEORDER", ebeln);

		poItem = function.getTableParameterList().getTable("POITEM");
		poItem.appendRow();
		poItem.setValue("PO_ITEM", ebelp);
		poItem.setValue("NET_PRICE", netpr);

		poItemx = function.getTableParameterList().getTable("POITEMX");
		poItemx.appendRow();
		poItemx.setValue("PO_ITEM", ebelp);
		poItemx.setValue("NET_PRICE", "X");
		poItemx.setValue("PO_ITEMX", "X");

		poCond = function.getTableParameterList().getTable("POCOND");
		poCond.appendRow();
		poCond.setValue("ITM_NUMBER", ebelp);
		poCond.setValue("COND_TYPE", "PB00");
		poCond.setValue("COND_VALUE", netpr);
		poCond.setValue("CURRENCY", waers);
		poCond.setValue("CHANGE_ID", "U");

		poCondx = function.getTableParameterList().getTable("POCONDX");
		poCondx.appendRow();
		poCondx.setValue("ITM_NUMBER", ebelp);
		poCondx.setValue("ITM_NUMBERX", "X");
		poCondx.setValue("COND_TYPE", "X");
		poCondx.setValue("COND_VALUE", "X");
		poCondx.setValue("CURRENCY", "X");
		poCondx.setValue("CHANGE_ID", "X");

		function.execute(dest);

		JCoTable returnMessage = function.getTableParameterList().getTable("RETURN");

		TableAdapterReader reader = new TableAdapterReader(returnMessage);
		for (int i = 0; i < reader.size(); i++) {
			System.out.println("TYPE:" + reader.get("TYPE") + " ID:" + reader.get("ID") + " NUMBER:" + reader.get("NUMBER") + " MESSAGE:" + reader.get("MESSAGE"));
			reader.next();
		}

		commit.execute(dest);

		JCoContext.end(dest);

		rfc = null;
	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}
}
