package imes.job;

import imes.core.sap.SapRfcConnection;
import imes.core.sap.TableAdapterReader;

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
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class RfcBapiGoodsmvtCreate311 implements Runnable {
	private ServletContext context;

	private Logger logger = Logger.getLogger(this.getClass());

	public RfcBapiGoodsmvtCreate311(ServletContext context) {
		super();
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		logger.info("RfcBapiGoodsmvtCreate311 Start Up");
		try {
			//ReadWMD04POS();
			ReadStkLocationTransferData();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("RfcBapiGoodsmvtCreate311 Shutdown");
	}
	
	public void ReadStkLocationTransferData()  throws Exception{
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		HashMap<String,String> map;
		
		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
		String werks,matnr,lgort,charg,nlogrt,menge,id;
		String sql="SELECT * FROM SAPWEB.T002 WHERE WERKS = '381A' AND MENGE > 0 AND ID > 4000";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			id = rst.getString("ID");
			werks = rst.getString("WERKS");
			matnr = rst.getString("MATNR");
			lgort = rst.getString("LGORT");
			charg = rst.getString("CHARG");
			menge = rst.getString("MENGE");
			nlogrt = rst.getString("NLOGRT");
			map = new HashMap<String, String>();
			map.put("ID", id);
			map.put("WERKS", werks);
			map.put("MATNR", matnr);
			map.put("LGORT", lgort);
			map.put("CHARG", charg);
			map.put("MENGE", menge);
			map.put("NLOGRT", nlogrt);
			list.add(map);
		}
		rst.close();
		pstm.close();
		conn.close();
		
		CreateStockMovement(list);
	}
	
	public void CreateStockMovement(List<HashMap<String, String>> list) throws Exception {
		SapRfcConnection rfc;
		JCoFunction function, commit;
		// JCoRepository repos;
		JCoDestination dest;
		JCoStructure header;
		JCoTable lines;
		
		rfc = new SapRfcConnection();

		// repos = rfc.getRepos();
		dest = rfc.getDest();

		JCoContext.begin(dest);

		commit = rfc.getFunction("BAPI_TRANSACTION_COMMIT");
		commit.getImportParameterList().setValue("WAIT", "X");

		function = rfc.getFunction("BAPI_GOODSMVT_CREATE");
		header = function.getImportParameterList().getStructure("GOODSMVT_HEADER");
		header.setValue("PSTNG_DATE", "20140518");
		header.setValue("DOC_DATE", "20140518");
		header.setValue("PR_UNAME", "LUM.LIN");
		header.setValue("HEADER_TXT","FLOOD-20140511");

		function.getImportParameterList().getStructure("GOODSMVT_CODE").setValue("GM_CODE", "04");
		
		lines = function.getTableParameterList().getTable("GOODSMVT_ITEM");
		int i = 0;
		
		for (HashMap<String,String> m : list) {
			i = i + 1;
			System.out.println(i+"/"+list.size()+"-"+m.get("MATNR") + "-" + m.get("WERKS") + "_" + m.get("CHARG"));
			lines.appendRow();
			lines.setValue("MATERIAL", m.get("MATNR"));
			lines.setValue("PLANT", m.get("WERKS"));
			lines.setValue("STGE_LOC", m.get("LGORT"));
			lines.setValue("MOVE_STLOC", m.get("NLOGRT"));
			lines.setValue("BATCH", m.get("CHARG"));
			lines.setValue("MOVE_TYPE", "311");
			lines.setValue("ENTRY_QNT", m.get("MENGE"));
			
		}
		
		function.execute(dest);
		JCoTable returnMessage = function.getTableParameterList().getTable("RETURN");

		TableAdapterReader reader = new TableAdapterReader(returnMessage);
		for (int j = 0; j < reader.size(); j++) {
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
