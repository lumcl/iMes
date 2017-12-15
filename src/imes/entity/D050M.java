package imes.entity;

import java.util.Date;

public class D050M {
	private int id = 0; // ID
	private String bdbh = ""; // 表單編碼
	private String aufnr = ""; // 工單
	private String werks = ""; // 工廠
	private String matnr = ""; // 料號
	private String matkl = ""; // 類別
	private String maktx = ""; // 規格
	private String meins = ""; // 單位
	private double bomqty = 0; // 單位用量
	private double reqqty = 0; // 需求用量
	private double stkqty = 0; // 庫存數量
	private double posqty = 0; // 採購數量
	private String ebeln = ""; // 採購訂單
	private String ebelp = ""; // 採購行號
	private String emeng = ""; // 採購數量
	private double cfmqty = 0; // 確認數量
	private String cfmusr = ""; // 確認用戶
	private String buyer = ""; // 採購員
	private String loekz = ""; // 刪除標誌
	private String notes = ""; // 文字
	private double usdpr = 0; // USD價格
	private double usdam = 0; // USD金額
	private String jlyh = ""; // 建立用戶
	private Date jlsj = null; // 建立時間
	private String gxyh = ""; // 更新用戶
	private Date gxsj = null; // 更新時間

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

	public String getMeins() {
		return meins;
	}

	public void setMeins(String meins) {
		this.meins = meins;
	}

	public double getBomqty() {
		return bomqty;
	}

	public void setBomqty(double bomqty) {
		this.bomqty = bomqty;
	}

	public double getReqqty() {
		return reqqty;
	}

	public void setReqqty(double reqqty) {
		this.reqqty = reqqty;
	}

	public double getStkqty() {
		return stkqty;
	}

	public void setStkqty(double stkqty) {
		this.stkqty = stkqty;
	}

	public double getPosqty() {
		return posqty;
	}

	public void setPosqty(double posqty) {
		this.posqty = posqty;
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

	public String getEmeng() {
		return emeng;
	}

	public void setEmeng(String emeng) {
		this.emeng = emeng;
	}

	public double getCfmqty() {
		return cfmqty;
	}

	public void setCfmqty(double cfmqty) {
		this.cfmqty = cfmqty;
	}

	public String getCfmusr() {
		return cfmusr;
	}

	public void setCfmusr(String cfmusr) {
		this.cfmusr = cfmusr;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getLoekz() {
		return loekz;
	}

	public void setLoekz(String loekz) {
		this.loekz = loekz;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public String getJlyh() {
		return jlyh;
	}

	public void setJlyh(String jlyh) {
		this.jlyh = jlyh;
	}

	public Date getJlsj() {
		return jlsj;
	}

	public void setJlsj(Date jlsj) {
		this.jlsj = jlsj;
	}

	public String getGxyh() {
		return gxyh;
	}

	public void setGxyh(String gxyh) {
		this.gxyh = gxyh;
	}

	public Date getGxsj() {
		return gxsj;
	}

	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}

}
