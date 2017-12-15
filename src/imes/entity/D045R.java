package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class D045R {
	private String GSDM = "";
	private String BDDM = "";
	private String BDBH = "";
	private int SQNR = 0;
	private String KOSTL = "";
	private String LIFNR = "";
	private String KUNNR = "";
	private String KTEXT = "";
	private String NAME1 = "";
	private int PCTG = 0;
	private String JLYH = "";
	private Date JLSJ = null;
	
	
	public void insertDb(Connection conn) throws Exception{
		String sql = "INSERT INTO D045R (GSDM,BDDM,BDBH,SQNR,KOSTL,KUNNR,LIFNR,KTEXT,NAME1,PCTG,JLYH,JLSJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1,GSDM);
		pstm.setString(2,BDDM);
		pstm.setString(3,BDBH);
		pstm.setInt(4,SQNR);
		pstm.setString(5,KOSTL);
		pstm.setString(6,KUNNR);
		pstm.setString(7,LIFNR);
		pstm.setString(8,KTEXT);
		pstm.setString(9,NAME1);
		pstm.setInt(10,PCTG);
		pstm.setString(11,JLYH);
		pstm.setTimestamp(12,Helper.toSqlDate(JLSJ));
		pstm.executeUpdate();
		pstm.close();
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

	public int getSQNR() {
		return SQNR;
	}

	public void setSQNR(int sQNR) {
		SQNR = sQNR;
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

	public int getPCTG() {
		return PCTG;
	}

	public void setPCTG(int pCTG) {
		PCTG = pCTG;
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

	public String getNAME1() {
		return NAME1;
	}

	public void setNAME1(String nAME1) {
		NAME1 = nAME1;
	}

	public String getLIFNR() {
		return LIFNR;
	}

	public void setLIFNR(String lIFNR) {
		LIFNR = lIFNR;
	}

	public String getKUNNR() {
		return KUNNR;
	}

	public void setKUNNR(String kUNNR) {
		KUNNR = kUNNR;
	}

	
}
