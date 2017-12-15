package imes.job;

import imes.core.Helper;
import imes.core.sap.SapRfcConnection;
import imes.core.sap.TableAdapterReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

public class RfcBapiGoodsmvtCreatePoGoodsReceipt implements Runnable {
	private ServletContext context;

	private Logger logger = Logger.getLogger(this.getClass());

	public RfcBapiGoodsmvtCreatePoGoodsReceipt(ServletContext context) {
		super();
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		logger.info("RfcBapiGoodsmvtCreatePoGoodsReceipt Start Up");
		try {
			// ReadWMD04POS();
			ReadPoGoodsReceipWithStatusAllocated();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("RfcBapiGoodsmvtCreatePoGoodsReceipt Shutdown");
	}

	@SuppressWarnings("unchecked")
	public void ReadPoGoodsReceipWithStatusAllocated() throws Exception {
		List<HashMap<String, Object>> supplier_delivery_notes = new ArrayList<HashMap<String, Object>>();
		List<HashMap<String, String>> po_receipts;
		HashMap<String, Object> map;
		HashMap<String, String> field;

		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
		//String sql = "SELECT A.LIFNR,A.LIFDN FROM SAPWEB.ZBC_PO_RECEIPTS A WHERE A.STATUS='ALC' GROUP BY A.LIFNR,A.LIFDN ORDER BY A.LIFNR, A.LIFDN";
		//String sql = "SELECT A.LIFNR,A.LIFDN FROM SAPWEB.ZBC_PO_RECEIPTS A WHERE A.STATUS='ALC' AND LIFDN='ITX1511015' GROUP BY A.LIFNR,A.LIFDN ORDER BY A.LIFNR, A.LIFDN";
		String sql = "select a.lifnr,a.lifdn,nvl(b.custom_invoice,' ') custom_invoice " //
				+ "  from sapweb.zbc_po_receipts a " //
				+ "    join sapweb.zbc_po_receipt_lines b on b.po_receipt_id=a.id " //
				+ "  where (a.status='ALC' or b.status='ALC') and b.status <> 'UPD'" //
				//+ "  and a.lifnr='BV THE BU'"
				+ "  group by a.lifnr,a.lifdn,b.custom_invoice " //
		;
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			map = new HashMap<String, Object>();
			map.put("LIFNR", rst.getString("LIFNR"));
			map.put("LIFDN", rst.getString("LIFDN"));
			map.put("CUSTOM_INVOICE", rst.getString("CUSTOM_INVOICE"));
			supplier_delivery_notes.add(map);
		}
		rst.close();
		pstm.close();

		sql = "SELECT A.LIFNR, A.LIFDN, A.MATNR, A.WERKS, A.PKG_NO, A.DATE_CODE, A.MFG_DATE, " //
				+ "       B.ID, B.PO_RECEIPT_ID, B.EBELN, B.EBELP, B.VMENG, B.CHARG, B.MEINS, NVL(B.CUSTOM_INVOICE,' ') CUSTOM_INVOICE " //
				+ "  FROM SAPWEB.ZBC_PO_RECEIPTS A, SAPWEB.ZBC_PO_RECEIPT_LINES B " //
				+ " WHERE     B.PO_RECEIPT_ID = A.ID " //
				+ "       AND (A.STATUS = 'ALC' OR B.STATUS = 'ALC')" //
				+ "       AND B.STATUS <> 'UPD'"
				+ "       AND A.LIFNR = ? " //
				+ "       AND A.LIFDN = ? " //
				+ "       AND NVL(B.CUSTOM_INVOICE,' ') = ? " //
		;
		pstm = conn.prepareStatement(sql);
		
		for (HashMap<String, Object> delivery_note : supplier_delivery_notes) {
			po_receipts = new ArrayList<HashMap<String, String>>();
			pstm.setString(1, (String) delivery_note.get("LIFNR"));
			pstm.setString(2, (String) delivery_note.get("LIFDN"));
			pstm.setString(3, (String) delivery_note.get("CUSTOM_INVOICE"));
			rst = pstm.executeQuery();
			while (rst.next()) {
				field = new HashMap<String, String>();
				field.put("LIFNR", rst.getString("LIFNR"));
				field.put("LIFDN", rst.getString("LIFDN"));
				field.put("MATNR", rst.getString("MATNR"));
				field.put("WERKS", rst.getString("WERKS"));
				field.put("PKG_NO", rst.getString("PKG_NO"));
				field.put("DATE_CODE", rst.getString("DATE_CODE"));
				field.put("MFG_DATE", rst.getString("MFG_DATE"));
				field.put("ID", rst.getString("ID"));
				field.put("PO_RECEIPT_ID", rst.getString("PO_RECEIPT_ID"));
				field.put("EBELN", rst.getString("EBELN"));
				field.put("EBELP", rst.getString("EBELP"));
				field.put("VMENG", rst.getString("VMENG"));
				field.put("MEINS", rst.getString("MEINS"));
				field.put("CHARG", rst.getString("CHARG"));
				field.put("CUSTOM_INVOICE", rst.getString("CUSTOM_INVOICE"));
				po_receipts.add(field);
			}	
			rst.close();
			delivery_note.put("PO_RECEIPTS", po_receipts);
		}
		pstm.close();
		conn.close();
		
		HashMap<String, String> results;
		for (HashMap<String, Object> delivery_note : supplier_delivery_notes) {
			try {
				System.out.println(delivery_note.get("LIFNR") + " " + delivery_note.get("LIFDN")+ " " + delivery_note.get("CUSTOM_INVOICE"));
				results = CreateStockMovement((ArrayList<HashMap<String, String>>)delivery_note.get("PO_RECEIPTS"));
				if (results.get("COMMIT").equals("TRUE")) {
					update_po_receipt_line((ArrayList<HashMap<String, String>>)delivery_note.get("PO_RECEIPTS"), results.get("MBLNR"), results.get("MJAHR"));
				}else {
					update_po_receipt_rfc_msg((ArrayList<HashMap<String, String>>)delivery_note.get("PO_RECEIPTS"), results.get("RFC_MSG"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}

	public HashMap<String, String> CreateStockMovement(List<HashMap<String, String>> list) throws Exception {
		SapRfcConnection rfc;
		JCoFunction function, commit, rollback;
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

		rollback = rfc.getFunction("BAPI_TRANSACTION_ROLLBACK");
		
		function = rfc.getFunction("BAPI_GOODSMVT_CREATE");
		function.getImportParameterList().getStructure("GOODSMVT_CODE").setValue("GM_CODE", "01");
		//function.getImportParameterList().setValue("TESTRUN", "X");
		lines = function.getTableParameterList().getTable("GOODSMVT_ITEM");
		int i = 0;
		
		String lifdn = "";
		String custom_invoice = " ";
		for (HashMap<String, String> m : list) {
			i = i + 1;
			System.out.println(i + "/" + list.size() + "-" + m.get("MATNR") + "-" + m.get("WERKS") + "_" + m.get("CHARG")+"_"+m.get("EBELN")+"_"+m.get("EBELP"));
			lines.appendRow();
			lines.setValue("MATERIAL", m.get("MATNR"));
			lines.setValue("PLANT", m.get("WERKS"));
			//lines.setValue("STGE_LOC", m.get("LGORT"));
			//lines.setValue("MOVE_STLOC", m.get("NLOGRT"));
			//lines.setValue("BATCH", m.get("CHARG"));
			lines.setValue("MOVE_TYPE", "101");
			lines.setValue("MVT_IND", "B");
			lines.setValue("ENTRY_QNT", m.get("VMENG"));
			lines.setValue("ENTRY_UOM", m.get("MEINS"));
			lines.setValue("PO_NUMBER", m.get("EBELN"));
			lines.setValue("PO_ITEM", m.get("EBELP"));
			lines.setValue("VENDRBATCH", m.get("DATE_CODE"));
			lines.setValue("PROD_DATE", m.get("MFG_DATE"));
			lines.setValue("VENDOR", m.get("LIFNR"));
			lines.setValue("ITEM_TEXT", m.get("ID"));
			lifdn = m.get("LIFDN");
			//if ((m.get("CUSTOM_INVOICE") != null) && (!custom_invoice.equals(" "))) {
				custom_invoice = m.get("CUSTOM_INVOICE");
			//}
			//lifdn = custom_invoice;
		}
		header = function.getImportParameterList().getStructure("GOODSMVT_HEADER");
		header.setValue("PSTNG_DATE", Helper.fmtDate(new Date(), "yyyyMMdd"));
		header.setValue("DOC_DATE", Helper.fmtDate(new Date(), "yyyyMMdd"));
		if (custom_invoice.equals(" ")) {
			header.setValue("BILL_OF_LADING", lifdn); //Invoice number
		}else {
//			String bill_of_lading = custom_invoice;
//			if (custom_invoice.length() > 10) {
//				bill_of_lading = custom_invoice.substring(0, 9);
//			}
			header.setValue("BILL_OF_LADING", custom_invoice); //Invoice number
		}
		header.setValue("REF_DOC_NO", lifdn);
		header.setValue("PR_UNAME", "LUM.LIN");
		header.setValue("HEADER_TXT", lifdn);
		
		
		function.execute(dest);
		System.out.println("executed");
		JCoTable returnMessage = function.getTableParameterList().getTable("RETURN");
		
		String rfc_msg = "";
		boolean isError = false;
		HashMap<String, String> results = new HashMap<String, String>();
		
		TableAdapterReader reader = new TableAdapterReader(returnMessage);
		for (int j = 0; j < reader.size(); j++) {
			if (!rfc_msg.equals("")) {
				rfc_msg = rfc_msg + "\n";
			}
			rfc_msg = rfc_msg + "TYPE:" + reader.get("TYPE") + " ID:" + reader.get("ID") + " NUMBER:" + reader.get("NUMBER") + " MESSAGE:" + reader.get("MESSAGE");
			System.out.println(rfc_msg);
			if (reader.get("TYPE").equals("E")) {
				isError = true;
			}
			reader.next();
		}
		results.put("RFC_MSG", rfc_msg);
		results.put("MBLNR", function.getExportParameterList().getString("MATERIALDOCUMENT"));
		results.put("MJAHR", function.getExportParameterList().getString("MATDOCUMENTYEAR"));
		results.put("COMMIT", "FALSE");
		if (!isError) {
			commit.execute(dest);
			results.put("COMMIT", "TRUE");
		}else {
			rollback.execute(dest);
		}
		JCoContext.end(dest);

		rfc = null;
		
		return results;
	}

	private void update_po_receipt_line(List<HashMap<String, String>> list, String mblnr, String mjahr) throws Exception {
		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
		String sql = "UPDATE SAPWEB.ZBC_PO_RECEIPT_LINES SET STATUS='UPD', MBLNR=?, MJAHR=? WHERE ID = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		for (HashMap<String, String> m : list) {
			pstm.setString(1, mblnr);
			pstm.setString(2, mjahr);
			pstm.setString(3, m.get("ID"));
			pstm.executeUpdate();
		}
		pstm.close();
		sql = "UPDATE SAPWEB.ZBC_PO_RECEIPTS SET STATUS='PST', RFC_MSG='' WHERE ID = ?";
		pstm = conn.prepareStatement(sql);
		for (HashMap<String, String> m : list) {
			pstm.setString(1, m.get("PO_RECEIPT_ID"));
			pstm.executeUpdate();
		}
		pstm.close();
		conn.close();
	}

	private void update_po_receipt_rfc_msg(List<HashMap<String, String>> list, String rfc_msg) throws Exception {
		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
		String sql = "UPDATE SAPWEB.ZBC_PO_RECEIPTS SET RFC_MSG=? WHERE ID = ?";
		PreparedStatement pstm = conn.prepareStatement(sql);
		for (HashMap<String, String> m : list) {
			pstm.setString(1, rfc_msg);
			pstm.setString(2, m.get("PO_RECEIPT_ID"));
			pstm.executeUpdate();
		}
		pstm.close();
		conn.close();
	}
	
	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}
}
