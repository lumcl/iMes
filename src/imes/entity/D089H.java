package imes.entity;

import imes.core.Helper;

import java.util.Date;

public class D089H {
	private String GSDM = "";
	private String BDDM = "";
	private String BDBH = "";
	private String BDRQ = "";
	private String BDZT = "";
	private String BDJG = "";
	private String BDFD = "";
	private String BDYY = "";
	private String BDNR = "";
	private String SQYH = "";
	private String QHKS = "";
	private String QHYH = "";
	private Date QHSJ = null;
	private String JLYH = "";
	private Date JLSJ = null;
	private String GXYH = "";
	private Date GXSJ = null;
	private String MANDT = "";
	private String EKORG = "";
	private String WERKS = "";
	private String LIFNR = "";
	private String NAME1 = "";
	private String SORTL = "";
	private String MWSKZ = "";
	private String WAERS = "";
	private double BDAMT = 0;
	private double MINPT = 0;
	private double MAXPT = 0;
	private double SNAMT = 0;
	private double AGAMT = 0;
	private double CUAMT = 0;
	private String MTCUR = "";

	private String SAPCF = "A";
	private String SAPRC = "";
	private String SAPEM = "";

	public String getBDZTT() {
		if (BDZT.equals("C")) {
			return "建立 [Created]";
		} else if (BDZT.equals("Q")) {
			return "簽核中 [Approving]";
		} else if (BDZT.equals("X")) {
			return "簽核完成[Completed]";
		} else {
			return BDZT;
		}
	}

	public String getMAXPTT() {
		if (MAXPT > 0) {
			return "漲價 [Increase]";
		} else if (MAXPT < 0) {
			return "降價 [Drop]";
		} else {
			return "";
		}
	}

	public String getPRDAT() {
		if (BDRQ == null || BDRQ.trim().equals("")) {
			BDRQ = Helper.fmtDate(new Date(), "yyyyMMdd");
		}

		int year = Helper.toInteger(BDRQ.substring(0, 4));
		int month = Helper.toInteger(BDRQ.substring(4, 6));

		String prdate = "";

		if (month > 5) {
			prdate = Integer.toString(year + 1) + "0630";
		} else {
			prdate = year + "1231";
		}

		return prdate;
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

	public String getBDFD() {
		return BDFD;
	}

	public void setBDFD(String bDFD) {
		BDFD = bDFD;
	}

	public String getBDYY() {
		return BDYY;
	}

	public void setBDYY(String bDYY) {
		BDYY = bDYY;
	}

	public String getBDNR() {
		return BDNR;
	}

	public void setBDNR(String bDNR) {
		BDNR = bDNR;
	}

	public String getSQYH() {
		return SQYH;
	}

	public void setSQYH(String sQYH) {
		SQYH = sQYH;
	}

	public String getQHKS() {
		return QHKS;
	}

	public void setQHKS(String qHKS) {
		QHKS = qHKS;
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

	public String getMANDT() {
		return MANDT;
	}

	public void setMANDT(String mANDT) {
		MANDT = mANDT;
	}

	public String getEKORG() {
		return EKORG;
	}

	public void setEKORG(String eKORG) {
		EKORG = eKORG;
	}

	public String getWERKS() {
		return WERKS;
	}

	public void setWERKS(String wERKS) {
		WERKS = wERKS;
	}

	public String getLIFNR() {
		return LIFNR;
	}

	public void setLIFNR(String lIFNR) {
		LIFNR = lIFNR;
	}

	public String getNAME1() {
		return NAME1;
	}

	public void setNAME1(String nAME1) {
		NAME1 = nAME1;
	}

	public String getSORTL() {
		return SORTL;
	}

	public void setSORTL(String sORTL) {
		SORTL = sORTL;
	}

	public String getMWSKZ() {
		return MWSKZ;
	}

	public void setMWSKZ(String mWSKZ) {
		MWSKZ = mWSKZ;
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

	public String getSAPCF() {
		return SAPCF;
	}

	public void setSAPCF(String sAPCF) {
		SAPCF = sAPCF;
	}

	public String getSAPRC() {
		return SAPRC;
	}

	public void setSAPRC(String sAPRC) {
		SAPRC = sAPRC;
	}

	public String getSAPEM() {
		return SAPEM;
	}

	public void setSAPEM(String sAPEM) {
		SAPEM = sAPEM;
	}

	public double getMINPT() {
		return MINPT;
	}

	public void setMINPT(double mINPT) {
		MINPT = mINPT;
	}

	public double getMAXPT() {
		return MAXPT;
	}

	public void setMAXPT(double mAXPT) {
		MAXPT = mAXPT;
	}

	public double getSNAMT() {
		return SNAMT;
	}

	public void setSNAMT(double sNAMT) {
		SNAMT = sNAMT;
	}

	public double getAGAMT() {
		return AGAMT;
	}

	public void setAGAMT(double aGAMT) {
		AGAMT = aGAMT;
	}

	public double getCUAMT() {
		return CUAMT;
	}

	public void setCUAMT(double cUAMT) {
		CUAMT = cUAMT;
	}

	public String getMTCUR() {
		return MTCUR;
	}

	public void setMTCUR(String mTCUR) {
		MTCUR = mTCUR;
	}

}
