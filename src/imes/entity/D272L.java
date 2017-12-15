package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class D272L {
	private String GSDM = "";
	private String BDDM = "";
	private String BDBH = "";
	private int SQNR = 0;
	private String CMATNR = "";
	private String CWERKS = "";
	private String CMATKL = "";
	private String CMAKTX = "";
	private String CMEINS = "";
	private String CEKGRP = "";
	private double NETPR = 0;
	private double REQQ = 0;
	private double REQA = 0;
	private double ISSQ = 0;
	private double ISSA = 0;
	private String CFMDT = "";
	private String CGSM = "";
	private String CGYH = "";
	private String XQSM = "";
	private String JLYH = "";
	private Date JLSJ = null;
	private String GXYH = "";
	private Date GXSJ = null;
	

	public int insertDb(Connection conn) throws Exception{
		int i = 0;
		String sql = "INSERT INTO D272L(GSDM,BDDM,BDBH,SQNR,CMATNR,CWERKS,CMATKL,CMAKTX,CMEINS,NETPR,REQQ,REQA,ISSQ,ISSA,CFMDT,CGSM,CGYH,XQSM,JLYH,JLSJ,GXYH,GXSJ,CEKGRP) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1,GSDM);
		pstm.setString(2,BDDM);
		pstm.setString(3,BDBH);
		pstm.setInt(4,SQNR);
		pstm.setString(5,CMATNR);
		pstm.setString(6,CWERKS);
		pstm.setString(7,CMATKL);
		pstm.setString(8,CMAKTX);
		pstm.setString(9,CMEINS);
		pstm.setDouble(10,NETPR);
		pstm.setDouble(11,REQQ);
		pstm.setDouble(12,REQA);
		pstm.setDouble(13,ISSQ);
		pstm.setDouble(14,ISSA);
		pstm.setString(15,CFMDT);
		pstm.setString(16,CGSM);
		pstm.setString(17,CGYH);
		pstm.setString(18,XQSM);
		pstm.setString(19,JLYH);
		pstm.setTimestamp(20,Helper.toSqlDate(JLSJ));
		pstm.setString(21,GXYH);
		pstm.setTimestamp(22,Helper.toSqlDate(GXSJ));
		pstm.setString(23, CEKGRP);
		i = pstm.executeUpdate();
		pstm.close();
		return i;
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

	public double getNETPR() {
		return NETPR;
	}

	public void setNETPR(double nETPR) {
		NETPR = nETPR;
	}

	public double getREQQ() {
		return REQQ;
	}

	public void setREQQ(double rEQQ) {
		REQQ = rEQQ;
	}

	public double getREQA() {
		return REQA;
	}

	public void setREQA(double rEQA) {
		REQA = rEQA;
	}

	public double getISSQ() {
		return ISSQ;
	}

	public void setISSQ(double iSSQ) {
		ISSQ = iSSQ;
	}

	public double getISSA() {
		return ISSA;
	}

	public void setISSA(double iSSA) {
		ISSA = iSSA;
	}

	public String getCFMDT() {
		return CFMDT;
	}

	public void setCFMDT(String cFMDT) {
		CFMDT = cFMDT;
	}

	public String getCGSM() {
		return CGSM;
	}

	public void setCGSM(String cGSM) {
		CGSM = cGSM;
	}

	public String getCGYH() {
		return CGYH;
	}

	public void setCGYH(String cGYH) {
		CGYH = cGYH;
	}

	public String getXQSM() {
		return XQSM;
	}

	public void setXQSM(String xQSM) {
		XQSM = xQSM;
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

	public String getCEKGRP() {
		return CEKGRP;
	}

	public void setCEKGRP(String cEKGRP) {
		CEKGRP = cEKGRP;
	}

	
}
