package imes.job;

import imes.vpojo.PoConfirmation;
import imes.core.Helper;
import imes.core.sap.SapRfcConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class RfcZConfirmationNewUpd implements Runnable {
	private ServletContext context;

	private Logger logger = Logger.getLogger(this.getClass());

	public RfcZConfirmationNewUpd(ServletContext context) {
		super();
		this.context = context;
	}

	public void SapRfc(List<PoConfirmation> confs) {

		try {

			HashMap<String, List<PoConfirmation>> maps = new HashMap<String, List<PoConfirmation>>();
			String key;
			List<PoConfirmation> list;

			for (PoConfirmation conf : confs) {
				key = conf.getEbeln();
				if (maps.containsKey(key)) {
					list = maps.get(key);
				} else {
					list = new ArrayList<PoConfirmation>();
				}
				list.add(conf);
				maps.put(key, list);
			}

			Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
			String sql = "SELECT EKET.EBELN, " //
					+ "       EKET.EBELP, " //
					+ "       EKET.MENGE EKET, " //
					+ "       EKES.MENGE EKES " //
					+ "  FROM (  SELECT EBELP, " //
					+ "                 EBELN, " //
					+ "                 SUM (MENGE - WEMNG) MENGE " //
					+ "            FROM SAPSR3.EKET@SAPP " //
					+ "           WHERE EBELN = ? AND EBELP = ? AND MANDT='168'" //
					+ "        GROUP BY EBELP, EBELN) EKET, " //
					+ "       (  SELECT EBELP, " //
					+ "                 EBELN, " //
					+ "                 SUM (MENGE - DABMG) MENGE " //
					+ "            FROM SAPSR3.EKES@SAPP " //
					+ "           WHERE EBELN = ? AND EBELP = ? AND MANDT='168'" //
					+ "        GROUP BY EBELP, EBELN) EKES " //
					+ " WHERE     EKET.EBELN = EKES.EBELN " //
					+ "       AND EKET.EBELP = EKES.EBELP " //
			;

			PreparedStatement pstm = conn.prepareStatement(sql);
			ResultSet rst;
			double ekes, eket, available;

			SapRfcConnection rfc;
			JCoFunction function;
			JCoTable impDat;

			rfc = new SapRfcConnection();

			for (String ebeln : maps.keySet()) {
				function = rfc.getFunction("Z_CONFIRMATION_NEW_UPD");
				function.getImportParameterList().setValue("I_EBELN", ebeln);
				impDat = function.getTableParameterList().getTable("XEKES");
				list = maps.get(ebeln);
				for (PoConfirmation c : list) {

					impDat.appendRow();
					impDat.setValue("MANDT", "168");
					impDat.setValue("EBELN", c.getEbeln());
					impDat.setValue("EBELP", c.getEbelp());
					impDat.setValue("ETENS", c.getEtens());
					impDat.setValue("EBTYP", "LA");
					// impDat.setValue("EINDT", c.getCfmdt());
					impDat.setValue("EINDT", c.getEindt());
					impDat.setValue("ERDAT", Helper.fmtDate(new Date(), "yyyyMMdd"));
					impDat.setValue("EZEIT", Helper.fmtDate(new Date(), "HHmmss"));
					impDat.setValue("MENGE", c.getCfmqty());
					impDat.setValue("KZDIS", "X");
					impDat.setValue("XBLNR", c.getId());
					impDat.setValue("ESTKZ", "1");
					impDat.setValue("LPEIN", "1");

					if (c.getBuycnf().equals("NG")) {
						impDat.setValue("KZ", "U");
						impDat.setValue("MENGE", 0);
					} else {

						impDat.setValue("KZ", "I");
						// Check Eket Quantity
						pstm.setString(1, c.getEbeln());
						pstm.setString(2, c.getEbelp());
						pstm.setString(3, c.getEbeln());
						pstm.setString(4, c.getEbelp());
						rst = pstm.executeQuery();
						if (rst.next()) {
							ekes = rst.getDouble("EKES");
							eket = rst.getDouble("EKET");
							available = eket - ekes;
							if (c.getCfmqty() > available) {
								impDat.setValue("MENGE", available);
								c.setCfmqty(available);
							}
						}
						rst.close();
					}
				} // for (PoConfirmation c : list)

				rfc.execute(function);
			}

			pstm.close();
			conn.close();
			rfc = null;

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void read_ekes_eindt_gt_eket_eindt() throws Exception {
		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
		String sql = "SELECT EBELN, " //
				+ "       EBELP, " //
				+ "       ETENR, " //
				+ "       EKES, " //
				+ "       EKET " //
				+ "  FROM (  SELECT A.EBELN, " //
				+ "                 A.EBELP, " //
				+ "                 B.ETENR, " //
				+ "                 MAX (A.EINDT) EKES, " //
				+ "                 MAX (B.EINDT) EKET " //
				+ "            FROM SAPSR3.EKES@SAPP A, SAPSR3.EKET@SAPP B, SAPSR3.EKPO@SAPP C " //
				+ "           WHERE     A.MANDT = '168' " //
				+ "                 AND B.MANDT = '168' " //
				+ "                 AND C.MANDT = '168' " //
				+ "                 AND C.EBELN = A.EBELN " //
				+ "                 AND C.EBELP = A.EBELP " //
				+ "                 AND C.LOEKZ = ' ' " //
				+ "                 AND C.ELIKZ = ' ' " //
				+ "                 AND A.EBELN = B.EBELN " //
				+ "                 AND A.EBELP = B.EBELP " //
				+ "                 AND B.MENGE > B.WEMNG " //
				+ "        GROUP BY A.EBELN, A.EBELP, B.ETENR) " //
				+ " WHERE EKES > EKET " //
		;
		String ebeln, ebelp, etenr, eindt;
		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst = pstm.executeQuery();
		while (rst.next()) {
			ebeln = rst.getString("EBELN");
			ebelp = rst.getString("EBELP");
			etenr = rst.getString("ETENR");
			eindt = rst.getString("EKES");
			System.out.println(ebeln + "-" + ebelp + "-" + etenr + "-" + eindt);
			ChangePoSchedule(ebeln, ebelp, etenr, eindt);
		}
		rst.close();
		pstm.close();
		conn.close();
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

	public void test() throws Exception {
		SapRfcConnection rfc;
		JCoFunction function;
		JCoTable impDat;

		rfc = new SapRfcConnection();

		function = rfc.getFunction("BAPI_PO_CHANGE");
		function.getImportParameterList().setValue("PURCHASEORDER", "8000876358"); // 00380
		impDat = function.getTableParameterList().getTable("POCONFIRMATION");
		impDat.appendRow();
		impDat.setValue("PO_ITEM", "380");
		impDat.setValue("CONF_SER", "1");
		impDat.setValue("CONF_TYPE", "LA");
		impDat.setValue("DEL_DATCAT", "D");
		impDat.setValue("DELIV_DATE", "20131108");
		impDat.setValue("QUANTITY", "400");
		impDat.setValue("DISPO_REL", "X");
		impDat.setValue("RECEIPT_REL", "X");

		rfc.execute(function);

		function = rfc.getFunction("BAPI_TRANSACTION_COMMIT");
		rfc.execute(function);

		rfc = null;
	}

	public void determineEkesEtens(List<PoConfirmation> confs) {

		HashMap<String, Integer> maps = new HashMap<String, Integer>();
		String key;
		int value;

		DecimalFormat df = new DecimalFormat("0000");

		for (PoConfirmation conf : confs) {

			if (conf.getEtens() == null || conf.getEtens().equals("")) {

				key = conf.getEbeln() + "@" + conf.getEbelp();

				System.out.println(key);

				if (maps.containsKey(key)) {
					value = maps.get(key) + 1;
				} else {
					value = Integer.parseInt(conf.getPetens()) + 1;
				}

				maps.put(key, value);

				conf.setEtens(df.format(value));
			}
		}

		maps.clear();
		maps = null;
	}

	public List<PoConfirmation> readPoConfirmations() throws Exception {
		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();

		String sql = "  SELECT ID, RID, " //
				+ "         EBELN, " //
				+ "         EBELP, " //
				+ "         ETENS, " //
				+ "         CFMDT, " //
				+ "         CFMQTY, " //
				+ "         CFM_AT, " //
				+ "         BUYCNF, " //
				+ "         SHPDT, " //
				+ "         EINDT, " //
				+ "         RFCSTS, " //
				+ "         (SELECT NVL (MAX (ETENS), 0) " //
				+ "            FROM SAPSR3.EKES@SAPP " //
				+ "           WHERE     EKES.MANDT = '168' " //
				+ "                 AND EKES.EBELP = T001.EBELP " //
				+ "                 AND EKES.EBELN = T001.EBELN) " //
				+ "            PETENS " //
				+ "    FROM IPS.PO_CONFIRMATIONS T001 " //
				+ "   WHERE RFCSTS = 'A' " //
				+ "ORDER BY EBELN, EBELP, CFMDT " //
		;

		PreparedStatement pstm = conn.prepareStatement(sql);
		ResultSet rst = pstm.executeQuery();

		PoConfirmation conf;
		List<PoConfirmation> confs = new ArrayList<PoConfirmation>();

		while (rst.next()) {
			conf = new PoConfirmation();
			conf.setId(rst.getInt("ID"));
			conf.setRid(rst.getInt("RID"));
			conf.setEbeln(rst.getString("EBELN"));
			conf.setEbelp(rst.getString("EBELP"));
			conf.setEtens(rst.getString("ETENS"));
			conf.setCfmdt(rst.getString("CFMDT"));
			conf.setCfmqty(rst.getDouble("CFMQTY"));
			conf.setBuycnf(rst.getString("BUYCNF"));
			conf.setShpdt(rst.getString("SHPDT"));
			conf.setEindt(rst.getString("EINDT"));
			conf.setPetens(rst.getString("PETENS"));

			confs.add(conf);
		}
		rst.close();
		pstm.close();
		conn.close();

		return confs;
	}

	public void check(List<PoConfirmation> confs) throws Exception {
		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();

		String sql = "SELECT COUNT (*) CNT, SUM(MENGE) MENGE " //
				+ "  FROM SAPSR3.EKES@SAPP " //
				+ " WHERE MANDT = '168' AND EBELN = ? AND EBELP = ? AND ETENS = ? " //
		;
		PreparedStatement pstm_ekes = conn.prepareStatement(sql);

		sql = "UPDATE IPS.PO_CONFIRMATIONS " //
				+ "   SET ETENS = ?, CFMQTY = ?,RFCSTS = 'X', RFCNAM = 'Z_CONFIRMATION_NEW_UPD', RFC_IP = '172.31.4.177', RFC_AT = SYSDATE " //
				+ " WHERE ID = ? " //
		;
		PreparedStatement updconf = conn.prepareStatement(sql);

		sql = "UPDATE IPS.POSCHEDULE SET CFMQTY = ? WHERE RID = ?";
		PreparedStatement updschedule = conn.prepareStatement(sql);

		ResultSet rst;
		int cnt;
		double menge;

		for (PoConfirmation conf : confs) {
			pstm_ekes.setString(1, conf.getEbeln());
			pstm_ekes.setString(2, conf.getEbelp());
			pstm_ekes.setString(3, conf.getEtens());

			rst = pstm_ekes.executeQuery();
			if (rst.next()) {
				cnt = rst.getInt("CNT");
				menge = rst.getDouble("MENGE");
				if ((conf.getBuycnf().equals("OK") && cnt != 0) || (conf.getBuycnf().equals("NG") && menge == 0)) {
					updconf.setString(1, conf.getEtens());
					updconf.setDouble(2, conf.getCfmqty());
					updconf.setInt(3, conf.getId());
					updconf.executeUpdate();

					updschedule.setDouble(1, conf.getCfmqty());
					updschedule.setInt(2, conf.getRid());
					updschedule.executeUpdate();
				}
			} else {
				updschedule.setDouble(1, 0);
				updschedule.setInt(2, conf.getRid());
				updschedule.executeUpdate();
			}
			rst.close();
		}

		pstm_ekes.close();
		updconf.close();
		updschedule.close();

		conn.close();

		confs.clear();
	}

	private void fixed_mrp_qty() throws NamingException {
		SapRfcConnection rfc;
		JCoFunction function;
		JCoTable impDat;

		rfc = new SapRfcConnection();

		function = rfc.getFunction("Z_CONFIRMATION_NEW_UPD");
		function.getImportParameterList().setValue("I_EBELN", "8000904915");
		impDat = function.getTableParameterList().getTable("XEKES");

		impDat.appendRow();
		impDat.setValue("MANDT", "168");
		impDat.setValue("EBELN", "8000904915");
		impDat.setValue("EBELP", "00050");
		impDat.setValue("ETENS", "999");
		impDat.setValue("EBTYP", "LA");
		impDat.setValue("EINDT", "20131101");
		impDat.setValue("ERDAT", Helper.fmtDate(new Date(), "yyyyMMdd"));
		impDat.setValue("EZEIT", Helper.fmtDate(new Date(), "HHmmss"));
		impDat.setValue("MENGE", "2750");
		impDat.setValue("KZDIS", "X");
		impDat.setValue("XBLNR", "OPNBAL");
		impDat.setValue("ESTKZ", "1");
		impDat.setValue("LPEIN", "1");
		impDat.setValue("DABMG", "2750");
		impDat.setValue("KZ", "I");

		rfc.execute(function);

		rfc = null;

	}

	public void run_a() {
		logger.info("RfcZConfirmationNewUpd Start Up");
		try {
			fixed_mrp_qty();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		logger.info("RfcZConfirmationNewUpd Shutdown");
	}

	// @Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("RfcZConfirmationNewUpd Start Up");
		/*
		 * try { test(); } catch (Exception e1) { // TODO Auto-generated catch
		 * block e1.printStackTrace(); }
		 */

		List<PoConfirmation> confs;
		try {
			confs = readPoConfirmations();
			determineEkesEtens(confs);
			SapRfc(confs);
			check(confs);
			confs.clear();
			confs = null;
			read_ekes_eindt_gt_eket_eindt();
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info("RfcZConfirmationNewUpd Shutdown");
	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}
}
