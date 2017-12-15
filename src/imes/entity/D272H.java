package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class D272H {
	private String GSDM = "";
	private String BDDM = "";
	private String BDBH = "";
	private String BDRQ = "";
	private String BDZT = "";
	private String BDJG = "";
	private String YYLB = "";
	private String YYSM = "";
	private String AUFNR = "";
	private String WERKS = "";
	private String GSTRI = "";
	private double PSMNG = 0;
	private double WEMNG = 0;
	private String MATNR = "";
	private String MAKTX = "";
	private String KOSTL = "";
	private String KTEXT = "";
	private String WAERS = "";
	private double BDAMT = 0;
	private String ZRFY = "";
	private String SQYH = "";
	private String QHYH = "";
	private Date QHSJ = null;
	private String JLYH = "";
	private Date JLSJ = null;
	private String GXYH = "";
	private Date GXSJ = null;
	private String BDFD = "";
	private String RSNUM = "";
	private String selWS = "";
	private String VORNR = "";
	private String ARBPL = "";

	public int insertDb(Connection conn) throws Exception {
		int i = 0;
		String sql = "INSERT INTO D272H (GSDM,BDDM,BDBH,BDRQ,BDZT,BDJG,YYLB,YYSM,AUFNR,WERKS,GSTRI,PSMNG,WEMNG,MATNR,MAKTX,KOSTL,KTEXT,WAERS,BDAMT,ZRFY,SQYH,QHYH,QHSJ,JLYH,JLSJ,GXYH,GXSJ,BDFD,RSNUM,VORNR,ARBPL) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1, GSDM);
		pstm.setString(2, BDDM);
		pstm.setString(3, BDBH);
		pstm.setString(4, BDRQ);
		pstm.setString(5, BDZT);
		pstm.setString(6, BDJG);
		pstm.setString(7, YYLB);
		pstm.setString(8, YYSM);
		pstm.setString(9, AUFNR);
		pstm.setString(10, WERKS);
		pstm.setString(11, GSTRI);
		pstm.setDouble(12, PSMNG);
		pstm.setDouble(13, WEMNG);
		pstm.setString(14, MATNR);
		pstm.setString(15, MAKTX);
		pstm.setString(16, KOSTL);
		pstm.setString(17, KTEXT);
		pstm.setString(18, WAERS);
		pstm.setDouble(19, BDAMT);
		pstm.setString(20, ZRFY);
		pstm.setString(21, SQYH);
		pstm.setString(22, QHYH);
		pstm.setTimestamp(23, Helper.toSqlDate(QHSJ));
		pstm.setString(24, JLYH);
		pstm.setTimestamp(25, Helper.toSqlDate(JLSJ));
		pstm.setString(26, GXYH);
		pstm.setTimestamp(27, Helper.toSqlDate(GXSJ));
		pstm.setString(28, BDFD);
		pstm.setString(29, RSNUM);
		pstm.setString(30, VORNR);
		pstm.setString(31, ARBPL);
		i = pstm.executeUpdate();
		pstm.close();
		return i;
	}

	public void find(Connection conn) {

		String sql = "SELECT * FROM D272H WHERE GSDM=? AND BDDM=? AND BDBH=?";
		try {
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, GSDM);
			pstm.setString(2, BDDM);
			pstm.setString(3, BDBH);

			ResultSet rst = pstm.executeQuery();

			if (rst.next()) {
				BDRQ = rst.getString("BDRQ");
				BDZT = rst.getString("BDZT");
				BDJG = rst.getString("BDJG");
				YYLB = rst.getString("YYLB");
				YYSM = rst.getString("YYSM");
				AUFNR = rst.getString("AUFNR");
				WERKS = rst.getString("WERKS");
				GSTRI = rst.getString("GSTRI");
				PSMNG = rst.getDouble("PSMNG");
				WEMNG = rst.getDouble("WEMNG");
				MATNR = rst.getString("MATNR");
				MAKTX = rst.getString("MAKTX");
				KOSTL = rst.getString("KOSTL");
				KTEXT = rst.getString("KTEXT");
				WAERS = rst.getString("WAERS");
				BDAMT = rst.getDouble("BDAMT");
				ZRFY = rst.getString("ZRFY");
				SQYH = rst.getString("SQYH");
				QHYH = rst.getString("QHYH");
				QHSJ = rst.getTimestamp("QHSJ");
				JLYH = rst.getString("JLYH");
				JLSJ = rst.getTimestamp("JLSJ");
				GXYH = rst.getString("GXYH");
				GXSJ = rst.getTimestamp("GXSJ");
				BDFD = rst.getString("BDFD");
				RSNUM = rst.getString("RSNUM");
				VORNR = rst.getString("VORNR");
				ARBPL = rst.getString("ARBPL");
				selWS = "<option value=\""+rst.getString("VORNR")+"_"+rst.getString("ARBPL")+"\">"+rst.getString("VORNR")+"_"+rst.getString("ARBPL")+"</option>";
			}
			rst.close();
			pstm.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public String getBDRQ() {
		return BDRQ;
	}

	public void setBDRQ(String bDRQ) {
		BDRQ = bDRQ;
	}

	public String getBDZT() {
		return BDZT;
	}

	public void setBDZT(String bDZT) {
		BDZT = bDZT;
	}

	public String getBDJG() {
		return BDJG;
	}

	public void setBDJG(String bDJG) {
		BDJG = bDJG;
	}

	public String getYYLB() {
		return YYLB;
	}

	public void setYYLB(String yYLB) {
		YYLB = yYLB;
	}

	public String getYYSM() {
		return YYSM;
	}

	public void setYYSM(String yYSM) {
		YYSM = yYSM;
	}

	public String getAUFNR() {
		return AUFNR;
	}

	public void setAUFNR(String aUFNR) {
		AUFNR = aUFNR;
	}

	public String getWERKS() {
		return WERKS;
	}

	public void setWERKS(String wERKS) {
		WERKS = wERKS;
	}

	public String getGSTRI() {
		return GSTRI;
	}

	public void setGSTRI(String gSTRI) {
		GSTRI = gSTRI;
	}

	public double getPSMNG() {
		return PSMNG;
	}

	public void setPSMNG(double pSMNG) {
		PSMNG = pSMNG;
	}

	public double getWEMNG() {
		return WEMNG;
	}

	public void setWEMNG(double wEMNG) {
		WEMNG = wEMNG;
	}

	public String getMATNR() {
		return MATNR;
	}

	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}

	public String getMAKTX() {
		return MAKTX;
	}

	public void setMAKTX(String mAKTX) {
		MAKTX = mAKTX;
	}

	public String getKOSTL() {
		return KOSTL;
	}

	public void setKOSTL(String kOSTL) {
		KOSTL = kOSTL;
	}

	public String getKTEXT() {
		return KTEXT;
	}

	public void setKTEXT(String kTEXT) {
		KTEXT = kTEXT;
	}

	public String getWAERS() {
		return WAERS;
	}

	public void setWAERS(String wAERS) {
		WAERS = wAERS;
	}

	public double getBDAMT() {
		return BDAMT;
	}

	public void setBDAMT(double bDAMT) {
		BDAMT = bDAMT;
	}

	public String getZRFY() {
		return ZRFY;
	}

	public void setZRFY(String zRFY) {
		ZRFY = zRFY;
	}

	public String getSQYH() {
		return SQYH;
	}

	public void setSQYH(String sQYH) {
		SQYH = sQYH;
	}

	public String getQHYH() {
		return QHYH;
	}

	public void setQHYH(String qHYH) {
		QHYH = qHYH;
	}

	public Date getQHSJ() {
		return QHSJ;
	}

	public void setQHSJ(Date qHSJ) {
		QHSJ = qHSJ;
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

	public String getBDFD() {
		return BDFD;
	}

	public void setBDFD(String bDFD) {
		BDFD = bDFD;
	}

	public String getRSNUM() {
		return RSNUM;
	}

	public void setRSNUM(String rSNUM) {
		RSNUM = rSNUM;
	}

	public String getSelWS() {
		return selWS;
	}

	public void setSelWS(String selWS) {
		this.selWS = selWS;
	}

	public String getVORNR() {
		return VORNR;
	}

	public void setVORNR(String vORNR) {
		VORNR = vORNR;
	}

	public String getARBPL() {
		return ARBPL;
	}

	public void setARBPL(String aRBPL) {
		ARBPL = aRBPL;
	}

}
