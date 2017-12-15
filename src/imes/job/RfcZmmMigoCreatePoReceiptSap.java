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
import com.sap.conn.jco.JCoTable;

public class RfcZmmMigoCreatePoReceiptSap implements Runnable {
	private ServletContext context;

	private Logger logger = Logger.getLogger(this.getClass());

	public RfcZmmMigoCreatePoReceiptSap(ServletContext context) {
		super();
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("RfcZmmMigoCreatePoReceiptSap Start Up");
		try {
			ProcessPoReceiptSaps();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("RfcZmmMigoCreatePoReceiptSap Shutdown");
	}

	private void ProcessPoReceiptSaps() throws Exception {

		List<HashMap<String, Object>> list = ReadPoReceiptSaps();

		ExecuteSapRfc(list);

		list.clear();
		list = null;
	}

	private void ExecuteSapRfc(List<HashMap<String, Object>> list) throws Exception {
		SapRfcConnection rfc;
		JCoFunction function, commit;
		JCoTable impDat;
		// JCoRepository repos;
		JCoDestination dest;

		rfc = new SapRfcConnection();

		// repos = rfc.getRepos();
		dest = rfc.getDest();

		JCoContext.begin(dest);

		commit = rfc.getFunction("BAPI_TRANSACTION_COMMIT");
		commit.getImportParameterList().setValue("WAIT", "X");

		function = rfc.getFunction("ZMM_MIGO_CREATE");

		impDat = function.getTableParameterList().getTable("IMP_DAT");

		for (HashMap<String, Object> row : list) {
			impDat.appendRow();
			impDat.setValue("LIFNR", row.get("lifnr"));
			impDat.setValue("REFDN", row.get("lifdn"));
			impDat.setValue("IMBIL", row.get("lifdn"));
			impDat.setValue("MATNR", row.get("matnr"));
			impDat.setValue("WERKS", row.get("werks"));
			impDat.setValue("BATCH", row.get("charg"));
			impDat.setValue("PRDAT", row.get("mfgdt"));
			impDat.setValue("PONUM", row.get("ebeln"));
			impDat.setValue("POITM", row.get("ebelp"));
			impDat.setValue("EBQTY", row.get("menge"));
			impDat.setValue("VENDRBATCH", row.get("charg"));
			impDat.setValue("HDTXT", row.get("lifdn"));
			
			System.out.println(row.get("ebeln")+":"+row.get("ebelp")+":"+row.get("id"));
		}

		function.execute(dest);

		JCoTable rtn_rst = function.getTableParameterList().getTable("RTN_RST");
		TableAdapterReader reader = new TableAdapterReader(rtn_rst);

		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();

		String sql = "UPDATE MES.PO_RECEIPT_SAPS " //
				+ "   SET STATUS = '90', RFC_MTYPE = ?, RFC_MESGE = ?, MBLNR = ?, MJAHR = ?, " //
				+ "       ZEILE = ?, BUDAT = TO_CHAR ( SYSDATE, 'YYYYMMDD'), RFC_AT = SYSDATE " //
				+ " WHERE     STATUS = '20' " //
				+ "       AND EBELN = ? " //
				+ "       AND EBELP = ? " //
				+ "       AND CHARG = ? " //
				+ "       AND MENGE = ? " //
		;
		
		PreparedStatement pstm = conn.prepareStatement(sql);

		String[] buf;
		String mblnr, mjahr, zeile;
		
		for (int i = 0; i < reader.size(); i++) {

			System.out.println("MTYPE:"+reader.get("MTYPE"));
			System.out.println("MESGE:"+reader.get("MESGE"));
			System.out.println("PONUM:"+reader.get("PONUM"));
			System.out.println("POITM:"+reader.get("POITM"));
			System.out.println("BATCH:"+reader.get("BATCH"));
			System.out.println("EBQTY:"+reader.get("EBQTY"));
			
			buf = null;
			mblnr = " ";
			mjahr = " ";
			zeile = " ";
			
			if (reader.get("MESGE") != null) {
				buf = reader.get("MESGE").split(" ");
				if (buf.length > 2) {
					mblnr = buf[0];
					mjahr = buf[1];
					zeile = buf[2];
				}
			}
			
			pstm.setString(1, reader.get("MTYPE"));
			pstm.setString(2, reader.get("MESGE"));
			pstm.setString(3, mblnr);
			pstm.setString(4, mjahr);
			pstm.setString(5, zeile);
			pstm.setString(6, reader.get("PONUM"));
			pstm.setString(7, reader.get("POITM"));
			pstm.setString(8, reader.get("BATCH"));
			pstm.setDouble(9, reader.getDouble("EBQTY"));
			
			pstm.executeUpdate();
			
			reader.next();
		}

		pstm.close();
		conn.close();

		commit.execute(dest);

		JCoContext.end(dest);

		rfc = null;
	}

	private List<HashMap<String, Object>> ReadPoReceiptSaps() throws Exception {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map;

		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
		String sql = "SELECT ID, EBELN, EBELP, LIFDN, CHARG, MFGDT, MENGE, WERKS FROM MES.PO_RECEIPT_SAPS WHERE STATUS = '20' AND ROWNUM=1 ORDER BY MATNR,EBELN,EBELP";
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			
			map = new HashMap<String, Object>();
			map.put("ebeln", rst.getString("EBELN"));
			map.put("ebelp", rst.getString("EBELP"));
			map.put("lifdn", rst.getString("LIFDN"));
			map.put("charg", rst.getString("CHARG"));
			map.put("mfgdt", rst.getString("MFGDT"));
			map.put("werks", rst.getString("WERKS"));
			map.put("id", rst.getInt("ID"));
			map.put("menge", rst.getDouble("MENGE"));
			
			list.add(map);
		}
		rst.close();
		pstm.close();
		conn.close();

		return list;
	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

}
