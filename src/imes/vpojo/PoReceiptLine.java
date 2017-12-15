package imes.vpojo;

public class PoReceiptLine {
	private int id = 0;
	private int po_receipt_id = 0;
	private String ebeln = "";
	private String ebelp = "";
	private String charg = "";
	private String mblnr = "";
	private String mjahr = "";
	private String zeile = "";
	private double menge = 0;
	private String mtype = "";
	private String mesge = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPo_receipt_id() {
		return po_receipt_id;
	}

	public void setPo_receipt_id(int po_receipt_id) {
		this.po_receipt_id = po_receipt_id;
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

	public String getCharg() {
		return charg;
	}

	public void setCharg(String charg) {
		this.charg = charg;
	}

	public String getMblnr() {
		return mblnr;
	}

	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}

	public String getMjahr() {
		return mjahr;
	}

	public void setMjahr(String mjahr) {
		this.mjahr = mjahr;
	}

	public String getZeile() {
		return zeile;
	}

	public void setZeile(String zeile) {
		this.zeile = zeile;
	}

	public double getMenge() {
		return menge;
	}

	public void setMenge(double menge) {
		this.menge = menge;
	}

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getMesge() {
		return mesge;
	}

	public void setMesge(String mesge) {
		this.mesge = mesge;
	}

}
