package imes.entity;

import imes.core.Helper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

public class D045L {
	
	private String GSDM = ""; //    -- 公司代码
	private String BDDM = ""; //    -- 表单代码
	private String BDBH = ""; //   -- 表单编号
	private int SQNR = 0; //      -- 项数  
	private String CMATNR = ""; //  -- 元件 
	private String CWERKS = ""; //   -- 厂别 
	private String CMATKL = ""; //    -- 采购分类 
	private String CMAKTX = ""; //   -- 规格 
	private String CCUM = ""; //   -- 单位 
	private double CCQTY = 0; //  -- 数量 
	private double CPRICE = 0; //   -- 单价 
	private String CCURR = ""; //   -- 货币 
	private double CWEIGHT = 0; //   -- 单重 
	private String JLYH = ""; //
	private Date JLSJ = null;
	private String GXYH = ""; //
	private Date GXSJ = null;

	public int insertDb(Connection conn) throws Exception{
		int i = 0;
		String sql = "INSERT INTO D045L(GSDM, BDDM, BDBH, SQNR, CMATNR, CWERKS, CMATKL, CMAKTX, CCUM, CCQTY, CPRICE, CCURR, CWEIGHT, JLYH, JLSJ, GXYH, GXSJ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstm = conn.prepareStatement(sql);
		pstm.setString(1,GSDM);
		pstm.setString(2,BDDM);
		pstm.setString(3,BDBH);
		pstm.setInt(4,SQNR);
		pstm.setString(5,CMATNR);
		pstm.setString(6,CWERKS);
		pstm.setString(7,CMATKL);
		pstm.setString(8,CMAKTX);
		pstm.setString(9,CCUM);
		pstm.setDouble(10,CCQTY);
		pstm.setDouble(11,CPRICE);
		pstm.setString(12,CCURR);
		pstm.setDouble(13,CWEIGHT);
		pstm.setString(14,JLYH);
		pstm.setTimestamp(15,Helper.toSqlDate(JLSJ));
		pstm.setString(16,GXYH);
		pstm.setTimestamp(17,Helper.toSqlDate(GXSJ));
		i = pstm.executeUpdate();
		pstm.close();
		return i;
	}

	public String getGSDM() {
		return GSDM;
	}

	public void setGSDM(String gSDM) {
		GSDM = gSDM;
	}

	public String getBDDM() {
		return BDDM;
	}

	public void setBDDM(String bDDM) {
		BDDM = bDDM;
	}

	public String getBDBH() {
		return BDBH;
	}

	public void setBDBH(String bDBH) {
		BDBH = bDBH;
	}

	public int getSQNR() {
		return SQNR;
	}

	public void setSQNR(int sQNR) {
		SQNR = sQNR;
	}

	public String getCMATNR() {
		return CMATNR;
	}

	public void setCMATNR(String cMATNR) {
		CMATNR = cMATNR;
	}

	public String getCWERKS() {
		return CWERKS;
	}

	public void setCWERKS(String cWERKS) {
		CWERKS = cWERKS;
	}

	public String getCMATKL() {
		return CMATKL;
	}

	public void setCMATKL(String cMATKL) {
		CMATKL = cMATKL;
	}

	public String getCMAKTX() {
		return CMAKTX;
	}

	public void setCMAKTX(String cMAKTX) {
		CMAKTX = cMAKTX;
	}

	public String getCCUM() {
		return CCUM;
	}

	public void setCCUM(String cCUM) {
		CCUM = cCUM;
	}

	public double getCCQTY() {
		return CCQTY;
	}

	public void setCCQTY(double cCQTY) {
		CCQTY = cCQTY;
	}

	public double getCPRICE() {
		return CPRICE;
	}

	public void setCPRICE(double cPRICE) {
		CPRICE = cPRICE;
	}

	public String getCCURR() {
		return CCURR;
	}

	public void setCCURR(String cCURR) {
		CCURR = cCURR;
	}

	public double getCWEIGHT() {
		return CWEIGHT;
	}

	public void setCWEIGHT(double cWEIGHT) {
		CWEIGHT = cWEIGHT;
	}

	public String getJLYH() {
		return JLYH;
	}

	public void setJLYH(String jLYH) {
		JLYH = jLYH;
	}

	public Date getJLSJ() {
		return JLSJ;
	}

	public void setJLSJ(Date jLSJ) {
		JLSJ = jLSJ;
	}

	public String getGXYH() {
		return GXYH;
	}

	public void setGXYH(String gXYH) {
		GXYH = gXYH;
	}

	public Date getGXSJ() {
		return GXSJ;
	}

	public void setGXSJ(Date gXSJ) {
		GXSJ = gXSJ;
	}
}
