package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class SGME01H {
	private int id =0;
	private String MATNR = "";
	private String MAKTX = "";
	private String WERKS = "";
	private String VDATU = "";
	private String BDATU = "";
	private String LIFNR = "";
	private String SORTL = "";
	private String EKORG = "";
	private String AUTET = "";
	private String REVLV = "";
	private String FREI_DAT = "";
	private double FREI_MNG = 0;
	private String JLYH = "";
	private Date JLSJ = null;
	private String ME01 = "A";
	private String QI01 = "A";
	private Date ME01JLSJ = null;
	private Date QI01JLSJ = null;
	private String ME01EMSG = "";
	private String QI01EMSG = "";
	private String SPERRGRUND = "";
	
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

	public String getWERKS() {
		return WERKS;
	}

	public void setWERKS(String wERKS) {
		WERKS = wERKS;
	}

	public String getVDATU() {
		return VDATU;
	}

	public void setVDATU(String vDATU) {
		VDATU = vDATU;
	}

	public String getBDATU() {
		return BDATU;
	}

	public void setBDATU(String bDATU) {
		BDATU = bDATU;
	}

	public String getLIFNR() {
		return LIFNR;
	}

	public void setLIFNR(String lIFNR) {
		LIFNR = lIFNR;
	}

	public String getSORTL() {
		return SORTL;
	}

	public void setSORTL(String sORTL) {
		SORTL = sORTL;
	}

	public String getEKORG() {
		return EKORG;
	}

	public void setEKORG(String eKORG) {
		EKORG = eKORG;
	}

	public String getAUTET() {
		return AUTET;
	}

	public void setAUTET(String aUTET) {
		AUTET = aUTET;
	}

	public String getREVLV() {
		return REVLV;
	}

	public void setREVLV(String rEVLV) {
		REVLV = rEVLV;
	}

	public String getFREI_DAT() {
		return FREI_DAT;
	}

	public void setFREI_DAT(String fREI_DAT) {
		FREI_DAT = fREI_DAT;
	}

	public double getFREI_MNG() {
		return FREI_MNG;
	}

	public void setFREI_MNG(double fREI_MNG) {
		FREI_MNG = fREI_MNG;
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

	public String getME01() {
		return ME01;
	}

	public void setME01(String mE01) {
		ME01 = mE01;
	}

	public String getQI01() {
		return QI01;
	}

	public void setQI01(String qI01) {
		QI01 = qI01;
	}

	public Date getME01JLSJ() {
		return ME01JLSJ;
	}

	public void setME01JLSJ(Date mE01JLSJ) {
		ME01JLSJ = mE01JLSJ;
	}

	public Date getQI01JLSJ() {
		return QI01JLSJ;
	}

	public void setQI01JLSJ(Date qI01JLSJ) {
		QI01JLSJ = qI01JLSJ;
	}

	public String getME01EMSG() {
		return ME01EMSG;
	}

	public void setME01EMSG(String mE01EMSG) {
		ME01EMSG = mE01EMSG;
	}

	public String getQI01EMSG() {
		return QI01EMSG;
	}

	public void setQI01EMSG(String qI01EMSG) {
		QI01EMSG = qI01EMSG;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getSPERRGRUND() {
		return SPERRGRUND;
	}

	public void setSPERRGRUND(String sPERRGRUND) {
		SPERRGRUND = sPERRGRUND;
	}

	public void insertDb(Connection conn) throws Exception {
		String sql = "INSERT INTO SGME01(ID,MATNR,MAKTX,WERKS,VDATU,BDATU,LIFNR,SORTL,EKORG,AUTET,REVLV,FREI_DAT,FREI_MNG,JLYH,JLSJ,ME01,QI01,ME01JLSJ,QI01JLSJ,ME01EMSG,QI01EMSG,SPERRGRUND) VALUES(SGME01SQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1,MATNR);
		pstm.setString(2,MAKTX);
		pstm.setString(3,WERKS);
		pstm.setString(4,VDATU);
		pstm.setString(5,BDATU);
		pstm.setString(6,LIFNR);
		pstm.setString(7,SORTL);
		pstm.setString(8,EKORG);
		pstm.setString(9,AUTET);
		pstm.setString(10,REVLV);
		pstm.setString(11,FREI_DAT);
		pstm.setDouble(12,FREI_MNG);
		pstm.setString(13,JLYH);
		pstm.setTimestamp(14,Helper.toSqlDate(JLSJ));
		pstm.setString(15,ME01);
		pstm.setString(16,QI01);
		pstm.setTimestamp(17,Helper.toSqlDate(ME01JLSJ));
		pstm.setTimestamp(18,Helper.toSqlDate(QI01JLSJ));
		pstm.setString(19,ME01EMSG);
		pstm.setString(20,QI01EMSG);
		pstm.setString(21, SPERRGRUND);
		pstm.executeUpdate();
		pstm.close();
	}

}
