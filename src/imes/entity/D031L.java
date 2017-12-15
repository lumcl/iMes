package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class D031L implements Cloneable {
	private String GSDM = ""; // 公司代碼
	private String BDDM = ""; // 表單代碼
	private String BDBH = ""; // 表單編號
	private String XCLB = ""; // 項次類別 A=原來, C=更改
	private int XCXH = 0; // 項次序號
	private String XCZT = ""; // 項次狀態
	private Date GZSJ = new Date(); // 過帳時間
	private String GZYH = ""; // 過帳用戶
	private String VTWEG = "";
	private String WERKS = "";
	private String KUNNR = "";
	private String SORTL = "";
	private String AUART = "";
	private String VBELN = "";
	private String POSNR = "";
	private String POSNR2 = "";
	private String ETENR = "";
	private String EDATU = "";
	private String MBDAT = "";
	private String MATNR = "";
	private String ARKTX = "";
	private String MATKL = "";
	private double OMENG = 0;
	private String MEINS = "";
	private double NETPR = 0;
	private String WAERK = "";
	private String VSART = "";
	private String ZTERM = "";
	private String INCO1 = "";
	private String INCO2 = "";
	private String KDMAT = "";
	private String AUFNR = "";
	private String JLYH = ""; // 建立用戶
	private Date JLSJ = new Date(); // 建立時間
	private String GXYH = ""; // 更新用戶
	private Date GXSJ = new Date(); // 更新時間

	public int insertDb(Connection conn) throws Exception {
		int i;
		String sql = "INSERT INTO D031L " //
				+ "(GSDM,BDDM,BDBH,XCLB,XCXH,XCZT,GZSJ,GZYH,VTWEG,WERKS,KUNNR,SORTL,AUART,VBELN,POSNR,POSNR2,ETENR,EDATU,MBDAT,MATNR,ARKTX,MATKL,OMENG,MEINS,NETPR,WAERK,VSART,ZTERM,INCO1,INCO2,KDMAT,JLYH,JLSJ,GXYH,GXSJ,AUFNR) " //
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";//
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, GSDM);
		pstm.setString(2, BDDM);
		pstm.setString(3, BDBH);
		pstm.setString(4, XCLB);
		pstm.setInt(5, XCXH);
		pstm.setString(6, XCZT);
		pstm.setTimestamp(7, Helper.toSqlDate(GZSJ));
		pstm.setString(8, GZYH);
		pstm.setString(9, VTWEG);
		pstm.setString(10, WERKS);
		pstm.setString(11, KUNNR);
		pstm.setString(12, SORTL);
		pstm.setString(13, AUART);
		pstm.setString(14, VBELN);
		pstm.setString(15, POSNR);
		pstm.setString(16, POSNR2);
		pstm.setString(17, ETENR);
		pstm.setString(18, EDATU);
		pstm.setString(19, MBDAT);
		pstm.setString(20, MATNR);
		pstm.setString(21, ARKTX);
		pstm.setString(22, MATKL);
		pstm.setDouble(23, OMENG);
		pstm.setString(24, MEINS);
		pstm.setDouble(25, NETPR);
		pstm.setString(26, WAERK);
		pstm.setString(27, VSART);
		pstm.setString(28, ZTERM);
		pstm.setString(29, INCO1);
		pstm.setString(30, INCO2);
		pstm.setString(31, KDMAT);
		pstm.setString(32, JLYH);
		pstm.setTimestamp(33, Helper.toSqlDate(JLSJ));
		pstm.setString(34, GXYH);
		pstm.setTimestamp(35, Helper.toSqlDate(GXSJ));
		pstm.setString(36, AUFNR);
		i = pstm.executeUpdate();
		pstm.close();
		return i;
	}

	public static List<D031L> findBy(String GSDM, String BDDM, String BDBH, Connection conn) {

		List<D031L> list = new ArrayList<D031L>();

		D031L e;

		String sql = "SELECT * FROM D031L WHERE GSDM=? AND BDDM=? AND BDBH=? ORDER BY XCXH";

		try {

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			ResultSet rst = pstm.executeQuery();

			while (rst.next()) {

				e = new D031L();

				e.setGSDM(rst.getString("GSDM"));
				e.setBDDM(rst.getString("BDDM"));
				e.setBDBH(rst.getString("BDBH"));
				e.setXCLB(rst.getString("XCLB"));
				e.setXCXH(rst.getInt("XCXH"));
				e.setXCZT(rst.getString("XCZT"));
				e.setGZSJ(rst.getTimestamp("GZSJ"));
				e.setGZYH(rst.getString("GZYH"));
				e.setVTWEG(rst.getString("VTWEG"));
				e.setWERKS(rst.getString("WERKS"));
				e.setKUNNR(rst.getString("KUNNR"));
				e.setSORTL(rst.getString("SORTL"));
				e.setAUART(rst.getString("AUART"));
				e.setVBELN(rst.getString("VBELN"));
				e.setPOSNR(rst.getString("POSNR"));
				e.setPOSNR2(rst.getString("POSNR2"));
				e.setETENR(rst.getString("ETENR"));
				e.setEDATU(rst.getString("EDATU"));
				e.setMBDAT(rst.getString("MBDAT"));
				e.setMATNR(rst.getString("MATNR"));
				e.setARKTX(rst.getString("ARKTX"));
				e.setMATKL(rst.getString("MATKL"));
				e.setOMENG(rst.getDouble("OMENG"));
				e.setMEINS(rst.getString("MEINS"));
				e.setNETPR(rst.getDouble("NETPR"));
				e.setWAERK(rst.getString("WAERK"));
				e.setVSART(rst.getString("VSART"));
				e.setZTERM(rst.getString("ZTERM"));
				e.setINCO1(rst.getString("INCO1"));
				e.setINCO2(rst.getString("INCO2"));
				e.setKDMAT(rst.getString("KDMAT"));
				e.setJLYH(rst.getString("JLYH"));
				e.setJLSJ(rst.getTimestamp("JLSJ"));
				e.setGXYH(rst.getString("GXYH"));
				e.setGXSJ(rst.getTimestamp("GXSJ"));
				e.setAUFNR(rst.getString("AUFNR"));

				list.add(e);
			}

			rst.close();
			pstm.close();

		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

		return list;
	}

	public static List<HashMap<String, Object>> getSummary(String GSDM, String BDDM, String BDBH, Connection conn) {

		List<HashMap<String, Object>> list;

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
				+ "SELECT TBF.BDBH, TBF.SORTL, TBF.MATNR, TBF.WERKS, TBF.BFQTY, " //
				+ "       TBF.BFDAT, TAF.AFQTY, TAF.AFDAT, HD.YYLB, HD.JLYH, " //
				+ "       HD.JLSJ, HD.QHYH, HD.QHSJ, HD.BDJG, (AFQTY - BFQTY) DFQTY " //
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

		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			list = Helper.resultSetToArrayList(pstm.executeQuery());
			//System.out.println("List:"+list.size());
			pstm.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			list = new ArrayList<HashMap<String,Object>>();
		}
		return list;
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

	public String getXCLB() {
		return XCLB;
	}

	public void setXCLB(String xCLB) {
		XCLB = xCLB;
	}

	public int getXCXH() {
		return XCXH;
	}

	public void setXCXH(int xCXH) {
		XCXH = xCXH;
	}

	public String getXCZT() {
		return XCZT;
	}

	public void setXCZT(String xCZT) {
		XCZT = xCZT;
	}

	public Date getGZSJ() {
		return GZSJ;
	}

	public void setGZSJ(Date gZSJ) {
		GZSJ = gZSJ;
	}

	public String getGZYH() {
		return GZYH;
	}

	public void setGZYH(String gZYH) {
		GZYH = gZYH;
	}

	public String getVTWEG() {
		return VTWEG;
	}

	public void setVTWEG(String vTWEG) {
		VTWEG = vTWEG;
	}

	public String getWERKS() {
		return WERKS;
	}

	public void setWERKS(String wERKS) {
		WERKS = wERKS;
	}

	public String getKUNNR() {
		return KUNNR;
	}

	public void setKUNNR(String kUNNR) {
		KUNNR = kUNNR;
	}

	public String getSORTL() {
		return SORTL;
	}

	public void setSORTL(String sORTL) {
		SORTL = sORTL;
	}

	public String getAUART() {
		return AUART;
	}

	public void setAUART(String aUART) {
		AUART = aUART;
	}

	public String getVBELN() {
		return VBELN;
	}

	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}

	public String getPOSNR() {
		return POSNR;
	}

	public void setPOSNR(String pOSNR) {
		POSNR = pOSNR;
	}

	public String getPOSNR2() {
		return POSNR2;
	}

	public void setPOSNR2(String pOSNR2) {
		POSNR2 = pOSNR2;
	}

	public String getETENR() {
		return ETENR;
	}

	public void setETENR(String eTENR) {
		ETENR = eTENR;
	}

	public String getEDATU() {
		return EDATU;
	}

	public void setEDATU(String eDATU) {
		EDATU = eDATU;
	}

	public String getMBDAT() {
		return MBDAT;
	}

	public void setMBDAT(String mBDAT) {
		MBDAT = mBDAT;
	}

	public String getMATNR() {
		return MATNR;
	}

	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}

	public String getARKTX() {
		return ARKTX;
	}

	public void setARKTX(String aRKTX) {
		ARKTX = aRKTX;
	}

	public String getMATKL() {
		return MATKL;
	}

	public void setMATKL(String mATKL) {
		MATKL = mATKL;
	}

	public double getOMENG() {
		return OMENG;
	}

	public void setOMENG(double oMENG) {
		OMENG = oMENG;
	}

	public String getMEINS() {
		return MEINS;
	}

	public void setMEINS(String mEINS) {
		MEINS = mEINS;
	}

	public double getNETPR() {
		return NETPR;
	}

	public void setNETPR(double nETPR) {
		NETPR = nETPR;
	}

	public String getWAERK() {
		return WAERK;
	}

	public void setWAERK(String wAERK) {
		WAERK = wAERK;
	}

	public String getVSART() {
		return VSART;
	}

	public void setVSART(String vSART) {
		VSART = vSART;
	}

	public String getZTERM() {
		return ZTERM;
	}

	public void setZTERM(String zTERM) {
		ZTERM = zTERM;
	}

	public String getINCO1() {
		return INCO1;
	}

	public void setINCO1(String iNCO1) {
		INCO1 = iNCO1;
	}

	public String getINCO2() {
		return INCO2;
	}

	public void setINCO2(String iNCO2) {
		INCO2 = iNCO2;
	}

	public String getKDMAT() {
		return KDMAT;
	}

	public void setKDMAT(String kDMAT) {
		KDMAT = kDMAT;
	}

	public String getAUFNR() {
		return AUFNR;
	}

	public void setAUFNR(String aUFNR) {
		AUFNR = aUFNR;
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

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	
	
}
