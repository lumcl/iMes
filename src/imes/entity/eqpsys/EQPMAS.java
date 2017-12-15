package imes.entity.eqpsys;

import imes.core.AppConst;

import java.util.Date;
import java.util.HashMap;

public class EQPMAS {
	private int id = 0; // id
	private String cmpnbr = ""; // 公司
	private String facnbr = ""; // 工廠
	private String eqpnbr = ""; // 物件號碼 (唯一碼)
	private String eqpdes = ""; // 物件說明
	private String eqpmdl = ""; // 物件型號
	private String eqpspc = ""; // 物件規格
	private String eqpsts = ""; // 物件狀態
	private String astnbr = ""; // 財產編號
	private String eqptyp = ""; // 物件大類
	private String eqpgrp = ""; // 物件小類
	private String fixtyp = ""; // 固定資產 / 列管資產
	private String duedat = ""; // 預計資產到期日
	private String locate = ""; // 放置位置
	private String rspdep = ""; // 保管部門
	private String rspuid = ""; // 保管人
	private double eqpqty = 1; // 數量
	private String eqpuom = ""; // 單位
	private String locker = ""; // 治具放置櫃
	private String prdnbr = ""; // 使用機種
	private String prddes = ""; // 使用機種說明
	private String pcbnbr = ""; // PCB板號
	private String compnr = ""; // 組件
	private String compmd = ""; // 組件型號
	private String serial = ""; // 機身編號
	private String prdlin = ""; // 線別
	private String strmnt = ""; // 開始保養日期
	private int mntcyc = 0; // 保養週期 (天)
	private String mntdat = ""; // 保養日期
	private String remark = ""; // 備註
	private String mfgnam = ""; // 生產廠家
	private String brand = ""; // 品牌
	private String buyer = ""; // 購買者
	private String purdat = ""; // 購買日期
	private double rmbamt = 0; // 購買價格 (RMB)
	private String purdoc = ""; // 購買參考文件
	private String appdoc = ""; // 簽呈編號
	private String iqcuid = ""; // 驗收人員
	private String iqcdat = ""; // 驗收日期
	private String wtydue = ""; // 質保到期日
	private String vendor = ""; // 供應商編號
	private String vdrnam = ""; // 供應商公司名稱
	private String vdrcnt = ""; // 供應商聯繫人
	private String vdradd = ""; // 供應商聯繫地址
	private String vdrphn = ""; // 供應商聯繫電話
	private Date crtdat = new Date(); // 建立時間
	private String crtuid = ""; // 建立用戶
	private Date chgdat = new Date(); // 更改時間
	private String chguid = ""; // 更改用戶

	/*
	 * Handle jsp select & checked
	 */

	public String isCmpnbr(String val) {
		return cmpnbr.equals(val) ? AppConst.CHECKED : "";
	}

	public String isFacnbr(String val) {
		return facnbr.equals(val) ? AppConst.CHECKED : "";
	}

	public String isEqptyp(String val) {
		return eqptyp.equals(val) ? AppConst.SELECTED : "";
	}

	public String isEqpsts(String val) {
		return eqpsts == null ? "" : eqpsts.equals(val) ? AppConst.CHECKED : "";
	}

	public String isFixtyp(String val) {
		return fixtyp == null ? "" : fixtyp.equals(val) ? AppConst.CHECKED : "";
	}

	public String isEqpiom(String val) {
		return eqpuom.equals(val) ? AppConst.CHECKED : "";
	}

	public static String getEqpstsTxt(String eqpsts) {

		if (eqpsts.equals("OK"))
			return "OK 良好";
		else if (eqpsts.equals("NG"))
			return "NG 不良";
		else if (eqpsts.equals("MT"))
			return "MT 保養";
		else if (eqpsts.equals("RP"))
			return "RP 送修";
		else if (eqpsts.equals("SC"))
			return "SC 報廢";
		else if (eqpsts.equals("LC"))
			return "LS 遺失";
		else if (eqpsts.equals("LN"))
			return "LN 借出";
		else
			return eqpsts;
	}
	
	public static HashMap<String, String> eqptyp(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("A", "A ATE");
		map.put("H", "H 耐壓治具");
		map.put("I", "I ICT");
		map.put("T", "T 特性治具");
		map.put("W", "W 外觀治具");
		map.put("Z", "Z 過錫爐治具");
		return map;
	}

	/*
	 * Standard POJO
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCmpnbr() {
		return cmpnbr;
	}

	public void setCmpnbr(String cmpnbr) {
		this.cmpnbr = cmpnbr;
	}

	public String getFacnbr() {
		return facnbr;
	}

	public void setFacnbr(String facnbr) {
		this.facnbr = facnbr;
	}

	public String getEqpnbr() {
		return eqpnbr;
	}

	public void setEqpnbr(String eqpnbr) {
		this.eqpnbr = eqpnbr;
	}

	public String getEqpdes() {
		return eqpdes;
	}

	public void setEqpdes(String eqpdes) {
		this.eqpdes = eqpdes;
	}

	public String getEqpmdl() {
		return eqpmdl;
	}

	public void setEqpmdl(String eqpmdl) {
		this.eqpmdl = eqpmdl;
	}

	public String getEqpspc() {
		return eqpspc;
	}

	public void setEqpspc(String eqpspc) {
		this.eqpspc = eqpspc;
	}

	public String getEqpsts() {
		return eqpsts;
	}

	public void setEqpsts(String eqpsts) {
		this.eqpsts = eqpsts;
	}

	public String getAstnbr() {
		return astnbr;
	}

	public void setAstnbr(String astnbr) {
		this.astnbr = astnbr;
	}

	public String getEqptyp() {
		return eqptyp;
	}

	public void setEqptyp(String eqptyp) {
		this.eqptyp = eqptyp;
	}

	public String getEqpgrp() {
		return eqpgrp;
	}

	public void setEqpgrp(String eqpgrp) {
		this.eqpgrp = eqpgrp;
	}

	public String getFixtyp() {
		return fixtyp;
	}

	public void setFixtyp(String fixtyp) {
		this.fixtyp = fixtyp;
	}

	public String getDuedat() {
		return duedat;
	}

	public void setDuedat(String duedat) {
		this.duedat = duedat;
	}

	public String getLocate() {
		return locate;
	}

	public void setLocate(String locate) {
		this.locate = locate;
	}

	public String getRspdep() {
		return rspdep;
	}

	public void setRspdep(String rspdep) {
		this.rspdep = rspdep;
	}

	public String getRspuid() {
		return rspuid;
	}

	public void setRspuid(String rspuid) {
		this.rspuid = rspuid;
	}

	public double getEqpqty() {
		return eqpqty;
	}

	public void setEqpqty(double eqpqty) {
		this.eqpqty = eqpqty;
	}

	public String getEqpuom() {
		return eqpuom;
	}

	public void setEqpuom(String eqpuom) {
		this.eqpuom = eqpuom;
	}

	public String getLocker() {
		return locker;
	}

	public void setLocker(String locker) {
		this.locker = locker;
	}

	public String getPrdnbr() {
		return prdnbr;
	}

	public void setPrdnbr(String prdnbr) {
		this.prdnbr = prdnbr;
	}

	public String getPrddes() {
		return prddes;
	}

	public void setPrddes(String prddes) {
		this.prddes = prddes;
	}

	public String getPcbnbr() {
		return pcbnbr;
	}

	public void setPcbnbr(String pcbnbr) {
		this.pcbnbr = pcbnbr;
	}

	public String getCompnr() {
		return compnr;
	}

	public void setCompnr(String compnr) {
		this.compnr = compnr;
	}

	public String getCompmd() {
		return compmd;
	}

	public void setCompmd(String compmd) {
		this.compmd = compmd;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getPrdlin() {
		return prdlin;
	}

	public void setPrdlin(String prdlin) {
		this.prdlin = prdlin;
	}

	public String getStrmnt() {
		return strmnt;
	}

	public void setStrmnt(String strmnt) {
		this.strmnt = strmnt;
	}

	public int getMntcyc() {
		return mntcyc;
	}

	public void setMntcyc(int mntcyc) {
		this.mntcyc = mntcyc;
	}

	public String getMntdat() {
		return mntdat;
	}

	public void setMntdat(String mntdat) {
		this.mntdat = mntdat;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMfgnam() {
		return mfgnam;
	}

	public void setMfgnam(String mfgnam) {
		this.mfgnam = mfgnam;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getPurdat() {
		return purdat;
	}

	public void setPurdat(String purdat) {
		this.purdat = purdat;
	}

	public double getRmbamt() {
		return rmbamt;
	}

	public void setRmbamt(double rmbamt) {
		this.rmbamt = rmbamt;
	}

	public String getPurdoc() {
		return purdoc;
	}

	public void setPurdoc(String purdoc) {
		this.purdoc = purdoc;
	}

	public String getAppdoc() {
		return appdoc;
	}

	public void setAppdoc(String appdoc) {
		this.appdoc = appdoc;
	}

	public String getIqcuid() {
		return iqcuid;
	}

	public void setIqcuid(String iqcuid) {
		this.iqcuid = iqcuid;
	}

	public String getIqcdat() {
		return iqcdat;
	}

	public void setIqcdat(String iqcdat) {
		this.iqcdat = iqcdat;
	}

	public String getWtydue() {
		return wtydue;
	}

	public void setWtydue(String wtydue) {
		this.wtydue = wtydue;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVdrnam() {
		return vdrnam;
	}

	public void setVdrnam(String vdrnam) {
		this.vdrnam = vdrnam;
	}

	public String getVdrcnt() {
		return vdrcnt;
	}

	public void setVdrcnt(String vdrcnt) {
		this.vdrcnt = vdrcnt;
	}

	public String getVdradd() {
		return vdradd;
	}

	public void setVdradd(String vdradd) {
		this.vdradd = vdradd;
	}

	public String getVdrphn() {
		return vdrphn;
	}

	public void setVdrphn(String vdrphn) {
		this.vdrphn = vdrphn;
	}

	public Date getCrtdat() {
		return crtdat;
	}

	public void setCrtdat(Date crtdat) {
		this.crtdat = crtdat;
	}

	public String getCrtuid() {
		return crtuid;
	}

	public void setCrtuid(String crtuid) {
		this.crtuid = crtuid;
	}

	public Date getChgdat() {
		return chgdat;
	}

	public void setChgdat(Date chgdat) {
		this.chgdat = chgdat;
	}

	public String getChguid() {
		return chguid;
	}

	public void setChguid(String chguid) {
		this.chguid = chguid;
	}

}
