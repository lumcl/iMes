package imes.entity;

import imes.core.AppConst;

import java.util.Date;

public class Q001L {
	private int id = 0; // id
	private int q001h_id = 0; // Q001H ID
	private String bdbh = ""; // 表單號碼
	private int sqnr = 0; // 序列
	private String lfxm = ""; // 姓名
	private String sex = ""; // 性別
	private String zjlx = ""; // 證件類型
	private String zjhm = ""; // 證件號碼
	private String lfdw = ""; // 來訪單位
	private String lfzw = ""; // 職位
	private String lfdh = ""; // 電話
	private String lfyj = ""; // 郵件
	private String addr = ""; // 地址
	private String sdrq = ""; // 實際到達日期
	private String sdsj = ""; // 實際到達時間
	private String slrq = ""; // 實際離開日期
	private String slsj = ""; // 實際離開時間
	private String cphm = ""; // 車牌號碼
	private String hghm = ""; // 貨櫃號碼
	private String lfbz = ""; // 備註
	private String lffd = ""; // 附檔
	private Date jlsj = null; // 建立時間
	private Date gxsj = null; // 更新時間
	private String jlyh = ""; // 建立用戶
	private String gxyh = ""; // 更新用戶

	public String isZjlx(String val) {
		return zjlx == null ? "" : zjlx.contains(val) ? AppConst.CHECKED : "";
	}
	
	public String isSex(String val) {
		return zjlx == null ? "" : sex.contains(val) ? AppConst.CHECKED : "";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQ001h_id() {
		return q001h_id;
	}

	public void setQ001h_id(int q001h_id) {
		this.q001h_id = q001h_id;
	}

	public String getBdbh() {
		return bdbh;
	}

	public void setBdbh(String bdbh) {
		this.bdbh = bdbh;
	}

	public int getSqnr() {
		return sqnr;
	}

	public void setSqnr(int sqnr) {
		this.sqnr = sqnr;
	}

	public String getLfxm() {
		return lfxm;
	}

	public void setLfxm(String lfxm) {
		this.lfxm = lfxm;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getZjlx() {
		return zjlx;
	}

	public void setZjlx(String zjlx) {
		this.zjlx = zjlx;
	}

	public String getZjhm() {
		return zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getLfdw() {
		return lfdw;
	}

	public void setLfdw(String lfdw) {
		this.lfdw = lfdw;
	}

	public String getLfzw() {
		return lfzw;
	}

	public void setLfzw(String lfzw) {
		this.lfzw = lfzw;
	}

	public String getLfdh() {
		return lfdh;
	}

	public void setLfdh(String lfdh) {
		this.lfdh = lfdh;
	}

	public String getLfyj() {
		return lfyj;
	}

	public void setLfyj(String lfyj) {
		this.lfyj = lfyj;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
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

	public String getCphm() {
		return cphm;
	}

	public void setCphm(String cphm) {
		this.cphm = cphm;
	}

	public String getHghm() {
		return hghm;
	}

	public void setHghm(String hghm) {
		this.hghm = hghm;
	}

	public String getLfbz() {
		return lfbz;
	}

	public void setLfbz(String lfbz) {
		this.lfbz = lfbz;
	}

	public String getLffd() {
		return lffd;
	}

	public void setLffd(String lffd) {
		this.lffd = lffd;
	}

	public Date getJlsj() {
		return jlsj;
	}

	public void setJlsj(Date jlsj) {
		this.jlsj = jlsj;
	}

	public Date getGxsj() {
		return gxsj;
	}

	public void setGxsj(Date gxsj) {
		this.gxsj = gxsj;
	}

	public String getJlyh() {
		return jlyh;
	}

	public void setJlyh(String jlyh) {
		this.jlyh = jlyh;
	}

	public String getGxyh() {
		return gxyh;
	}

	public void setGxyh(String gxyh) {
		this.gxyh = gxyh;
	}

}
