package imes.entity.eqpsys;

import imes.core.HtmlHelper;

import java.util.Date;

public class MTRMAS {
	private int id = 0; // id
	private String cmpnbr = ""; // 公司
	private String facnbr = ""; // 工廠
	private String matnbr = ""; // 物料號碼
	private int mattyp = 0; // 物料大類
	private int matgrp = 0; // 物料小類
	private String matsts = ""; // 物料狀態
	private String matdes = ""; // 物料說明
	private String matmdl = ""; // 物料型號
	private String matspe = ""; // 物料規格
	private String matbrd = ""; // 物料品牌
	private double safqty = 0; // 安全數量
	private double stkqty = 0; // 庫存數量
	private String matuom = ""; // 物料單位
	private String mfgnam = ""; // 生產廠家
	private String locate = ""; // 放置位置
	private String repdep = ""; // 保管部門
	private String repuid = ""; // 保管人
	private Date crtdat = new Date(); // 建立时间
	private String crtuid = ""; // 建立用户
	private Date chgdat = new Date(); // 更改时间
	private String chguid = ""; // 更改用户

	/*
	 * Handle jsp select & checked
	 */

	public String getRadioFacnbr() {
		return HtmlHelper.inputRadio("facnbr", facnbr, BASDAT.facs());
	}

	public String getRadioCmpnbr() {
		return HtmlHelper.inputRadio("cmpnbr", cmpnbr, BASDAT.cmps());
	}

	public String getRadioMatuom() {
		return HtmlHelper.inputRadio("matuom", matuom, BASDAT.uoms());
	}

	/*
	 * java pojo
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

	public String getMatnbr() {
		return matnbr;
	}

	public void setMatnbr(String matnbr) {
		this.matnbr = matnbr;
	}

	public String getMatsts() {
		return matsts;
	}

	public void setMatsts(String matsts) {
		this.matsts = matsts;
	}

	public String getMatdes() {
		return matdes;
	}

	public void setMatdes(String matdes) {
		this.matdes = matdes;
	}

	public String getMatmdl() {
		return matmdl;
	}

	public void setMatmdl(String matmdl) {
		this.matmdl = matmdl;
	}

	public String getMatspe() {
		return matspe;
	}

	public void setMatspe(String matspe) {
		this.matspe = matspe;
	}

	public String getMatbrd() {
		return matbrd;
	}

	public void setMatbrd(String matbrd) {
		this.matbrd = matbrd;
	}

	public double getSafqty() {
		return safqty;
	}

	public void setSafqty(double safqty) {
		this.safqty = safqty;
	}

	public double getStkqty() {
		return stkqty;
	}

	public void setStkqty(double stkqty) {
		this.stkqty = stkqty;
	}

	public String getMatuom() {
		return matuom;
	}

	public void setMatuom(String matuom) {
		this.matuom = matuom;
	}

	public String getMfgnam() {
		return mfgnam;
	}

	public void setMfgnam(String mfgnam) {
		this.mfgnam = mfgnam;
	}

	public String getLocate() {
		return locate;
	}

	public void setLocate(String locate) {
		this.locate = locate;
	}

	public String getRepdep() {
		return repdep;
	}

	public void setRepdep(String repdep) {
		this.repdep = repdep;
	}

	public String getRepuid() {
		return repuid;
	}

	public void setRepuid(String repuid) {
		this.repuid = repuid;
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

	public int getMattyp() {
		return mattyp;
	}

	public void setMattyp(int mattyp) {
		this.mattyp = mattyp;
	}

	public int getMatgrp() {
		return matgrp;
	}

	public void setMatgrp(int matgrp) {
		this.matgrp = matgrp;
	}

}
