package imes.job;

import imes.core.sap.SapRfcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class RfcBapiPoChangeEket implements Runnable {
	private ServletContext context;

	private Logger logger = Logger.getLogger(this.getClass());

	public RfcBapiPoChangeEket(ServletContext context) {
		super();
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("RfcBapiPoChangeEket Start Up");
		try {
			//ReadOpenPoWithoutConfirmationControl();
			ReadPoSchedule();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("RfcBapiPoChangeEket Shutdown");
	}

	
	public void ReadOpenPoWithoutConfirmationControl() throws Exception {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String,String> map;
		
		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
		
		String sql = "SELECT /*+DRIVING_SITE(B)*/ " //
				+ "      B.EBELN, A.EBELP " //
				+ "  FROM SAPSR3.EKPO@SAPP B, " //
				+ "       (SELECT DISTINCT A.EBELN, A.EBELP " //
				+ "          FROM IPS.POSCHEDULE A " //
				+ "         WHERE LIFNR IN (SELECT DISTINCT LIFNR FROM IPS.SUPPLIER_USERS) " //
				+ "               AND SORT2 <> '02') A " //
				+ " WHERE     B.MANDT = '168' " //
				+ "       AND B.EBELN = A.EBELN " //
				+ "       AND B.EBELP = A.EBELP " //
				+ "       AND B.BSTAE = ' ' " //
				+ " AND ROWNUM = 1"
		;
		String ebeln, ebelp;
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			ebeln = rst.getString("EBELN");
			ebelp = rst.getString("EBELP");
			System.out.println(ebeln + "-" + ebelp);
			map = new HashMap<String, String>();
			map.put("EBELN", ebeln);
			map.put("EBELP", ebelp);
			list.add(map);
		}
		rst.close();
		pstm.close();
		conn.close();

		int i = 0;
		
		for (HashMap<String,String> m : list) {
			ebeln = m.get("EBELN");
			ebelp = m.get("EBELP");
			
			i++;
			
			System.out.println(i+"/"+list.size()+"-"+ebeln + "-" + ebelp);

			ChangePoItemConfCtrl(ebeln, ebelp);

		}
		
	}
	
	public void ReadPoSchedule() throws Exception {
		
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String,String> map;
		
		Connection conn = ((DataSource) getContext().getAttribute("sapcods")).getConnection();
		String sql = "  SELECT POS.EBELN, " //
				+ "         POS.EBELP, " //
				+ "         POS.ETENR, " //
				+ "         POS.EINDT, " //
				+ "         EKET.EINDT EKET " //
				+ "    FROM SAPSR3.EKET@SAPP, " //
				+ "         (  SELECT EBELN, " //
				+ "                   EBELP, " //
				+ "                   ETENR, " //
				+ "                   MIN (REPLACE ( DAT01D, '-', '')) EINDT " //
				+ "              FROM IT.POSCHEDULE " //
				+ "             WHERE     WERKS IN ('481A', '482A') " //
				+ "                   AND SORT2 <> '02' " //
				+ "                   AND DAT01D <> '9999-99-99' " //
				+ "          GROUP BY EBELN, EBELP, ETENR) POS " //
				+ "   WHERE     EKET.MANDT = '168' " //
				+ "         AND EKET.EBELN = POS.EBELN " //
				+ "         AND EKET.EBELP = POS.EBELP " //
				+ "         AND EKET.ETENR = POS.ETENR " //
				+ "         AND EKET.EINDT <> POS.EINDT " //
				+ "         AND EKET.EBELN > '8' " //
				+ "ORDER BY EBELN, EBELP " //
		;
		
		String ebeln, ebelp, etenr, eindt;
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			ebeln = rst.getString("EBELN");
			ebelp = rst.getString("EBELP");
			etenr = rst.getString("ETENR");
			eindt = rst.getString("EINDT");
			System.out.println(ebeln + "-" + ebelp + "-" + etenr + "-" + eindt);
			
			map = new HashMap<String, String>();
			map.put("EBELN", ebeln);
			map.put("EBELP", ebelp);
			map.put("ETENR", etenr);
			map.put("EINDT", eindt);
			list.add(map);
		}
		rst.close();
		pstm.close();
		conn.close();
		
		int i = 0;
		
		for (HashMap<String,String> m : list) {
			ebeln = m.get("EBELN");
			ebelp = m.get("EBELP");
			etenr = m.get("ETENR");
			eindt = m.get("EINDT");
			
			i++;
			
			System.out.println(i+"/"+list.size()+"-"+ebeln + "-" + ebelp + "-" + etenr + "-" + eindt);

			ChangePoSchedule(ebeln, ebelp, etenr, eindt);

		}
	}

	public void ChangePoItemConfCtrl(String ebeln, String ebelp) throws Exception {
		SapRfcConnection rfc;
		JCoFunction function, commit;
		JCoTable impDat, impDatx;
		// JCoRepository repos;
		JCoDestination dest;

		rfc = new SapRfcConnection();

		// repos = rfc.getRepos();
		dest = rfc.getDest();

		JCoContext.begin(dest);

		commit = rfc.getFunction("BAPI_TRANSACTION_COMMIT");
		commit.getImportParameterList().setValue("WAIT", "X");

		function = rfc.getFunction("BAPI_PO_CHANGE");
		function.getImportParameterList().setValue("PURCHASEORDER", ebeln); // 00380
		impDat = function.getTableParameterList().getTable("POITEM");
		impDat.appendRow();
		impDat.setValue("PO_ITEM", ebelp);
		impDat.setValue("CONF_CTRL", "0001");

		impDatx = function.getTableParameterList().getTable("POITEMX");
		impDatx.appendRow();
		impDatx.setValue("PO_ITEM", ebelp);
		impDatx.setValue("PO_ITEMX", "X");
		impDatx.setValue("CONF_CTRL", "X");

		function.execute(dest);

		commit.execute(dest);

		JCoContext.end(dest);

		rfc = null;

	}
	
	public void ChangePoSchedule(String ebeln, String ebelp, String etenr, String eindt) throws Exception {
		SapRfcConnection rfc;
		JCoFunction function, commit;
		JCoTable impDat, impDatx;
		// JCoRepository repos;
		JCoDestination dest;

		rfc = new SapRfcConnection();

		// repos = rfc.getRepos();
		dest = rfc.getDest();

		JCoContext.begin(dest);

		commit = rfc.getFunction("BAPI_TRANSACTION_COMMIT");
		commit.getImportParameterList().setValue("WAIT", "X");

		function = rfc.getFunction("BAPI_PO_CHANGE");
		function.getImportParameterList().setValue("PURCHASEORDER", ebeln); // 00380
		impDat = function.getTableParameterList().getTable("POSCHEDULE");
		impDat.appendRow();
		impDat.setValue("PO_ITEM", ebelp);
		impDat.setValue("SCHED_LINE", etenr);
		impDat.setValue("DELIVERY_DATE", eindt);
		impDat.setValue("DEL_DATCAT_EXT", "D");

		impDatx = function.getTableParameterList().getTable("POSCHEDULEX");
		impDatx.appendRow();
		impDatx.setValue("PO_ITEM", ebelp);
		impDatx.setValue("PO_ITEMX", "X");
		impDatx.setValue("SCHED_LINE", etenr);
		impDatx.setValue("SCHED_LINEX", "X");
		impDatx.setValue("DELIVERY_DATE", "X");
		impDatx.setValue("DEL_DATCAT_EXT", "X");

		function.execute(dest);

		// JCoTable returnMessage =
		// function.getTableParameterList().getTable("RETURN");

		// for (int i = 0; i < returnMessage.getNumRows(); i++) {
		// String big5 = new
		// String(returnMessage.getString("MESSAGE").getBytes("iso8859-1"),
		// "BIG5");
		// System.out.println(big5);
		// returnMessage.nextRow();
		// }

		// TableAdapterReader reader = new TableAdapterReader(returnMessage);

		// for (int i = 0; i < reader.size(); i++) {
		// System.out.println("TYPE:" + reader.get("TYPE") + " ID:" +
		// reader.get("ID") + " NUMBER:" + reader.get("NUMBER") + " MESSAGE:" +
		// reader.get("MESSAGE"));
		// reader.next();
		// }

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
