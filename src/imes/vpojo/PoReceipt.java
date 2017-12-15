package imes.vpojo;

public class PoReceipt {

	private int id = 0;
	private String lifnr = "";
	private String refdn = "";
	private String imbil = "";
	private String matnr = "";
	private String werks = "";
	private String licha = "";
	private String charg = "";
	private String hsdat = "";
	private double erfmg = 0;
	private String smdat = "";
	private String smtim = "";
	private double allocQty = 0;
	private double remainQty = 0;

	private String ebeln = "";
	private String ebelp = "";
	private double menge = 0;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLifnr() {
		return lifnr;
	}

	public void setLifnr(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getRefdn() {
		return refdn;
	}

	public void setRefdn(String refdn) {
		this.refdn = refdn;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getLicha() {
		return licha;
	}

	public void setLicha(String licha) {
		this.licha = licha;
	}

	public String getHsdat() {
		return hsdat;
	}

	public void setHsdat(String hsdat) {
		this.hsdat = hsdat;
	}

	public double getErfmg() {
		return erfmg;
	}

	public void setErfmg(double erfmg) {
		this.erfmg = erfmg;
	}

	public String getSmdat() {
		return smdat;
	}

	public void setSmdat(String smdat) {
		this.smdat = smdat;
	}

	public String getSmtim() {
		return smtim;
	}

	public void setSmtim(String smtim) {
		this.smtim = smtim;
	}

	public double getAllocQty() {
		return allocQty;
	}

	public void setAllocQty(double allocQty) {
		this.allocQty = allocQty;
	}

	public double getRemainQty() {
		return remainQty;
	}

	public void setRemainQty(double remainQty) {
		this.remainQty = remainQty;
	}

	public String getImbil() {
		return imbil;
	}

	public void setImbil(String imbil) {
		this.imbil = imbil;
	}

	public String getCharg() {
		return charg;
	}

	public void setCharg(String charg) {
		this.charg = charg;
	}

	public String getEbeln() {
		return ebeln;
	}

	public void setEbeln(String ebeln) {
		this.ebeln = ebeln;
	}

	public String getEbelp() {
		return ebelp;
	}

	public void setEbelp(String ebelp) {
		this.ebelp = ebelp;
	}

	public double getMenge() {
		return menge;
	}

	public void setMenge(double menge) {
		this.menge = menge;
	}

}
