package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class D031M {
	private String GSDM = "";
	private String BDDM = "";
	private String BDBH = "";
	private String PMATNR = "";
	private String PWERKS = "";
	private int SQNR = 0;
	private String CMATNR = "";
	private String CWERKS = "";
	private String CMATKL = "";
	private String CMAKTX = "";
	private String CMEINS = "";
	private double BOMQ = 0;
	private double TRNQ = 0;
	private double STKQ = 0;
	private double PURQ = 0;
	private double IDEQ = 0;
	private double NETPR = 0;
	private double PLIFZ = 0;
	private double BSTRF = 0;
	private String CFMDT = "";
	private String LTEXT = "";
	private String JLYH = "";
	private Date JLSJ = null;
	private String GXYH = "";
	private Date GXSJ = null;

	public void insertDb(Connection conn) throws Exception {

		String sql = "INSERT INTO D031M(GSDM,BDDM,BDBH,PMATNR,PWERKS,SQNR,CMATNR,CWERKS,CMATKL,CMAKTX,CMEINS,BOMQ,TRNQ,STKQ,PURQ,IDEQ,NETPR,CFMDT,LTEXT,JLYH,JLSJ,GXYH,GXSJ) " //
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "; //

		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, GSDM);
		pstm.setString(2, BDDM);
		pstm.setString(3, BDBH);
		pstm.setString(4, PMATNR);
		pstm.setString(5, PWERKS);
		pstm.setInt(6, SQNR);
		pstm.setString(7, CMATNR);
		pstm.setString(8, CWERKS);
		pstm.setString(9, CMATKL);
		pstm.setString(10, CMAKTX);
		pstm.setString(11, CMEINS);
		pstm.setDouble(12, BOMQ);
		pstm.setDouble(13, TRNQ);
		pstm.setDouble(14, STKQ);
		pstm.setDouble(15, PURQ);
		pstm.setDouble(16, IDEQ);
		pstm.setDouble(17, NETPR);
		pstm.setString(18, CFMDT);
		pstm.setString(19, LTEXT);
		pstm.setString(20, JLYH);
		pstm.setTimestamp(21, Helper.toSqlDate(JLSJ));
		pstm.setString(22, GXYH);
		pstm.setTimestamp(23, Helper.toSqlDate(GXSJ));
		pstm.executeUpdate();
		pstm.close();

	}

	public void insertDb(List<D031M> list, Connection conn) throws Exception {

		String sql = "INSERT INTO D031M(GSDM,BDDM,BDBH,PMATNR,PWERKS,SQNR,CMATNR,CWERKS,CMATKL,CMAKTX,CMEINS,BOMQ,TRNQ,STKQ,PURQ,IDEQ,NETPR,CFMDT,LTEXT,JLYH,JLSJ,GXYH,GXSJ) " //
				+ " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "; //

		PreparedStatement pstm = conn.prepareStatement(sql);

		for (D031M e : list) {
			pstm.setString(1, e.getGSDM());
			pstm.setString(2, e.getBDDM());
			pstm.setString(3, e.getBDBH());
			pstm.setString(4, e.getPMATNR());
			pstm.setString(5, e.getPWERKS());
			pstm.setInt(6, e.getSQNR());
			pstm.setString(7, e.getCMATNR());
			pstm.setString(8, e.getCWERKS());
			pstm.setString(9, e.getCMATKL());
			pstm.setString(10, e.getCMAKTX());
			pstm.setString(11, e.getCMEINS());
			pstm.setDouble(12, e.getBOMQ());
			pstm.setDouble(13, e.getTRNQ());
			pstm.setDouble(14, e.getSTKQ());
			pstm.setDouble(15, e.getPURQ());
			pstm.setDouble(16, e.getIDEQ());
			pstm.setDouble(17, e.getNETPR());
			pstm.setString(18, e.getCFMDT());
			pstm.setString(19, e.getLTEXT());
			pstm.setString(20, e.getJLYH());
			pstm.setTimestamp(21, Helper.toSqlDate(e.getJLSJ()));
			pstm.setString(22, e.getGXYH());
			pstm.setTimestamp(23, Helper.toSqlDate(e.getGXSJ()));
			pstm.executeUpdate();
		}
		pstm.close();

	}

	public void create(Connection conn) {

		List<D031M> list = new ArrayList<D031M>();

		String sql = "WITH TBF " //
				+ "     AS (  SELECT GSDM, BDDM, BDBH, MATNR, SORTL, " //
				+ "                  WERKS, SUM (OMENG) BFQTY, MIN (MBDAT) BFDAT " //
				+ "             FROM D031L BF " //
				+ "            WHERE XCLB = 'A' " //
				+ "         GROUP BY GSDM, BDDM, BDBH, MATNR, SORTL, " //
				+ "                  WERKS " //
				+ "         ORDER BY GSDM, BDDM, BDBH, MATNR, SORTL, " //
				+ "                  WERKS), " //
				+ "     TAF " //
				+ "     AS (  SELECT GSDM, BDDM, BDBH, MATNR, SORTL, " //
				+ "                  WERKS, SUM (OMENG) AFQTY, MIN (MBDAT) AFDAT " //
				+ "             FROM D031L BF " //
				+ "            WHERE XCLB = 'C' " //
				+ "         GROUP BY GSDM, BDDM, BDBH, MATNR, SORTL, " //
				+ "                  WERKS " //
				+ "         ORDER BY GSDM, BDDM, BDBH, MATNR, SORTL, " //
				+ "                  WERKS) " //
				+ "SELECT TBF.MATNR, TBF.WERKS, (AFQTY - BFQTY) DFQTY " //
				+ "  FROM TBF, TAF, D031H HD " //
				+ " WHERE     TBF.GSDM = TAF.GSDM " //
				+ "       AND TBF.BDDM = TAF.BDDM " //
				+ "       AND TBF.BDBH = TAF.BDBH " //
				+ "       AND TBF.MATNR = TAF.MATNR " //
				+ "       AND HD.GSDM = TAF.GSDM " //
				+ "       AND HD.BDDM = TAF.BDDM " //
				+ "       AND HD.BDBH = TAF.BDBH " //
				+ "       AND HD.GSDM = ? " //
				+ "       AND HD.BDDM = ? " //
				+ "       AND HD.BDBH = ? "; //

		PreparedStatement pstm;

		ResultSet rst;

		D031M e;

		double qty;

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			List<HashMap<String, Object>> hds = Helper.resultSetToArrayList(pstm.executeQuery());

			pstm.close();

			sql = "WITH TMAT " //
					+ "     AS (  SELECT WERKS, PMATNR, CMATNR, CMAKTX, CMATKL, " //
					+ "                  CWERKS, SUM (MENGE) DUSAGE, DUOM, MATPR, PRSRC " //
					+ "             FROM SAPCO.VBMAT@ORACLETW " //
					+ "            WHERE (ALPOS = ' ' OR (ALPOS = 'X' AND EWAHR = 100)) " //
					+ "         GROUP BY WERKS, PMATNR, CMATNR, CMAKTX, CMATKL, " //
					+ "                  CWERKS, DUOM, MATPR, PRSRC " //
					+ "         ORDER BY CMATKL), " //
					+ "     STKQ " //
					+ "     AS (  SELECT MATNR, SUM (DECODE (WERKS, '101A', MNG01)) W101AS, " //
					+ "                  SUM (DECODE (WERKS, '481A', MNG01)) W481AS, SUM (DECODE (WERKS, '482A', MNG01)) W482AS, " //
					+ "                  SUM (DECODE (WERKS, '281A', MNG01)) W281AS, SUM (DECODE (WERKS, '381A', MNG01)) W381AS, " //
					+ "                  SUM (DECODE (WERKS, '382A', MNG01)) W382AS, SUM (DECODE (WERKS, '701A', MNG01)) W701AS, " //
					+ "                  SUM (DECODE (WERKS, '921A', MNG01)) W921AS " //
					+ "             FROM IT.WMD04S@ORACLETW " //
					+ "            WHERE DELKZ = 'WB' " //
					+ "         GROUP BY MATNR), " //
					+ "     PURQ " //
					+ "     AS (  SELECT MATNR, SUM (DECODE (WERKS, '101A', MNG01)) W101AP, " //
					+ "                  SUM (DECODE (WERKS, '481A', MNG01)) W481AP, SUM (DECODE (WERKS, '482A', MNG01)) W482AP, " //
					+ "                  SUM (DECODE (WERKS, '281A', MNG01)) W281AP, SUM (DECODE (WERKS, '381A', MNG01)) W381AP, " //
					+ "                  SUM (DECODE (WERKS, '382A', MNG01)) W382AP, SUM (DECODE (WERKS, '701A', MNG01)) W701AP, " //
					+ "                  SUM (DECODE (WERKS, '921A', MNG01)) W921AP " //
					+ "             FROM IT.WMD04S@ORACLETW " //
					+ "            WHERE DELKZ = 'BE' AND SUBSTR (LIFNR, 1, 2) NOT IN ('L1', 'L2', 'L3', 'L4', 'L7', 'L9') " //
					+ "         GROUP BY MATNR) " //
					+ "  SELECT WERKS, PMATNR, CMATNR, CMAKTX, CMATKL, " //
					+ "         CWERKS, DUSAGE, DUOM, W101AS, W481AS, " //
					+ "         W482AS, W281AS, W381AS, W382AS, W701AS, " //
					+ "         W921AS, W101AP, W481AP, W482AP, W281AP, " //
					+ "         W381AP, W382AP, W701AP, W921AP, MATPR, " //
					+ "         PRSRC " //
					+ "    FROM TMAT, STKQ, PURQ " //
					+ "   WHERE     TMAT.WERKS = ? " //
					+ "         AND TMAT.PMATNR = ? " //
					+ "         AND STKQ.MATNR(+) = TMAT.CMATNR " //
					+ "         AND PURQ.MATNR(+) = TMAT.CMATNR " //
					+ "ORDER BY CMATKL, CMAKTX "; //

			pstm = conn.prepareStatement(sql);

			String werks = "";

			for (HashMap<String, Object> h : hds) {

				int i = 0;

				double dfqty = Double.parseDouble(h.get("DFQTY").toString());

				if (dfqty < 0) {

					dfqty = dfqty * -1;
				}

				werks = h.get("WERKS").toString();

				if (werks.equals("101A")) {
					if (GSDM.equals("L400")) {
						werks = "481A";
					} else {
						werks = "381A";
					}
				}

				pstm.setString(1, h.get("WERKS").toString());
				pstm.setString(2, h.get("MATNR").toString());

				rst = pstm.executeQuery();

				while (rst.next()) {
					i += 1;

					e = new D031M();

					e.setGSDM(GSDM);
					e.setBDDM(BDDM);
					e.setBDBH(BDBH);
					e.setPMATNR(rst.getString("PMATNR"));
					e.setPWERKS(rst.getString("WERKS"));
					e.setSQNR(i);
					e.setCMATNR(rst.getString("CMATNR"));
					e.setCWERKS(rst.getString("CWERKS"));
					e.setCMATKL(rst.getString("CMATKL"));
					e.setCMAKTX(rst.getString("CMAKTX"));
					e.setCMEINS(rst.getString("DUOM"));
					e.setBOMQ(rst.getDouble("DUSAGE"));
					e.setTRNQ(e.getBOMQ() * dfqty);
					e.setNETPR(rst.getDouble("MATPR") * 1.03);

					if (e.getCWERKS().equals("481A")) {

						qty = rst.getDouble("W481AS");
						e.setSTKQ(qty);

						qty = rst.getDouble("W481AP");
						e.setPURQ(qty);

					} else if (e.getCWERKS().equals("482A")) {

						qty = rst.getDouble("W482AS");
						e.setSTKQ(qty);

						qty = rst.getDouble("W482AP");
						e.setPURQ(qty);
					}
					e.setJLYH(JLYH);
					e.setJLSJ(new Date());

					list.add(e);

				}

				rst.close();

			}

			insertDb(list, conn);

		} catch (Exception ex) {

			ex.printStackTrace();
		}

	}

	public static List<D031M> findBy(String GSDM, String BDDM, String BDBH, Connection conn) {

		List<D031M> list = new ArrayList<D031M>();

		D031M e;

		String sql = "SELECT * FROM D031M WHERE GSDM=? AND BDDM=? AND BDBH=? ORDER BY PMATNR,SQNR";
		try {

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			ResultSet rst = pstm.executeQuery();

			while (rst.next()) {

				e = new D031M();
				e.setGSDM(rst.getString("GSDM"));
				e.setBDDM(rst.getString("BDDM"));
				e.setBDBH(rst.getString("BDBH"));
				e.setPMATNR(rst.getString("PMATNR"));
				e.setPWERKS(rst.getString("PWERKS"));
				e.setSQNR(rst.getInt("SQNR"));
				e.setCMATNR(rst.getString("CMATNR"));
				e.setCWERKS(rst.getString("CWERKS"));
				e.setCMATKL(rst.getString("CMATKL"));
				e.setCMAKTX(rst.getString("CMAKTX"));
				e.setCMEINS(rst.getString("CMEINS"));
				e.setBOMQ(rst.getDouble("BOMQ"));
				e.setTRNQ(rst.getDouble("TRNQ"));
				e.setSTKQ(rst.getDouble("STKQ"));
				e.setPURQ(rst.getDouble("PURQ"));
				e.setIDEQ(rst.getDouble("IDEQ"));
				e.setNETPR(rst.getDouble("NETPR"));
				e.setCFMDT(rst.getString("CFMDT"));
				e.setLTEXT(rst.getString("LTEXT"));
				e.setJLYH(rst.getString("JLYH"));
				e.setJLSJ(rst.getTimestamp("JLSJ"));
				e.setGXYH(rst.getString("GXYH"));
				e.setGXSJ(rst.getTimestamp("GXSJ"));

				list.add(e);
			}

			rst.close();

			pstm.close();

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		return list;
	}

	public static void updateDb(List<D031M> list, Connection conn) throws Exception {
		conn.setAutoCommit(false);
		try {

			String sql = "UPDATE D031M " //
					+ "   SET GXSJ = SYSDATE, " //
					+ "       IDEQ = ?, " //
					+ "       CFMDT = ?, " //
					+ "       LTEXT = ?, " //
					+ "       GXYH = ? " //
					+ " WHERE GSDM = ? AND BDDM = ? AND BDBH = ? AND PMATNR = ? AND SQNR = ?"; //

			PreparedStatement pstm = conn.prepareStatement(sql);

			for (D031M e : list) {
				pstm.setDouble(1, e.getIDEQ());
				pstm.setString(2, e.getCFMDT());
				pstm.setString(3, e.getLTEXT());
				pstm.setString(4, e.getGXYH());
				pstm.setString(5, e.getGSDM());
				pstm.setString(6, e.getBDDM());
				pstm.setString(7, e.getBDBH());
				pstm.setString(8, e.getPMATNR());
				pstm.setInt(9, e.getSQNR());
				pstm.executeUpdate();
			}
			pstm.close();

			conn.commit();
			conn.setAutoCommit(true);

		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		}
		conn.close();
	}

	public String getGSDM() {
		return GSDM;
	}

	public void setGSDM(String gSDM) {
		GSDM = gSDM;
	}

	public String getBDDM() {
		return BDDM;
	}

	public void setBDDM(String bDDM) {
		BDDM = bDDM;
	}

	public String getBDBH() {
		return BDBH;
	}

	public void setBDBH(String bDBH) {
		BDBH = bDBH;
	}

	public String getPMATNR() {
		return PMATNR;
	}

	public void setPMATNR(String pMATNR) {
		PMATNR = pMATNR;
	}

	public String getPWERKS() {
		return PWERKS;
	}

	public void setPWERKS(String pWERKS) {
		PWERKS = pWERKS;
	}

	public int getSQNR() {
		return SQNR;
	}

	public void setSQNR(int sQNR) {
		SQNR = sQNR;
	}

	public String getCMATNR() {
		return CMATNR;
	}

	public void setCMATNR(String cMATNR) {
		CMATNR = cMATNR;
	}

	public String getCWERKS() {
		return CWERKS;
	}

	public void setCWERKS(String cWERKS) {
		CWERKS = cWERKS;
	}

	public String getCMATKL() {
		return CMATKL;
	}

	public void setCMATKL(String cMATKL) {
		CMATKL = cMATKL;
	}

	public String getCMAKTX() {
		return CMAKTX;
	}

	public void setCMAKTX(String cMAKTX) {
		CMAKTX = cMAKTX;
	}

	public String getCMEINS() {
		return CMEINS;
	}

	public void setCMEINS(String cMEINS) {
		CMEINS = cMEINS;
	}

	public double getBOMQ() {
		return BOMQ;
	}

	public void setBOMQ(double bOMQ) {
		BOMQ = bOMQ;
	}

	public double getTRNQ() {
		return TRNQ;
	}

	public void setTRNQ(double tRNQ) {
		TRNQ = tRNQ;
	}

	public double getSTKQ() {
		return STKQ;
	}

	public void setSTKQ(double sTKQ) {
		STKQ = sTKQ;
	}

	public double getPURQ() {
		return PURQ;
	}

	public void setPURQ(double pURQ) {
		PURQ = pURQ;
	}

	public double getIDEQ() {
		return IDEQ;
	}

	public void setIDEQ(double iDEQ) {
		IDEQ = iDEQ;
	}

	public double getNETPR() {
		return NETPR;
	}

	public void setNETPR(double nETPR) {
		NETPR = nETPR;
	}

	public String getCFMDT() {
		return CFMDT;
	}

	public void setCFMDT(String cFMDT) {
		CFMDT = cFMDT;
	}

	public String getLTEXT() {
		return LTEXT;
	}

	public void setLTEXT(String lTEXT) {
		LTEXT = lTEXT;
	}

	public String getJLYH() {
		return JLYH;
	}

	public void setJLYH(String jLYH) {
		JLYH = jLYH;
	}

	public Date getJLSJ() {
		return JLSJ;
	}

	public void setJLSJ(Date jLSJ) {
		JLSJ = jLSJ;
	}

	public String getGXYH() {
		return GXYH;
	}

	public void setGXYH(String gXYH) {
		GXYH = gXYH;
	}

	public Date getGXSJ() {
		return GXSJ;
	}

	public void setGXSJ(Date gXSJ) {
		GXSJ = gXSJ;
	}

	public double getPLIFZ() {
		return PLIFZ;
	}

	public void setPLIFZ(double pLIFZ) {
		PLIFZ = pLIFZ;
	}

	public double getBSTRF() {
		return BSTRF;
	}

	public void setBSTRF(double bSTRF) {
		BSTRF = bSTRF;
	}

}
