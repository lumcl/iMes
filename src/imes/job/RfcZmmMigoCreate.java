package imes.job;

import imes.core.sap.SapRfcConnection;
import imes.core.sap.TableAdapterReader;
import imes.vpojo.OpenPo;
import imes.vpojo.PoReceipt;
import imes.vpojo.PoReceiptLine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class RfcZmmMigoCreate implements Runnable {

	private ServletContext context;

	private Logger logger = Logger.getLogger(this.getClass());

	public RfcZmmMigoCreate(ServletContext context) {
		super();
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("RfcZmmMigoCreate Start Up");
		try {
			List<PoReceipt> receipts = getPoReceipts();
			HashMap<String, List<OpenPo>> openpos = getOpenPo(receipts);
			List<PoReceipt> results = allocation(receipts, openpos);
			SapRfc(results);
			// allocPurchaseOrderLine(receipts);

		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("RfcZmmMigoCreate Shutdown");
	}

	public List<PoReceipt> allocation(List<PoReceipt> receipts, HashMap<String, List<OpenPo>> openpos) {

		List<PoReceipt> results = new ArrayList<PoReceipt>();
		PoReceipt result;
		String key;
		List<OpenPo> pos;
		double qty;
		for (PoReceipt receipt : receipts) {
			key = receipt.getLifnr() + "@" + receipt.getWerks() + "@" + receipt.getMatnr();
			if (openpos.containsKey(key)) {
				pos = openpos.get(key);
				for (OpenPo po : pos) {
					if (po.getBalqty() != 0) {

						if (po.getBalqty() > receipt.getRemainQty()) {
							qty = receipt.getRemainQty();
						} else {
							qty = po.getBalqty();
						}

						po.setAllocqty(po.getAllocqty() + qty);
						po.setBalqty(po.getBalqty() - qty);

						receipt.setAllocQty(receipt.getAllocQty() + qty);
						receipt.setRemainQty(receipt.getRemainQty() - qty);

						result = new PoReceipt();
						result.setAllocQty(receipt.getAllocQty());
						result.setCharg(receipt.getCharg());
						result.setEbeln(po.getEbeln());
						result.setEbelp(po.getEbelp());
						result.setErfmg(receipt.getErfmg());
						result.setHsdat(receipt.getHsdat());
						result.setId(receipt.getId());
						result.setImbil(receipt.getImbil());
						result.setLicha(receipt.getLicha());
						result.setLifnr(receipt.getLifnr());
						result.setMatnr(receipt.getMatnr());
						result.setMenge(qty);
						result.setRefdn(receipt.getRefdn());
						result.setRemainQty(receipt.getRemainQty());
						result.setWerks(receipt.getWerks());
						results.add(result);
					} // if (po.getBalqty() != 0)

					if (receipt.getRemainQty() == 0) {
						break;
					}
				} // for (OpenPo po : pos)
			} // (openpos.containsKey(key))
		}

		return results;
	}

	public void SapRfc(List<PoReceipt> results) {
		SapRfcConnection rfc;
		JCoFunction function;

		try {
			rfc = new SapRfcConnection();
			function = rfc.getFunction("ZMM_MIGO_CREATE");

			JCoTable impDat = function.getTableParameterList().getTable("IMP_DAT");

			for (PoReceipt receipt : results) {
				impDat.appendRow();
				impDat.setValue("LIFNR", receipt.getLifnr());
				impDat.setValue("REFDN", receipt.getRefdn());
				impDat.setValue("IMBIL", receipt.getImbil());
				impDat.setValue("MATNR", receipt.getMatnr());
				impDat.setValue("WERKS", receipt.getWerks());
				impDat.setValue("BATCH", receipt.getCharg());
				impDat.setValue("PRDAT", receipt.getHsdat());
				impDat.setValue("PONUM", receipt.getEbeln());
				impDat.setValue("POITM", receipt.getEbelp());
				impDat.setValue("EBQTY", receipt.getMenge());
				impDat.setValue("VENDRBATCH", receipt.getLicha());
			}
			rfc.execute(function);

			JCoTable rtn_rst = function.getTableParameterList().getTable("RTN_RST");
			TableAdapterReader reader = new TableAdapterReader(rtn_rst);
			
			PoReceiptLine line;
			for (int i = 0; i < reader.size(); i++) {
				
				line = new PoReceiptLine();
				line.setMtype(reader.get("MTYPE"));
				line.setMesge(reader.get("MESGE"));
				line.setCharg(reader.get("BATCH"));
				line.setEbeln(reader.get("PONUM"));
				line.setEbelp(reader.get("POITM"));
				line.setMenge(reader.getDouble("EBQTY"));
				updateStatus(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		rfc = null;
	}

	public HashMap<String, List<OpenPo>> getOpenPo(List<PoReceipt> receipts) throws Exception {
		HashMap<String, List<OpenPo>> openpos = new HashMap<String, List<OpenPo>>();

		for (PoReceipt p : receipts) {
			String key = p.getLifnr() + "@" + p.getWerks() + "@" + p.getMatnr();
			if (!openpos.containsKey(key)) {
				openpos.put(key, null);
			}
		}

		String sql = "  SELECT MDBS.EBELN, " //
				+ "         MDBS.EBELP, " //
				+ "         (MDBS.MENGE - MDBS.WEMNG) BALQTY " //
				+ "    FROM SAPSR3.MDBS, SAPSR3.EKKO " //
				+ "   WHERE     MDBS.MANDT = '168' " //
				+ "         AND MDBS.MATNR = ? " //
				+ "         AND MDBS.WERKS = ? " //
				+ "         AND MDBS.ELIKZ = ' ' " //
				+ "         AND MDBS.LOEKZ = ' ' " //
				+ "         AND EKKO.MANDT = '168' " //
				+ "         AND EKKO.EBELN = MDBS.EBELN " //
				+ "         AND EKKO.LIFNR = ? " //
				+ " AND (MDBS.MENGE - MDBS.WEMNG) > 0 " //
				+ "ORDER BY MDBS.EBELN, MDBS.EBELP " //
		;
		Connection conn = ((DataSource) getContext().getAttribute("sapprdds")).getConnection();
		PreparedStatement pstm;
		ResultSet rst;
		String lifnr, matnr, werks;
		String buf[];
		List<OpenPo> pos;
		OpenPo po;

		for (String key : openpos.keySet()) {

			buf = key.split("@");
			lifnr = buf[0];
			werks = buf[1];
			matnr = buf[2];

			pos = new ArrayList<OpenPo>();

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, matnr);
			pstm.setString(2, werks);
			pstm.setString(3, lifnr);

			rst = pstm.executeQuery();
			while (rst.next()) {
				po = new OpenPo();
				po.setEbeln(rst.getString("EBELN"));
				po.setEbelp(rst.getString("EBELP"));
				po.setBalqty(rst.getDouble("BALQTY"));
				po.setOpenqty(rst.getDouble("BALQTY"));
				pos.add(po);
			}
			rst.close();
			pstm.close();
			openpos.put(key, pos);
		}
		conn.close();

		return openpos;
	}

	public List<PoReceipt> getPoReceipts() {

		PoReceipt receipt;
		List<PoReceipt> receipts = new ArrayList<PoReceipt>();

		try {
			Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
			String sql = "SELECT ID, " //
					+ "       LIFNR, " //
					+ "       REFDN, " //
					+ "       IMBIL, " //
					+ "       MATNR, " //
					+ "       WERKS, " //
					+ "       LICHA, " //
					+ "       CHARG, " //
					+ "       HSDAT, " //
					+ "       ERFMG, " //
					+ "       SMDAT, " //
					+ "       REMAINQTY, " //
					+ "       ALLOCQTY, " //
					+ "       SMTIM " //
					+ "  FROM BCS.PO_RECEIPTS " //
					+ " WHERE SMFLG = 'A' " //
					+ " ORDER BY LIFNR,MATNR,ID " //
			;

			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rst = pstm.executeQuery();
			while (rst.next()) {

				receipt = new PoReceipt();
				receipt.setId(rst.getInt("ID"));
				receipt.setRefdn(rst.getString("REFDN"));
				receipt.setImbil(rst.getString("IMBIL"));
				receipt.setMatnr(rst.getString("MATNR"));
				receipt.setLifnr(rst.getString("LIFNR"));
				receipt.setWerks(rst.getString("WERKS"));
				receipt.setLicha(rst.getString("LICHA"));
				receipt.setCharg(rst.getString("CHARG"));
				receipt.setHsdat(rst.getString("HSDAT"));
				receipt.setErfmg(rst.getDouble("ERFMG"));
				receipt.setSmdat(rst.getString("SMDAT"));
				receipt.setSmtim(rst.getString("SMTIM"));

				receipt.setAllocQty(rst.getDouble("ALLOCQTY"));
				receipt.setRemainQty(rst.getDouble("REMAINQTY"));

				receipts.add(receipt);

			}
			rst.close();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return receipts;
	}

	public List<PoReceiptLine> allocPurchaseOrderLine(List<PoReceipt> receipts) throws Exception {

		List<PoReceiptLine> lines = new ArrayList<PoReceiptLine>();
		PoReceiptLine line;
		// sapprdds

		String sql = "  SELECT MDBS.EBELN, " //
				+ "         MDBS.EBELP, " //
				// + "         MDBS.MENGE, " //
				// + "         MDBS.WEMNG, " //
				+ "         (MDBS.MENGE - MDBS.WEMNG) BALQTY " //
				+ "    FROM SAPSR3.MDBS, SAPSR3.EKKO " //
				+ "   WHERE     MDBS.MANDT = '168' " //
				+ "         AND MDBS.MATNR = ? " //
				+ "         AND MDBS.WERKS = ? " //
				+ "         AND MDBS.ELIKZ = ' ' " //
				+ "         AND MDBS.LOEKZ = ' ' " //
				+ "         AND EKKO.MANDT = '168' " //
				+ "         AND EKKO.EBELN = MDBS.EBELN " //
				+ "         AND EKKO.LIFNR = ? " //
				+ " AND (MDBS.MENGE - MDBS.WEMNG) > 0 " //
				+ "ORDER BY MDBS.EBELN, MDBS.EBELP " //
		;

		Connection conn = ((DataSource) getContext().getAttribute("sapprdds")).getConnection();
		PreparedStatement pstm;
		ResultSet rst;

		String ebeln, ebelp;
		double balqty;

		for (PoReceipt p : receipts) {
			while (p.getRemainQty() > 0) {
				pstm = conn.prepareStatement(sql);

				pstm.setString(1, p.getMatnr());
				pstm.setString(2, p.getWerks());
				pstm.setString(3, p.getLifnr());
				rst = pstm.executeQuery();
				if (rst.next()) {

					ebeln = rst.getString("EBELN");
					ebelp = rst.getString("EBELP");
					balqty = rst.getDouble("BALQTY");
					System.out.println(ebeln + ":" + ebelp + ":" + balqty);
					line = new PoReceiptLine();
					line.setPo_receipt_id(p.getId());
					line.setEbeln(ebeln);
					line.setEbelp(ebelp);

					if (balqty < p.getRemainQty()) {
						line.setMenge(balqty);
						p.setAllocQty(p.getAllocQty() + balqty);
						p.setRemainQty(p.getRemainQty() - balqty);
					} else {
						line.setMenge(p.getRemainQty());
						p.setAllocQty(p.getAllocQty() + p.getRemainQty());
						p.setRemainQty(0);
					}
					System.out.println("Saprfc");
					SapRfcOld(p, line);
					System.out.println("UpdateStatus");
					updateStatus(p, line);

					lines.add(line);

				} else {
					break;
				}
				rst.close();
				pstm.close();
			}
		}
		conn.close();

		return lines;
	}

	public void SapRfcOld(PoReceipt receipt, PoReceiptLine line) {
		SapRfcConnection rfc;
		JCoFunction function;

		try {
			rfc = new SapRfcConnection();
			function = rfc.getFunction("ZMM_MIGO_CREATE");

			JCoTable impDat = function.getTableParameterList().getTable("IMP_DAT");
			impDat.appendRow();
			impDat.setValue("LIFNR", receipt.getLifnr());
			impDat.setValue("REFDN", receipt.getRefdn());
			impDat.setValue("IMBIL", receipt.getImbil());
			impDat.setValue("MATNR", receipt.getMatnr());
			impDat.setValue("WERKS", receipt.getWerks());
			impDat.setValue("BATCH", receipt.getCharg());
			impDat.setValue("PRDAT", receipt.getHsdat());
			impDat.setValue("PONUM", line.getEbeln());
			impDat.setValue("POITM", line.getEbelp());
			impDat.setValue("EBQTY", line.getMenge());
			impDat.setValue("VENDRBATCH", receipt.getLicha());

			rfc.execute(function);

			JCoTable rtn_rst = function.getTableParameterList().getTable("RTN_RST");
			TableAdapterReader reader = new TableAdapterReader(rtn_rst);

			line.setMtype(reader.get("MTYPE"));
			line.setMesge(reader.get("MESGE"));
			line.setCharg(reader.get("BATCH"));

			System.out.println(reader.get("PONUM"));
			System.out.println(reader.get("POITM"));
			System.out.println(reader.get("EBQTY"));

			// if (reader.get("MTYPE").equalsIgnoreCase("E")) {
			// receipt.setAllocQty(receipt.getAllocQty());
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}

		rfc = null;
	}

	public void updateStatus(PoReceipt receipt, PoReceiptLine line) throws Exception {

		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();

		String sql = "INSERT INTO BCS.PO_RECEIPT_LINES ( ID, PO_RECEIPT_ID, EBELN, EBELP, CHARG, " //
				+ "                                   MBLNR, MJAHR, ZEILE, MENGE, MTYPE, " //
				+ "                                   MESGE, CREATED_AT) " //
				+ "     VALUES (BCS.PO_RECEIPT_LINES_SEQ.NEXTVAL, ?, ?, ?, ? " //
				+ "             , ?, ?, ?, ?, ?, " //
				+ "             ?, SYSDATE) " //
		;
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, receipt.getId());
		pstm.setString(2, line.getEbeln());
		pstm.setString(3, line.getEbelp());
		pstm.setString(4, line.getCharg());
		pstm.setString(5, line.getMblnr());
		pstm.setString(6, line.getMjahr());
		pstm.setString(7, line.getZeile());
		pstm.setDouble(8, line.getMenge());
		pstm.setString(9, line.getMtype());
		pstm.setString(10, line.getMesge());
		pstm.executeUpdate();
		pstm.close();

		sql = "UPDATE BCS.PO_RECEIPTS " //
				+ "   SET ALLOCQTY = ?, REMAINQTY = ?, SMDAT = TO_CHAR ( SYSDATE, 'YYYYMMDD'), SMTIM = TO_CHAR ( SYSDATE, 'hh24miss'), SMNAM = 'IMES' " //
				+ "       , SMTEM = 'IMES', SMFLG = ? " //
				+ " WHERE ID = ? " //
		;

		String smflg = "A";

		if (receipt.getRemainQty() == 0)
			smflg = "C";

		pstm = conn.prepareStatement(sql);
		pstm.setDouble(1, receipt.getAllocQty());
		pstm.setDouble(2, receipt.getRemainQty());
		pstm.setString(3, smflg);
		pstm.setInt(4, receipt.getId());
		pstm.executeUpdate();
		pstm.close();

	}

	public void updateStatus(PoReceiptLine line) throws Exception {

		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();

		String sql = "INSERT INTO BCS.PO_RECEIPT_LINES ( ID, PO_RECEIPT_ID, EBELN, EBELP, CHARG, " //
				+ "                                   MBLNR, MJAHR, ZEILE, MENGE, MTYPE, " //
				+ "                                   MESGE, CREATED_AT) " //
				+ "     VALUES (BCS.PO_RECEIPT_LINES_SEQ.NEXTVAL, ?, ?, ?, ? " //
				+ "             , ?, ?, ?, ?, ?, " //
				+ "             ?, SYSDATE) " //
		;
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setInt(1, 0);
		pstm.setString(2, line.getEbeln());
		pstm.setString(3, line.getEbelp());
		pstm.setString(4, line.getCharg());
		pstm.setString(5, line.getMblnr());
		pstm.setString(6, line.getMjahr());
		pstm.setString(7, line.getZeile());
		pstm.setDouble(8, line.getMenge());
		pstm.setString(9, line.getMtype());
		pstm.setString(10, line.getMesge());
		pstm.executeUpdate();
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
