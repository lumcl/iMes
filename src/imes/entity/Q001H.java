package imes.entity;

import imes.core.AppConst;

import java.text.DecimalFormat;
import java.util.Date;

public class Q001H {

	public static String CHECKED = "checked=\"checked\"";

	private int id = 0; // ID
	private String gsdm = ""; // 公司代碼
	private String bddm = ""; // 表單代碼
	private String bdbh = ""; // 表單編碼
	private String bdrq = ""; // 表單日期
	private String bdzt = ""; // 表單狀態 C=建立 0=簽核中 9=取消 X=完成
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
	private String lflb = ""; // 來訪類別
	private String lfdw = ""; // 來訪單位
	private String lfdd = ""; // 來訪擔當
	private String ydrq = ""; // 預計到達日期
	private String ydsj = ""; // 預計到達時間
	private String ylrq = ""; // 預計離開日期
	private String ylsj = ""; // 預計離開時間
	private String lfmd = ""; // 來訪目的
	private String pbry = ""; // 陪伴人員
	private String gjzg = ""; // 高階主管
	private String hqrq = ""; // 會前會日期
	private String hqsj = ""; // 會前會時間
	private String bezu = ""; // 備註
	private String jcfs = ""; // 客戶進場方式
	private String jsdd = ""; // 接送地點
	private int bfrs = 0; // 拜訪人數
	private String jcdd = ""; // 就餐地點
	private double jcbz = 0; // 就餐標準
	private String jcsj = ""; // 就餐時間
	private String jcry = ""; // 就餐人員
	private String fygs = ""; // 就餐費用歸屬
	private String zsap = ""; // 住宿安排
	private int zsts = 0; // 住宿天數
	private String hyzy = ""; // 會議室資源
	private int xlfs = 0; // 公司型錄分數
	private String qtzy = ""; // 其他資源
	private String zwbz = ""; // 總務備註
	private String sdrq = ""; // 實際到達日期
	private String sdsj = ""; // 實際到達時間
	private String slrq = ""; // 實際離開日期
	private String slsj = ""; // 實際離開時間

	public String getBdztTxt() {
		return AppConst.getBdztText(bdzt);
	}
	
	public String getBdjgTxt() {
		return AppConst.getBdjgText(bdjg);
	}
	
	public String isLflb(String val) {
		return lflb == null ? "" : (lflb.contains(val)) ? CHECKED : "";
	}

	public String isJcfs(String val) {
		return jcfs == null ? "" : (jcfs.contains(val)) ? CHECKED : "";
	}

	public String isJcdd(String val) {
		return jcdd == null ? "" : (jcdd.contains(val)) ? CHECKED : "";
	}

	public String isZsap(String val) {
		return zsap == null ? "" : (zsap.contains(val)) ? CHECKED : "";
	}

	public String isHyzy(String val) {
		return hyzy == null ? "" : (hyzy.contains(val)) ? CHECKED : "";
	}

	public String isQtzy(String val) {
		return qtzy == null ? "" : (qtzy.contains(val)) ? CHECKED : "";
	}

	public String nbrfmt(int val) {
		return new DecimalFormat("00").format(val);
	}

	public void setYlsj(int xs, int fz) {
		this.ylsj = nbrfmt(xs) + nbrfmt(fz);
	}

	public void setYdsj(int xs, int fz) {
		this.ydsj = nbrfmt(xs) + nbrfmt(fz);
	}

	public void setHqsj(int xs, int fz) {
		this.hqsj = nbrfmt(xs) + nbrfmt(fz);
	}

	public void setSlsj(int xs, int fz) {
		this.slsj = nbrfmt(xs) + nbrfmt(fz);
	}

	public void setSdsj(int xs, int fz) {
		this.sdsj = nbrfmt(xs) + nbrfmt(fz);
	}

	public String getXs(String sj) {
		String str = "";
		try {
			str = sj.substring(0, 2);
		} catch (Exception ex) {
			str = "00";
		}
		return str;
	}

	public String getFz(String sj) {
		String str = "";
		try {
			str = sj.substring(2, 4);
		} catch (Exception ex) {
			str = "00";
		}
		return str;
	}

	public String getYlsj_xs() {
		return getXs(ylsj);
	}

	public String getYlsj_fz() {
		return getFz(ylsj);
	}

	public String getYdsj_xs() {
		return getXs(ydsj);
	}

	public String getYdsj_fz() {
		return getFz(ydsj);
	}

	public String getHqsj_xs() {
		return getXs(hqsj);
	}

	public String getHqsj_fz() {
		return getFz(hqsj);
	}

	public String getSlsj_xs() {
		return getXs(slsj);
	}

	public String getSlsj_fz() {
		return getFz(slsj);
	}

	public String getSdsj_xs() {
		return getXs(sdsj);
	}

	public String getSdsj_fz() {
		return getFz(sdsj);
	}

	/* Plain Old Java Object */

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

	public String getLflb() {
		return lflb;
	}

	public void setLflb(String lflb) {
		this.lflb = lflb;
	}

	public String getLfdw() {
		return lfdw;
	}

	public void setLfdw(String lfdw) {
		this.lfdw = lfdw;
	}

	public String getLfdd() {
		return lfdd;
	}

	public void setLfdd(String lfdd) {
		this.lfdd = lfdd;
	}

	public String getYdrq() {
		return ydrq;
	}

	public void setYdrq(String ydrq) {
		this.ydrq = ydrq;
	}

	public String getYdsj() {
		return ydsj;
	}

	public void setYdsj(String ydsj) {
		this.ydsj = ydsj;
	}

	public String getYlrq() {
		return ylrq;
	}

	public void setYlrq(String ylrq) {
		this.ylrq = ylrq;
	}

	public String getYlsj() {
		return ylsj;
	}

	public void setYlsj(String ylsj) {
		this.ylsj = ylsj;
	}

	public String getLfmd() {
		return lfmd;
	}

	public void setLfmd(String lfmd) {
		this.lfmd = lfmd;
	}

	public String getPbry() {
		return pbry;
	}

	public void setPbry(String pbry) {
		this.pbry = pbry;
	}

	public String getGjzg() {
		return gjzg;
	}

	public void setGjzg(String gjzg) {
		this.gjzg = gjzg;
	}

	public String getHqrq() {
		return hqrq;
	}

	public void setHqrq(String hqrq) {
		this.hqrq = hqrq;
	}

	public String getHqsj() {
		return hqsj;
	}

	public void setHqsj(String hqsj) {
		this.hqsj = hqsj;
	}

	public String getBezu() {
		return bezu;
	}

	public void setBezu(String bezu) {
		this.bezu = bezu;
	}

	public String getJcfs() {
		return jcfs;
	}

	public void setJcfs(String jcfs) {
		this.jcfs = jcfs;
	}

	public int getBfrs() {
		return bfrs;
	}

	public void setBfrs(int bfrs) {
		this.bfrs = bfrs;
	}

	public String getJcdd() {
		return jcdd;
	}

	public void setJcdd(String jcdd) {
		this.jcdd = jcdd;
	}

	public double getJcbz() {
		return jcbz;
	}

	public void setJcbz(double jcbz) {
		this.jcbz = jcbz;
	}

	public String getJcsj() {
		return jcsj;
	}

	public void setJcsj(String jcsj) {
		this.jcsj = jcsj;
	}

	public String getJcry() {
		return jcry;
	}

	public void setJcry(String jcry) {
		this.jcry = jcry;
	}

	public String getFygs() {
		return fygs;
	}

	public void setFygs(String fygs) {
		this.fygs = fygs;
	}

	public String getZsap() {
		return zsap;
	}

	public void setZsap(String zsap) {
		this.zsap = zsap;
	}

	public int getZsts() {
		return zsts;
	}

	public void setZsts(int zsts) {
		this.zsts = zsts;
	}

	public String getHyzy() {
		return hyzy;
	}

	public void setHyzy(String hyzy) {
		this.hyzy = hyzy;
	}

	public int getXlfs() {
		return xlfs;
	}

	public void setXlfs(int xlfs) {
		this.xlfs = xlfs;
	}

	public String getQtzy() {
		return qtzy;
	}

	public void setQtzy(String qtzy) {
		this.qtzy = qtzy;
	}

	public String getZwbz() {
		return zwbz;
	}

	public void setZwbz(String zwbz) {
		this.zwbz = zwbz;
	}

	public String getSdrq() {
		return sdrq;
	}

	public void setSdrq(String sdrq) {
		this.sdrq = sdrq;
	}

	public String getSdsj() {
		return sdsj;
	}

	public void setSdsj(String sdsj) {
		this.sdsj = sdsj;
	}

	public String getSlrq() {
		return slrq;
	}

	public void setSlrq(String slrq) {
		this.slrq = slrq;
	}

	public String getSlsj() {
		return slsj;
	}

	public void setSlsj(String slsj) {
		this.slsj = slsj;
	}

	public String getJsdd() {
		return jsdd;
	}

	public void setJsdd(String jsdd) {
		this.jsdd = jsdd;
	}

}
