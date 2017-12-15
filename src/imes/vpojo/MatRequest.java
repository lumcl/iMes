package imes.vpojo;

public class MatRequest implements Cloneable {

	private String BDART = " ";
	private String MATNR = " ";
	private String WERKS = " ";
	private String LGORT = " ";
	private String BDTER = " ";
	private double BDMNG = 0;
	private double ENMNG = 0;
	private double REMNG = 0;
	private String MEINS = " ";
	private String PBDNR = " ";
	private String PLNUM = " ";
	private String AUFNR = " ";
	private String PMATNR = " ";
	private String PWERKS = " ";
	private String POSNR = " ";
	private String BWART = " ";
	private String RSNUM = " ";
	private String RSPOS = " ";
	private String STYPE = " ";
	private String EBELN = " ";
	private String EBELP = " ";
	private String ETENR = " ";
	private double MENGE = 0;
	private String LIFNR = " ";
	private String AEDAT = " ";
	private String EKORG = " ";
	private String MAKTX = " ";
	private String MATKL = " ";
	private String ZEINR = " ";
	private String AESZN = " ";
	private String SORTL = " ";
	private String EKGRP = " ";
	private int BSTRF = 0;
	private String SHPDT = " ";

	public String getBDART() {
		return BDART;
	}

	public void setBDART(String bDART) {
		BDART = bDART;
	}

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

	public String getLGORT() {
		return LGORT;
	}

	public void setLGORT(String lGORT) {
		LGORT = lGORT;
	}

	public String getBDTER() {
		return BDTER;
	}

	public void setBDTER(String bDTER) {
		BDTER = bDTER;
	}

	public double getBDMNG() {
		return BDMNG;
	}

	public void setBDMNG(double bDMNG) {
		BDMNG = bDMNG;
	}

	public double getENMNG() {
		return ENMNG;
	}

	public void setENMNG(double eNMNG) {
		ENMNG = eNMNG;
	}

	public double getREMNG() {
		return REMNG;
	}

	public void setREMNG(double rEMNG) {
		REMNG = rEMNG;
	}

	public String getMEINS() {
		return MEINS;
	}

	public void setMEINS(String mEINS) {
		MEINS = mEINS;
	}

	public String getPBDNR() {
		return PBDNR;
	}

	public void setPBDNR(String pBDNR) {
		PBDNR = pBDNR;
	}

	public String getPLNUM() {
		return PLNUM;
	}

	public void setPLNUM(String pLNUM) {
		PLNUM = pLNUM;
	}

	public String getAUFNR() {
		return AUFNR;
	}

	public void setAUFNR(String aUFNR) {
		AUFNR = aUFNR;
	}

	public String getPOSNR() {
		return POSNR;
	}

	public void setPOSNR(String pOSNR) {
		POSNR = pOSNR;
	}

	public String getBWART() {
		return BWART;
	}

	public void setBWART(String bWART) {
		BWART = bWART;
	}

	public String getRSNUM() {
		return RSNUM;
	}

	public void setRSNUM(String rSNUM) {
		RSNUM = rSNUM;
	}

	public String getRSPOS() {
		return RSPOS;
	}

	public void setRSPOS(String rSPOS) {
		RSPOS = rSPOS;
	}

	public String getSTYPE() {
		return STYPE;
	}

	public void setSTYPE(String sTYPE) {
		STYPE = sTYPE;
	}

	public String getEBELN() {
		return EBELN;
	}

	public void setEBELN(String eBELN) {
		EBELN = eBELN;
	}

	public String getEBELP() {
		return EBELP;
	}

	public void setEBELP(String eBELP) {
		EBELP = eBELP;
	}

	public String getETENR() {
		return ETENR;
	}

	public void setETENR(String eTENR) {
		ETENR = eTENR;
	}

	public double getMENGE() {
		return MENGE;
	}

	public void setMENGE(double mENGE) {
		MENGE = mENGE;
	}

	public String getLIFNR() {
		return LIFNR;
	}

	public void setLIFNR(String lIFNR) {
		LIFNR = lIFNR;
	}

	public String getAEDAT() {
		return AEDAT;
	}

	public void setAEDAT(String aEDAT) {
		AEDAT = aEDAT;
	}

	public String getEKORG() {
		return EKORG;
	}

	public void setEKORG(String eKORG) {
		EKORG = eKORG;
	}

	public MatRequest clone() throws CloneNotSupportedException {
		return (MatRequest) super.clone();
	}

	public String getMAKTX() {
		return MAKTX;
	}

	public void setMAKTX(String mAKTX) {
		MAKTX = mAKTX;
	}

	public String getMATKL() {
		return MATKL;
	}

	public void setMATKL(String mATKL) {
		MATKL = mATKL;
	}

	public String getSORTL() {
		return SORTL;
	}

	public void setSORTL(String sORTL) {
		SORTL = sORTL;
	}

	public String getZEINR() {
		return ZEINR;
	}

	public void setZEINR(String zEINR) {
		ZEINR = zEINR;
	}

	public String getEKGRP() {
		return EKGRP;
	}

	public void setEKGRP(String eKGRP) {
		EKGRP = eKGRP;
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

	public String getAESZN() {
		return AESZN;
	}

	public void setAESZN(String aESZN) {
		AESZN = aESZN;
	}

	public int getBSTRF() {
		return BSTRF;
	}

	public void setBSTRF(int bSTRF) {
		BSTRF = bSTRF;
	}

	public String getSHPDT() {
		return SHPDT;
	}

	public void setSHPDT(String sHPDT) {
		SHPDT = sHPDT;
	}

}
