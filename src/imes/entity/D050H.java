package imes.entity;

import java.util.Date;

public class D050H {
	private int id = 0; // ID
	private String gsdm = ""; // 公司代碼
	private String bddm = ""; // 表單代碼
	private String bdbh = ""; // 表單編碼
	private String bdrq = ""; // 表單日期
	private String bdzt = "C"; // 表單狀態 C=建立 0=簽核中 X=完成
	private String bdjg = ""; // 表單結果 Y=核准 N=否決 X=退件
	private String bdfd = ""; // 表單附件
	private String bdyy = ""; // 表單原因
	private String sqyh = ""; // 申請用戶
	private String qhyh = ""; // 表單核准人
	private Date qhsj = null; // 表單核准時間
	private String qhks = ""; // 流程開始標示
	private String hqyh = ""; // 會簽用戶
	private String jlyh = ""; // 建立用戶
	private Date jlsj = null; // 建立時間
	private String gxyh = ""; // 更新用戶
	private Date gxsj = null; // 更新時間
	private String kunnr = ""; // 客戶代號
	private String name1 = ""; // 客戶名稱
	private String sortl = ""; // 客戶簡稱
	private double salam = 0; // 銷售金額
	private double matam = 0; // 材料金額

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGsdm() {
		return gsdm;
	}

	public void setGsdm(String gsdm) {
		this.gsdm = gsdm;
	}

	public String getBddm() {
		return bddm;
	}

	public void setBddm(String bddm) {
		this.bddm = bddm;
	}

	public String getBdbh() {
		return bdbh;
	}

	public void setBdbh(String bdbh) {
		this.bdbh = bdbh;
	}

	public String getBdrq() {
		return bdrq;
	}

	public void setBdrq(String bdrq) {
		this.bdrq = bdrq;
	}

	public String getBdzt() {
		return bdzt;
	}

	public void setBdzt(String bdzt) {
		this.bdzt = bdzt;
	}

	public String getBdjg() {
		return bdjg;
	}

	public void setBdjg(String bdjg) {
		this.bdjg = bdjg;
	}

	public String getBdfd() {
		return bdfd;
	}

	public void setBdfd(String bdfd) {
		this.bdfd = bdfd;
	}

	public String getBdyy() {
		return bdyy;
	}

	public void setBdyy(String bdyy) {
		this.bdyy = bdyy;
	}

	public String getSqyh() {
		return sqyh;
	}

	public void setSqyh(String sqyh) {
		this.sqyh = sqyh;
	}

	public String getQhyh() {
		return qhyh;
	}

	public void setQhyh(String qhyh) {
		this.qhyh = qhyh;
	}

	public Date getQhsj() {
		return qhsj;
	}

	public void setQhsj(Date qhsj) {
		this.qhsj = qhsj;
	}

	public String getQhks() {
		return qhks;
	}

	public void setQhks(String qhks) {
		this.qhks = qhks;
	}

	public String getHqyh() {
		return hqyh;
	}

	public void setHqyh(String hqyh) {
		this.hqyh = hqyh;
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

	public String getKunnr() {
		return kunnr;
	}

	public void setKunnr(String kunnr) {
		this.kunnr = kunnr;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getSortl() {
		return sortl;
	}

	public void setSortl(String sortl) {
		this.sortl = sortl;
	}

	public double getSalam() {
		return salam;
	}

	public void setSalam(double salam) {
		this.salam = salam;
	}

	public double getMatam() {
		return matam;
	}

	public void setMatam(double matam) {
		this.matam = matam;
	}

	public String getBdztText() {
		String bdztText = "";
		if (bdzt.equals("C"))
			bdztText = "建立";
		else if (bdzt.equals("0"))
			bdztText = "簽核中";
		else if (bdrq.equals("X"))
			bdztText = "完成";
		else
			bdztText = "未定義";
		return bdztText;
	}

	public String getBdjgText() {
		String bdjgText = "";
		if (bdjg.equals("Y"))
			bdjgText = "核准";
		else if (bdjg.equals("N"))
			bdjgText = "否決";
		else if (bdjg.equals("X"))
			bdjgText = "退件";
		else
			bdjgText = "";
		return bdjgText;
	}

}
