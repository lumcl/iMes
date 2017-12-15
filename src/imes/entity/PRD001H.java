package imes.entity;

import java.util.Date;

public class PRD001H {
	private int id = 0; // id
	private String line = ""; // 生产线别
	private String mo = ""; // MO编号
	private long factqty = 0; // 实际数量
	private long planqty = 0; // 计划数量
	private Date scsj = null; // 生产时间
	private Date jlsj = null; // 建立時間
	private Date gxsj = null; // 更新時間
	private String jlyh = ""; // 建立用戶
	private String gxyh = ""; // 更新用戶
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getMo() {
		return mo;
	}

	public void setMo(String mo) {
		this.mo = mo;
	}

	public long getFactqty() {
		return factqty;
	}

	public void setFactqty(long factqty) {
		this.factqty = factqty;
	}

	public long getPlanqty() {
		return planqty;
	}

	public void setPlanqty(long planqty) {
		this.planqty = planqty;
	}

	public Date getScsj() {
		return scsj;
	}

	public void setScsj(Date scsj) {
		this.scsj = scsj;
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
