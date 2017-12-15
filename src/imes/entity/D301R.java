package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class D301R {
	private String GSDM = "";
	private String BDDM = "";
	private String BDBH = "";
	private int SEQ = 0;
	private String SUPPLIER = "";
	private String SUPPLIER_NAME = "";
	private String CONTACT = "";
	private String PHONE = "";
	private String PRICE = "";
	private String UNIT = "";
	private String QUALITY = "";
	private String CURRENCY = "";
	private String JLYH = "";
	private Date JLSJ = null;
	private String BDFD = "";
	
	public void insertDb(Connection conn) throws Exception{
		String sql = "INSERT INTO D301R (GSDM,BDDM,BDBH,SEQ,SUPPLIER,CONTACT,PHONE,PRICE,UNIT,QUALITY,CURRENCY,JLYH,JLSJ,BDFD,SUPPLIER_NAME) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1,GSDM);
		pstm.setString(2,BDDM);
		pstm.setString(3,BDBH);
		pstm.setInt(4,SEQ);
		pstm.setString(5,SUPPLIER);
		pstm.setString(6,CONTACT);
		pstm.setString(7,PHONE);
		pstm.setString(8,PRICE);
		pstm.setString(9,UNIT);
		pstm.setString(10,QUALITY);
		pstm.setString(11,CURRENCY);
		pstm.setString(12,JLYH);
		pstm.setTimestamp(13,Helper.toSqlDate(JLSJ));
		pstm.setString(14,BDFD);
		pstm.setString(15,SUPPLIER_NAME);
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

	public int getSEQ() {
		return SEQ;
	}

	public void setSEQ(int sEQ) {
		SEQ = sEQ;
	}

	public String getSUPPLIER() {
		return SUPPLIER;
	}

	public void setSUPPLIER(String sUPPLIER) {
		SUPPLIER = sUPPLIER;
	}

	public String getSUPPLIER_NAME() {
		return SUPPLIER_NAME;
	}

	public void setSUPPLIER_NAME(String sUPPLIER_NAME) {
		SUPPLIER_NAME = sUPPLIER_NAME;
	}

	public String getCONTACT() {
		return CONTACT;
	}

	public void setCONTACT(String cONTACT) {
		CONTACT = cONTACT;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public String getPRICE() {
		return PRICE;
	}

	public void setPRICE(String pRICE) {
		PRICE = pRICE;
	}

	public String getUNIT() {
		return UNIT;
	}

	public void setUNIT(String uNIT) {
		UNIT = uNIT;
	}

	public String getQUALITY() {
		return QUALITY;
	}

	public void setQUALITY(String qUALITY) {
		QUALITY = qUALITY;
	}

	public String getCURRENCY() {
		return CURRENCY;
	}

	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
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

	public String getBDFD() {
		return BDFD;
	}

	public void setBDFD(String bDFD) {
		BDFD = bDFD;
	}
	
}
