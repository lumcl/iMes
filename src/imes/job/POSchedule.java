package imes.job;

import imes.vpojo.MatRequest;
import imes.vpojo.MatSupply;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class POSchedule implements Runnable {

	private ServletContext context;

	private Logger logger = Logger.getLogger(this.getClass());
	
	private List<MatRequest> matRequests = new ArrayList<MatRequest>();
	private List<MatRequest> matSchedules = new ArrayList<MatRequest>();

	private HashMap<String, Double> matWerksBals = new HashMap<String, Double>();
	private HashMap<String, List<MatSupply>> matSupplyMap = new HashMap<String, List<MatSupply>>();

	public POSchedule(ServletContext context) {
		super();
		setContext(context);
	}

	public void start() {

		try {
			
			logger.info("ReadMatRequest");
			ReadMatRequest();
			logger.info("ReadMatSupply");
			ReadMatSupply();
			logger.info("ReadStkBalance");
			ReadStkBalance();
			logger.info("Process Data");
			ProcessData();
			logger.info("Insert Db");
			InsertDb();

			matRequests.clear();
			matSchedules.clear();
			matWerksBals.clear();
			matSupplyMap.clear();
			
			matRequests = null;
			matSchedules = null;
			matWerksBals = null;
			matSupplyMap = null;
			
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	private void ReadMatRequest() throws Exception {

		MatRequest e;

		Connection conn = ((DataSource) getContext().getAttribute("sapprdds")).getConnection();
		
		//BSTRF
		
		String sql = "SELECT RESB.BDART, RESB.MATNR, RESB.WERKS, RESB.LGORT, RESB.BDTER, " //
				+ "       RESB.BDMNG, RESB.ENMNG, (RESB.BDMNG - RESB.ENMNG) REMNG, RESB.MEINS, RESB.PBDNR, " //
				+ "       RESB.PLNUM, RESB.AUFNR, RESB.POSNR, RESB.BWART, RESB.RSNUM, MARC.BSTRF," //
				+ "       RESB.RSPOS, MAKT.MAKTX, MARA.MATKL, MARA.ZEINR, MARC.EKGRP, MARA.AESZN," //
				+ "       NVL (AFPO.MATNR, PLAF.MATNR) PMATNR, NVL (AFPO.PWERK, PLAF.PLWRK) PWERKS " //
				+ "  FROM SAPSR3.RESB, " //
				+ "       SAPSR3.MARA, " //
				+ "       SAPSR3.MAKT, " //
				+ "       SAPSR3.MARC, " //
				+ "       SAPSR3.AFPO, " //
				+ "       SAPSR3.PLAF " //
				+ " WHERE     RESB.MANDT = '168' " //
				+ "       AND RESB.WERKS IN ('481A', '482A') " //
				+ "       AND RESB.XLOEK = ' ' " //
				+ "       AND RESB.KZEAR = ' ' " //
				+ "       AND RESB.BDMNG <> RESB.ENMNG " //
				//+ "       AND RESB.BDTER <= TO_CHAR (SYSDATE + 90, 'YYYYMMDD') " //
				+ "       AND MARA.MANDT = RESB.MANDT " //
				+ "       AND MARA.MATNR = RESB.MATNR " //
				+ "       AND MAKT.MANDT = RESB.MANDT " //
				+ "       AND MAKT.MATNR = RESB.MATNR " //
				+ "       AND MAKT.SPRAS = 'M' " //
				+ "       AND MARC.MANDT = RESB.MANDT " //
				+ "       AND MARC.MATNR = RESB.MATNR " //
				+ "       AND MARC.WERKS = RESB.WERKS " //
				+ "       AND AFPO.MANDT(+) = RESB.MANDT " //
				+ "       AND AFPO.AUFNR(+) = RESB.AUFNR " //
				+ "       AND PLAF.MANDT(+) = RESB.MANDT " //
				+ "       AND PLAF.PLNUM(+) = RESB.PLNUM " //
				+ "ORDER BY WERKS, MATNR, BDTER "; //

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rst = pstm.executeQuery();

		while (rst.next()) {

			e = new MatRequest();
			e.setBDART(rst.getString("BDART"));
			e.setMATNR(rst.getString("MATNR"));
			e.setWERKS(rst.getString("WERKS"));
			e.setLGORT(rst.getString("LGORT"));
			e.setBDTER(rst.getString("BDTER"));
			e.setBDMNG(rst.getDouble("BDMNG"));
			e.setENMNG(rst.getDouble("ENMNG"));
			e.setREMNG(rst.getDouble("REMNG"));
			e.setMEINS(rst.getString("MEINS"));
			e.setPBDNR(rst.getString("PBDNR"));
			e.setPLNUM(rst.getString("PLNUM"));
			e.setAUFNR(rst.getString("AUFNR"));
			e.setPOSNR(rst.getString("POSNR"));
			e.setBWART(rst.getString("BWART"));
			e.setRSNUM(rst.getString("RSNUM"));
			e.setRSPOS(rst.getString("RSPOS"));
			e.setMAKTX(rst.getString("MAKTX"));
			e.setMATKL(rst.getString("MATKL"));
			e.setZEINR(rst.getString("ZEINR"));
			e.setAESZN(rst.getString("AESZN"));
			e.setEKGRP(rst.getString("EKGRP"));
			e.setPMATNR(rst.getString("PMATNR"));
			e.setPWERKS(rst.getString("PWERKS"));
			e.setBSTRF(rst.getInt("BSTRF"));
			matRequests.add(e);
		}

		rst.close();
		pstm.close();
		conn.close();
	}

	private void ReadMatSupply() throws Exception {

		String key;

		List<MatSupply> matSupplies;

		MatSupply e;

		Connection conn = ((DataSource) getContext().getAttribute("sapprdds")).getConnection();

		String sql = "  SELECT MDBS.EBELN, MDBS.EBELP, MDBS.ETENR, MDBS.MATNR, MDBS.WERKS, " //
				+ "         MDBS.LGORT, MDBS.MEINS, MDBS.MENGE, MDBS.WEMNG, (MENGE - WEMNG) REMNG, " //
				+ "         EKKO.LIFNR, EKKO.AEDAT, EKKO.EKORG, LFA1.SORTL " //
				+ "    FROM SAPSR3.MDBS, SAPSR3.EKKO, SAPSR3.LFA1 " //
				+ "   WHERE     MDBS.MANDT = '168' " //
				+ "         AND MDBS.BSTYP = 'F' " //
				+ "         AND MDBS.MATNR <> ' ' " //
				+ "         AND (MDBS.LOEKZ = ' ' OR MDBS.LOEKZ = 'S') " //
				+ "         AND MDBS.ELIKZ = ' ' " //
				+ "         AND MDBS.MENGE <> MDBS.WEMNG " //
				+ "         AND EKKO.MANDT = MDBS.MANDT " //
				+ "         AND EKKO.EBELN = MDBS.EBELN " //
				+ "         AND EKKO.LIFNR NOT BETWEEN 'L1' AND 'L9' " //
				+ "         AND EKKO.EKORG IN ('L400', 'L100') " //
				+ "         AND LFA1.MANDT = EKKO.MANDT " //
				+ "         AND LFA1.LIFNR = EKKO.LIFNR " //
				+ "ORDER BY WERKS, MATNR, AEDAT, LIFNR "; //

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rst = pstm.executeQuery();

		while (rst.next()) {

			key = rst.getString("MATNR") + "_" + rst.getString("WERKS");

			if (matSupplyMap.containsKey(key)) {
				matSupplies = matSupplyMap.get(key);
			} else {
				matSupplies = new ArrayList<MatSupply>();
			}

			e = new MatSupply();
			e.setEBELN(rst.getString("EBELN"));
			e.setEBELP(rst.getString("EBELP"));
			e.setETENR(rst.getString("ETENR"));
			e.setMATNR(rst.getString("MATNR"));
			e.setWERKS(rst.getString("WERKS"));
			e.setLGORT(rst.getString("LGORT"));
			e.setMEINS(rst.getString("MEINS"));
			e.setMENGE(rst.getDouble("MENGE"));
			e.setWEMNG(rst.getDouble("WEMNG"));
			e.setREMNG(rst.getDouble("REMNG"));
			e.setLIFNR(rst.getString("LIFNR"));
			e.setAEDAT(rst.getString("AEDAT"));
			e.setEKORG(rst.getString("EKORG"));
			e.setSORTL(rst.getString("SORTL"));
			matSupplies.add(e);

			matSupplyMap.put(key, matSupplies);
		}

		rst.close();
		pstm.close();
		conn.close();
	}

	private void ReadStkBalance() throws Exception {

		String key;

		Connection conn = ((DataSource) getContext().getAttribute("sapprdds")).getConnection();

		String sql = "  SELECT MATNR, " //
				+ "         SUM (CASE WHEN (WERKS = '101A' AND LGORT = 'RM01') OR WERKS = '481A' THEN (LABST + EINME + INSME) END) W481A, " //
				+ "         SUM (CASE WHEN (WERKS = '101A' AND LGORT = 'RM02') OR WERKS = '482A' THEN (LABST + EINME + INSME) END) W482A " //
				+ "    FROM SAPSR3.MARD " //
				+ "   WHERE MANDT = '168' AND WERKS IN ('481A', '482A', '101A') AND (LABST + EINME + INSME) > 0 " //
				+ "GROUP BY MATNR ";//

		PreparedStatement pstm = conn.prepareStatement(sql);

		ResultSet rst = pstm.executeQuery();

		while (rst.next()) {

			key = rst.getString("MATNR") + "_" + "481A";
			matWerksBals.put(key, rst.getDouble("W481A"));

			key = rst.getString("MATNR") + "_" + "482A";
			matWerksBals.put(key, rst.getDouble("W482A"));

		}

		rst.close();
		pstm.close();
		conn.close();
	}

	private void ProcessData() throws Exception {

		MatRequest matSchedule;

		List<MatSupply> matSupplies;

		String key;
		double stkbalqty;

		for (MatRequest e : matRequests) {

			stkbalqty = 0;
			key = e.getMATNR() + "_" + e.getWERKS();

			if (matWerksBals.containsKey(key)) {
				stkbalqty = matWerksBals.get(key);

				if (stkbalqty > 0) {

					if (e.getREMNG() <= stkbalqty) {

						matSchedule = e.clone();
						matSchedule.setSTYPE("WB");
						matSchedule.setMENGE(e.getREMNG());
						matSchedule.setLIFNR(e.getWERKS());
						matSchedules.add(matSchedule);

						stkbalqty = stkbalqty - e.getREMNG();

						e.setREMNG(0);

						matWerksBals.put(key, stkbalqty);

					} else {

						matSchedule = e.clone();
						matSchedule.setSTYPE("WB");
						matSchedule.setMENGE(stkbalqty);
						matSchedule.setLIFNR(e.getWERKS());
						matSchedules.add(matSchedule);

						stkbalqty = 0;

						e.setREMNG(e.getREMNG() - matSchedule.getMENGE());

						matWerksBals.put(key, 0.0);
					}

				} // if (stkbalqty > 0)
			} // if (matWerksBals.containsKey(key))

			if (e.getREMNG() > 0) {

				if (matSupplyMap.containsKey(key)) {
					matSupplies = matSupplyMap.get(key);

					for (MatSupply po : matSupplies) {

						if (po.getREMNG() > 0) {

							if (e.getREMNG() <= po.getREMNG()) {

								matSchedule = e.clone();
								matSchedule.setSTYPE("PO");
								matSchedule.setEBELN(po.getEBELN());
								matSchedule.setEBELP(po.getEBELP());
								matSchedule.setETENR(po.getETENR());
								matSchedule.setMENGE(e.getREMNG());
								matSchedule.setLIFNR(po.getLIFNR());
								matSchedule.setAEDAT(po.getAEDAT());
								matSchedule.setEKORG(po.getEKORG());
								matSchedule.setSORTL(po.getSORTL());
								matSchedules.add(matSchedule);

								po.setREMNG(po.getREMNG() - e.getREMNG());

								e.setREMNG(0);

								break;

							} else {

								matSchedule = e.clone();
								matSchedule.setSTYPE("PO");
								matSchedule.setEBELN(po.getEBELN());
								matSchedule.setEBELP(po.getEBELP());
								matSchedule.setETENR(po.getETENR());
								matSchedule.setMENGE(po.getREMNG());
								matSchedule.setLIFNR(po.getLIFNR());
								matSchedule.setAEDAT(po.getAEDAT());
								matSchedule.setEKORG(po.getEKORG());
								matSchedule.setSORTL(po.getSORTL());
								matSchedules.add(matSchedule);

								e.setREMNG(e.getREMNG() - po.getREMNG());

								po.setREMNG(0);

							}

						}

					} // for (MatSupply s : matSupplies)

					matSupplyMap.put(key, matSupplies);

				} // if (matSupplyMap.containsKey(key))

			} // (e.getREMNG() > 0)

		} // for (MatRequest e : matRequests){
	}

	private void InsertDb() throws Exception {

		Connection conn = ((DataSource) getContext().getAttribute("imesds")).getConnection();

		conn.setAutoCommit(false);
		
		try {
			String sql = "TRUNCATE TABLE ZMM66";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
			pstm.close();
			
			
			sql = "INSERT INTO ZMM66 (JLSJ,BDART,MATNR,WERKS,LGORT,BDTER,BDMNG,ENMNG,REMNG,MEINS,PBDNR,PLNUM,AUFNR,POSNR,BWART,RSNUM,RSPOS,EBELN,EBELP,ETENR,MENGE,LIFNR,AEDAT,EKORG,STYPE,MAKTX,SORTL,MATKL,ZEINR,EKGRP,PMATNR,PWERKS,AESZN,BSTRF,SHPDT) " //
					+ "VALUES (SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "; //

			pstm = conn.prepareStatement(sql);

			for (MatRequest e : matSchedules) {

				pstm.setString(1, e.getBDART());
				pstm.setString(2, e.getMATNR());
				pstm.setString(3, e.getWERKS());
				pstm.setString(4, e.getLGORT());
				pstm.setString(5, e.getBDTER());
				pstm.setDouble(6, e.getBDMNG());
				pstm.setDouble(7, e.getENMNG());
				pstm.setDouble(8, e.getREMNG());
				pstm.setString(9, e.getMEINS());
				pstm.setString(10, e.getPBDNR());
				pstm.setString(11, e.getPLNUM());
				pstm.setString(12, e.getAUFNR());
				pstm.setString(13, e.getPOSNR());
				pstm.setString(14, e.getBWART());
				pstm.setString(15, e.getRSNUM());
				pstm.setString(16, e.getRSPOS());
				pstm.setString(17, e.getEBELN());
				pstm.setString(18, e.getEBELP());
				pstm.setString(19, e.getETENR());
				pstm.setDouble(20, e.getMENGE());
				pstm.setString(21, e.getLIFNR());
				pstm.setString(22, e.getAEDAT());
				pstm.setString(23, e.getEKORG());
				pstm.setString(24, e.getSTYPE());
				pstm.setString(25, e.getMAKTX());
				pstm.setString(26, e.getSORTL());
				pstm.setString(27, e.getMATKL());
				pstm.setString(28, e.getZEINR());
				pstm.setString(29, e.getEKGRP());
				pstm.setString(30, e.getPMATNR());
				pstm.setString(31, e.getPWERKS());
				pstm.setString(32, e.getAESZN());
				pstm.setInt(33, e.getBSTRF());
				pstm.setString(34, e.getSHPDT());
				pstm.executeUpdate();

			}

			pstm.close();
			conn.commit();
			logger.info("ZMM66 Commit Completed");
			
		} catch (Exception e) {
			
			conn.rollback();
			e.printStackTrace();
		}
		
		
		conn.setAutoCommit(true);
		
		conn.close();
	}

	@Override
	public void run() {
		start();
	}
	
	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}
}
