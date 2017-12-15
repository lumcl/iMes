package imes.entity;

public class D050P {
	private int id = 0; // ID
	private String bdbh = ""; // 表單編碼
	private String aufnr = ""; // 工單
	private String werks = ""; // 工廠
	private String matnr = ""; // 料號
	private String matkl = ""; // 類別
	private String maktx = ""; // 規格
	private double menge = 0; // 數量
	private String meins = ""; // 單位
	private String duedt = ""; // 到期日期
	private double matam = 0; //

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBdbh() {
		return bdbh;
	}

	public void setBdbh(String bdbh) {
		this.bdbh = bdbh;
	}

	public String getAufnr() {
		return aufnr;
	}

	public void setAufnr(String aufnr) {
		this.aufnr = aufnr;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getMatkl() {
		return matkl;
	}

	public void setMatkl(String matkl) {
		this.matkl = matkl;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public double getMenge() {
		return menge;
	}

	public void setMenge(double menge) {
		this.menge = menge;
	}

	public String getMeins() {
		return meins;
	}

	public void setMeins(String meins) {
		this.meins = meins;
	}

	public String getDuedt() {
		return duedt;
	}

	public void setDuedt(String duedt) {
		this.duedt = duedt;
	}

	public double getMatam() {
		return matam;
	}

	public void setMatam(double matam) {
		this.matam = matam;
	}

}
