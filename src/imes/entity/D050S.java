package imes.entity;

public class D050S {
	private int id = 0; // ID
	private String bdbh = ""; // 表單編碼
	private String vbeln = ""; // 訂單號
	private String posnr = ""; // 行號
	private String werks = ""; // 工廠
	private String matnr = ""; // 料號
	private String matkl = ""; // 類別
	private String maktx = ""; // 規格
	private double menge = 0; // 數量
	private String meins = ""; // 單位
	private String waerk = ""; // 幣別
	private double netpr = 0; // 單價
	private double netwr = 0; // 金額
	private double exrat = 0; // 匯率
	private double usdpr = 0; // USD價格
	private double usdam = 0; // USD金額
	private String duedt = ""; // 到期日期

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

	public String getVbeln() {
		return vbeln;
	}

	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}

	public String getPosnr() {
		return posnr;
	}

	public void setPosnr(String posnr) {
		this.posnr = posnr;
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

	public String getWaerk() {
		return waerk;
	}

	public void setWaerk(String waerk) {
		this.waerk = waerk;
	}

	public double getNetpr() {
		return netpr;
	}

	public void setNetpr(double netpr) {
		this.netpr = netpr;
	}

	public double getNetwr() {
		return netwr;
	}

	public void setNetwr(double netwr) {
		this.netwr = netwr;
	}

	public double getExrat() {
		return exrat;
	}

	public void setExrat(double exrat) {
		this.exrat = exrat;
	}

	public double getUsdpr() {
		return usdpr;
	}

	public void setUsdpr(double usdpr) {
		this.usdpr = usdpr;
	}

	public double getUsdam() {
		return usdam;
	}

	public void setUsdam(double usdam) {
		this.usdam = usdam;
	}

	public String getDuedt() {
		return duedt;
	}

	public void setDuedt(String duedt) {
		this.duedt = duedt;
	}

}
