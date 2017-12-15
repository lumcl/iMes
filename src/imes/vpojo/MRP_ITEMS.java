package imes.vpojo;

import java.util.Date;

public class MRP_ITEMS {

	private String MATNR = "";
	private String WERKS = "";
	private Date DAT00; // AVAIL_DATE
	private int TAG00 = 0; // SORTIND_00
	private String SORT1 = ""; // SORTIND_01
	private String SORT2 = ""; // SORTIND_02
	private String DELKZ = ""; // MRP_ELEMENT_IND
	private String PLUMI = ""; // PLUS_MINUS
	private double MNG01 = 0; // REC_REQD_QTY
	private Date DAT01; // FINISH_DATE
	private Date UMDAT1; // RESCHED_DATE1
	private Date UMDAT2; // RESCHED_DATE2
	private String BAART = ""; // ORDER_TYPE
	private String AUSSL = ""; // EXCMSGKEY01
	private String OLDSL = ""; // EXCMSGKEY02
	private String LGORT_D = ""; // STORAGE_LOC
	private String DELNR = ""; // MRP_NO
	private String DEL12 = ""; // MRP_NO12
	private String DELPS = ""; // MRP_POS
	private String DELET = ""; // MRP_ITEM
	private String BAUGR = ""; // PEGGEDRQMT
	private String LIFNR = ""; // VENDOR_NO
	private String KUNNR = ""; // CUSTOMER
	private String SORTL = "";
	private String AUFVR = ""; // SOURCE_NO
	private String POSVR = ""; // SOURCE_ITEM
	private String PWWRK = ""; // PROD_PLANT
	private Date BEDAT; // PO_DATE

	private double ATP01 = 0;
	private String DELB0 = "";

	public String getMATNR() {
		return MATNR;
	}

	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}

	public String getWERKS() {
		return WERKS;
	}

	public void setWERKS(String wERKS) {
		WERKS = wERKS;
	}

	public Date getDAT00() {
		return DAT00;
	}

	public void setDAT00(Date dAT00) {
		DAT00 = dAT00;
	}

	public int getTAG00() {
		return TAG00;
	}

	public void setTAG00(int tAG00) {
		TAG00 = tAG00;
	}

	public String getSORT1() {
		return SORT1;
	}

	public void setSORT1(String sORT1) {
		SORT1 = sORT1;
	}

	public String getSORT2() {
		return SORT2;
	}

	public void setSORT2(String sORT2) {
		SORT2 = sORT2;
	}

	public String getDELKZ() {
		return DELKZ;
	}

	public void setDELKZ(String dELKZ) {
		DELKZ = dELKZ;
	}

	public String getPLUMI() {
		return PLUMI;
	}

	public void setPLUMI(String pLUMI) {
		PLUMI = pLUMI;
	}

	public double getMNG01() {
		return MNG01;
	}

	public void setMNG01(double mNG01) {
		MNG01 = mNG01;
	}

	public Date getDAT01() {
		return DAT01;
	}

	public void setDAT01(Date dAT01) {
		DAT01 = dAT01;
	}

	public Date getUMDAT1() {
		return UMDAT1;
	}

	public void setUMDAT1(Date uMDAT1) {
		UMDAT1 = uMDAT1;
	}

	public Date getUMDAT2() {
		return UMDAT2;
	}

	public void setUMDAT2(Date uMDAT2) {
		UMDAT2 = uMDAT2;
	}

	public String getBAART() {
		return BAART;
	}

	public void setBAART(String bAART) {
		BAART = bAART;
	}

	public String getAUSSL() {
		return AUSSL;
	}

	public void setAUSSL(String aUSSL) {
		AUSSL = aUSSL;
	}

	public String getOLDSL() {
		return OLDSL;
	}

	public void setOLDSL(String oLDSL) {
		OLDSL = oLDSL;
	}

	public String getLGORT_D() {
		return LGORT_D;
	}

	public void setLGORT_D(String lGORT_D) {
		LGORT_D = lGORT_D;
	}

	public String getDELNR() {
		return DELNR;
	}

	public void setDELNR(String dELNR) {
		DELNR = dELNR;
	}

	public String getDEL12() {
		return DEL12;
	}

	public void setDEL12(String dEL12) {
		DEL12 = dEL12;
	}

	public String getDELPS() {
		return DELPS;
	}

	public void setDELPS(String dELPS) {
		DELPS = dELPS;
	}

	public String getDELET() {
		return DELET;
	}

	public void setDELET(String dELET) {
		DELET = dELET;
	}

	public String getBAUGR() {
		return BAUGR;
	}

	public void setBAUGR(String bAUGR) {
		BAUGR = bAUGR;
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

	public double getATP01() {
		return ATP01;
	}

	public void setATP01(double aTP01) {
		ATP01 = aTP01;
	}

	public String getDELB0() {
		return DELB0;
	}

	public void setDELB0(String dELB0) {
		DELB0 = dELB0;
	}

	public String getSORTL() {
		return SORTL;
	}

	public void setSORTL(String sORTL) {
		SORTL = sORTL;
	}

	public String getAUFVR() {
		return AUFVR;
	}

	public void setAUFVR(String aUFVR) {
		AUFVR = aUFVR;
	}

	public String getPOSVR() {
		return POSVR;
	}

	public void setPOSVR(String pOSVR) {
		POSVR = pOSVR;
	}

	public String getPWWRK() {
		return PWWRK;
	}

	public void setPWWRK(String pWWRK) {
		PWWRK = pWWRK;
	}

	public Date getBEDAT() {
		return BEDAT;
	}

	public void setBEDAT(Date bEDAT) {
		BEDAT = bEDAT;
	}

}
