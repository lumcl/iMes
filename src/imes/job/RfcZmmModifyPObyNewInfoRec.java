package imes.job;

import imes.core.Helper;
import imes.core.sap.SapRfcConnection;
import imes.core.sap.TableAdapterReader;
import imes.dao.D089Dao;
import imes.entity.D089Z;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class RfcZmmModifyPObyNewInfoRec implements Runnable {

	private ServletContext context;

	private Logger logger = Logger.getLogger(this.getClass());

	public RfcZmmModifyPObyNewInfoRec(ServletContext context) {
		super();
		this.context = context;
	}

	public void SapRfc() {

		List<HashMap<String, Object>> datas = getDatas();

		if (datas.isEmpty() == true) {
			return;
		}

		SapRfcConnection rfc;
		JCoFunction function;

		try {
			rfc = new SapRfcConnection();

			function = rfc.getFunction("ZMM_MODIFY_PO_BY_NEW_INFO_REC");

			JCoTable impDat = function.getTableParameterList().getTable("IMP_DAT");

			HashMap<String, String> bdbhs = new HashMap<String, String>();

			for (HashMap<String, Object> map : datas) {
				impDat.appendRow();
				impDat.setValue("LIFNR", map.get("LIFNR"));
				impDat.setValue("MATNR", map.get("MATNR"));
				impDat.setValue("WERKS", map.get("WERKS"));
				impDat.setValue("EKORG", map.get("EKORG"));
				if (map.get("CHGPO").equals("2")) {
					// All Open PO - don't care up or down
					impDat.setValue("HIPRF", "X");
				} else if (map.get("CHGPO").equals("1")) {
					// All Open PO but if lower price
					impDat.setValue("HIPRF", " ");
				}
				impDat.setValue("FLONO", map.get("BDBH"));
				impDat.setValue("ERDAB", map.get("PODAT"));
				bdbhs.put(map.get("BDBH").toString(), map.get("GSDM").toString() + ";" + map.get("BDDM").toString());
			}

			rfc.execute(function);

			JCoTable errMsg = function.getTableParameterList().getTable("ERR_MSG");

			TableAdapterReader reader = new TableAdapterReader(errMsg);

			List<D089Z> list = new ArrayList<D089Z>();
			D089Z e;
			String[] buf, gsdmbddm;
			String matnr, gsdm, bddm, bdbh;
			for (int i = 0; i < reader.size(); i++) {

				matnr = "";
				gsdm = "";
				bddm = "";
				bdbh = "";

				buf = reader.get("MESSAGE").split(";");
				if (buf.length >= 1) {
					bdbh = buf[0];
					if (bdbhs.containsKey(bdbh)) {
						gsdmbddm = bdbhs.get(bdbh).split(";");
						gsdm = gsdmbddm[0];
						bddm = gsdmbddm[1];
					}
				}

				if (buf.length >= 2) {
					matnr = buf[1];
				}

				e = new D089Z();
				e.setGSDM(gsdm);
				e.setBDDM(bddm);
				e.setBDBH(bdbh);
				e.setMATNR(matnr);
				e.setMSGID(reader.get("ID"));
				e.setMSGNR(reader.get("NUMBER"));
				e.setMSGTY(reader.get("TYPE"));
				e.setMSGTX(reader.get("MESSAGE"));
				list.add(e);
				reader.next();
			}

			if (list.isEmpty() == false) {

				try {
					Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();
					conn.setAutoCommit(false);
					D089Dao dao = new D089Dao(conn);
					for (D089Z z : list) {
						dao.insertD089Z(z);
					}

					dao.updateD089LAfterModifyPobyNewInfoRec(datas);

					conn.commit();
					conn.setAutoCommit(true);
					dao = null;
					conn.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		} catch (NamingException ex) {
			ex.printStackTrace();
		}

		rfc = null;
	}

	public List<HashMap<String, Object>> getDatas() {

		List<HashMap<String, Object>> datas = new ArrayList<HashMap<String, Object>>();

		try {
			Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();

			String sql = "SELECT h.gsdm, h.bddm, h.bdbh, h.ekorg, h.werks, " //
					+ "       l.sqnr, h.lifnr, l.infnr, l.matnr, l.chgpo, " //
					+ "       l.podat " //
					+ "  FROM d089h h, d089l l " //
					+ " WHERE     h.gsdm = l.gsdm " //
					+ "       AND h.bddm = l.bddm " //
					+ "       AND h.bdbh = l.bdbh " //
					+ "       AND h.bdjg = 'Y' " //
					+ "       AND h.rfccf = 'A' " //
					+ "       AND l.sapcf = 'C' " //
					+ "       AND l.rfccf = 'A' " //
					+ "       AND l.dltfg = 'N' " //
					+ "       AND l.chgpo <> '3'"
					+ "       AND h.bdyy <> 'E. Close Agrm' " //
					//+ "       AND h.bdbh = 'DT-D089-1303571' " //

			;

			PreparedStatement pstm = conn.prepareStatement(sql);
			datas = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();
			conn.close();

		} catch (SQLException ex) {

			ex.printStackTrace();
		}

		return datas;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("RfcZmmModifyPObyNewInfoRec Start Up");
		SapRfc();
		logger.info("RfcZmmModifyPObyNewInfoRec Shutdown");
	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

}
